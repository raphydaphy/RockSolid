package com.raphydaphy.rocksolid.api.gui;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.IInventory;

public class ContainerEmpty extends ItemContainer
{
	public ContainerEmpty(final AbstractEntityPlayer player) 
	{
        super(player, new IInventory[] { player.getInv() });
        this.addPlayerInventory(player, 20, 60);
    }
}
