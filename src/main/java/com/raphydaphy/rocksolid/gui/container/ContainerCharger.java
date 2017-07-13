package com.raphydaphy.rocksolid.gui.container;

import com.raphydaphy.rocksolid.api.IItemWithPower;
import com.raphydaphy.rocksolid.tileentity.TileEntityCharger;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.IInventory;

public class ContainerCharger extends ItemContainer
{
	public ContainerCharger(final AbstractEntityPlayer player, final TileEntityCharger tile) 
	{
        super(player, new IInventory[] { player.getInv(), tile.inventory });
        this.addSlot(new InputSlot(tile.inventory, 0, 90, 30, instance -> IItemWithPower.class.isAssignableFrom(instance.getItem().getClass())));
        this.addPlayerInventory(player, 20, 60);
    }
}
