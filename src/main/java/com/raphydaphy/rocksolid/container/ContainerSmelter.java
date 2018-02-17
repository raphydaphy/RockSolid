package com.raphydaphy.rocksolid.container;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tileentity.TileEntitySmelter;
import com.raphydaphy.rocksolid.util.FilteredSlot;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class ContainerSmelter extends ItemContainer
{

	public ContainerSmelter(AbstractEntityPlayer player, TileEntitySmelter tile)
	{
		super(player, player.getInv(), tile.getInventory());
		this.addPlayerInventory(player, 0, 50);
		this.addSlot(new FilteredSlot(tile.getInventory(), 0, 60, 20, tile.getInventory().getSlots().get(0).getPredicate()));
		this.addSlot(new FilteredSlot(tile.getInventory(), 1, 25, 0, tile.getInventory().getSlots().get(1).getPredicate()));
		this.addSlot(new FilteredSlot(tile.getInventory(), 2, 93, 0, tile.getInventory().getSlots().get(2).getPredicate()));
	}

	@Override
	public IResourceName getName()
	{
		return RockSolid.createRes("container_smelter");
	}

}