package com.raphydaphy.rocksolid.api.gui;

import de.ellpeck.rockbottom.api.gui.container.ContainerSlot;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class OutputSlot extends ContainerSlot
{
	public OutputSlot(final IInventory inventory, final int slot, final int x, final int y)
	{
		super(inventory, slot, x, y);
	}

	@Override
	public boolean canPlace(final ItemInstance instance)
	{
		return false;
	}
}
