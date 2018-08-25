package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.recipe.AlloySmelterRecipe;
import com.raphydaphy.rocksolid.recipe.AssemblyRecipe;
import com.raphydaphy.rocksolid.recipe.BlastingRecipe;
import com.raphydaphy.rocksolid.recipe.SeparatorRecipe;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.ParentedNameRegistry;

public class ModRecipes
{
	public static final ParentedNameRegistry<AssemblyRecipe> ASSEMBLY_STATION_RECIPES = new ParentedNameRegistry<>(RockSolid.createRes("assembly_station_registry"), true, Registries.ALL_CONSTRUCTION_RECIPES).register();

	public static void init()
	{
		new SeparatorRecipe(GameContent.RES_COPPER_RAW, ModItems.COPPER_GRIT, 300).register();
		new SeparatorRecipe(ModItems.RES_TIN_RAW, ModItems.TIN_GRIT, 300).register();
		new SeparatorRecipe(RockSolid.createRes("iron_raw_separation"),new ResUseInfo(ModItems.RES_IRON_RAW), new ItemInstance(ModItems.IRON_GRIT), new ItemInstance(ModItems.NICKEL_GRIT), 2, 300).register();
		new SeparatorRecipe(ModItems.RES_MAGNESIUM_RAW, ModItems.MAGNESIUM_GRIT, 300).register();

		new AlloySmelterRecipe(GameContent.RES_COPPER_PROCESSED, 3, ModItems.RES_TIN_PROCESSED, 1, ModItems.BRONZE_INGOT, 4, 350).register();
		new AlloySmelterRecipe(ModItems.RES_COPPER_CRUSHED, 3, ModItems.RES_TIN_CRUSHED, 1, ModItems.BRONZE_GRIT, 4, 350).register();
		new AlloySmelterRecipe(ModItems.RES_IRON_PROCESSED, 1, ModItems.RES_COAL_PROCESSED, 1, ModItems.STEEL_INGOT, 1, 500).register();
		new AlloySmelterRecipe(ModItems.RES_MAGNESIUM_PROCESSED, 1, ModItems.RES_TITANIUM_IMPURE, 1, ModItems.TITANIUM_INGOT, 1, 650).register();

		new BlastingRecipe(GameContent.RES_COAL, ModItems.COKE, 5000).register();
		new BlastingRecipe(ModTiles.RES_TITANIUM_RAW, ModItems.IMPURE_TITANIUM_INGOT, 2500).register();

		new AssemblyRecipe(0.8f, new ItemInstance(ModTiles.BOILER), 55, 8, 12).disableEfficiency().disableThroughput().registerAssembly();
		new AssemblyRecipe(0.6f, new ItemInstance(ModTiles.TURBINE),49, 5, 6).disableThroughput().registerAssembly();
		new AssemblyRecipe(0.5f, new ItemInstance(ModTiles.BATTERY), 75,6, 5).disableBonusYield().disableEfficiency().disableSpeed().registerAssembly();
		new AssemblyRecipe(0.55f, new ItemInstance(ModTiles.ELECTRIC_FURNACE), 30, 5, 16).registerAssembly();
		new AssemblyRecipe(0.6f, new ItemInstance(ModTiles.ELECTRIC_SEPARATOR), 45, 7, 15).registerAssembly();
		new AssemblyRecipe(0.6f, new ItemInstance(ModTiles.ELECTRIC_ALLOY_SMELTER), 35, 9, 10).registerAssembly();
		new AssemblyRecipe(0.6f, new ItemInstance(ModTiles.ELECTRIC_BLAST_FURNACE), 50, 8, 20).registerAssembly();
		new AssemblyRecipe(0.6f, new ItemInstance(ModTiles.PUMP),40, 4, 8).disableEfficiency().registerAssembly();
	}
}
