package com.raphydaphy.rocksolid.gui.slot;

import java.util.function.Predicate;

import de.ellpeck.rockbottom.api.gui.container.ContainerSlot;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class InputSlot extends ContainerSlot
{
	private final Predicate<ItemInstance> allowedInput;
	private final boolean isUnlocked;

	public InputSlot(final IInventory inventory, final int slot, final int x, final int y,
			final Predicate<ItemInstance> allowedInput)
	{
		super(inventory, slot, x, y);
		this.allowedInput = allowedInput;
		isUnlocked = false;
	}

	public InputSlot(final IInventory inventory, final int slot, final int x, final int y)
	{
		super(inventory, slot, x, y);
		isUnlocked = true;
		allowedInput = null;
	}

	@Override
	public boolean canPlace(final ItemInstance instance)
	{
		if (isUnlocked)
		{
			return true;
		} else
		{
			return this.allowedInput.test(instance);
		}
	}
}
