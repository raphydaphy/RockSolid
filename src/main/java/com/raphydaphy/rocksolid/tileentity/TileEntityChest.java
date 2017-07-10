package com.raphydaphy.rocksolid.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.raphydaphy.rocksolid.gui.inventory.ContainerInventory;
import com.raphydaphy.rocksolid.util.IHasInventory;

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
		List<Integer> insertSlots = new ArrayList<Integer>();
		insertSlots.add(0);
		return insertSlots;
	}

	@Override
	public List<Integer> getOutputs() 
	{
		List<Integer> outputSlots = new ArrayList<Integer>();
		outputSlots.add(0);
		return outputSlots;
	}
}