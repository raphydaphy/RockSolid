package com.raphydaphy.rocksolid.util;

import java.util.ArrayList;
import java.util.List;

import com.raphydaphy.rocksolid.recipe.AlloySmelterRecipe;

import de.ellpeck.rockbottom.api.item.ItemInstance;

public class RockSolidAPI {
	 public static final String VERSION = "0.0.8";
	 
	 public static final List<AlloySmelterRecipe> ALLOY_SMELTER_RECIPES = new ArrayList<>();
	 
	 public static AlloySmelterRecipe getAlloySmelterRecipe(ItemInstance input1, ItemInstance input2)
	 {
        for(AlloySmelterRecipe recipe : ALLOY_SMELTER_RECIPES){
            if(input1.isEffectivelyEqualWithWildcard(recipe.getInput1()) &&
            	input2.isEffectivelyEqualWithWildcard(recipe.getInput2()))
            {
                return recipe;
            }
        }
        return null;
    }
}
