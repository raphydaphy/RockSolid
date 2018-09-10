package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.recipe.AssemblyRecipe;
import com.raphydaphy.rocksolid.recipe.PrecisionRecipe;
import com.raphydaphy.rocksolid.recipe.category.AlloyingCategory;
import com.raphydaphy.rocksolid.recipe.category.BlastingCategory;
import com.raphydaphy.rocksolid.recipe.category.CompressingCategory;
import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.NameRegistry;

public class ModRecipes
{
	public static final NameRegistry<AssemblyRecipe> ASSEMBLY_STATION_RECIPES = new NameRegistry<>(RockSolid.createRes("assembly_station_registry"), true).register();
	public static final NameRegistry<PrecisionRecipe> PRECISION_ASSEMBLER_RECIPES = new NameRegistry<>(RockSolid.createRes("precision_assembler_registry"), true).register();

	public static void init()
	{
		new AssemblyRecipe(0.8f, new ItemInstance(ModTiles.BOILER), 55, 8, 12).disableEfficiency().disableThroughput().registerAssembly();
		new AssemblyRecipe(0.6f, new ItemInstance(ModTiles.TURBINE),49, 5, 6).disableThroughput().registerAssembly();
		new AssemblyRecipe(0.5f, new ItemInstance(ModTiles.BATTERY), 75,6, 5).disableBonusYield().disableEfficiency().disableSpeed().registerAssembly();
		new AssemblyRecipe(0.55f, new ItemInstance(ModTiles.ELECTRIC_FURNACE), 30, 5, 16).registerAssembly();
		new AssemblyRecipe(0.6f, new ItemInstance(ModTiles.ELECTRIC_SEPARATOR), 45, 7, 15).registerAssembly();
		new AssemblyRecipe(0.6f, new ItemInstance(ModTiles.ELECTRIC_ALLOY_SMELTER), 35, 9, 10).registerAssembly();
		new AssemblyRecipe(0.6f, new ItemInstance(ModTiles.ELECTRIC_BLAST_FURNACE), 50, 8, 20).registerAssembly();
		new AssemblyRecipe(0.6f, new ItemInstance(ModTiles.ELECTRIC_COMPRESSOR), 70, 6, 8).registerAssembly();
		new AssemblyRecipe(0.7f, new ItemInstance(ModTiles.REFINERY), 45, 12, 8).registerAssembly();
		new AssemblyRecipe(0.6f, new ItemInstance(ModTiles.PUMP),40, 4, 8).disableEfficiency().registerAssembly();
		new AssemblyRecipe(0.6f, new ResUseInfo(ModItems.RES_TITANIUM_PROCESSED, 15), new ItemInstance(ModTiles.NUCLEAR_REACTOR), 75, 10).registerAssembly();
		new AssemblyRecipe(0.6f, new ResUseInfo(ModItems.RES_URANIUM_COMPRESSED, 4), new ItemInstance(ModItems.URANIUM_ROD), 16, 4).disableThroughput().disableSpeed().disableBonusYield().registerAssembly();
		new AssemblyRecipe(0.6f, new ItemInstance(ModTiles.TEMPSHIFT_PLATE),50, 15, 5).disableBonusYield().disableEfficiency().disableSpeed().disableThroughput().registerAssembly();
		new AssemblyRecipe(0.7f, new ItemInstance(ModTiles.LAUNCH_PAD), 25, 16,3).disableSpeed().disableBonusYield().registerAssembly();

		new PrecisionRecipe(0.7f, new ResUseInfo(ModItems.RES_NICKEL_TUNGSTEN_CARBIDE_PROCESSED, 18), new ItemInstance(ModItems.ROCKET), 50, 15, 300).disableCapacity().disableBonusYield().disableSpeed().disableThroughput().disableEfficiency().registerPrecision();

		new AlloyingCategory().register();
		new BlastingCategory().register();
		new CompressingCategory().register();
	}
}
