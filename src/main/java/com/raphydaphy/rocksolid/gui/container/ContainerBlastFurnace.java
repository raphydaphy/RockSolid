package com.raphydaphy.rocksolid.gui.container;

import com.raphydaphy.rocksolid.api.RockSolidAPI;
import com.raphydaphy.rocksolid.api.gui.InputSlot;
import com.raphydaphy.rocksolid.api.gui.OutputSlot;
import com.raphydaphy.rocksolid.tileentity.TileEntityBlastFurnace;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class ContainerBlastFurnace extends ItemContainer
{
	public ContainerBlastFurnace(final AbstractEntityPlayer player, final TileEntityBlastFurnace tile)
	{
		super(player, new IInventory[] { player.getInv(), tile.inventory });
		this.addSlot(new InputSlot(tile.inventory, 0, 40, 10,
				instance -> RockSolidAPI.getArcFurnaceRecipe(instance) != null));
		this.addSlot(new OutputSlot(tile.inventory, 1, 140, 10));
		this.addPlayerInventory(player, 20, 60);
	}

	@Override
	public IResourceName getName()
	{
		return RockSolidLib.makeRes("containerBlastFurnace");
	}
}
