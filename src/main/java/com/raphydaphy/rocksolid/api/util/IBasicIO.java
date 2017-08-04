package com.raphydaphy.rocksolid.api.util;

import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.IInventoryHolder;

public interface IBasicIO extends IInventoryHolder
{
	boolean isValidInput(ItemInstance item);
}
