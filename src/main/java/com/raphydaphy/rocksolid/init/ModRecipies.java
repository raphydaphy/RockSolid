package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.recipe.AlloySmelterRecipe;
import com.raphydaphy.rocksolid.util.RockSolidAPI;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.SeparatorRecipe;
import de.ellpeck.rockbottom.api.construction.SmelterRecipe;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class ModRecipies 
{
	public static void init()
	{
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.ingotBronze, 4), new ItemInstance(GameContent.ITEM_COPPER_INGOT, 3), new ItemInstance(ModItems.ingotTin, 1), 500));
		
		RockBottomAPI.SEPARATOR_RECIPES.add(new SeparatorRecipe(new ItemInstance(ModItems.gritTin, 2), new ItemInstance(ModItems.clusterTin), 350, new ItemInstance(GameContent.ITEM_SLAG), 0.25f));
		
		RockBottomAPI.SMELTER_RECIPES.add(new SmelterRecipe(new ItemInstance(ModItems.ingotTin), new ItemInstance(ModItems.gritTin), 300));
	}
}
