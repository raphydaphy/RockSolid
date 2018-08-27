package com.raphydaphy.rocksolid.container.slot;

import com.raphydaphy.rocksolid.gui.component.ComponentAssemblySlot;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentSlot;
import de.ellpeck.rockbottom.api.gui.container.RestrictedInputSlot;
import de.ellpeck.rockbottom.api.tile.entity.IFilteredInventory;

public class AssemblySlot extends RestrictedInputSlot
{

	public AssemblySlot(IFilteredInventory inventory, int slot, int x, int y)
	{
		super(inventory, slot, x, y);
	}

	@Override
	public ComponentSlot getGraphicalSlot(GuiContainer gui, int index, int xOffset, int yOffset) {
		return new ComponentAssemblySlot(gui, this, index, xOffset + this.x, yOffset + this.y);
	}
}
