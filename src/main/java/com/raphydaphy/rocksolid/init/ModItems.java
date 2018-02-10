package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.item.ItemBucket;

import de.ellpeck.rockbottom.api.item.Item;

public class ModItems
{
	public static Item BUCKET;

	public static void init()
	{
		BUCKET = new ItemBucket();
	}
}
