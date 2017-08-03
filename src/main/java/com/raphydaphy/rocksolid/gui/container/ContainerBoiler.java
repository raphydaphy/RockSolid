package com.raphydaphy.rocksolid.gui.container;

import com.raphydaphy.rocksolid.api.gui.InputSlot;
import com.raphydaphy.rocksolid.tileentity.TileEntityBoiler;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class ContainerBoiler extends ItemContainer
{
	public ContainerBoiler(final AbstractEntityPlayer player, final TileEntityBoiler tile)
	{
		super(player, new IInventory[] { player.getInv(), tile.inventory });
		this.addSlot(new InputSlot(tile.inventory, 0, 90, 30, instance -> RockBottomAPI.getFuelValue(instance) > 0));
		this.addPlayerInventory(player, 20, 60);
	}

	@Override
	public IResourceName getName()
	{
		return RockSolidLib.makeRes("containerCoalGenerator");
	}
}
