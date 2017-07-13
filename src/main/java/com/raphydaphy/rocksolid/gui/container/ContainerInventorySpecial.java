package com.raphydaphy.rocksolid.gui.container;

import com.raphydaphy.rocksolid.gui.inventory.JetpackInventory;
import com.raphydaphy.rocksolid.init.ModItems;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;

public class ContainerInventorySpecial extends ItemContainer
{
	public ContainerInventorySpecial(AbstractEntityPlayer player) 
	{
		super(player, player.getInv(), new JetpackInventory(player));
        this.addSlot(new InputSlot(this.containedInventories[1], 0, 80, 15, instance -> instance.getItem().equals(ModItems.jetpack)));
        this.addSlot(new InputSlot(this.containedInventories[1], 1, 100, 15, instance -> false));
        this.addPlayerInventory(player, 20, 40);
    }
}
