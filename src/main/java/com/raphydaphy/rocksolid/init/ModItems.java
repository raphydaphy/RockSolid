package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.item.ItemBucket;
import com.raphydaphy.rocksolid.item.ItemWrench;

import de.ellpeck.rockbottom.api.item.Item;

public class ModItems
{
	public static Item BUCKET;
	public static Item WRENCH;

	public static void init()
	{
		BUCKET = new ItemBucket();
		WRENCH = new ItemWrench();
	}
}
