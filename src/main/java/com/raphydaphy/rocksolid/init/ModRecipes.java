package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.api.RockSolidAPI;
import com.raphydaphy.rocksolid.api.content.BaseResources;
import com.raphydaphy.rocksolid.api.content.RockSolidContent;
import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.api.recipe.AlloySmelterRecipe;
import com.raphydaphy.rocksolid.api.recipe.BlastFurnaceRecipe;
import com.raphydaphy.rocksolid.api.recipe.CompressorRecipe;
import com.raphydaphy.rocksolid.api.recipe.ElectrolyzerRecipe;
import com.raphydaphy.rocksolid.api.recipe.PurifierRecipe;
import com.raphydaphy.rocksolid.api.recipe.RefineryRecipe;
import com.raphydaphy.rocksolid.api.recipe.RockCrusherRecipe;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.BasicRecipe;
import de.ellpeck.rockbottom.api.construction.SeparatorRecipe;
import de.ellpeck.rockbottom.api.construction.SmelterRecipe;
import de.ellpeck.rockbottom.api.construction.resource.ItemUseInfo;
import de.ellpeck.rockbottom.api.construction.resource.ResInfo;
import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.construction.resource.ResourceRegistry;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class ModRecipes
{
	public static void init()
	{
		// ik i can make this better ill do it one day
		ResourceRegistry.addResources(BaseResources.PROCESSED_TIN, new ResInfo(RockSolidContent.ingotTin));
		ResourceRegistry.addResources(BaseResources.PARTLY_PROCESSED_TIN, new ResInfo(RockSolidContent.gritTin));
		ResourceRegistry.addResources(BaseResources.RAW_TIN, new ResInfo(RockSolidContent.clusterTin));

		ResourceRegistry.addResources(BaseResources.PROCESSED_BRONZE, new ResInfo(RockSolidContent.ingotBronze));

		ResourceRegistry.addResources(BaseResources.PROCESSED_IRON, new ResInfo(RockSolidContent.ingotIron));
		ResourceRegistry.addResources(BaseResources.PARTLY_PROCESSED_IRON, new ResInfo(RockSolidContent.gritIron));
		ResourceRegistry.addResources(BaseResources.RAW_IRON, new ResInfo(RockSolidContent.clusterIron));

		ResourceRegistry.addResources(BaseResources.PROCESSED_NICKEL, new ResInfo(RockSolidContent.ingotNickel));
		ResourceRegistry.addResources(BaseResources.PARTLY_PROCESSED_NICKEL, new ResInfo(RockSolidContent.gritNickel));

		ResourceRegistry.addResources(BaseResources.PROCESSED_STEEL, new ResInfo(RockSolidContent.ingotSteel));
		ResourceRegistry.addResources(BaseResources.PROCESSED_COAL, new ResInfo(RockSolidContent.gemCoke));

		ResourceRegistry.addResources(BaseResources.PROCESSED_MAGNESIUM, new ResInfo(RockSolidContent.ingotMagnesium));
		ResourceRegistry.addResources(BaseResources.PARTLY_PROCESSED_MAGNESIUM,
				new ResInfo(RockSolidContent.gritMagnesium));
		ResourceRegistry.addResources(BaseResources.RAW_MAGNESIUM, new ResInfo(RockSolidContent.clusterMagnesium));

		ResourceRegistry.addResources(BaseResources.PROCESSED_CLAY, new ResInfo(RockSolidContent.porcelianClay));
		ResourceRegistry.addResources(BaseResources.PARTLY_PROCESSED_CLAY, new ResInfo(RockSolidContent.itemClay));
		ResourceRegistry.addResources(BaseResources.RAW_CLAY, new ResInfo(RockSolidContent.clumpClay));

		ResourceRegistry.addResources(BaseResources.COMPRESSED_URANIUM, new ResInfo(RockSolidContent.pelletUranium));
		ResourceRegistry.addResources(BaseResources.PROCESSED_URANIUM, new ResInfo(RockSolidContent.ingotUranium));
		ResourceRegistry.addResources(BaseResources.PARTLY_PROCESSED_URANIUM,
				new ResInfo(RockSolidContent.gritUranium));
		ResourceRegistry.addResources(BaseResources.RAW_URANIUM, new ResInfo(RockSolidContent.clusterUranium));

		ResourceRegistry.addResources(BaseResources.PROCESSED_TITANIUM, new ResInfo(RockSolidContent.ingotTitanium));
		ResourceRegistry.addResources(BaseResources.PARTLY_PROCESSED_TITANIUM,
				new ResInfo(RockSolidContent.ingotImpureTitanium));

		ResourceRegistry.addResources(BaseResources.TUNGSTEN_CARBIDE,
				new ResInfo(RockSolidContent.ingotNickelTungstenCarbide));
		ResourceRegistry.addResources(BaseResources.TUNGSTEN_ALLOY, new ResInfo(RockSolidContent.ingotNickelTungsten));

		ResourceRegistry.addResources(BaseResources.PROCESSED_TUNGSTEN, new ResInfo(RockSolidContent.ingotTungsten));
		ResourceRegistry.addResources(BaseResources.PARTLY_PROCESSED_TUNGSTEN,
				new ResInfo(RockSolidContent.gritTungsten));
		ResourceRegistry.addResources(BaseResources.RAW_TUNGSTEN, new ResInfo(RockSolidContent.clusterTungsten));

		ResourceRegistry.addResources(BaseResources.COMPRESSED_STEEL,
				new ResInfo(RockSolidContent.constructionBlockSteel));
		ResourceRegistry.addResources(BaseResources.COMPRESSED_TITANIUM,
				new ResInfo(RockSolidContent.constructionBlockTitanium));

		ResourceRegistry.addResources(BaseResources.RAW_SHINING, new ResInfo(GameContent.ITEM_GLOW_CLUSTER));
		ResourceRegistry.addResources(BaseResources.ORE_SHINING, new ResInfo(GameContent.TILE_GLOW_ORE));
		ResourceRegistry.addResources(BaseResources.ORE_COAL, new ResInfo(GameContent.TILE_COAL_ORE));
		ResourceRegistry.addResources(BaseResources.ORE_COPPER, new ResInfo(GameContent.TILE_COPPER_ORE));
		
		ResourceRegistry.addResources(BaseResources.TANK, new ResInfo(RockSolidContent.tank));

		ResourceRegistry.addResources(BaseResources.ORE_TIN, new ResInfo(RockSolidContent.oreTin));
		ResourceRegistry.addResources(BaseResources.ORE_IRON, new ResInfo(RockSolidContent.oreIron));
		ResourceRegistry.addResources(BaseResources.ORE_MAGNESIUM, new ResInfo(RockSolidContent.oreMagnesium));
		ResourceRegistry.addResources(BaseResources.ORE_RUTILE, new ResInfo(RockSolidContent.oreRutile));
		ResourceRegistry.addResources(BaseResources.ORE_URANIUM, new ResInfo(RockSolidContent.oreUranium));
		ResourceRegistry.addResources(BaseResources.ORE_WOLFRAMITE, new ResInfo(RockSolidContent.oreWolframite));
		ResourceRegistry.addResources(BaseResources.ORE_COBALT, new ResInfo(RockSolidContent.oreCobalt));
		ResourceRegistry.addResources(BaseResources.ORE_NICKEL, new ResInfo(RockSolidContent.oreNickel));
		ResourceRegistry.addResources(BaseResources.ORE_ALUMINUM, new ResInfo(RockSolidContent.oreAluminum));

		// Fuel registration
		RockBottomAPI.FUEL_REGISTRY.put(new ResUseInfo(BaseResources.PROCESSED_COAL), 3600);

		// Alloy recipes
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(RockSolidContent.ingotBronze, 4),
				new ResUseInfo(ResourceRegistry.PROCESSED_COPPER, 3), new ResUseInfo(BaseResources.PROCESSED_TIN, 1),
				500));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(RockSolidContent.ingotSteel, 1),
				new ResUseInfo(BaseResources.PROCESSED_IRON, 1), new ResUseInfo(BaseResources.PROCESSED_COAL, 1), 500));
		RockSolidAPI.ALLOY_SMELTER_RECIPES
				.add(new AlloySmelterRecipe(new ItemInstance(RockSolidContent.ingotTitanium, 1),
						new ResUseInfo(BaseResources.PROCESSED_MAGNESIUM, 1),
						new ResUseInfo(BaseResources.PARTLY_PROCESSED_TITANIUM, 1), 1000));
		RockSolidAPI.ALLOY_SMELTER_RECIPES
				.add(new AlloySmelterRecipe(new ItemInstance(RockSolidContent.ingotNickelTungsten, 2),
						new ResUseInfo(BaseResources.PROCESSED_NICKEL, 1),
						new ResUseInfo(BaseResources.PROCESSED_TUNGSTEN, 1), 1250));
		RockSolidAPI.ALLOY_SMELTER_RECIPES
				.add(new AlloySmelterRecipe(new ItemInstance(RockSolidContent.ingotNickelTungstenCarbide, 2),
						new ResUseInfo(BaseResources.PROCESSED_COAL, 1),
						new ResUseInfo(BaseResources.TUNGSTEN_ALLOY, 1), 1250));

		// Alloy recipies, reverse
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(RockSolidContent.ingotBronze, 4),
				new ResUseInfo(BaseResources.PROCESSED_TIN, 1), new ResUseInfo(ResourceRegistry.PROCESSED_COPPER, 3),
				500));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(RockSolidContent.ingotSteel, 1),
				new ResUseInfo(BaseResources.PROCESSED_COAL, 1), new ResUseInfo(BaseResources.PROCESSED_IRON, 1), 500));
		RockSolidAPI.ALLOY_SMELTER_RECIPES
				.add(new AlloySmelterRecipe(new ItemInstance(RockSolidContent.ingotTitanium, 1),
						new ResUseInfo(BaseResources.PARTLY_PROCESSED_TITANIUM, 1),
						new ResUseInfo(BaseResources.PROCESSED_MAGNESIUM, 1), 1000));
		RockSolidAPI.ALLOY_SMELTER_RECIPES
				.add(new AlloySmelterRecipe(new ItemInstance(RockSolidContent.ingotNickelTungsten, 2),
						new ResUseInfo(BaseResources.PROCESSED_TUNGSTEN, 1),
						new ResUseInfo(BaseResources.PROCESSED_NICKEL, 1), 1250));
		RockSolidAPI.ALLOY_SMELTER_RECIPES
				.add(new AlloySmelterRecipe(new ItemInstance(RockSolidContent.ingotNickelTungstenCarbide, 2),
						new ResUseInfo(BaseResources.TUNGSTEN_ALLOY, 1),
						new ResUseInfo(BaseResources.PROCESSED_COAL, 1), 1250));

		// Cluster tripling recipies
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(RockSolidContent.gritIron, 3),
				new ResUseInfo(BaseResources.RAW_IRON, 1), new ResUseInfo(ResourceRegistry.SLAG, 1), 750));
		RockSolidAPI.ALLOY_SMELTER_RECIPES
				.add(new AlloySmelterRecipe(new ItemInstance(RockSolidContent.gritMagnesium, 3),
						new ResUseInfo(BaseResources.RAW_MAGNESIUM, 1), new ResUseInfo(ResourceRegistry.SLAG, 1), 750));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(RockSolidContent.gritTin, 3),
				new ResUseInfo(BaseResources.RAW_TIN, 1), new ResUseInfo(ResourceRegistry.SLAG, 1), 750));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(RockSolidContent.gritUranium, 3),
				new ResUseInfo(BaseResources.RAW_URANIUM, 1), new ResUseInfo(ResourceRegistry.SLAG, 1), 750));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(GameContent.ITEM_COPPER_GRIT, 3),
				new ResUseInfo(ResourceRegistry.RAW_COPPER, 1), new ResUseInfo(ResourceRegistry.SLAG, 1), 750));
		RockSolidAPI.ALLOY_SMELTER_RECIPES
				.add(new AlloySmelterRecipe(new ItemInstance(RockSolidContent.gritTungsten, 3),
						new ResUseInfo(BaseResources.RAW_TUNGSTEN, 1), new ResUseInfo(ResourceRegistry.SLAG, 1), 750));

		// Cluster tripling recipies, reverse
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(RockSolidContent.gritIron, 3),
				new ResUseInfo(ResourceRegistry.SLAG, 1), new ResUseInfo(BaseResources.RAW_IRON, 1), 750));
		RockSolidAPI.ALLOY_SMELTER_RECIPES
				.add(new AlloySmelterRecipe(new ItemInstance(RockSolidContent.gritMagnesium, 3),
						new ResUseInfo(ResourceRegistry.SLAG, 1), new ResUseInfo(BaseResources.RAW_MAGNESIUM, 1), 750));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(RockSolidContent.gritTin, 3),
				new ResUseInfo(ResourceRegistry.SLAG, 1), new ResUseInfo(BaseResources.RAW_TIN, 1), 750));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(RockSolidContent.gritUranium, 3),
				new ResUseInfo(ResourceRegistry.SLAG, 1), new ResUseInfo(BaseResources.RAW_URANIUM, 1), 750));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(GameContent.ITEM_COPPER_GRIT, 3),
				new ResUseInfo(ResourceRegistry.SLAG, 1), new ResUseInfo(ResourceRegistry.RAW_COPPER, 1), 750));
		RockSolidAPI.ALLOY_SMELTER_RECIPES
				.add(new AlloySmelterRecipe(new ItemInstance(RockSolidContent.gritTungsten, 3),
						new ResUseInfo(ResourceRegistry.SLAG, 1), new ResUseInfo(BaseResources.RAW_TUNGSTEN, 1), 750));

		// Purifier Recipes
		RockSolidAPI.PURIFIER_RECIPES.add(new PurifierRecipe(new ItemInstance(RockSolidContent.itemClay),
				new ResUseInfo(BaseResources.RAW_CLAY), Fluid.WATER.getName(), 100, 850));

		// Refinery Recipes
		RockSolidAPI.REFINERY_RECIPES.add(new RefineryRecipe(Fluid.OIL, Fluid.FUEL, 50, 25, 300));

		// Electrolyzer Recipes
		RockSolidAPI.ELECTROLYZER_RECIPE.add(new ElectrolyzerRecipe(RockSolidContent.gasHydrogen.toString(),
				RockSolidContent.gasOxygen.toString(), Fluid.WATER.getName(), 150, 100, 50, 800));

		// Arc furnace recipes
		RockSolidAPI.BLAST_FURNACE_RECIPES.add(new BlastFurnaceRecipe(new ItemInstance(RockSolidContent.gemCoke),
				new ResUseInfo(ResourceRegistry.COAL), 5000));
		RockSolidAPI.BLAST_FURNACE_RECIPES
				.add(new BlastFurnaceRecipe(new ItemInstance(RockSolidContent.ingotImpureTitanium),
						new ResUseInfo(BaseResources.ORE_RUTILE, 1), 5000));

		// Compressor Recipes
		RockSolidAPI.COMPRESSOR_RECIPES.add(new CompressorRecipe(new ItemInstance(RockSolidContent.pelletUranium),
				new ResUseInfo(BaseResources.PROCESSED_URANIUM, 5), 500));
		RockSolidAPI.COMPRESSOR_RECIPES.add(new CompressorRecipe(new ItemInstance(GameContent.TILE_HARDENED_STONE),
				new ResUseInfo(ResourceRegistry.RAW_STONE, 3), 30));

		// Rock Crusher Recipes
		RockSolidAPI.ROCK_CRUSHER_RECIPES.add(new RockCrusherRecipe(new ItemInstance(GameContent.ITEM_COPPER_CLUSTER, 2), new ResUseInfo(BaseResources.ORE_COPPER), 150));
		RockSolidAPI.ROCK_CRUSHER_RECIPES.add(new RockCrusherRecipe(new ItemInstance(GameContent.ITEM_COAL, 2), new ResUseInfo(BaseResources.ORE_COAL), 150));
		RockSolidAPI.ROCK_CRUSHER_RECIPES.add(new RockCrusherRecipe(new ItemInstance(GameContent.ITEM_GLOW_CLUSTER, 2), new ResUseInfo(BaseResources.ORE_SHINING), 150));
		RockSolidAPI.ROCK_CRUSHER_RECIPES.add(new RockCrusherRecipe(new ItemInstance(RockSolidContent.clusterTin, 2), new ResUseInfo(BaseResources.ORE_TIN), 150));
		RockSolidAPI.ROCK_CRUSHER_RECIPES.add(new RockCrusherRecipe(new ItemInstance(RockSolidContent.clusterIron, 2), new ResUseInfo(BaseResources.ORE_IRON), 150));
		RockSolidAPI.ROCK_CRUSHER_RECIPES.add(new RockCrusherRecipe(new ItemInstance(RockSolidContent.clusterMagnesium, 2), new ResUseInfo(BaseResources.ORE_MAGNESIUM), 150));
		RockSolidAPI.ROCK_CRUSHER_RECIPES.add(new RockCrusherRecipe(new ItemInstance(RockSolidContent.clusterUranium, 2), new ResUseInfo(BaseResources.ORE_URANIUM), 150));
		RockSolidAPI.ROCK_CRUSHER_RECIPES.add(new RockCrusherRecipe(new ItemInstance(RockSolidContent.clusterTungsten, 2), new ResUseInfo(BaseResources.ORE_WOLFRAMITE), 150));
		
		// Cluster to grit (separator)
		RockBottomAPI.SEPARATOR_RECIPES.add(new SeparatorRecipe(new ItemInstance(RockSolidContent.gritTin, 2),
				new ResUseInfo(BaseResources.RAW_TIN), 350, new ItemInstance(GameContent.ITEM_SLAG), 0.25f));
		RockBottomAPI.SEPARATOR_RECIPES.add(new SeparatorRecipe(new ItemInstance(RockSolidContent.gritIron, 2),
				new ResUseInfo(BaseResources.RAW_IRON), 350, new ItemInstance(RockSolidContent.gritNickel), 0.2f));
		RockBottomAPI.SEPARATOR_RECIPES.add(new SeparatorRecipe(new ItemInstance(RockSolidContent.gritMagnesium, 2),
				new ResUseInfo(BaseResources.RAW_MAGNESIUM), 350, new ItemInstance(GameContent.ITEM_SLAG), 0.8f));
		RockBottomAPI.SEPARATOR_RECIPES.add(new SeparatorRecipe(new ItemInstance(RockSolidContent.gritUranium, 2),
				new ResUseInfo(BaseResources.RAW_URANIUM), 350, new ItemInstance(GameContent.ITEM_SLAG), 0.5f));
		RockBottomAPI.SEPARATOR_RECIPES.add(new SeparatorRecipe(new ItemInstance(RockSolidContent.gritTungsten, 2),
				new ResUseInfo(BaseResources.RAW_TUNGSTEN), 350, new ItemInstance(GameContent.ITEM_COAL), 0.5f));

		// misc separator recipies
		RockBottomAPI.SEPARATOR_RECIPES.add(new SeparatorRecipe(new ItemInstance(GameContent.ITEM_COPPER_INGOT, 3),
				new ResUseInfo(BaseResources.PROCESSED_BRONZE, 4), 450, new ItemInstance(RockSolidContent.ingotTin),
				1));

		// grit to ingot (smelter)
		RockBottomAPI.SMELTER_RECIPES.add(new SmelterRecipe(new ItemInstance(RockSolidContent.ingotTin),
				new ResUseInfo(BaseResources.PARTLY_PROCESSED_TIN), 300));
		RockBottomAPI.SMELTER_RECIPES.add(new SmelterRecipe(new ItemInstance(RockSolidContent.ingotIron),
				new ResUseInfo(BaseResources.PARTLY_PROCESSED_IRON), 300));
		RockBottomAPI.SMELTER_RECIPES.add(new SmelterRecipe(new ItemInstance(RockSolidContent.ingotNickel),
				new ResUseInfo(BaseResources.PARTLY_PROCESSED_NICKEL), 300));
		RockBottomAPI.SMELTER_RECIPES.add(new SmelterRecipe(new ItemInstance(RockSolidContent.ingotMagnesium),
				new ResUseInfo("partly_processed_magnesum"), 300));
		RockBottomAPI.SMELTER_RECIPES.add(new SmelterRecipe(new ItemInstance(RockSolidContent.ingotUranium),
				new ResUseInfo(BaseResources.PARTLY_PROCESSED_URANIUM), 300));
		RockBottomAPI.SMELTER_RECIPES.add(new SmelterRecipe(new ItemInstance(RockSolidContent.ingotTungsten),
				new ResUseInfo(BaseResources.PARTLY_PROCESSED_TUNGSTEN), 300));

		// misc manual crafting recipies
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.alloySmelter),
				new ResUseInfo(BaseResources.PROCESSED_TIN, 10), new ResUseInfo(ResourceRegistry.PROCESSED_COPPER, 10),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 30),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 15)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.blastFurnace),
				new ResUseInfo(BaseResources.PROCESSED_IRON, 15), new ResUseInfo(BaseResources.PROCESSED_BRONZE, 5),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 20),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 40)));

		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.wrench),
				new ResUseInfo(BaseResources.PROCESSED_IRON, 4), new ResUseInfo(BaseResources.PROCESSED_TIN, 2)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.jetpack),
				new ResUseInfo(BaseResources.COMPRESSED_STEEL, 4), new ResUseInfo(BaseResources.COMPRESSED_TITANIUM, 2),
				new ResUseInfo(BaseResources.PARTLY_PROCESSED_TITANIUM, 5),
				new ResUseInfo(BaseResources.PROCESSED_STEEL, 10), new ResUseInfo(BaseResources.PROCESSED_URANIUM, 5),
				new ResUseInfo(BaseResources.PROCESSED_TITANIUM, 8),
				new ResUseInfo(BaseResources.PROCESSED_MAGNESIUM, 5),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 20)));

		// Rocket Crafting
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.rocketEngine),
				new ResUseInfo(BaseResources.COMPRESSED_TITANIUM, 2),
				new ResUseInfo(BaseResources.TUNGSTEN_CARBIDE, 12), new ResUseInfo(BaseResources.PROCESSED_STEEL, 18),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 150)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.rocketFairing),
				new ResUseInfo(BaseResources.COMPRESSED_TITANIUM, 1),
				new ResUseInfo(BaseResources.TUNGSTEN_CARBIDE, 20), new ResUseInfo(BaseResources.PROCESSED_STEEL, 9),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 280)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(
				new ItemInstance(RockSolidContent.asteroidDataChip), new ResUseInfo(BaseResources.PROCESSED_NICKEL, 8),
				new ResUseInfo(BaseResources.PROCESSED_STEEL, 4),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 20)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.drillCore),
				new ResUseInfo(BaseResources.COMPRESSED_TITANIUM, 1),
				new ResUseInfo(BaseResources.PROCESSED_TUNGSTEN, 8), new ResUseInfo(BaseResources.PROCESSED_STEEL, 4)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.sateliteCore),
				new ResUseInfo(BaseResources.COMPRESSED_STEEL, 1), new ResUseInfo(BaseResources.PROCESSED_MAGNESIUM, 8),
				new ResUseInfo(BaseResources.PROCESSED_TITANIUM, 4)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.rocket),
				new ResUseInfo(BaseResources.TUNGSTEN_CARBIDE, 8), new ResUseInfo(BaseResources.TANK, 2),
				new ItemUseInfo(new ItemInstance(RockSolidContent.rocketEngine)),
				new ItemUseInfo(new ItemInstance(RockSolidContent.rocketFairing)),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 150)));

		// Lantern crafting
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.electricLantern),
				new ResUseInfo(BaseResources.COMPRESSED_STEEL, 1), new ResUseInfo(BaseResources.PROCESSED_STEEL, 10),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 40),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 25)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.lantern),
				new ResUseInfo(BaseResources.RAW_SHINING, 8), new ResUseInfo(BaseResources.PROCESSED_TIN, 10),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 20),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 10)));

		// Electric manual crafting
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(
				new ItemInstance(RockSolidContent.constructionBlockSteel),
				new ResUseInfo(BaseResources.PROCESSED_IRON, 4), new ResUseInfo(BaseResources.PROCESSED_STEEL, 8)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(
				new ItemInstance(RockSolidContent.constructionBlockTitanium),
				new ResUseInfo(BaseResources.PROCESSED_STEEL, 4), new ResUseInfo(BaseResources.PROCESSED_TITANIUM, 8)));

		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.boiler),
				new ResUseInfo(BaseResources.COMPRESSED_STEEL, 1), new ResUseInfo(BaseResources.PROCESSED_COAL, 4),
				new ResUseInfo(BaseResources.PROCESSED_BRONZE, 8), new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 20),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 40)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.liquidBoiler),
				new ResUseInfo(BaseResources.COMPRESSED_TITANIUM, 1),
				new ResUseInfo(BaseResources.PROCESSED_MAGNESIUM, 4), new ResUseInfo(BaseResources.PROCESSED_STEEL, 8),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 30),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 60)));

		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.turbine),
				new ResUseInfo(BaseResources.COMPRESSED_STEEL, 1), new ResUseInfo(BaseResources.PROCESSED_TIN, 6),
				new ResUseInfo(BaseResources.PROCESSED_BRONZE, 12),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 20),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 70)));

		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(
				new ItemInstance(RockSolidContent.combustionEngine), new ResUseInfo(BaseResources.COMPRESSED_STEEL, 3),
				new ResUseInfo(BaseResources.TANK, 1), new ResUseInfo(BaseResources.PROCESSED_STEEL, 4),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 50),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 50)));

		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.nuclearReactor),
				new ResUseInfo(BaseResources.COMPRESSED_STEEL, 2), new ResUseInfo(BaseResources.COMPRESSED_TITANIUM, 2),
				new ResUseInfo(BaseResources.COMPRESSED_URANIUM, 4), new ResUseInfo(BaseResources.PROCESSED_STEEL, 8),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 30),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 50)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.battery),
				new ResUseInfo(BaseResources.COMPRESSED_STEEL, 1), new ResUseInfo(BaseResources.PROCESSED_STEEL, 4),
				new ResUseInfo(BaseResources.PROCESSED_BRONZE, 8), new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 30),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 60)));

		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.tank),
				new ResUseInfo(BaseResources.PROCESSED_STEEL, 4), new ResUseInfo(BaseResources.PROCESSED_BRONZE, 4),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 5), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 10)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.gasTank),
				new ResUseInfo(BaseResources.PROCESSED_STEEL, 4), new ResUseInfo(BaseResources.PROCESSED_TIN, 4),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 15),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 15)));

		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.bucket),
				new ResUseInfo(BaseResources.PROCESSED_IRON, 6), new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 4),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 5)));

		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(
				new ItemInstance(RockSolidContent.energyConduit, 8),
				new ResUseInfo(ResourceRegistry.PROCESSED_COPPER, 8),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 5), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 10)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.fluidConduit, 8),
				new ResUseInfo(BaseResources.PROCESSED_IRON, 8), new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 10),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 10)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.gasConduit, 8),
				new ResUseInfo(BaseResources.PROCESSED_STEEL, 8), new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 10),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 10)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.itemConduit, 8),
				new ResUseInfo(BaseResources.PROCESSED_TIN, 4), new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 5),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 5)));

		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.fluidPump),
				new ResUseInfo(BaseResources.COMPRESSED_STEEL, 1), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 55),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 20), new ResUseInfo(BaseResources.TANK, 1),
				new ResUseInfo(BaseResources.PROCESSED_STEEL, 5)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(
				new ItemInstance(RockSolidContent.electricPurifier), new ResUseInfo(BaseResources.COMPRESSED_STEEL, 2),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 60), new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 40),
				new ResUseInfo(BaseResources.PROCESSED_BRONZE, 30), new ResUseInfo(BaseResources.RAW_CLAY, 10)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.electrolyzer),
				new ResUseInfo(BaseResources.COMPRESSED_TITANIUM, 1), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 100),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 50),
				new ResUseInfo(BaseResources.PROCESSED_MAGNESIUM, 30), new ResUseInfo(BaseResources.TANK, 1)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(
				new ItemInstance(RockSolidContent.electricAlloySmelter),
				new ResUseInfo(BaseResources.COMPRESSED_STEEL, 2), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 50),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 30),
				new ResUseInfo(BaseResources.PROCESSED_BRONZE, 18), new ResUseInfo(BaseResources.PROCESSED_STEEL, 5)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(
				new ItemInstance(RockSolidContent.electricBlastFurnace),
				new ResUseInfo(BaseResources.COMPRESSED_STEEL, 2), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 60),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 40),
				new ResUseInfo(BaseResources.PROCESSED_BRONZE, 30), new ResUseInfo(BaseResources.PROCESSED_COAL, 10)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(
				new ItemInstance(RockSolidContent.electricSeparator), new ResUseInfo(BaseResources.COMPRESSED_STEEL, 2),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 65), new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 50),
				new ResUseInfo(BaseResources.PROCESSED_BRONZE, 25), new ResUseInfo(BaseResources.PROCESSED_IRON, 25)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.electricSmelter),
				new ResUseInfo(BaseResources.COMPRESSED_STEEL, 2), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 35),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 30),
				new ResUseInfo(BaseResources.PROCESSED_BRONZE, 15), new ResUseInfo(BaseResources.PROCESSED_TIN, 10)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.compressor),
				new ResUseInfo(BaseResources.COMPRESSED_STEEL, 2), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 50),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 40),
				new ResUseInfo(BaseResources.PROCESSED_URANIUM, 15),
				new ResUseInfo(BaseResources.PROCESSED_MAGNESIUM, 10)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.charger),
				new ResUseInfo(BaseResources.COMPRESSED_STEEL, 2), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 35),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 30),
				new ResUseInfo(BaseResources.PROCESSED_BRONZE, 25), new ResUseInfo(BaseResources.PROCESSED_TIN, 10),
				new ResUseInfo(BaseResources.PROCESSED_MAGNESIUM, 10)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.quarry),
				new ResUseInfo(BaseResources.COMPRESSED_TITANIUM, 4), new ResUseInfo(BaseResources.COMPRESSED_STEEL, 2),
				new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 150),
				new ResUseInfo(ResourceRegistry.PROCESSED_STONE, 200),
				new ResUseInfo(BaseResources.PROCESSED_STEEL, 15),
				new ResUseInfo(BaseResources.PROCESSED_TITANIUM, 10)));

		// Pickaxe manual crafting
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.pickaxeBronze),
				new ResUseInfo(BaseResources.PROCESSED_BRONZE, 8), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 8)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.pickaxeIron),
				new ResUseInfo(BaseResources.PROCESSED_IRON, 8), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 8)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.pickaxeSteel),
				new ResUseInfo(BaseResources.PROCESSED_STEEL, 8), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 8)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.pickaxeTitanium),
				new ResUseInfo(BaseResources.PROCESSED_TITANIUM, 8), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 8)));

		// Axe manual crafting
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.axeBronze),
				new ResUseInfo(BaseResources.PROCESSED_BRONZE, 8), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 8)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.axeIron),
				new ResUseInfo(BaseResources.PROCESSED_IRON, 8), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 8)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.axeSteel),
				new ResUseInfo(BaseResources.PROCESSED_STEEL, 8), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 8)));
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.axeTitanium),
				new ResUseInfo(BaseResources.PROCESSED_TITANIUM, 8), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 8)));

		// Shovel manual crafting
		RockBottomAPI.CONSTRUCTION_TABLE_RECIPES.add(new BasicRecipe(new ItemInstance(RockSolidContent.shovelIron),
				new ResUseInfo(BaseResources.PROCESSED_IRON, 8), new ResUseInfo(ResourceRegistry.WOOD_BOARDS, 8)));
	}
}
