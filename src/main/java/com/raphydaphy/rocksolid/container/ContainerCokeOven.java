package com.raphydaphy.rocksolid.container;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tileentity.TileEntityCokeOven;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.gui.container.OutputSlot;
import de.ellpeck.rockbottom.api.gui.container.RestrictedInputSlot;
import de.ellpeck.rockbottom.api.tile.entity.IFilteredInventory;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class ContainerCokeOven extends ItemContainer
{

	public ContainerCokeOven(AbstractEntityPlayer player, TileEntityCokeOven tile)
	{
		super(player);
		this.addPlayerInventory(player, 0, 30);

		IFilteredInventory inv = tile.getTileInventory();
		this.addSlot(new RestrictedInputSlot(inv, 0, 29, 1));
		this.addSlot(new OutputSlot(inv, 1, 90, 1));
	}

	@Override
	public ResourceName getName()
	{
		return RockSolid.createRes("container_coke_oven");
	}

}
