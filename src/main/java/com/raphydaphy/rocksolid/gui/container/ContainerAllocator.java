package com.raphydaphy.rocksolid.gui.container;

import com.raphydaphy.rocksolid.gui.slot.InputSlot;
import com.raphydaphy.rocksolid.tileentity.TileEntityAllocator;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class ContainerAllocator extends ItemContainer
{
	public ContainerAllocator(final AbstractEntityPlayer player, final TileEntityAllocator tile) 
	{
        super(player, new IInventory[] { player.getInv(), tile.inventory });
        this.addSlot(new InputSlot(tile.inventory, 0, 20, 10));
        this.addSlot(new InputSlot(tile.inventory, 1, 40, 10));
        this.addSlot(new InputSlot(tile.inventory, 2, 60, 10));
        this.addSlot(new InputSlot(tile.inventory, 3, 80, 10));
        this.addSlot(new InputSlot(tile.inventory, 4, 100, 10));
        this.addSlot(new InputSlot(tile.inventory, 5, 160, 10));
        this.addPlayerInventory(player, 20, 60);
    }

	@Override
	public IResourceName getName() 
	{
		return RockSolidLib.makeRes("containerAllocator");
	}
}
