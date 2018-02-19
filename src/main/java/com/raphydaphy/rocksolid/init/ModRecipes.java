package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.recipe.AlloySmelterRecipe;
import com.raphydaphy.rocksolid.recipe.SeparatorRecipe;
import com.raphydaphy.rocksolid.recipe.SmelterRecipe;

public class ModRecipes
{
	public static void init()
	{
		SmelterRecipe.REGISTRY.add(new SmelterRecipe(ModItems.COPPER_GRIT, ModItems.COPPER_INGOT));
		SmelterRecipe.REGISTRY.add(new SmelterRecipe(ModItems.TIN_GRIT, ModItems.TIN_INGOT));
		SmelterRecipe.REGISTRY.add(new SmelterRecipe(ModItems.IRON_GRIT, ModItems.IRON_INGOT));

		SeparatorRecipe.REGISTRY.add(new SeparatorRecipe(ModItems.COPPER_CLUSTER, ModItems.COPPER_GRIT));
		SeparatorRecipe.REGISTRY.add(new SeparatorRecipe(ModItems.TIN_CLUSTER, ModItems.TIN_GRIT));
		SeparatorRecipe.REGISTRY.add(new SeparatorRecipe(ModItems.IRON_CLUSTER, ModItems.IRON_GRIT));

		AlloySmelterRecipe.REGISTRY.add(new AlloySmelterRecipe(ModItems.COPPER_INGOT, 3, ModItems.TIN_INGOT, 1, ModItems.BRONZE_INGOT, 4));
		AlloySmelterRecipe.REGISTRY.add(new AlloySmelterRecipe(ModItems.COPPER_GRIT, 3, ModItems.TIN_GRIT, 1, ModItems.BRONZE_GRIT, 4));
		AlloySmelterRecipe.REGISTRY.add(new AlloySmelterRecipe(ModItems.IRON_INGOT, 1, ModItems.COKE, 1, ModItems.STEEL_INGOT, 1));
	}
}
