package com.raphydaphy.rocksolid.gui.container;

import com.raphydaphy.rocksolid.api.gui.InputSlot;
import com.raphydaphy.rocksolid.api.gui.OutputSlot;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.tileentity.TileEntityElectricSeparator;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class ContainerElectricSeparator extends ItemContainer
{
	public ContainerElectricSeparator(final AbstractEntityPlayer player, final TileEntityElectricSeparator tile)
	{
		super(player, new IInventory[] { player.getInv(), tile.inventory });
		this.addSlot(new InputSlot(tile.inventory, 0, 40, 10,
				instance -> RockBottomAPI.getSeparatorRecipe(instance) != null));
		this.addSlot(new OutputSlot(tile.inventory, 1, 120, 10));
		this.addSlot(new OutputSlot(tile.inventory, 2, 140, 10));
		this.addPlayerInventory(player, 20, 60);
	}

	@Override
	public IResourceName getName()
	{
		return RockSolidAPILib.makeInternalRes("containerElectricSeparator");
	}
}