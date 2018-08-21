package com.raphydaphy.rocksolid.container;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tileentity.TileEntityArcFurnace;
import com.raphydaphy.rocksolid.util.FilteredSlot;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class ContainerArcFurnace extends ItemContainer
{

	public ContainerArcFurnace(AbstractEntityPlayer player, TileEntityArcFurnace tile)
	{
		super(player);
		this.addPlayerInventory(player, 0, 41);
		this.addSlot(new FilteredSlot(tile.getTileInventory(), 0, 27, 1, tile.getTileInventory().getSlots().get(0).getPredicate()));
		this.addSlot(new FilteredSlot(tile.getTileInventory(), 1, 95, 1, tile.getTileInventory().getSlots().get(1).getPredicate()));
	}

	@Override
	public ResourceName getName()
	{
		return RockSolid.createRes("container_arc_furnace");
	}

}
