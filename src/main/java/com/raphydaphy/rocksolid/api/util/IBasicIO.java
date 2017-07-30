package com.raphydaphy.rocksolid.api.util;

import de.ellpeck.rockbottom.api.item.ItemInstance;

public interface IBasicIO extends IHasInventory
{
	boolean isValidInput(ItemInstance item);
}
