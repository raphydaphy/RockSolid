package com.raphydaphy.rocksolid.gui.container;

import com.raphydaphy.rocksolid.gui.inventory.JetpackInventory;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;

public class ContainerInventorySpecial extends ItemContainer
{
	public ContainerInventorySpecial(final AbstractEntityPlayer player) 
	{
		super(player, player.getInv(), new JetpackInventory());
        this.addSlot(new InputSlot(this.containedInventories[1], 0, 85, 20));
        this.addPlayerInventory(player, 20, 40);
    }
}
