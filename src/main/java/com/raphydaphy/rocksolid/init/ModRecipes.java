package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.recipe.AlloySmelterRecipe;
import com.raphydaphy.rocksolid.recipe.SeparatorRecipe;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.construction.BasicRecipe;
import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.ParentedNameRegistry;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class ModRecipes
{
	public static final ParentedNameRegistry<BasicRecipe> ASSEMBLY_STATION_RECIPES = new ParentedNameRegistry<>(RockSolid.createRes("assembly_station_registry"), true, Registries.ALL_CONSTRUCTION_RECIPES).register();

	public static void init()
	{
		new SeparatorRecipe(GameContent.RES_COPPER_RAW, ModItems.COPPER_GRIT).register();
		new SeparatorRecipe(ModItems.RES_TIN_RAW, ModItems.TIN_GRIT).register();
		new SeparatorRecipe(ModItems.RES_IRON_RAW, ModItems.IRON_GRIT).register();

		new AlloySmelterRecipe(GameContent.RES_COPPER_PROCESSED, 3, ModItems.RES_TIN_PROCESSED, 1, ModItems.BRONZE_INGOT, 4).register();
		new AlloySmelterRecipe(ModItems.RES_COPPER_CRUSHED, 3, ModItems.RES_TIN_CRUSHED, 1, ModItems.BRONZE_GRIT, 4).register();
		new AlloySmelterRecipe(ModItems.RES_IRON_PROCESSED, 1, ModItems.RES_COAL_PROCESSED, 1, ModItems.STEEL_INGOT, 1).register();

		new BasicRecipe(0.8f, new ItemInstance(ModTiles.BOILER), new ResUseInfo(GameContent.RES_STONE, 55), new ResUseInfo(ModItems.RES_STEEL_PROCESSED, 8), new ResUseInfo(ModItems.RES_IRON_PROCESSED, 12), new ResUseInfo(ModItems.RES_BRONZE_PROCESSED, 4)).register(ASSEMBLY_STATION_RECIPES);
		new BasicRecipe(0.6f, new ItemInstance(ModTiles.TURBINE), new ResUseInfo(GameContent.RES_STONE, 40), new ResUseInfo(ModItems.RES_STEEL_PROCESSED, 5), new ResUseInfo(ModItems.RES_IRON_PROCESSED, 8), new ResUseInfo(ModItems.RES_BRONZE_PROCESSED, 16)).register(ASSEMBLY_STATION_RECIPES);
		new BasicRecipe(0.5f, new ItemInstance(ModItems.WRENCH), new ResUseInfo(GameContent.RES_STICK, 8), new ResUseInfo(ModItems.RES_STEEL_PROCESSED, 4)).register(ASSEMBLY_STATION_RECIPES);
		new BasicRecipe(0.2f, new ItemInstance(ModTiles.ENERGY_CONDUIT, 8), new ResUseInfo(GameContent.RES_STONE, 15), new ResUseInfo(GameContent.RES_COPPER_PROCESSED, 6)).register(ASSEMBLY_STATION_RECIPES);
		new BasicRecipe(0.2f, new ItemInstance(ModTiles.ITEM_CONDUIT, 8), new ResUseInfo(GameContent.RES_STONE, 10), new ResUseInfo(ModItems.RES_IRON_PROCESSED, 5)).register(ASSEMBLY_STATION_RECIPES);
		new BasicRecipe(0.2f, new ItemInstance(ModTiles.FLUID_CONDUIT, 8), new ResUseInfo(GameContent.RES_STONE, 20), new ResUseInfo(ModItems.RES_TIN_PROCESSED, 7)).register(ASSEMBLY_STATION_RECIPES);
		new BasicRecipe(0.2f, new ItemInstance(ModTiles.GAS_CONDUIT, 8), new ResUseInfo(GameContent.RES_STONE, 20), new ResUseInfo(ModItems.RES_BRONZE_PROCESSED, 5)).register(ASSEMBLY_STATION_RECIPES);
		new BasicRecipe(0.5f, new ItemInstance(ModTiles.BATTERY), new ResUseInfo(GameContent.RES_STONE, 85), new ResUseInfo(ModItems.RES_STEEL_PROCESSED, 6), new ResUseInfo(ModItems.RES_IRON_PROCESSED, 4), new ResUseInfo(GameContent.RES_COPPER_PROCESSED, 20)).register(ASSEMBLY_STATION_RECIPES);
		new BasicRecipe(0.55f, new ItemInstance(ModTiles.ELECTRIC_FURNACE), new ResUseInfo(GameContent.RES_STONE, 30), new ResUseInfo(ModItems.RES_STEEL_PROCESSED, 5), new ResUseInfo(GameContent.RES_COAL, 16), new ResUseInfo(ModItems.RES_BRONZE_PROCESSED, 8)).register(ASSEMBLY_STATION_RECIPES);
		new BasicRecipe(0.6f, new ItemInstance(ModTiles.ELECTRIC_SEPARATOR), new ResUseInfo(GameContent.RES_STONE, 35), new ResUseInfo(ModItems.RES_STEEL_PROCESSED, 7), new ResUseInfo(GameContent.RES_COAL, 12), new ResUseInfo(ModItems.RES_TIN_PROCESSED, 14)).register(ASSEMBLY_STATION_RECIPES);
		new BasicRecipe(0.6f, new ItemInstance(ModTiles.PUMP), new ResUseInfo(GameContent.RES_STONE, 40), new ResUseInfo(ModItems.RES_STEEL_PROCESSED, 3), new ResUseInfo(ModItems.RES_TIN_PROCESSED, 18), new ResUseInfo(GameContent.RES_SAND, 30)).register(ASSEMBLY_STATION_RECIPES);
	}
}
