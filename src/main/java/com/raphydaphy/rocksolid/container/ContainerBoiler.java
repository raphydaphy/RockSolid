package com.raphydaphy.rocksolid.container;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tileentity.TileEntityBoiler;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.gui.container.RestrictedInputSlot;
import de.ellpeck.rockbottom.api.tile.entity.IFilteredInventory;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class ContainerBoiler extends ItemContainer
{

	public ContainerBoiler(AbstractEntityPlayer player, TileEntityBoiler tile)
	{
		super(player);
		this.addPlayerInventory(player, 0, 61);

		IFilteredInventory inv = tile.getTileInventory();
		this.addSlot(new RestrictedInputSlot(inv, 0, 53, 16));
	}

	@Override
	public ResourceName getName()
	{
		return RockSolid.createRes("container_boiler");
	}

}
