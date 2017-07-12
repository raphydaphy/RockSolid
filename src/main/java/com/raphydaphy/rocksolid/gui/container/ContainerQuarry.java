package com.raphydaphy.rocksolid.gui.container;

import com.raphydaphy.rocksolid.tileentity.TileEntityQuarry;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.IInventory;

public class ContainerQuarry extends ItemContainer
{
	public ContainerQuarry(final AbstractEntityPlayer player, final TileEntityQuarry tile) 
	{
        super(player, new IInventory[] { player.getInv(), tile.inventory });
        this.addSlot(new OutputSlot(tile.inventory, 0, 90, 10));
        this.addPlayerInventory(player, 20, 60);
    }
}