package com.raphydaphy.rocksolid.item;

import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ItemTile;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class ItemSensitiveTile extends ItemTile
{
	public ItemSensitiveTile(ResourceName name)
	{
		super(name);
	}

	@Override
	public boolean isDataSensitive(ItemInstance item)
	{
		return true;
	}
}
