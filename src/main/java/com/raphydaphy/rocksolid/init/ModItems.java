package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.item.ItemBase;
import com.raphydaphy.rocksolid.item.ItemBucket;
import com.raphydaphy.rocksolid.item.ItemWrench;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.resource.IResourceRegistry;
import de.ellpeck.rockbottom.api.construction.resource.ItemUseInfo;
import de.ellpeck.rockbottom.api.construction.resource.ResInfo;
import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemTool;
import de.ellpeck.rockbottom.api.item.ToolType;

public class ModItems
{
	public static Item BUCKET;
	public static Item WRENCH;

	public static Item TIN_CLUSTER;
	public static Item IRON_CLUSTER;
	public static Item MAGNESIUM_CLUSTER;

	public static Item COPPER_GRIT;
	public static Item TIN_GRIT;
	public static Item IRON_GRIT;
	public static Item NICKEL_GRIT;
	public static Item BRONZE_GRIT;
	public static Item MAGNESIUM_GRIT;

	public static Item TIN_INGOT;
	public static Item IRON_INGOT;
	public static Item NICKEL_INGOT;
	public static Item BRONZE_INGOT;
	public static Item STEEL_INGOT;
	public static Item MAGNESIUM_INGOT;
	public static Item IMPURE_TITANIUM_INGOT;
	public static Item TITANIUM_INGOT;

	public static Item BRONZE_PICKAXE;
	public static Item STEEL_PICKAXE;
	public static Item TITANIUM_PICKAXE;

	public static Item BRONZE_AXE;
	public static Item STEEL_AXE;
	public static Item TITANIUM_AXE;

	public static Item BRONZE_SHOVEL;
	public static Item STEEL_SHOVEL;
	public static Item TITANIUM_SHOVEL;

	public static Item COKE;
	public static Item SLAG;

	public static String RES_TIN_RAW;
	public static String RES_IRON_RAW;
	public static String RES_MAGNESIUM_RAW;

	public static String RES_COPPER_CRUSHED;
	public static String RES_TIN_CRUSHED;
	public static String RES_BRONZE_CRUSHED;
	public static String RES_IRON_CRUSHED;
	public static String RES_NICKEL_CRUSHED;
	public static String RES_MAGNESIUM_CRUSHED;

	public static String RES_TITANIUM_IMPURE;

	public static String RES_TIN_PROCESSED;
	public static String RES_BRONZE_PROCESSED;
	public static String RES_IRON_PROCESSED;
	public static String RES_NICKEL_PROCESSED;
	public static String RES_STEEL_PROCESSED;
	public static String RES_MAGNESIUM_PROCESSED;
	public static String RES_TITANIUM_PROCESSED;

	public static String RES_COAL_PROCESSED;

	private static IResourceRegistry res()
	{
		return RockBottomAPI.getResourceRegistry();
	}

