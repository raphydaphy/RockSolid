package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.recipe.*;
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
		new AlloySmelterRecipe(GameContent.RES_COPPER_PROCESSED, 3, ModItems.RES_TIN_PROCESSED, 1, ModItems.BRONZE_INGOT, 4, 350).register();
		new AlloySmelterRecipe(ModItems.RES_COPPER_CRUSHED, 3, ModItems.RES_TIN_CRUSHED, 1, ModItems.BRONZE_GRIT, 4, 350).register();
		new AlloySmelterRecipe(ModItems.RES_IRON_PROCESSED, 1, ModItems.RES_COAL_PROCESSED, 1, ModItems.STEEL_INGOT, 1, 500).register();
		new AlloySmelterRecipe(ModItems.RES_MAGNESIUM_PROCESSED, 1, ModItems.RES_TITANIUM_IMPURE, 1, ModItems.TITANIUM_INGOT, 1, 650).register();

		new AssemblyRecipe(0.8f, new ItemInstance(ModTiles.BOILER), 55, 8, 12).disableEfficiency().disableThroughput().registerAssembly();
		new AssemblyRecipe(0.6f, new ItemInstance(ModTiles.TURBINE),49, 5, 6).disableThroughput().registerAssembly();
		new AssemblyRecipe(0.6f, new ResUseInfo(ModItems.RES_TITANIUM_PROCESSED, 15), new ItemInstance(ModTiles.NUCLEAR_REACTOR), 75, 10).disableThroughput().registerAssembly();
		new AssemblyRecipe(0.5f, new ItemInstance(ModTiles.BATTERY), 75,6, 5).disableBonusYield().disableEfficiency().disableSpeed().registerAssembly();
		new AssemblyRecipe(0.55f, new ItemInstance(ModTiles.ELECTRIC_FURNACE), 30, 5, 16).registerAssembly();
		new AssemblyRecipe(0.6f, new ItemInstance(ModTiles.ELECTRIC_SEPARATOR), 45, 7, 15).registerAssembly();
		new AssemblyRecipe(0.6f, new ItemInstance(ModTiles.ELECTRIC_ALLOY_SMELTER), 35, 9, 10).registerAssembly();
		new AssemblyRecipe(0.6f, new ItemInstance(ModTiles.ELECTRIC_BLAST_FURNACE), 50, 8, 20).registerAssembly();
		new AssemblyRecipe(0.6f, new ItemInstance(ModTiles.ELECTRIC_COMPRESSOR), 70, 6, 8).registerAssembly();
		new AssemblyRecipe(0.6f, new ItemInstance(ModTiles.PUMP),40, 4, 8).disableEfficiency().registerAssembly();
		new AssemblyRecipe(0.6f, new ResUseInfo(ModItems.RES_URANIUM_COMPRESSED, 4), new ItemInstance(ModItems.URANIUM_ROD), 16, 4).disableThroughput().disableSpeed().disableBonusYield().registerAssembly();
	}
}
