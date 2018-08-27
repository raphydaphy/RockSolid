package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.tileentity.base.TileEntityElectric;
import com.raphydaphy.rocksolid.util.ModUtils;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.construction.smelting.SmeltingRecipe;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.TileInventory;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.ArrayList;
import java.util.Collections;

public class TileEntityElectricFurnace extends TileEntityElectric
{
	private static final String KEY_OUTPUT = "output";

	private final TileInventory inventory = new TileInventory(this, 2, (input) ->
	{
		ArrayList<Integer> avalableSlots = new ArrayList<>(1);
		if (ModUtils.getSmeltingRecipeSafe(input) != null)
		{
			avalableSlots.add(0);
		}
		return avalableSlots;
	}, Collections.singletonList(1));

	public ItemInstance output;

	public TileEntityElectricFurnace(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
		this.maxEnergyStored.set(2500);
	}

	@Override
	public TileInventory getTileInventory()
	{
		return this.inventory;
	}

	@Override
	public void save(DataSet set, boolean forSync)
	{
		super.save(set, forSync);
		getTileInventory().save(set);
		if (!forSync)
		{
			if (this.output != null)
			{
				DataSet tmpSet = new DataSet();
				this.output.save(tmpSet);
				set.addDataSet(KEY_OUTPUT, tmpSet);
			}
		}
	}

	@Override
	public void load(DataSet set, boolean forSync)
	{
		super.load(set, forSync);
		getTileInventory().load(set);
		if (!forSync)
		{
			if (set.hasKey(KEY_OUTPUT))
			{
				DataSet tepSet = set.getDataSet(KEY_OUTPUT);
				this.output = ItemInstance.load(tepSet);
			}
		}
	}

	@Override
	protected void getRecipeAndStart()
	{
		ItemInstance item;
		SmeltingRecipe smeltingRecipe;
		if ((item = this.getTileInventory().get(0)) != null && (smeltingRecipe = SmeltingRecipe.forInput(item)) != null)
		{
			IUseInfo input = smeltingRecipe.getInput();
			if (item.getAmount() >= input.getAmount())
			{
				item = smeltingRecipe.getOutput();
				ItemInstance curOutputSlot;
				if (((curOutputSlot = this.getTileInventory().get(1)) == null || curOutputSlot.isEffectivelyEqual(item) && curOutputSlot.fitsAmount(item.getAmount())))
				{
					this.output = item.copy();

					double chance = Math.pow(2, 5 * (getBonusYieldModifier() / 2f)); // Bonus Yield Modifier
					if ((Util.RANDOM.nextDouble() * 100) < chance && (curOutputSlot == null || curOutputSlot.fitsAmount(item.getAmount() + 1)))
					{
						this.output.addAmount(1);
					}

					this.maxSmeltTime.set((int) ((smeltingRecipe.getTime() / 2.5f) / getSpeedModifier())); // speed multiplier
					this.getTileInventory().remove(0, input.getAmount());
				}
			}
		}

		if (this.maxSmeltTime.get() <= 0)
		{
			this.output = null;
		}
	}

	@Override
	protected void putOutputItems()
	{
		ItemInstance outSlot;

		if ((outSlot = this.getTileInventory().get(1)) != null && outSlot.isEffectivelyEqual(this.output))
		{
			this.getTileInventory().add(1, this.output.getAmount());
		} else
		{
			this.getTileInventory().set(1, this.output);
		}

		this.output = null;
	}

	@Override
	public int getEnergyCapacity(IWorld world, Pos2 pos)
	{
		Pos2 innerCoord = ModUtils.innerCoord(world, pos);
		if (innerCoord != null && innerCoord.getY() == 1)
		{
			return 0;
		}
		return maxEnergyStored.get();
	}
}
