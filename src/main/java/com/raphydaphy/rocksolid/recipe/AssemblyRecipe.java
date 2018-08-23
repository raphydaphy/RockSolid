package com.raphydaphy.rocksolid.recipe;

import com.raphydaphy.rocksolid.init.ModMisc;
import com.raphydaphy.rocksolid.init.ModRecipes;
import de.ellpeck.rockbottom.api.construction.BasicRecipe;
import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class AssemblyRecipe extends BasicRecipe
{
	public AssemblyRecipe(float skillReward, ItemInstance output, int baseAmount, int metalAmount, int fuelAmount)
	{
		super(output.getItem().getName(), skillReward, output, new ResUseInfo(ModMisc.RES_MACHINE_MATERIALS, baseAmount), new ResUseInfo(ModMisc.RES_ALL_INGOTS, metalAmount), new ResUseInfo(ModMisc.RES_ALL_FUELS, fuelAmount));
	}

	public AssemblyRecipe registerAssembly()
	{
		ModRecipes.ASSEMBLY_STATION_RECIPES.register(this.getName(), this);
		return this;
	}
}
