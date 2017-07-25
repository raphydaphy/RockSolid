package com.raphydaphy.rocksolid.api;

import java.util.ArrayList;
import java.util.List;

import com.raphydaphy.rocksolid.recipe.AlloySmelterRecipe;
import com.raphydaphy.rocksolid.recipe.BlastFurnaceRecipe;
import com.raphydaphy.rocksolid.recipe.CompressorRecipe;

import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.NameRegistry;

public class RockSolidAPI {
	 public static final String VERSION = "0.0.8";
	 
	 public static final List<AlloySmelterRecipe> ALLOY_SMELTER_RECIPES = new ArrayList<>();
	 public static final List<BlastFurnaceRecipe> BLAST_FURNACE_RECIPES = new ArrayList<>();
	 public static final List<CompressorRecipe> COMPRESSOR_RECIPES = new ArrayList<>();
	 
	 public static final NameRegistry<Fluid> FLUID_REGISTRY = new NameRegistry<>("fluid_registry");
	 
	 public static AlloySmelterRecipe getAlloySmelterRecipe(ItemInstance input1, ItemInstance input2)
	 {
        for(AlloySmelterRecipe recipe : ALLOY_SMELTER_RECIPES)
        {
            if(input1.isEffectivelyEqualWithWildcard(recipe.getInput1()) &&
            	input2.isEffectivelyEqualWithWildcard(recipe.getInput2()))
            {
                return recipe;
            }
        }
        return null;
    }
	 
	 public static boolean existsInAlloyRecipe(ItemInstance item)
	 {
		 for(AlloySmelterRecipe recipe : ALLOY_SMELTER_RECIPES)
		 {
            if(item.isEffectivelyEqualWithWildcard(recipe.getInput1()))
            {
                return true;
            }
        }
        return false;
	 }
	 
	 public static BlastFurnaceRecipe getArcFurnaceRecipe(ItemInstance input)
	 {
        for(BlastFurnaceRecipe recipe : BLAST_FURNACE_RECIPES)
        {
            if(input.isEffectivelyEqualWithWildcard(recipe.getInput()))
            {
                return recipe;
            }
        }
        return null;
    }
	 

	 public static CompressorRecipe getCompressorRecipe(ItemInstance input)
	 {
        for(CompressorRecipe recipe : COMPRESSOR_RECIPES)
        {
            if(input.isEffectivelyEqualWithWildcard(recipe.getInput()))
            {
                return recipe;
            }
        }
        return null;
    }
}
