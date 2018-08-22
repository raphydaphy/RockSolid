package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.recipe.AlloySmelterRecipe;
import com.raphydaphy.rocksolid.recipe.SeparatorRecipe;
import de.ellpeck.rockbottom.api.GameContent;

public class ModRecipes
{
	public static void init()
	{
		new SeparatorRecipe(GameContent.RES_COPPER_RAW, ModItems.COPPER_GRIT).register();
		new SeparatorRecipe(ModItems.RES_TIN_RAW, ModItems.TIN_GRIT).register();
		new SeparatorRecipe(ModItems.RES_IRON_RAW, ModItems.IRON_GRIT).register();

		new AlloySmelterRecipe(GameContent.RES_COPPER_PROCESSED, 3, ModItems.RES_TIN_PROCESSED, 1, ModItems.BRONZE_INGOT, 4).register();
		new AlloySmelterRecipe(ModItems.RES_COPPER_CRUSHED, 3, ModItems.RES_TIN_CRUSHED, 1, ModItems.BRONZE_GRIT, 4).register();
		new AlloySmelterRecipe(ModItems.RES_IRON_PROCESSED, 1, ModItems.RES_COAL_PROCESSED, 1, ModItems.STEEL_INGOT, 1).register();
	}
}
