package com.raphydaphy.rocksolid.util;

import com.google.common.base.Predicate;

import de.ellpeck.rockbottom.api.gui.container.ContainerSlot;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class FilteredSlot extends ContainerSlot
{
	private final Predicate<ItemInstance> accepts;

	public FilteredSlot(IInventory inventory, int slot, int x, int y, Predicate<ItemInstance> accepts)
	{
		super(inventory, slot, x, y);
		this.accepts = accepts;
	}

	public boolean canPlace(ItemInstance instance)
	{
		return accepts.test(instance);
	}

}
