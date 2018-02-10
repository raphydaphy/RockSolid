package com.raphydaphy.rocksolid.container;

import com.raphydaphy.rocksolid.RockSolid;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class ContainerBase extends ItemContainer
{
	public ContainerBase(AbstractEntityPlayer player, TileEntity tile)
	{
		this(player, tile, 32, 65);
	}

	public ContainerBase(AbstractEntityPlayer player, TileEntity tile, int invX, int invY)
	{
		super(player, new IInventory[] { player.getInv() });
		this.addPlayerInventory(player, invX, invY);
	}

	@Override
	public IResourceName getName()
	{
		return RockSolid.createRes("container_base");
	}

}
