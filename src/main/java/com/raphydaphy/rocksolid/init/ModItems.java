package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.item.ItemBase;
import com.raphydaphy.rocksolid.item.ItemBucket;
import com.raphydaphy.rocksolid.item.ItemWrench;
import de.ellpeck.rockbottom.api.item.Item;

public class ModItems
{
	public static Item BUCKET;
	public static Item WRENCH;

	public static Item COPPER_CLUSTER:
	public static Item TIN_CLUSTER;
	public static Item IRON_CLUSTER;

	public static Item COKE;

	public static void init()
	{
		BUCKET = new ItemBucket();
		WRENCH = new ItemWrench();

		COPPER_CLUSTER = new ItemBase("copper_cluster");
		TIN_CLUSTER = new ItemBase("tin_cluster");
		IRON_CLUSTER = new ItemBase("iron_cluster");

		COKE = new ItemBase("coal_coke");
	}
}
