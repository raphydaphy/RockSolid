package com.raphydaphy.rocksolid.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.raphydaphy.rocksolid.api.util.IHasInventory;
import com.raphydaphy.rocksolid.gui.inventory.ContainerInventory;

import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityChest extends TileEntity implements IHasInventory
{
  public final ContainerInventory inventory = new ContainerInventory(this, 20);
  public int openCount;
  
  public TileEntityChest(IWorld world, int x, int y)
  {
    super(world, x, y);
  }
  
  public void save(DataSet set, boolean forSync)
  {
    if (!forSync) {
      this.inventory.save(set);
    }
  }
  
  public void load(DataSet set, boolean forSync)
  {
    if (!forSync) {
      this.inventory.load(set);
    }
  }

	@Override
	public Inventory getInventory() 
	{
		return this.inventory;
	}
	
	@Override
	public List<Integer> getInputs() 
	{
		// i hope canitzp dosent find this...
		List<Integer> insertSlots = new ArrayList<Integer>();
		insertSlots.add(0);
		insertSlots.add(1);
		insertSlots.add(2);
		insertSlots.add(3);
		insertSlots.add(4);
		insertSlots.add(5);
		insertSlots.add(6);
		insertSlots.add(7);
		insertSlots.add(8);
		insertSlots.add(9);
		insertSlots.add(10);
		insertSlots.add(11);
		insertSlots.add(12);
		insertSlots.add(13);
		insertSlots.add(14);
		insertSlots.add(15);
		insertSlots.add(16);
		insertSlots.add(17);
		insertSlots.add(18);
		insertSlots.add(19);
		return insertSlots;
	}

	@Override
	public List<Integer> getOutputs() 
	{
		// or this...
		List<Integer> outputSlots = new ArrayList<Integer>();
		outputSlots.add(0);
		outputSlots.add(1);
		outputSlots.add(2);
		outputSlots.add(3);
		outputSlots.add(4);
		outputSlots.add(5);
		outputSlots.add(6);
		outputSlots.add(7);
		outputSlots.add(8);
		outputSlots.add(9);
		outputSlots.add(10);
		outputSlots.add(11);
		outputSlots.add(12);
		outputSlots.add(13);
		outputSlots.add(14);
		outputSlots.add(15);
		outputSlots.add(16);
		outputSlots.add(17);
		outputSlots.add(18);
		outputSlots.add(19);
		return outputSlots;
	}
}