	public static void init()
	{
		BUCKET = new ItemBucket();
		WRENCH = new ItemWrench();

		TIN_CLUSTER = new ItemBase("cluster.tin").setMaxAmount(35);
		IRON_CLUSTER = new ItemBase("cluster.iron").setMaxAmount(35);
		MAGNESIUM_CLUSTER = new ItemBase("cluster.magnesium").setMaxAmount(35);

		COPPER_GRIT = new ItemBase("grit.copper").setMaxAmount(40);
		TIN_GRIT = new ItemBase("grit.tin").setMaxAmount(40);
		IRON_GRIT = new ItemBase("grit.iron").setMaxAmount(40);
		NICKEL_GRIT = new ItemBase("grit.nickel").setMaxAmount(40);
		BRONZE_GRIT = new ItemBase("grit.bronze").setMaxAmount(40);
		MAGNESIUM_GRIT = new ItemBase("grit.magnesium").setMaxAmount(40);

		TIN_INGOT = new ItemBase("ingot.tin").setMaxAmount(50);
		IRON_INGOT = new ItemBase("ingot.iron").setMaxAmount(50);
		NICKEL_INGOT = new ItemBase("ingot.nickel").setMaxAmount(50);
		BRONZE_INGOT = new ItemBase("ingot.bronze").setMaxAmount(50);
		STEEL_INGOT = new ItemBase("ingot.steel").setMaxAmount(50);
		MAGNESIUM_INGOT = new ItemBase("ingot.magnesium").setMaxAmount(50);
		IMPURE_TITANIUM_INGOT = new ItemBase("ingot.impure_titanium").setMaxAmount(50);
		TITANIUM_INGOT = new ItemBase("ingot.titanium").setMaxAmount(50);

		BRONZE_PICKAXE = new ItemTool(RockSolid.createRes("pickaxe.bronze"), 6, 500, ToolType.PICKAXE, 15).register();
		STEEL_PICKAXE = new ItemTool(RockSolid.createRes("pickaxe.steel"), 8.5f, 750, ToolType.PICKAXE, 20).register();
		TITANIUM_PICKAXE = new ItemTool(RockSolid.createRes("pickaxe.titanium"), 10f, 1000, ToolType.PICKAXE, 30).register();

		BRONZE_AXE = new ItemTool(RockSolid.createRes("axe.bronze"), 3, 500, ToolType.AXE, 15).register();
		STEEL_AXE = new ItemTool(RockSolid.createRes("axe.steel"), 4.5f, 750, ToolType.AXE, 20).register();
		TITANIUM_AXE = new ItemTool(RockSolid.createRes("axe.titanium"), 6f, 1000, ToolType.AXE, 30).register();

		BRONZE_SHOVEL = new ItemTool(RockSolid.createRes("shovel.bronze"), 3, 500, ToolType.SHOVEL, 15).register();
		STEEL_SHOVEL = new ItemTool(RockSolid.createRes("shovel.steel"), 4.5f, 750, ToolType.SHOVEL, 20).register();
		TITANIUM_SHOVEL = new ItemTool(RockSolid.createRes("shovel.titanium"), 6f, 1000, ToolType.SHOVEL, 30).register();

		COKE = new ItemBase("coal_coke").setMaxAmount(45);
		SLAG = new ItemBase("slag").setMaxAmount(45);

		RES_TIN_RAW = res().addResources("tin_raw", TIN_CLUSTER);
		RES_IRON_RAW = res().addResources("iron_raw", IRON_CLUSTER);
		RES_MAGNESIUM_RAW = res().addResources("magnesium_raw", MAGNESIUM_CLUSTER);

		RES_COPPER_CRUSHED = res().addResources("copper_crushed", COPPER_GRIT);
		RES_TIN_CRUSHED = res().addResources("tin_crushed", TIN_GRIT);
		RES_BRONZE_CRUSHED = res().addResources("bronze_crushed", BRONZE_GRIT);
		RES_IRON_CRUSHED = res().addResources("iron_crushed", IRON_GRIT);
		RES_NICKEL_CRUSHED = res().addResources("nickel_crushed", NICKEL_GRIT);
		RES_MAGNESIUM_CRUSHED = res().addResources("magnesium_crushed", MAGNESIUM_GRIT);

		RES_TITANIUM_IMPURE = res().addResources("titanium_impure", IMPURE_TITANIUM_INGOT);

		RES_TIN_PROCESSED = res().addResources("tin_processed", TIN_INGOT);
		RES_BRONZE_PROCESSED = res().addResources("bronze_processed", BRONZE_INGOT);
		RES_IRON_PROCESSED = res().addResources("iron_processed", IRON_INGOT);
		RES_NICKEL_PROCESSED = res().addResources("nickel_processed", NICKEL_INGOT);
		RES_STEEL_PROCESSED = res().addResources("steel_processed", STEEL_INGOT);
		RES_MAGNESIUM_PROCESSED = res().addResources("magnesium_processed", MAGNESIUM_INGOT);
		RES_TITANIUM_PROCESSED = res().addResources("titanium_processed", TITANIUM_INGOT);

		RES_COAL_PROCESSED = res().addResources("coal_processed", COKE);

	}
}
