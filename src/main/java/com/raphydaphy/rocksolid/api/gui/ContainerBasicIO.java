package com.raphydaphy.rocksolid.api.gui;

import com.raphydaphy.rocksolid.api.util.IBasicIO;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class ContainerBasicIO extends ItemContainer
{
	public ContainerBasicIO(final AbstractEntityPlayer player, final IBasicIO tile)
	{
		super(player, new IInventory[] { player.getInv(), tile.getInventory() });
		this.addSlot(new InputSlot(tile.getInventory(), 0, 40, 10, instance -> tile.isValidInput(instance)));
		this.addSlot(new OutputSlot(tile.getInventory(), 1, 140, 10));
		this.addPlayerInventory(player, 20, 60);
	}

	@Override
	public IResourceName getName()
	{
		return RockSolidAPILib.makeInternalRes("containerBasicIO");
	}
}
