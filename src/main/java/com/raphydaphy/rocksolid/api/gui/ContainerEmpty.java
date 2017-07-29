package com.raphydaphy.rocksolid.api.gui;

import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class ContainerEmpty extends ItemContainer
{
	public ContainerEmpty(final AbstractEntityPlayer player) 
	{
        super(player, new IInventory[] { player.getInv() });
        this.addPlayerInventory(player, 20, 60);
    }
	
	@Override
	public IResourceName getName() 
	{
		return RockSolidLib.makeRes("containerEmpty");
	}
}
