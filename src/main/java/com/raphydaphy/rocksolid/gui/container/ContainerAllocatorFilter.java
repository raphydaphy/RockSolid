package com.raphydaphy.rocksolid.gui.container;

import com.raphydaphy.rocksolid.gui.slot.InputSlot;
import com.raphydaphy.rocksolid.tileentity.TileEntityAllocator;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.IInventory;

public class ContainerAllocatorFilter extends ItemContainer
{
	public ContainerAllocatorFilter(final AbstractEntityPlayer player, final TileEntityAllocator tile) 
	{
        super(player, new IInventory[] { player.getInv(), tile.inventory });
        this.addSlot(new InputSlot(tile.inventory, 0, 40, 10));
        this.addPlayerInventory(player, 20, 60);
    }
}
