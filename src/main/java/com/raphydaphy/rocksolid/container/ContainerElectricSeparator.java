package com.raphydaphy.rocksolid.container;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tileentity.TileEntityElectricSeparator;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.gui.container.OutputSlot;
import de.ellpeck.rockbottom.api.gui.container.RestrictedInputSlot;
import de.ellpeck.rockbottom.api.tile.entity.IFilteredInventory;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class ContainerElectricSeparator extends ItemContainer
{

	public ContainerElectricSeparator(AbstractEntityPlayer player, TileEntityElectricSeparator tile)
	{
		super(player);
		this.addPlayerInventory(player, 0, 43);

		IFilteredInventory inv = tile.getTileInventory();
		this.addSlot(new RestrictedInputSlot(inv, 0, 17, 0));
		this.addSlot(new OutputSlot(inv,  1, 85, 0));
		this.addSlot(new OutputSlot(inv,  2, 102, 0));
	}

	@Override
	public ResourceName getName()
	{
		return RockSolid.createRes("container_electric_separator");
	}

}