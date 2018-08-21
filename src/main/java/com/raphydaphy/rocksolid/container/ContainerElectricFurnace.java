package com.raphydaphy.rocksolid.container;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tileentity.TileEntityElectricFurnace;
import com.raphydaphy.rocksolid.util.FilteredSlot;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class ContainerElectricFurnace extends ItemContainer
{

	public ContainerElectricFurnace(AbstractEntityPlayer player, TileEntityElectricFurnace tile)
	{
		super(player);
		this.addPlayerInventory(player, 0, 43);
		this.addSlot(new FilteredSlot(tile.getTileInventory(), 0, 29, 0, tile.getTileInventory().getSlots().get(0).getPredicate()));
		this.addSlot(new FilteredSlot(tile.getTileInventory(), 1, 90, 0, tile.getTileInventory().getSlots().get(1).getPredicate()));
	}

	@Override
	public ResourceName getName()
	{
		return RockSolid.createRes("container_electric_furnace");
	}

}