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

	public static Item COPPER_GRIT;
	public static Item TIN_GRIT;
	public static Item IRON_GRIT;
	public static Item BRONZE_GRIT;

	public static Item TIN_INGOT;
	public static Item IRON_INGOT;
	public static Item BRONZE_INGOT;
	public static Item STEEL_INGOT;

	public static Item BRONZE_PICKAXE;
	public static Item STEEL_PICKAXE;

	public static Item BRONZE_AXE;
	public static Item STEEL_AXE;

	public static Item BRONZE_SHOVEL;
	public static Item STEEL_SHOVEL;

	public static Item COKE;
	public static Item SLAG;

	public static String RES_TIN_RAW;
	public static String RES_IRON_RAW;

	public static String RES_COPPER_CRUSHED;
	public static String RES_TIN_CRUSHED;
	public static String RES_BRONZE_CRUSHED;
	public static String RES_IRON_CRUSHED;

	public static String RES_TIN_PROCESSED;
	public static String RES_BRONZE_PROCESSED;
	public static String RES_IRON_PROCESSED;
	public static String RES_STEEL_PROCESSED;

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

		COPPER_GRIT = new ItemBase("grit.copper").setMaxAmount(40);
		TIN_GRIT = new ItemBase("grit.tin").setMaxAmount(40);
		IRON_GRIT = new ItemBase("grit.iron").setMaxAmount(40);
		BRONZE_GRIT = new ItemBase("grit.bronze").setMaxAmount(40);

		TIN_INGOT = new ItemBase("ingot.tin").setMaxAmount(50);
		IRON_INGOT = new ItemBase("ingot.iron").setMaxAmount(50);
		BRONZE_INGOT = new ItemBase("ingot.bronze").setMaxAmount(50);
		STEEL_INGOT = new ItemBase("ingot.steel").setMaxAmount(50);

		BRONZE_PICKAXE = new ItemTool(RockSolid.createRes("pickaxe.bronze"), 6, 500, ToolType.PICKAXE, 15).register();
		STEEL_PICKAXE = new ItemTool(RockSolid.createRes("pickaxe.steel"), 8.5f, 750, ToolType.PICKAXE, 20).register();

		BRONZE_AXE = new ItemTool(RockSolid.createRes("axe.bronze"), 3, 500, ToolType.AXE, 15).register();
		STEEL_AXE = new ItemTool(RockSolid.createRes("axe.steel"), 4.5f, 750, ToolType.AXE, 20).register();

		BRONZE_SHOVEL = new ItemTool(RockSolid.createRes("shovel.bronze"), 3, 500, ToolType.SHOVEL, 15).register();
		STEEL_SHOVEL = new ItemTool(RockSolid.createRes("shovel.steel"), 4.5f, 750, ToolType.SHOVEL, 20).register();

		COKE = new ItemBase("coal_coke").setMaxAmount(45);
		SLAG = new ItemBase("slag").setMaxAmount(45);

		RES_TIN_RAW = res().addResources("tin_raw", ModItems.TIN_CLUSTER);
		RES_IRON_RAW = res().addResources("iron_raw", ModItems.IRON_CLUSTER);

		RES_COPPER_CRUSHED = res().addResources("copper_crushed", ModItems.COPPER_GRIT);
		RES_TIN_CRUSHED = res().addResources("tin_crushed", ModItems.TIN_GRIT);
		RES_BRONZE_CRUSHED = res().addResources("bronze_crushed", ModItems.BRONZE_GRIT);
		RES_IRON_CRUSHED = res().addResources("iron_crushed", ModItems.IRON_GRIT);

		RES_TIN_PROCESSED = res().addResources("tin_processed", ModItems.TIN_INGOT);
		RES_BRONZE_PROCESSED = res().addResources("bronze_processed", ModItems.BRONZE_INGOT);
		RES_IRON_PROCESSED = res().addResources("iron_processed", ModItems.IRON_INGOT);
		RES_STEEL_PROCESSED = res().addResources("steel_processed", ModItems.STEEL_INGOT);

		RES_COAL_PROCESSED = res().addResources("coal_processed", ModItems.COKE);

	}
}
