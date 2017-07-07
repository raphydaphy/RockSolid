package com.raphydaphy.rocksolid.gui.container;

import com.raphydaphy.rocksolid.tileentity.TileEntityArcFurnace;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.IInventory;

public class ContainerArcFurnace extends ItemContainer
{
	public ContainerArcFurnace(final AbstractEntityPlayer player, final TileEntityArcFurnace tile) 
	{
        super(player, new IInventory[] { player.getInv(), tile.inventory });
        this.addSlot(new InputSlot(tile.inventory, 0, 40, 10));
        this.addSlot(new OutputSlot(tile.inventory, 1, 140, 10));
        this.addPlayerInventory(player, 20, 60);
    }
}
