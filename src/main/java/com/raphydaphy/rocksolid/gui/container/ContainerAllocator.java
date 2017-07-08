package com.raphydaphy.rocksolid.gui.container;

import com.raphydaphy.rocksolid.tileentity.TileEntityAllocator;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.IInventory;

public class ContainerAllocator extends ItemContainer
{
	public ContainerAllocator(final AbstractEntityPlayer player, final TileEntityAllocator tile) 
	{
        super(player, new IInventory[] { player.getInv(), tile.inventory });
        this.addSlot(new InputSlot(tile.inventory, 0, 40, 10));
        this.addSlot(new InputSlot(tile.inventory, 1, 60, 10));
        this.addSlot(new InputSlot(tile.inventory, 2, 80, 10));
        this.addSlot(new InputSlot(tile.inventory, 3, 100, 10));
        this.addSlot(new InputSlot(tile.inventory, 4, 120, 10));
        this.addSlot(new InputSlot(tile.inventory, 5, 140, 10));
        this.addPlayerInventory(player, 20, 60);
    }
}
