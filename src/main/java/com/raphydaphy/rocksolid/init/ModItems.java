package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.item.ItemBase;
import com.raphydaphy.rocksolid.item.ItemBucket;
import com.raphydaphy.rocksolid.item.ItemWrench;
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

	public static Item BRONZE_AXE;

	public static Item BRONZE_SHOVEL;

	public static Item COKE;
	public static Item SLAG;

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

		BRONZE_AXE = new ItemTool(RockSolid.createRes("axe.bronze"), 3, 500, ToolType.AXE, 15).register();

		BRONZE_SHOVEL = new ItemTool(RockSolid.createRes("shovel.bronze"), 3, 500, ToolType.SHOVEL, 15).register();

		COKE = new ItemBase("coal_coke").setMaxAmount(45);
		SLAG = new ItemBase("slag").setMaxAmount(45);
	}
}
