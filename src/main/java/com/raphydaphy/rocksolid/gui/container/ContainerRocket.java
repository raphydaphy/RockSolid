package com.raphydaphy.rocksolid.gui.container;

import com.raphydaphy.rocksolid.api.gui.InputSlot;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.tileentity.TileEntityRocket;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class ContainerRocket extends ItemContainer
{
	public ContainerRocket(final AbstractEntityPlayer player, final TileEntityRocket tile)
	{
		super(player, new IInventory[] { player.getInv(), tile.inventory });
		this.addSlotGrid(tile.getInventory(), 0, tile.getInventory().getSlotAmount() - 2, 20, 45, 8);
		this.addSlot(new InputSlot(tile.getInventory(), 16, 40, 18));
		this.addSlot(new InputSlot(tile.getInventory(), 17, 60, 18));
		this.addPlayerInventory(player, 20, 100);
	}

	@Override
	public IResourceName getName()
	{
		return RockSolidAPILib.makeInternalRes("containerRocket");
	}
}
