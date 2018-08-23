package com.raphydaphy.rocksolid.container.slot;

import com.google.common.base.Predicate;
import com.raphydaphy.rocksolid.gui.component.ComponentAssemblySlot;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentSlot;
import de.ellpeck.rockbottom.api.gui.container.ContainerSlot;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class AssemblySlot extends FilteredSlot
{
	public AssemblySlot(IInventory inventory, int slot, int x, int y, Predicate<ItemInstance> accepts)
	{
		super(inventory, slot, x, y, accepts);
	}

	@Override
	public ComponentSlot getGraphicalSlot(GuiContainer gui, int index, int xOffset, int yOffset) {
		return new ComponentAssemblySlot(gui, this, index, xOffset + this.x, yOffset + this.y);
	}
}
