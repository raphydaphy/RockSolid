package com.raphydaphy.rocksolid.container;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tileentity.TileEntityAlloySmelter;
import com.raphydaphy.rocksolid.util.FilteredSlot;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class ContainerAlloySmelter extends ItemContainer
{

	public ContainerAlloySmelter(AbstractEntityPlayer player, TileEntityAlloySmelter tile)
	{
		super(player);
		this.addPlayerInventory(player, 0, 50);
		this.addSlot(new FilteredSlot(tile.getTileInventory(), 0, 67, 20, tile.getTileInventory().getSlots().get(0).getPredicate()));
		this.addSlot(new FilteredSlot(tile.getTileInventory(), 1, 17, 0, tile.getTileInventory().getSlots().get(1).getPredicate()));
		this.addSlot(new FilteredSlot(tile.getTileInventory(), 2, 34, 0, tile.getTileInventory().getSlots().get(2).getPredicate()));
		this.addSlot(new FilteredSlot(tile.getTileInventory(), 3, 102, 0, tile.getTileInventory().getSlots().get(3).getPredicate()));
	}

	@Override
	public ResourceName getName()
	{
		return RockSolid.createRes("container_alloy_smelter");
	}

}