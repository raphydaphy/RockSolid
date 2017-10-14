package com.raphydaphy.rocksolid.gui.container;

import com.raphydaphy.rocksolid.api.RockSolidAPI;
import com.raphydaphy.rocksolid.api.gui.InputSlot;
import com.raphydaphy.rocksolid.api.gui.OutputSlot;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.tileentity.TileEntityAlloySmelter;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class ContainerAlloySmelter extends ItemContainer
{
	public ContainerAlloySmelter(final AbstractEntityPlayer player, final TileEntityAlloySmelter tile)
	{
		super(player, new IInventory[] { player.getInv(), tile.inventory });
		this.addSlot(new InputSlot(tile.inventory, 0, 25, 10, instance -> RockSolidAPI.existsInAlloyRecipe(instance)));
		this.addSlot(new InputSlot(tile.inventory, 1, 55, 10, instance -> RockSolidAPI.existsInAlloyRecipe(instance)));
		this.addSlot(new InputSlot(tile.inventory, 2, 90, 30, instance -> RockSolidAPI.getFuelValue(instance) > 0));
		this.addSlot(new OutputSlot(tile.inventory, 3, 140, 10));
		this.addPlayerInventory(player, 20, 60);
	}

	@Override
	public IResourceName getName()
	{
		return RockSolidAPILib.makeInternalRes("containerAlloySmelter");
	}

}
