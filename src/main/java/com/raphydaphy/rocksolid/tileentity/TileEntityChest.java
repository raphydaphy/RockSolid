package com.raphydaphy.rocksolid.tileentity;

import java.util.Arrays;
import java.util.List;

import com.raphydaphy.rocksolid.gui.inventory.ContainerInventory;

import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.IInventoryHolder;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Direction;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityChest extends TileEntity implements IInventoryHolder
{
	public final ContainerInventory inventory = new ContainerInventory(this, 20);
	public int openCount;

	public TileEntityChest(IWorld world, int x, int y)
	{
		super(world, x, y);
	}

	public void save(DataSet set, boolean forSync)
	{
		if (!forSync)
		{
			this.inventory.save(set);
		}
	}

	public void load(DataSet set, boolean forSync)
	{
		if (!forSync)
		{
			this.inventory.load(set);
		}
	}

	@Override
	public Inventory getInventory()
	{
		return this.inventory;
	}

	@Override
	public List<Integer> getInputSlots(ItemInstance input, Direction dir)
	{
		return Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,18,19);
	}

	@Override
	public List<Integer> getOutputSlots(Direction dir)
	{
		return Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,18,19);
	}
}