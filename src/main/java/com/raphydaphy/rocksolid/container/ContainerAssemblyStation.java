package com.raphydaphy.rocksolid.container;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.container.slot.AssemblySlot;
import com.raphydaphy.rocksolid.tileentity.TileEntityAssemblyStation;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class ContainerAssemblyStation extends ItemContainer
{
	public ContainerAssemblyStation(AbstractEntityPlayer player, TileEntityAssemblyStation tile)
	{
		super(player);
		this.addPlayerInventory(player, 0, 99);
		this.addSlot(new AssemblySlot(tile.getInvHidden(), 0, 34, 69, tile.getInvHidden().getSlots().get(0).getPredicate()));
		this.addSlot(new AssemblySlot(tile.getInvHidden(), 1, 34 + 18, 69, tile.getInvHidden().getSlots().get(1).getPredicate()));
		this.addSlot(new AssemblySlot(tile.getInvHidden(), 2, 34 + 18 * 2, 69, tile.getInvHidden().getSlots().get(2).getPredicate()));
	}

	@Override
	public ResourceName getName()
	{
		return RockSolid.createRes("container_assembly_station");
	}

}
