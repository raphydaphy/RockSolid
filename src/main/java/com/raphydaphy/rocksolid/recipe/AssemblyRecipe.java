package com.raphydaphy.rocksolid.recipe;

import com.raphydaphy.rocksolid.init.ModMisc;
import com.raphydaphy.rocksolid.init.ModRecipes;
import com.raphydaphy.rocksolid.tileentity.TileEntityAssemblyStation;
import com.raphydaphy.rocksolid.util.ModUtils;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.BasicRecipe;
import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.entity.AbstractEntityItem;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;

import java.util.Collections;
import java.util.List;

public class AssemblyRecipe extends BasicRecipe
{
	private boolean bonusYield = true;
	private ItemInstance output;

	public AssemblyRecipe(float skillReward, ItemInstance output, int baseAmount, int metalAmount, int fuelAmount)
	{
		super(output.getItem().getName(), skillReward, output, new ResUseInfo(ModMisc.RES_MACHINE_MATERIALS, baseAmount), new ResUseInfo(ModMisc.RES_ALL_INGOTS, metalAmount), new ResUseInfo(ModMisc.RES_ALL_FUELS, fuelAmount));
		this.output = output;
	}

	public AssemblyRecipe registerAssembly()
	{
		ModRecipes.ASSEMBLY_STATION_RECIPES.register(this.getName(), this);
		return this;
	}

	public AssemblyRecipe disableBonusYield()
	{
		bonusYield = false;
		return this;
	}

	public boolean hasBonusYield()
	{
		return bonusYield;
	}

	@Override
	public List<ItemInstance> getActualOutputs(IInventory inputInventory, IInventory outputInventory, List<ItemInstance> inputs)
	{
		ItemInstance nbtOut = output.copy();

		nbtOut.getOrCreateAdditionalData().addFloat(ModUtils.ASSEMBLY_CAPACITY_KEY, ModUtils.getAssemblyCapacity(inputs));
		nbtOut.getAdditionalData().addFloat(ModUtils.ASSEMBLY_EFFICIENCY_KEY, ModUtils.getAssemblyEfficiency(inputs));
		nbtOut.getAdditionalData().addFloat(ModUtils.ASSEMBLY_SPEED_KEY, ModUtils.getAssemblySpeed(inputs));

		if (hasBonusYield())
		{
			nbtOut.getAdditionalData().addFloat(ModUtils.ASSEMBLY_BONUS_KEY, ModUtils.getAssemblyBonusYield(inputs));
		}
		nbtOut.getAdditionalData().addFloat(ModUtils.ASSEMBLY_THROUGHPUT_KEY, ModUtils.getAssemblyThroughput(inputs));

		return Collections.singletonList(nbtOut);
	}

	public void playerConstruct(AbstractEntityPlayer player, TileEntityAssemblyStation station, int amount)
	{
		Inventory in = station.getTileInventory();
		Inventory out = player.getInv();
		List<ItemInstance> remains = RockBottomAPI.getApiHandler().construct(player, in, out, this, amount, this.getActualInputs(in), items -> this.getActualOutputs(in, out, items), this.getSkillReward());
		for (ItemInstance instance : remains)
		{
			AbstractEntityItem.spawn(player.world, instance, player.getX(), player.getY(), 0F, 0F);
		}
	}
}
