package com.raphydaphy.rocksolid.container;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tileentity.TileEntityArcFurnace;
import com.raphydaphy.rocksolid.util.FilteredSlot;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class ContainerArcFurnace extends ItemContainer
{

	public ContainerArcFurnace(AbstractEntityPlayer player, TileEntityArcFurnace tile)
	{
		super(player, player.getInv(), tile.getInventory());
		this.addPlayerInventory(player, 0, 55);
		this.addSlot(new FilteredSlot(tile.getInventory(), 0, 27, 15, tile.getInventory().getSlots().get(0).getPredicate()));
		this.addSlot(new FilteredSlot(tile.getInventory(), 1, 95, 15, tile.getInventory().getSlots().get(1).getPredicate()));
	}

	@Override
	public IResourceName getName()
	{
		return RockSolid.createRes("container_arc_furnace");
	}

}
