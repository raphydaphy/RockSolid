package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.api.RockSolidAPI;
import com.raphydaphy.rocksolid.api.recipe.AlloySmelterRecipe;
import com.raphydaphy.rocksolid.api.recipe.BlastFurnaceRecipe;
import com.raphydaphy.rocksolid.api.recipe.CompressorRecipe;
import com.raphydaphy.rocksolid.api.recipe.ElectrolyzerRecipe;
import com.raphydaphy.rocksolid.api.recipe.PurifierRecipe;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.BasicRecipe;
import de.ellpeck.rockbottom.api.construction.SeparatorRecipe;
import de.ellpeck.rockbottom.api.construction.SmelterRecipe;
import de.ellpeck.rockbottom.api.construction.resource.ResInfo;
import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.construction.resource.ResourceRegistry;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class ModRecipes
{
	public static void init()
	{
		// ik i can make this better ill do it one day
		ResourceRegistry.addResources(ModResources.PROCESSED_TIN, new ResInfo(ModItems.ingotTin));
		ResourceRegistry.addResources(ModResources.PARTLY_PROCESSED_TIN, new ResInfo(ModItems.gritTin));
		ResourceRegistry.addResources(ModResources.RAW_TIN, new ResInfo(ModItems.clusterTin));

		ResourceRegistry.addResources(ModResources.PROCESSED_BRONZE, new ResInfo(ModItems.ingotBronze));

		ResourceRegistry.addResources(ModResources.PROCESSED_IRON, new ResInfo(ModItems.ingotIron));
		ResourceRegistry.addResources(ModResources.PARTLY_PROCESSED_IRON, new ResInfo(ModItems.gritIron));
		ResourceRegistry.addResources(ModResources.RAW_IRON, new ResInfo(ModItems.clusterIron));

		ResourceRegistry.addResources(ModResources.PROCESSED_STEEL, new ResInfo(ModItems.ingotSteel));
		ResourceRegistry.addResources(ModResources.PROCESSED_COAL, new ResInfo(ModItems.gemCoke));

		ResourceRegistry.addResources(ModResources.PROCESSED_MAGNESIUM, new ResInfo(ModItems.ingotMagnesium));
		ResourceRegistry.addResources(ModResources.PARTLY_PROCESSED_MAGNESIUM, new ResInfo(ModItems.gritMagnesium));
		ResourceRegistry.addResources(ModResources.RAW_MAGNESIUM, new ResInfo(ModItems.clusterMagnesium));

		ResourceRegistry.addResources(ModResources.PROCESSED_CLAY, new ResInfo(ModItems.porcelianClay));
		ResourceRegistry.addResources(ModResources.PARTLY_PROCESSED_CLAY, new ResInfo(ModItems.clay));
		ResourceRegistry.addResources(ModResources.RAW_CLAY, new ResInfo(ModItems.clumpClay));

		ResourceRegistry.addResources(ModResources.COMPRESSED_URANIUM, new ResInfo(ModItems.pelletUranium));
		ResourceRegistry.addResources(ModResources.PROCESSED_URANIUM, new ResInfo(ModItems.ingotUranium));
		ResourceRegistry.addResources(ModResources.PARTLY_PROCESSED_URANIUM, new ResInfo(ModItems.gritUranium));
		ResourceRegistry.addResources(ModResources.RAW_URANIUM, new ResInfo(ModItems.clusterUranium));

		ResourceRegistry.addResources(ModResources.PROCESSED_TITANIUM, new ResInfo(ModItems.ingotTitanium));
		ResourceRegistry.addResources(ModResources.PARTLY_PROCESSED_TITANIUM,
				new ResInfo(ModItems.ingotImpureTitanium));

		ResourceRegistry.addResources(ModResources.COMPRESSED_STEEL, new ResInfo(ModTiles.constructionBlockSteel));
		ResourceRegistry.addResources(ModResources.COMPRESSED_TITANIUM,
				new ResInfo(ModTiles.constructionBlockTitanium));

		ResourceRegistry.addResources(ModResources.RAW_SHINING, new ResInfo(GameContent.ITEM_GLOW_CLUSTER));

		ResourceRegistry.addResources(ModResources.TANK, new ResInfo(ModTiles.tank));

		ResourceRegistry.addResources(ModResources.ORE_RUTILE, new ResInfo(ModTiles.oreRutile));

		// Fuel registration
		RockBottomAPI.FUEL_REGISTRY.put(new ResUseInfo(ModResources.PROCESSED_COAL), 3600);

		// Alloy recipes
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.ingotBronze, 4),
				new ResUseInfo(ResourceRegistry.PROCESSED_COPPER, 3), new ResUseInfo(ModResources.PROCESSED_TIN, 1),
				500));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.ingotSteel, 1),
				new ResUseInfo(ModResources.PROCESSED_IRON, 1), new ResUseInfo(ModResources.PROCESSED_COAL, 1), 500));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.ingotTitanium, 1),
				new ResUseInfo(ModResources.PROCESSED_MAGNESIUM, 1),
				new ResUseInfo(ModResources.PARTLY_PROCESSED_TITANIUM, 1), 1000));

		// Alloy recipies, reverse
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.ingotBronze, 4),
				new ResUseInfo(ModResources.PROCESSED_IRON, 1), new ResUseInfo(ResourceRegistry.PROCESSED_COPPER, 3),
				500));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.ingotSteel, 1),
				new ResUseInfo(ModResources.PROCESSED_COAL, 1), new ResUseInfo(ModResources.PROCESSED_IRON, 1), 500));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.ingotTitanium, 1),
				new ResUseInfo(ModResources.PARTLY_PROCESSED_TITANIUM, 1),
				new ResUseInfo(ModResources.PROCESSED_MAGNESIUM, 1), 1000));

		// Cluster tripling recipies
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.gritIron, 3),
				new ResUseInfo(ModResources.RAW_IRON, 1), new ResUseInfo(ResourceRegistry.SLAG, 1), 750));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.gritMagnesium, 3),
				new ResUseInfo(ModResources.RAW_MAGNESIUM, 1), new ResUseInfo(ResourceRegistry.SLAG, 1), 750));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.gritTin, 3),
				new ResUseInfo(ModResources.RAW_TIN, 1), new ResUseInfo(ResourceRegistry.SLAG, 1), 750));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.gritUranium, 3),
				new ResUseInfo(ModResources.RAW_URANIUM, 1), new ResUseInfo(ResourceRegistry.SLAG, 1), 750));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(GameContent.ITEM_COPPER_GRIT, 3),
				new ResUseInfo(ResourceRegistry.RAW_COPPER, 1), new ResUseInfo(ResourceRegistry.SLAG, 1), 750));

		// Cluster tripling recipies, reverse
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.gritIron, 3),
				new ResUseInfo(ResourceRegistry.SLAG, 1), new ResUseInfo(ModResources.RAW_IRON, 1), 750));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.gritMagnesium, 3),
				new ResUseInfo(ResourceRegistry.SLAG, 1), new ResUseInfo(ModResources.RAW_MAGNESIUM, 1), 750));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.gritTin, 3),
				new ResUseInfo(ResourceRegistry.SLAG, 1), new ResUseInfo(ModResources.RAW_TIN, 1), 750));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.gritUranium, 3),
				new ResUseInfo(ResourceRegistry.SLAG, 1), new ResUseInfo(ModResources.RAW_URANIUM, 1), 750));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(GameContent.ITEM_COPPER_GRIT, 3),
				new ResUseInfo(ResourceRegistry.SLAG, 1), new ResUseInfo(ResourceRegistry.RAW_COPPER, 1), 750));

		// Purifier Recipes
		RockSolidAPI.PURIFIER_RECIPES.add(new PurifierRecipe(new ItemInstance(ModItems.clay),
				new ResUseInfo(ModResources.RAW_CLAY), ModFluids.fluidWater.toString(), 100, 850));

		// Electrolyzer Recipes
		RockSolidAPI.ELECTROLYZER_RECIPE.add(new ElectrolyzerRecipe(ModGasses.gasHydrogen.toString(),
				ModGasses.gasOxygen.toString(), ModFluids.fluidWater.toString(), 150, 100, 50, 800));

		// Arc furnace recipes
		RockSolidAPI.BLAST_FURNACE_RECIPES.add(new BlastFurnaceRecipe(new ItemInstance(ModItems.gemCoke),
				new ResUseInfo(ResourceRegistry.COAL), 5000));
		RockSolidAPI.BLAST_FURNACE_RECIPES.add(new BlastFurnaceRecipe(new ItemInstance(ModItems.ingotImpureTitanium),
				new ResUseInfo(ModResources.ORE_RUTILE, 1), 5000));

		// Compressor Recipes
		RockSolidAPI.COMPRESSOR_RECIPES.add(new CompressorRecipe(new ItemInstance(ModItems.pelletUranium),
				new ResUseInfo(ModResources.PROCESSED_URANIUM, 5), 500));
		RockSolidAPI.COMPRESSOR_RECIPES.add(new CompressorRecipe(new ItemInstance(GameContent.TILE_HARDENED_STONE),
				new ResUseInfo(ResourceRegistry.RAW_STONE, 3), 30));

		// Cluster to grit (separator)
		RockBottomAPI.SEPARATOR_RECIPES.add(new SeparatorRecipe(new ItemInstance(ModItems.gritTin, 2),
				new ResUseInfo(ModResources.RAW_TIN), 350, new ItemInstance(GameContent.ITEM_SLAG), 0.25f));
		RockBottomAPI.SEPARATOR_RECIPES.add(new SeparatorRecipe(new ItemInstance(ModItems.gritIron, 2),
				new ResUseInfo(ModResources.RAW_IRON), 350, new ItemInstance(GameContent.ITEM_SLAG), 0.4f));
		RockBottomAPI.SEPARATOR_RECIPES.add(new SeparatorRecipe(new ItemInstance(ModItems.gritMagnesium, 2),
				new ResUseInfo(ModResources.RAW_MAGNESIUM), 350, new ItemInstance(GameContent.ITEM_SLAG), 0.8f));
		RockBottomAPI.SEPARATOR_RECIPES.add(new SeparatorRecipe(new ItemInstance(ModItems.gritUranium, 2),
				new ResUseInfo(ModResources.RAW_URANIUM), 350, new ItemInstance(GameContent.ITEM_SLAG), 0.5f));

		// misc separator recipies
		RockBottomAPI.SEPARATOR_RECIPES.add(new SeparatorRecipe(new ItemInstance(GameContent.ITEM_COPPER_INGOT, 3),
				new ResUseInfo(ModResources.PROCESSED_BRONZE, 4), 450, new ItemInstance(ModItems.ingotTin), 1));

		// grit to ingot (smelter)
		RockBottomAPI.SMELTER_RECIPES.add(new SmelterRecipe(new ItemInstance(ModItems.ingotTin),
				new ResUseInfo(ModResources.PARTLY_PROCESSED_TIN), 300));
		RockBottomAPI.SMELTER_RECIPES.add(new SmelterRecipe(new ItemInstance(ModItems.ingotIron),
				new ResUseInfo(ModResources.PARTLY_PROCESSED_IRON), 300));
		RockBottomAPI.SMELTER_RECIPES.add(new SmelterRecipe(new ItemInstance(ModItems.ingotMagnesium),
				new ResUseInfo("partly_processed_magnesum"), 300));
		RockBottomAPI.SMELTER_RECIPES.add(new SmelterRecipe(new ItemInstance(ModItems.ingotUranium),
				new ResUseInfo(ModResources.PARTLY_PROCESSED_URANIUM), 300));

		// Remove some vanilla recipes?
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.remove(4);
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.remove(4);
		
		// misc manual crafting recipies
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.alloySmelter),
				new ResUseInfo(ModResources.PROCESSED_TIN, 10), new ResUseInfo(ResourceRegistry.PROCESSED_COPPER, 10),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 30),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 15)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.blastFurnace),
				new ResUseInfo(ModResources.PROCESSED_IRON, 15), new ResUseInfo(ModResources.PROCESSED_BRONZE, 5),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 20),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 40)));

		
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.chest),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 30), new ResUseInfo(ResourceRegistry.PROCESSED_COPPER),
				new ResUseInfo(ResourceRegistry.WOOD_LOG, 5)));

		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.wrench),
				new ResUseInfo(ModResources.PROCESSED_IRON, 4), new ResUseInfo(ModResources.PROCESSED_TIN, 2)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.jetpack),
				new ResUseInfo(ModResources.COMPRESSED_STEEL, 4), new ResUseInfo(ModResources.COMPRESSED_TITANIUM, 2),
				new ResUseInfo(ModResources.PARTLY_PROCESSED_TITANIUM, 5),
				new ResUseInfo(ModResources.PROCESSED_STEEL, 10), new ResUseInfo(ModResources.PROCESSED_URANIUM, 5),
				new ResUseInfo(ModResources.PROCESSED_TITANIUM, 8), new ResUseInfo(ModResources.PROCESSED_MAGNESIUM, 5),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 20)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.lantern),
				new ResUseInfo(ModResources.RAW_SHINING, 8), new ResUseInfo(ModResources.PROCESSED_TIN, 10),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 20),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 10)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.electricLantern),
				new ResUseInfo(ModResources.COMPRESSED_STEEL, 1), new ResUseInfo(ModResources.PROCESSED_STEEL, 10),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 40),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 25)));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.lamp, 2),
				new ResUseInfo(ModResources.RAW_SHINING, 2), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 2),
				new ResUseInfo(ResourceRegistry.RAW_STONE, 4)));

		// Electric manual crafting
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.constructionBlockSteel),
				new ResUseInfo(ModResources.PROCESSED_IRON, 4), new ResUseInfo(ModResources.PROCESSED_STEEL, 8)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(
				new ItemInstance(ModTiles.constructionBlockTitanium), new ResUseInfo(ModResources.PROCESSED_STEEL, 4),
				new ResUseInfo(ModResources.PROCESSED_TITANIUM, 8)));

		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.boiler),
				new ResUseInfo(ModResources.COMPRESSED_STEEL, 1), new ResUseInfo(ModResources.PROCESSED_COAL, 4),
				new ResUseInfo(ModResources.PROCESSED_BRONZE, 8), new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 20),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 40)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.liquidBoiler),
				new ResUseInfo(ModResources.COMPRESSED_TITANIUM, 1),
				new ResUseInfo(ModResources.PROCESSED_MAGNESIUM, 4), new ResUseInfo(ModResources.PROCESSED_STEEL, 8),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 30),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 60)));

		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.turbine),
				new ResUseInfo(ModResources.COMPRESSED_STEEL, 1), new ResUseInfo(ModResources.PROCESSED_TIN, 6),
				new ResUseInfo(ModResources.PROCESSED_BRONZE, 12), new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 20),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 70)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.nuclearReactor),
				new ResUseInfo(ModResources.COMPRESSED_STEEL, 2), new ResUseInfo(ModResources.COMPRESSED_TITANIUM),
				new ResUseInfo(ModResources.COMPRESSED_URANIUM, 4), new ResUseInfo(ModResources.PROCESSED_STEEL, 8),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 30),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 50)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.battery),
				new ResUseInfo(ModResources.COMPRESSED_STEEL, 1), new ResUseInfo(ModResources.PROCESSED_STEEL, 4),
				new ResUseInfo(ModResources.PROCESSED_BRONZE, 8), new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 30),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 60)));

		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.tank),
				new ResUseInfo(ModResources.PROCESSED_STEEL, 4), new ResUseInfo(ModResources.PROCESSED_BRONZE, 4),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 5),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 10)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.gasTank),
				new ResUseInfo(ModResources.PROCESSED_STEEL, 4), new ResUseInfo(ModResources.PROCESSED_TIN, 4),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 15),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 15)));

		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.bucket),
				new ResUseInfo(ModResources.PROCESSED_IRON, 6), new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 4),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 5)));

		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.energyConduit, 8),
				new ResUseInfo(ResourceRegistry.PROCESSED_COPPER, 8),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 5),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 10)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.fluidConduit, 8),
				new ResUseInfo(ModResources.PROCESSED_IRON, 8), new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 10),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 10)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.gasConduit, 8),
				new ResUseInfo(ModResources.PROCESSED_STEEL, 8), new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 10),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 10)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.itemConduit, 8),
				new ResUseInfo(ModResources.PROCESSED_TIN, 4), new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 5),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 5)));

		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.fluidPump),
				new ResUseInfo(ModResources.COMPRESSED_STEEL, 1), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 55),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 20), new ResUseInfo(ModResources.TANK, 1),
				new ResUseInfo(ModResources.PROCESSED_STEEL, 5)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.electricPurifier),
				new ResUseInfo(ModResources.COMPRESSED_STEEL, 2), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 60),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 40),
				new ResUseInfo(ModResources.PROCESSED_BRONZE, 30), new ResUseInfo(ModResources.RAW_CLAY, 10)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.electrolyzer),
				new ResUseInfo(ModResources.COMPRESSED_TITANIUM, 1), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 100),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 50),
				new ResUseInfo(ModResources.PROCESSED_MAGNESIUM, 30), new ResUseInfo(ModResources.TANK, 1)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.electricAlloySmelter),
				new ResUseInfo(ModResources.COMPRESSED_STEEL, 2), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 50),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 30),
				new ResUseInfo(ModResources.PROCESSED_BRONZE, 18), new ResUseInfo(ModResources.PROCESSED_STEEL, 5)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.electricBlastFurnace),
				new ResUseInfo(ModResources.COMPRESSED_STEEL, 2), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 60),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 40),
				new ResUseInfo(ModResources.PROCESSED_BRONZE, 30), new ResUseInfo(ModResources.PROCESSED_COAL, 10)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.electricSeparator),
				new ResUseInfo(ModResources.COMPRESSED_STEEL, 2), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 65),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 50),
				new ResUseInfo(ModResources.PROCESSED_BRONZE, 25), new ResUseInfo(ModResources.PROCESSED_IRON, 25)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.electricSmelter),
				new ResUseInfo(ModResources.COMPRESSED_STEEL, 2), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 35),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 30), new ResUseInfo(ModResources.PROCESSED_BRONZE, 15),
				new ResUseInfo(ModResources.PROCESSED_TIN, 10)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.compressor),
				new ResUseInfo(ModResources.COMPRESSED_STEEL, 2), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 50),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 40),
				new ResUseInfo(ModResources.PROCESSED_URANIUM, 15),
				new ResUseInfo(ModResources.PROCESSED_MAGNESIUM, 10)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.charger),
				new ResUseInfo(ModResources.COMPRESSED_STEEL, 2), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 35),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 30), new ResUseInfo(ModResources.PROCESSED_BRONZE, 25),
				new ResUseInfo(ModResources.PROCESSED_TIN, 10), new ResUseInfo(ModResources.PROCESSED_MAGNESIUM, 10)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.quarry),
				new ResUseInfo(ModResources.COMPRESSED_TITANIUM, 4), new ResUseInfo(ModResources.COMPRESSED_STEEL, 2),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 150),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 200), new ResUseInfo(ModResources.PROCESSED_STEEL, 15),
				new ResUseInfo(ModResources.PROCESSED_TITANIUM, 10)));

		// Pickaxe manual crafting
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.pickaxeBronze),
				new ResUseInfo(ModResources.PROCESSED_BRONZE, 8), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 8)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.pickaxeCopper),
				new ResUseInfo(ResourceRegistry.PROCESSED_COPPER, 8), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 8)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.pickaxeIron),
				new ResUseInfo(ModResources.PROCESSED_IRON, 8), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 8)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.pickaxeSteel),
				new ResUseInfo(ModResources.PROCESSED_STEEL, 8), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 8)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.pickaxeTitanium),
				new ResUseInfo(ModResources.PROCESSED_TITANIUM, 8), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 8)));

		// Axe manual crafting
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(
				new BasicRecipe(new ItemInstance(ModItems.axeWood), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 16)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.axeRock),
				new ResUseInfo(ResourceRegistry.RAW_STONE, 8), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 8)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.axeCopper),
				new ResUseInfo(ResourceRegistry.PROCESSED_COPPER, 8), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 8)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.axeBronze),
				new ResUseInfo(ModResources.PROCESSED_BRONZE, 8), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 8)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.axeIron),
				new ResUseInfo(ModResources.PROCESSED_IRON, 8), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 8)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.axeSteel),
				new ResUseInfo(ModResources.PROCESSED_STEEL, 8), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 8)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.axeTitanium),
				new ResUseInfo(ModResources.PROCESSED_TITANIUM, 8), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 8)));

		// Shovel manual crafting
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.shovelWood),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 16)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.shovelRock),
				new ResUseInfo(ResourceRegistry.RAW_STONE, 8), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 8)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.shovelCopper),
				new ResUseInfo(ResourceRegistry.PROCESSED_COPPER, 8), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 8)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.shovelIron),
				new ResUseInfo(ModResources.PROCESSED_BRONZE, 8), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 8)));
	}
}
