package com.raphydaphy.rocksolid.gui.container;

import com.raphydaphy.rocksolid.gui.slot.InputSlot;
import com.raphydaphy.rocksolid.init.ModItems;
import com.raphydaphy.rocksolid.tileentity.TileEntityNuclearReactor;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.IInventory;

public class ContainerNuclearReactor extends ItemContainer
{
	public ContainerNuclearReactor(final AbstractEntityPlayer player, final TileEntityNuclearReactor tile) 
	{
        super(player, new IInventory[] { player.getInv(), tile.inventory });
        this.addSlot(new InputSlot(tile.inventory, 0, 90, 30, instance -> (instance.getItem().equals(ModItems.pelletUranium))));
        this.addPlayerInventory(player, 20, 60);
    }
}
