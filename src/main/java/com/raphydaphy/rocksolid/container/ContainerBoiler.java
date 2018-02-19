package com.raphydaphy.rocksolid.container;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tileentity.TileEntityBoiler;
import com.raphydaphy.rocksolid.util.FilteredSlot;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class ContainerBoiler extends ItemContainer
{

	public ContainerBoiler(AbstractEntityPlayer player, TileEntityBoiler tile)
	{
		super(player, player.getInv(), tile.getTileInventory());
		this.addPlayerInventory(player, 0, 61);
		this.addSlot(new FilteredSlot(tile.getTileInventory(), 0, 53, 16, tile.getTileInventory().getSlots().get(0).getPredicate()));
	}

	@Override
	public IResourceName getName()
	{
		return RockSolid.createRes("container_boiler");
	}

}
