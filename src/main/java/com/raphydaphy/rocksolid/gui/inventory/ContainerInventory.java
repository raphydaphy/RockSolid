package com.raphydaphy.rocksolid.gui.inventory;

import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;

public class ContainerInventory extends Inventory
{
	 public ContainerInventory(final TileEntity tile, final int slotAmount) 
	 {
        super(slotAmount);
        this.addChangeCallback((inv, slot, newInstance) -> tile.world.setDirty(tile.x, tile.y));
    }
}
