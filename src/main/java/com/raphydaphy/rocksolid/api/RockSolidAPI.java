package com.raphydaphy.rocksolid.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.raphydaphy.rocksolid.api.content.RockSolidContent;
import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.api.gas.Gas;
import com.raphydaphy.rocksolid.api.recipe.AlloySmelterRecipe;
import com.raphydaphy.rocksolid.api.recipe.BlastFurnaceRecipe;
import com.raphydaphy.rocksolid.api.recipe.CompressorRecipe;
import com.raphydaphy.rocksolid.api.recipe.ElectrolyzerRecipe;
import com.raphydaphy.rocksolid.api.recipe.PurifierRecipe;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.mod.IMod;
import de.ellpeck.rockbottom.api.util.reg.NameRegistry;

public class RockSolidAPI
{
	public static final String VERSION = "2.0.2";

	public static final IMod RockSolid = RockBottomAPI.getModLoader().getMod("rocksolid");

	public static final List<AlloySmelterRecipe> ALLOY_SMELTER_RECIPES = new ArrayList<>();
	public static final List<BlastFurnaceRecipe> BLAST_FURNACE_RECIPES = new ArrayList<>();
	public static final List<CompressorRecipe> COMPRESSOR_RECIPES = new ArrayList<>();

	public static final List<PurifierRecipe> PURIFIER_RECIPES = new ArrayList<>();

	public static final List<ElectrolyzerRecipe> ELECTROLYZER_RECIPE = new ArrayList<>();

	public static Map<String, Fluid> FLUID_REGISTRY = new HashMap<String, Fluid>();
	public static final NameRegistry<Gas> GAS_REGISTRY = new NameRegistry<>("gas_registry");

	public static AlloySmelterRecipe getAlloySmelterRecipe(ItemInstance input1, ItemInstance input2)
	{
		for (AlloySmelterRecipe recipe : ALLOY_SMELTER_RECIPES)
		{
			if (recipe.getInput1().containsItem(input1) && recipe.getInput2().containsItem(input2))
			{
				return recipe;
			}
		}
		return null;
	}

	public static PurifierRecipe getPurifierRecipe(ItemInstance item, String fluid, int fluidVolume)
	{
		for (PurifierRecipe recipe : PURIFIER_RECIPES)
		{
			boolean fluidMatches = fluid.equals(recipe.getFluid())
					|| (recipe.getFluid().equals(Fluid.EMPTY.toString()));
			if (fluidVolume >= recipe.getFluidVolume() && fluidMatches && recipe.getInput().containsItem(item))
			{
				return recipe;
			}
		}
		return null;
	}

	public static ElectrolyzerRecipe getElectrolyzerRecipe(String output1, String output2, String fluid,
			int fluidVolume)
	{
		for (ElectrolyzerRecipe recipe : ELECTROLYZER_RECIPE)
		{
			boolean fluidMatches = fluid.equals(recipe.getFluid())
					|| (recipe.getFluid().equals(Fluid.EMPTY.toString()));
			if (fluidVolume >= recipe.getFluidVolume() && fluidMatches
					&& (output1.equals(recipe.getOutput1()) || output1.equals(RockSolidContent.gasVacuum.toString()))
					&& (output2.equals(recipe.getOutput2()) || output2.equals(RockSolidContent.gasVacuum.toString())))
			{
				return recipe;
			}
		}
		return null;
	}

	public static boolean existsInPurifierRecipe(ItemInstance item)
	{
		for (PurifierRecipe recipe : PURIFIER_RECIPES)
		{
			if (recipe.getInput().containsItem(item))
			{
				return true;
			}
		}
		return false;
	}

	public static boolean existsInAlloyRecipe(ItemInstance item)
	{
		for (AlloySmelterRecipe recipe : ALLOY_SMELTER_RECIPES)
		{
			if (recipe.getInput1().containsItem(item))
			{
				return true;
			}
		}
		return false;
	}

	public static BlastFurnaceRecipe getArcFurnaceRecipe(ItemInstance input)
	{
		for (BlastFurnaceRecipe recipe : BLAST_FURNACE_RECIPES)
		{
			if (recipe.getInput().containsItem(input))
			{
				return recipe;
			}
		}
		return null;
	}

	public static CompressorRecipe getCompressorRecipe(ItemInstance input)
	{
		for (CompressorRecipe recipe : COMPRESSOR_RECIPES)
		{
			if (recipe.getInput().containsItem(input))
			{
				return recipe;
			}
		}
		return null;
	}
}
