package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.api.RockSolidAPI;
import com.raphydaphy.rocksolid.recipe.AlloySmelterRecipe;
import com.raphydaphy.rocksolid.recipe.BlastFurnaceRecipe;

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
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.ingotBronze, 4), new ItemInstance(GameContent.ITEM_COPPER_INGOT, 3), new ItemInstance(ModItems.ingotTin, 1), 500));
		RockSolidAPI.ALLOY_SMELTER_RECIPES.add(new AlloySmelterRecipe(new ItemInstance(ModItems.ingotSteel, 1), new ItemInstance(ModItems.ingotIron, 1), new ItemInstance(ModItems.gemCoke, 1), 500));
		
		// Arc furnace recipes
		RockSolidAPI.BLAST_FURNACE_RECIPES.add(new BlastFurnaceRecipe(new ItemInstance(ModItems.gemCoke), new ItemInstance(GameContent.ITEM_COAL, 1, 0), 5000));
		RockSolidAPI.BLAST_FURNACE_RECIPES.add(new BlastFurnaceRecipe(new ItemInstance(ModItems.gemCoke), new ItemInstance(GameContent.ITEM_COAL, 1, 1), 5000));
		
		// Cluster to grit (separator)
		RockBottomAPI.SEPARATOR_RECIPES.add(new SeparatorRecipe(new ItemInstance(ModItems.gritTin, 2), new ItemInstance(ModItems.clusterTin), 350, new ItemInstance(GameContent.ITEM_SLAG), 0.25f));
		RockBottomAPI.SEPARATOR_RECIPES.add(new SeparatorRecipe(new ItemInstance(ModItems.gritIron, 2), new ItemInstance(ModItems.clusterIron), 350, new ItemInstance(GameContent.ITEM_SLAG), 0.25f));
		
		// misc separator recipies
		RockBottomAPI.SEPARATOR_RECIPES.add(new SeparatorRecipe(new ItemInstance(GameContent.ITEM_COPPER_INGOT, 3), new ItemInstance(ModItems.ingotBronze, 4), 450, new ItemInstance(ModItems.ingotTin), 1));
		
		// grit to ingot (smelter)
		RockBottomAPI.SMELTER_RECIPES.add(new SmelterRecipe(new ItemInstance(ModItems.ingotTin), new ItemInstance(ModItems.gritTin), 300));
		RockBottomAPI.SMELTER_RECIPES.add(new SmelterRecipe(new ItemInstance(ModItems.ingotIron), new ItemInstance(ModItems.gritIron), 300));
		
		// Remove super pickaxe recipe
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.remove(6);
		
		// misc manual crafting recipies
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModBlocks.alloySmelter), new ItemInstance[]{new ItemInstance(ModItems.ingotTin, 10),new ItemInstance(GameContent.ITEM_COPPER_INGOT, 10),new ItemInstance(GameContent.TILE_WOOD_BOARDS, 30),new ItemInstance(GameContent.TILE_ROCK, 60)}));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModBlocks.blastFurnace), new ItemInstance[]{new ItemInstance(ModItems.ingotIron, 15), new ItemInstance(ModItems.ingotBronze, 5), new ItemInstance(GameContent.TILE_ROCK, 130), new ItemInstance(GameContent.TILE_WOOD_BOARDS, 40)}));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModBlocks.allocator, 8), new ItemInstance[]{new ItemInstance(ModItems.ingotIron, 8), new ItemInstance(ModItems.ingotSteel, 2), new ItemInstance(GameContent.TILE_ROCK, 10), new ItemInstance(GameContent.TILE_WOOD_BOARDS, 5)}));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModBlocks.chest), new ItemInstance[]{new ItemInstance(GameContent.TILE_WOOD_BOARDS, 20)}));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.wrench), new ItemInstance[]{new ItemInstance(ModItems.ingotIron, 4), new ItemInstance(ModItems.ingotSteel, 2)}));
		
		// Electric manual crafting
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModBlocks.constructionBlockSteel), new ItemInstance[]{new ItemInstance(ModItems.ingotIron, 4), new ItemInstance(ModItems.ingotSteel, 8)}));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModBlocks.coalGenerator), new ItemInstance[]{new ItemInstance(ModBlocks.constructionBlockSteel, 1), new ItemInstance(ModItems.gemCoke, 4), new ItemInstance(ModItems.ingotBronze, 8), new ItemInstance(GameContent.TILE_ROCK, 40), new ItemInstance(GameContent.TILE_WOOD_BOARDS, 40)}));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModBlocks.battery), new ItemInstance[]{new ItemInstance(ModBlocks.constructionBlockSteel, 1), new ItemInstance(ModItems.ingotSteel, 4), new ItemInstance(ModItems.ingotBronze, 8), new ItemInstance(GameContent.TILE_ROCK, 80), new ItemInstance(GameContent.TILE_WOOD_BOARDS, 60)}));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModBlocks.energyConduit, 8), new ItemInstance[]{new ItemInstance(ModItems.ingotBronze, 4), new ItemInstance(ModItems.ingotSteel, 4), new ItemInstance(GameContent.TILE_ROCK, 20), new ItemInstance(GameContent.TILE_WOOD_BOARDS, 10)}));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModBlocks.electricAlloySmelter), new ItemInstance[]{new ItemInstance(ModBlocks.constructionBlockSteel, 2), new ItemInstance(GameContent.TILE_WOOD_BOARDS, 50), new ItemInstance(GameContent.TILE_ROCK, 100), new ItemInstance(ModItems.ingotBronze, 18), new ItemInstance(ModItems.ingotSteel, 5), new ItemInstance(GameContent.ITEM_COAL, 20)}));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModBlocks.electricBlastFurnace), new ItemInstance[]{new ItemInstance(ModBlocks.constructionBlockSteel, 2), new ItemInstance(GameContent.TILE_WOOD_BOARDS, 60), new ItemInstance(GameContent.TILE_ROCK, 120), new ItemInstance(ModItems.ingotBronze, 30), new ItemInstance(ModItems.gemCoke, 10), new ItemInstance(GameContent.ITEM_COAL, 30)}));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModBlocks.electricSeparator), new ItemInstance[]{new ItemInstance(ModBlocks.constructionBlockSteel, 2), new ItemInstance(GameContent.TILE_WOOD_BOARDS, 65), new ItemInstance(GameContent.TILE_ROCK, 130), new ItemInstance(ModItems.ingotBronze, 25), new ItemInstance(ModItems.ingotIron, 25), new ItemInstance(GameContent.ITEM_COAL, 35)}));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModBlocks.electricSmelter), new ItemInstance[]{new ItemInstance(ModBlocks.constructionBlockSteel, 2), new ItemInstance(GameContent.TILE_WOOD_BOARDS, 35), new ItemInstance(GameContent.TILE_ROCK, 80), new ItemInstance(ModItems.ingotBronze, 15), new ItemInstance(ModItems.ingotTin, 10), new ItemInstance(GameContent.ITEM_COAL, 15)}));
		
		// Pickaxe manual crafting
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.pickaxeTin), new ItemInstance[]{new ItemInstance(ModItems.ingotTin, 8),new ItemInstance(GameContent.TILE_WOOD_BOARDS, 8)}));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.pickaxeBronze), new ItemInstance[]{new ItemInstance(ModItems.ingotBronze, 8),new ItemInstance(GameContent.TILE_WOOD_BOARDS, 8)}));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.pickaxeCopper), new ItemInstance[]{new ItemInstance(GameContent.ITEM_COPPER_INGOT, 8),new ItemInstance(GameContent.TILE_WOOD_BOARDS, 8)}));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.pickaxeIron), new ItemInstance[]{new ItemInstance(ModItems.ingotIron, 8),new ItemInstance(GameContent.TILE_WOOD_BOARDS, 8)}));
		RockBottomAPI.MANUAL_CONSTRUCTION_RECIPES.add(new BasicRecipe(new ItemInstance(ModItems.pickaxeSteel), new ItemInstance[]{new ItemInstance(ModItems.ingotSteel, 8),new ItemInstance(GameContent.TILE_WOOD_BOARDS, 8)}));
	}
}
