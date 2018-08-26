package com.raphydaphy.rocksolid.container;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tileentity.TileEntityNuclearReactor;
import com.raphydaphy.rocksolid.tileentity.base.TileEntityElectric;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.gui.container.OutputSlot;
import de.ellpeck.rockbottom.api.gui.container.RestrictedInputSlot;
import de.ellpeck.rockbottom.api.tile.entity.IFilteredInventory;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class ContainerNuclearReactor extends ItemContainer
{
	public ContainerNuclearReactor(AbstractEntityPlayer player, TileEntityNuclearReactor tile)
	{
		super(player);
		this.addPlayerInventory(player, 0, 65);

		IFilteredInventory inv = tile.getTileInventory();
		int start = 33;
		this.addSlot(new RestrictedInputSlot(inv, 0, start, 15));
		this.addSlot(new RestrictedInputSlot(inv, 1, start + 18, 15));
		this.addSlot(new RestrictedInputSlot(inv, 2, start + 18 * 2, 15));
		this.addSlot(new RestrictedInputSlot(inv, 3, start + 18 * 3, 15));
	}

	@Override
	public ResourceName getName()
	{
		return RockSolid.createRes("container_nuclear_reactor");
	}

}