package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.recipe.SmelterRecipe;

public class ModRecipes
{
	public static void init()
	{
		SmelterRecipe.REGISTRY.add(new SmelterRecipe(ModItems.COPPER_CLUSTER, ModItems.COPPER_GRIT));
		SmelterRecipe.REGISTRY.add(new SmelterRecipe(ModItems.TIN_CLUSTER, ModItems.TIN_GRIT));
		SmelterRecipe.REGISTRY.add(new SmelterRecipe(ModItems.IRON_CLUSTER, ModItems.IRON_GRIT));
	}
}
