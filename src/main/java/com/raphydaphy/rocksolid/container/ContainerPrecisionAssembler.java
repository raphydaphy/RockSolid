package com.raphydaphy.rocksolid.container;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.container.slot.AssemblySlot;
import com.raphydaphy.rocksolid.tileentity.TileEntityAssemblyStation;
import com.raphydaphy.rocksolid.tileentity.TileEntityPrecisionAssembler;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.tile.entity.IFilteredInventory;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class ContainerPrecisionAssembler extends ItemContainer
{
	public final TileEntityPrecisionAssembler te;
	public final AssemblySlot metalSlot;

	public ContainerPrecisionAssembler(AbstractEntityPlayer player, TileEntityPrecisionAssembler tile)
	{
		super(player);
		this.addPlayerInventory(player, 0, 99);

		IFilteredInventory inv = tile.getInvHidden();
		this.addSlot(new AssemblySlot(inv, 0, 34, 69));
		this.metalSlot = new AssemblySlot(inv, 1, 34 + 18, 69);
		this.addSlot(metalSlot);
		this.addSlot(new AssemblySlot(inv, 2, 34 + 18 * 2, 69));
		this.te = tile;
	}

	@Override
	public ResourceName getName()
	{
		return RockSolid.createRes("container_precision_assembler");
	}
}
