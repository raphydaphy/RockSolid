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
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class ModRecipies
{
	public static void init()
	{
		// Fuel registration
		RockBottomAPI.FUEL_REGISTRY.put(new ItemInstance(ModItems.gemCoke), 3600);

		// Alloy recipes
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.ingotBronze, 4),
				new ItemInstance(GameContent.ITEM_COPPER_INGOT, 3), new ItemInstance(ModItems.ingotTin, 1), 500));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.ingotSteel, 1),
				new ItemInstance(ModItems.ingotIron, 1), new ItemInstance(ModItems.gemCoke, 1), 500));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.ingotTitanium, 1),
				new ItemInstance(ModItems.ingotMagnesium, 1), new ItemInstance(ModItems.ingotImpureTitanium, 1), 1000));

		// Alloy recipies, reverse
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.ingotBronze, 4),
				new ItemInstance(ModItems.ingotTin, 1), new ItemInstance(GameContent.ITEM_COPPER_INGOT, 3), 500));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.ingotSteel, 1),
				new ItemInstance(ModItems.gemCoke, 1), new ItemInstance(ModItems.ingotIron, 1), 500));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.ingotTitanium, 1),
				new ItemInstance(ModItems.ingotImpureTitanium, 1), new ItemInstance(ModItems.ingotMagnesium, 1), 1000));

		// Cluster tripling recipies
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.gritIron, 3),
				new ItemInstance(ModItems.clusterIron, 1), new ItemInstance(GameContent.ITEM_SLAG, 1), 750));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.gritMagnesium, 3),
				new ItemInstance(ModItems.clusterMagnesium, 1), new ItemInstance(GameContent.ITEM_SLAG, 1), 750));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.gritTin, 3),
				new ItemInstance(ModItems.clusterTin, 1), new ItemInstance(GameContent.ITEM_SLAG, 1), 750));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.gritUranium, 3),
				new ItemInstance(ModItems.clusterUranium, 1), new ItemInstance(GameContent.ITEM_SLAG, 1), 750));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(GameContent.ITEM_COPPER_GRIT, 3),
				new ItemInstance(GameContent.ITEM_COPPER_CLUSTER, 1), new ItemInstance(GameContent.ITEM_SLAG, 1), 750));

		// Cluster tripling recipies, reverse
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.gritIron, 3),
				new ItemInstance(GameContent.ITEM_SLAG, 1), new ItemInstance(ModItems.clusterIron, 1), 750));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.gritMagnesium, 3),
				new ItemInstance(GameContent.ITEM_SLAG, 1), new ItemInstance(ModItems.clusterMagnesium, 1), 750));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.gritTin, 3),
				new ItemInstance(GameContent.ITEM_SLAG, 1), new ItemInstance(ModItems.clusterTin, 1), 750));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.gritUranium, 3),
				new ItemInstance(GameContent.ITEM_SLAG, 1), new ItemInstance(ModItems.clusterUranium, 1), 750));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(GameContent.ITEM_COPPER_GRIT, 3),
				new ItemInstance(GameContent.ITEM_SLAG, 1), new ItemInstance(GameContent.ITEM_COPPER_CLUSTER, 1), 750));

		// Purifier Recipes
		RockSolidAPI.PURIFIER_RECIPES.add(new PurifierRecipe(new ItemInstance(ModItems.clay),
				new ItemInstance(ModItems.clumpClay), ModFluids.fluidWater.toString(), 100, 850));

		// Electrolyzer Recipes
		RockSolidAPI.ELECTROLYZER_RECIPE.add(new ElectrolyzerRecipe(ModGasses.gasHydrogen.toString(),
				ModGasses.gasOxygen.toString(), ModFluids.fluidWater.toString(), 150, 100, 50, 800));

		// Arc furnace recipes
		RockSolidAPI.BLAST_FURNACE_RECIPES.add(new BlastFurnaceRecipe(new ItemInstance(ModItems.gemCoke),
				new ItemInstance(GameContent.ITEM_COAL, 1, 0), 5000));
		RockSolidAPI.BLAST_FURNACE_RECIPES.add(new BlastFurnaceRecipe(new ItemInstance(ModItems.gemCoke),
				new ItemInstance(GameContent.ITEM_COAL, 1, 1), 5000));
		RockSolidAPI.BLAST_FURNACE_RECIPES.add(new BlastFurnaceRecipe(new ItemInstance(ModItems.ingotImpureTitanium),
				new ItemInstance(ModTiles.oreRutile, 1), 5000));

		// Compressor Recipes
		RockSolidAPI.COMPRESSOR_RECIPES.add(new CompressorRecipe(new ItemInstance(ModItems.pelletUranium),
				new ItemInstance(ModItems.ingotUranium, 5), 500));

		// Cluster to grit (separator)
		RockBottomAPI.SEPARATOR_RECIPES.add(new SeparatorRecipe(new ItemInstance(ModItems.gritTin, 2),
				new ItemInstance(ModItems.clusterTin), 350, new ItemInstance(GameContent.ITEM_SLAG), 0.25f));
		RockBottomAPI.SEPARATOR_RECIPES.add(new SeparatorRecipe(new ItemInstance(ModItems.gritIron, 2),
				new ItemInstance(ModItems.clusterIron), 350, new ItemInstance(GameContent.ITEM_SLAG), 0.4f));
		RockBottomAPI.SEPARATOR_RECIPES.add(new SeparatorRecipe(new ItemInstance(ModItems.gritMagnesium, 2),
				new ItemInstance(ModItems.clusterMagnesium), 350, new ItemInstance(GameContent.ITEM_SLAG), 0.8f));
		RockBottomAPI.SEPARATOR_RECIPES.add(new SeparatorRecipe(new ItemInstance(ModItems.gritUranium, 2),
				new ItemInstance(ModItems.clusterUranium), 350, new ItemInstance(GameContent.ITEM_SLAG), 0.5f));

		// misc separator recipies
		RockBottomAPI.SEPARATOR_RECIPES.add(new SeparatorRecipe(new ItemInstance(GameContent.ITEM_COPPER_INGOT, 3),
				new ItemInstance(ModItems.ingotBronze, 4), 450, new ItemInstance(ModItems.ingotTin), 1));

		// grit to ingot (smelter)
		RockBottomAPI.SMELTER_RECIPES
				.add(new SmelterRecipe(new ItemInstance(ModItems.ingotTin), new ItemInstance(ModItems.gritTin), 300));
		RockBottomAPI.SMELTER_RECIPES
				.add(new SmelterRecipe(new ItemInstance(ModItems.ingotIron), new ItemInstance(ModItems.gritIron), 300));
		RockBottomAPI.SMELTER_RECIPES.add(new SmelterRecipe(new ItemInstance(ModItems.ingotMagnesium),
				new ItemInstance(ModItems.gritMagnesium), 300));
		RockBottomAPI.SMELTER_RECIPES.add(new SmelterRecipe(new ItemInstance(ModItems.ingotUranium),
				new ItemInstance(ModItems.gritUranium), 300));

		// Remove super pickaxe recipe
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.remove(6);

		// misc manual crafting recipies
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.alloySmelter),
				new ItemInstance[] { new ItemInstance(ModItems.ingotTin, 10),
						new ItemInstance(GameContent.ITEM_COPPER_INGOT, 10),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 30),
						new ItemInstance(GameContent.TILE_ROCK, 60) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.blastFurnace),
				new ItemInstance[] { new ItemInstance(ModItems.ingotIron, 15),
						new ItemInstance(ModItems.ingotBronze, 5), new ItemInstance(GameContent.TILE_ROCK, 130),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 40) }));

		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.chest),
				new ItemInstance[] { new ItemInstance(GameContent.TILE_WOOD_BOARDS, 20) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(GameContent.TILE_DOOR),
				new ItemInstance[] { new ItemInstance(GameContent.TILE_WOOD_BOARDS, 10),
						new ItemInstance(GameContent.TILE_ROCK, 5) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(GameContent.TILE_LADDER, 4),
				new ItemInstance[] { new ItemInstance(GameContent.TILE_WOOD_BOARDS, 8),
						new ItemInstance(GameContent.TILE_ROCK, 4) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES
				.add(new BasicRecipe(new ItemInstance(ModItems.wrench), new ItemInstance[] {
						new ItemInstance(ModItems.ingotIron, 4), new ItemInstance(ModItems.ingotTin, 2) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.jetpack),
				new ItemInstance[] { new ItemInstance(ModTiles.constructionBlockSteel, 4),
						new ItemInstance(ModTiles.constructionBlockTitanium, 2),
						new ItemInstance(ModItems.ingotImpureTitanium, 5), new ItemInstance(ModItems.ingotSteel, 10),
						new ItemInstance(ModItems.ingotUranium, 5), new ItemInstance(ModItems.ingotTitanium, 8),
						new ItemInstance(ModItems.ingotMagnesium, 5), new ItemInstance(GameContent.TILE_ROCK, 50) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.lantern),
				new ItemInstance[] { new ItemInstance(GameContent.ITEM_GLOW_CLUSTER, 8),
						new ItemInstance(ModItems.ingotTin, 10), new ItemInstance(GameContent.TILE_WOOD_BOARDS, 20),
						new ItemInstance(GameContent.TILE_ROCK, 30) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.electricLantern),
				new ItemInstance[] { new ItemInstance(ModTiles.constructionBlockSteel, 1),
						new ItemInstance(ModItems.ingotSteel, 10), new ItemInstance(GameContent.TILE_WOOD_BOARDS, 40),
						new ItemInstance(GameContent.TILE_ROCK, 80) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.lamp, 2),
				new ItemInstance[] { new ItemInstance(GameContent.ITEM_GLOW_CLUSTER, 2),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 2),
						new ItemInstance(GameContent.TILE_ROCK, 4) }));

		// Electric manual crafting
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES
				.add(new BasicRecipe(new ItemInstance(ModTiles.constructionBlockSteel), new ItemInstance[] {
						new ItemInstance(ModItems.ingotIron, 4), new ItemInstance(ModItems.ingotSteel, 8) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES
				.add(new BasicRecipe(new ItemInstance(ModTiles.constructionBlockTitanium), new ItemInstance[] {
						new ItemInstance(ModItems.ingotSteel, 4), new ItemInstance(ModItems.ingotTitanium, 8) }));

		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.boiler),
				new ItemInstance[] { new ItemInstance(ModTiles.constructionBlockSteel, 1),
						new ItemInstance(ModItems.gemCoke, 4), new ItemInstance(ModItems.ingotBronze, 8),
						new ItemInstance(GameContent.TILE_ROCK, 40),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 40) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.nuclearReactor),
				new ItemInstance[] { new ItemInstance(ModTiles.constructionBlockSteel, 2),
						new ItemInstance(ModTiles.constructionBlockTitanium),
						new ItemInstance(ModItems.pelletUranium, 4), new ItemInstance(ModItems.ingotSteel, 8),
						new ItemInstance(GameContent.TILE_ROCK, 80),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 50) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.battery),
				new ItemInstance[] { new ItemInstance(ModTiles.constructionBlockSteel, 1),
						new ItemInstance(ModItems.ingotSteel, 4), new ItemInstance(ModItems.ingotBronze, 8),
						new ItemInstance(GameContent.TILE_ROCK, 80),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 60) }));

		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.tank),
				new ItemInstance[] { new ItemInstance(ModItems.ingotSteel, 4),
						new ItemInstance(ModItems.ingotBronze, 4), new ItemInstance(GameContent.TILE_ROCK, 10),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 10) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.gasTank),
				new ItemInstance[] { new ItemInstance(ModItems.ingotSteel, 4),
						new ItemInstance(ModItems.ingotTin, 4), new ItemInstance(GameContent.TILE_ROCK, 30),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 15) }));

		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.bucket),
				new ItemInstance[] { new ItemInstance(ModItems.ingotIron, 6),
						new ItemInstance(GameContent.TILE_ROCK, 10),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 5) }));

		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.energyConduit, 8),
				new ItemInstance[] { new ItemInstance(GameContent.ITEM_COPPER_INGOT, 8),
						new ItemInstance(GameContent.TILE_ROCK, 20),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 10) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.fluidConduit, 8),
				new ItemInstance[] { new ItemInstance(ModItems.ingotIron, 8),
						new ItemInstance(GameContent.TILE_ROCK, 20),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 10) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.gasConduit, 8),
				new ItemInstance[] { new ItemInstance(ModItems.ingotSteel, 8),
						new ItemInstance(GameContent.TILE_ROCK, 20),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 10) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.allocator, 8),
				new ItemInstance[] { new ItemInstance(ModItems.ingotIron, 4), new ItemInstance(ModItems.ingotTin, 4),
						new ItemInstance(GameContent.TILE_ROCK, 10),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 5) }));

		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.fluidPump),
				new ItemInstance[] { new ItemInstance(ModTiles.constructionBlockSteel, 1),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 55), new ItemInstance(GameContent.TILE_ROCK, 50),
						new ItemInstance(ModTiles.tank, 1), new ItemInstance(ModItems.ingotSteel, 5) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.electricPurifier),
				new ItemInstance[] { new ItemInstance(ModTiles.constructionBlockSteel, 2),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 60),
						new ItemInstance(GameContent.TILE_ROCK, 120), new ItemInstance(ModItems.ingotBronze, 30),
						new ItemInstance(ModItems.clumpClay, 10) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.electrolyzer),
				new ItemInstance[] { new ItemInstance(ModTiles.constructionBlockTitanium, 1),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 100),
						new ItemInstance(GameContent.TILE_ROCK, 150), new ItemInstance(ModItems.ingotMagnesium, 30),
						new ItemInstance(ModTiles.tank, 1) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.electricAlloySmelter),
				new ItemInstance[] { new ItemInstance(ModTiles.constructionBlockSteel, 2),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 50),
						new ItemInstance(GameContent.TILE_ROCK, 100), new ItemInstance(ModItems.ingotBronze, 18),
						new ItemInstance(ModItems.ingotSteel, 5) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.electricBlastFurnace),
				new ItemInstance[] { new ItemInstance(ModTiles.constructionBlockSteel, 2),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 60),
						new ItemInstance(GameContent.TILE_ROCK, 120), new ItemInstance(ModItems.ingotBronze, 30),
						new ItemInstance(ModItems.gemCoke, 10) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.electricSeparator),
				new ItemInstance[] { new ItemInstance(ModTiles.constructionBlockSteel, 2),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 65),
						new ItemInstance(GameContent.TILE_ROCK, 130), new ItemInstance(ModItems.ingotBronze, 25),
						new ItemInstance(ModItems.ingotIron, 25) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.electricSmelter),
				new ItemInstance[] { new ItemInstance(ModTiles.constructionBlockSteel, 2),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 35), new ItemInstance(GameContent.TILE_ROCK, 80),
						new ItemInstance(ModItems.ingotBronze, 15), new ItemInstance(ModItems.ingotTin, 10) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.compressor),
				new ItemInstance[] { new ItemInstance(ModTiles.constructionBlockSteel, 2),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 55), new ItemInstance(GameContent.TILE_ROCK, 50),
						new ItemInstance(ModItems.ingotUranium, 15), new ItemInstance(ModItems.ingotMagnesium, 10) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.charger),
				new ItemInstance[] { new ItemInstance(ModTiles.constructionBlockSteel, 2),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 35), new ItemInstance(GameContent.TILE_ROCK, 80),
						new ItemInstance(ModItems.ingotBronze, 25), new ItemInstance(ModItems.ingotTin, 10),
						new ItemInstance(ModItems.ingotMagnesium, 10) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModTiles.quarry),
				new ItemInstance[] { new ItemInstance(ModTiles.constructionBlockTitanium, 4),
						new ItemInstance(ModTiles.constructionBlockSteel, 2),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 150),
						new ItemInstance(GameContent.TILE_ROCK, 350), new ItemInstance(ModItems.ingotSteel, 15),
						new ItemInstance(ModItems.ingotTitanium, 10) }));

		// Pickaxe manual crafting
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.pickaxeBronze),
				new ItemInstance[] { new ItemInstance(ModItems.ingotBronze, 8),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 8) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.pickaxeCopper),
				new ItemInstance[] { new ItemInstance(GameContent.ITEM_COPPER_INGOT, 8),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 8) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES
				.add(new BasicRecipe(new ItemInstance(ModItems.pickaxeIron), new ItemInstance[] {
						new ItemInstance(ModItems.ingotIron, 8), new ItemInstance(GameContent.TILE_WOOD_BOARDS, 8) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES
				.add(new BasicRecipe(new ItemInstance(ModItems.pickaxeSteel), new ItemInstance[] {
						new ItemInstance(ModItems.ingotSteel, 8), new ItemInstance(GameContent.TILE_WOOD_BOARDS, 8) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.pickaxeTitanium),
				new ItemInstance[] { new ItemInstance(ModItems.ingotTitanium, 8),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 8) }));

		// Axe manual crafting
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.axeWood),
				new ItemInstance[] { new ItemInstance(GameContent.TILE_WOOD_BOARDS, 16) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.axeRock),
				new ItemInstance[] { new ItemInstance(GameContent.TILE_ROCK, 8),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 8) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.axeCopper),
				new ItemInstance[] { new ItemInstance(GameContent.ITEM_COPPER_INGOT, 8),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 8) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.axeBronze),
				new ItemInstance[] { new ItemInstance(ModItems.ingotBronze, 8),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 8) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES
				.add(new BasicRecipe(new ItemInstance(ModItems.axeIron), new ItemInstance[] {
						new ItemInstance(ModItems.ingotIron, 8), new ItemInstance(GameContent.TILE_WOOD_BOARDS, 8) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES
				.add(new BasicRecipe(new ItemInstance(ModItems.axeSteel), new ItemInstance[] {
						new ItemInstance(ModItems.ingotSteel, 8), new ItemInstance(GameContent.TILE_WOOD_BOARDS, 8) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.axeTitanium),
				new ItemInstance[] { new ItemInstance(ModItems.ingotTitanium, 8),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 8) }));

		// Shovel manual crafting
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.shovelWood),
				new ItemInstance[] { new ItemInstance(GameContent.TILE_WOOD_BOARDS, 16) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.shovelRock),
				new ItemInstance[] { new ItemInstance(GameContent.TILE_ROCK, 8),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 8) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.shovelCopper),
				new ItemInstance[] { new ItemInstance(GameContent.ITEM_COPPER_INGOT, 8),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 8) }));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.shovelIron),
				new ItemInstance[] { new ItemInstance(ModItems.ingotBronze, 8),
						new ItemInstance(GameContent.TILE_WOOD_BOARDS, 8) }));
	}
}
