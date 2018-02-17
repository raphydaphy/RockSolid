package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.item.ItemBase;
import com.raphydaphy.rocksolid.item.ItemBucket;
import com.raphydaphy.rocksolid.item.ItemWrench;
import de.ellpeck.rockbottom.api.item.Item;

public class ModItems
{
	public static Item BUCKET;
	public static Item WRENCH;

	public static Item COPPER_CLUSTER;
	public static Item TIN_CLUSTER;
	public static Item IRON_CLUSTER;

	public static Item COPPER_GRIT;
	public static Item TIN_GRIT;
	public static Item IRON_GRIT;
	public static Item BRONZE_GRIT;

	public static Item COPPER_INGOT;
	public static Item TIN_INGOT;
	public static Item IRON_INGOT;
	public static Item BRONZE_INGOT;

	public static Item COKE;
	public static Item SLAG;

	public static void init()
	{
		BUCKET = new ItemBucket();
		WRENCH = new ItemWrench();

		COPPER_CLUSTER = new ItemBase("cluster.copper");
		TIN_CLUSTER = new ItemBase("cluster.tin");
		IRON_CLUSTER = new ItemBase("cluster.iron");

		COPPER_GRIT = new ItemBase("grit.copper");
		TIN_GRIT = new ItemBase("grit.tin");
		IRON_GRIT = new ItemBase("grit.iron");
		BRONZE_GRIT = new ItemBase("grit.bronze");

		COPPER_INGOT = new ItemBase("ingot.copper");
		TIN_INGOT = new ItemBase("ingot.tin");
		IRON_INGOT = new ItemBase("ingot.iron");
		BRONZE_INGOT = new ItemBase("ingot.bronze");

		COKE = new ItemBase("coal_coke");
		SLAG = new ItemBase("slag");
	}
}
