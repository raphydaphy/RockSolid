package com.raphydaphy.rocksolid.container;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tileentity.TileEntityBoiler;
import com.raphydaphy.rocksolid.util.FilteredSlot;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class ContainerBoiler extends ItemContainer
{

	public ContainerBoiler(AbstractEntityPlayer player, TileEntityBoiler tile)
	{
		super(player, new IInventory[] { player.getInv(), tile.getInventory() });
		this.addPlayerInventory(player, 20, 55);
		this.addSlot(new FilteredSlot(tile.getInventory(), 0, 50, 25, instance -> { return instance.getItem().equals(GameContent.TILE_COAL.getItem()); }));
	}

	@Override
	public IResourceName getName()
	{
		return RockSolid.createRes("container_boiler");
	}

}
