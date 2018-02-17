package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.recipe.SmelterRecipe;

public class ModRecipes
{
	public static void init()
	{
		SmelterRecipe.REGISTRY.add(new SmelterRecipe(ModItems.COPPER_GRIT, ModItems.COPPER_INGOT));
		SmelterRecipe.REGISTRY.add(new SmelterRecipe(ModItems.TIN_GRIT, ModItems.TIN_INGOT));
		SmelterRecipe.REGISTRY.add(new SmelterRecipe(ModItems.IRON_GRIT, ModItems.IRON_INGOT));
	}
}
