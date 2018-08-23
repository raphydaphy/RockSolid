package com.raphydaphy.rocksolid.container;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tileentity.TileEntitySeparator;
import com.raphydaphy.rocksolid.container.slot.FilteredSlot;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.gui.container.OutputSlot;
import de.ellpeck.rockbottom.api.gui.container.RestrictedInputSlot;
import de.ellpeck.rockbottom.api.tile.entity.IFilteredInventory;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class ContainerSeparator extends ItemContainer
{

	public ContainerSeparator(AbstractEntityPlayer player, TileEntitySeparator tile)
	{
		super(player);
		this.addPlayerInventory(player, 0, 50);
		IFilteredInventory inv = tile.getTileInventory();
		this.addSlot(new RestrictedInputSlot(inv, 0, 51, 20));
		this.addSlot(new RestrictedInputSlot(inv, 1, 17, 0));
		this.addSlot(new OutputSlot(inv, 2, 85, 0));
		this.addSlot(new OutputSlot(inv, 3, 102, 0));
	}

	@Override
	public ResourceName getName()
	{
		return RockSolid.createRes("container_separator");
	}

}