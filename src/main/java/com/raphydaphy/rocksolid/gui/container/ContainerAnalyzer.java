package com.raphydaphy.rocksolid.gui.container;

import com.raphydaphy.rocksolid.api.content.RockSolidContent;
import com.raphydaphy.rocksolid.api.gui.InputSlot;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.tileentity.TileEntityAnalyzer;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class ContainerAnalyzer extends ItemContainer
{
	public ContainerAnalyzer(final AbstractEntityPlayer player, final TileEntityAnalyzer tile)
	{
		super(player, new IInventory[] { player.getInv(), tile.inventory });
		this.addSlot(new InputSlot(tile.inventory, 0, 90, 25,
				instance -> instance.getItem().equals(RockSolidContent.asteroidDataChip)));
		this.addPlayerInventory(player, 20, 60);
	}

	@Override
	public IResourceName getName()
	{
		return RockSolidAPILib.makeInternalRes("containerAnalyzer");
	}
}