package com.raphydaphy.rocksolid.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.raphydaphy.rocksolid.gui.inventory.ContainerInventory;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityAllocator extends TileEntity
{

	public static final int INPUT = 0;
    public static final int OUTPUT = 1;
    public final ContainerInventory inventory;
    
    public TileEntityAllocator(final IWorld world, final int x, final int y) 
    {
        super(world, x, y);
        this.inventory = new ContainerInventory(this, 6);
    }
    
    @Override
    protected boolean needsSync() 
    {
        return false;
    }
    
    @Override
    protected void onSync() 
    {
        super.onSync();
    }
    
    @Override
    public void update(IGameInstance game) 
    {
	   	if (world.getWorldInfo().totalTimeInWorld % 10 == 0)
	   	{
	   		// first we extract stuff
	   		TileEntity tryExtract = RockSolidLib.getTileFromPos(x, y + 1, world);
	   		if (tryExtract == null)
	   		{
	   			tryExtract = RockSolidLib.getTileFromPos(x + 1, y, world);
	   		}
	       
	       if (tryExtract != null)
	       {
	    	   ContainerInventory aboveInventory = null;
	    	   int extractSlot = 0;
	    	   
	    	   if (tryExtract instanceof TileEntityArcFurnace)
	    	   {
	    		   aboveInventory = ((TileEntityArcFurnace)tryExtract).inventory;
	    		   extractSlot = 1;
	    	   }
	    	   else if (tryExtract instanceof TileEntityAllocator)
	    	   {
	    		   aboveInventory = ((TileEntityAllocator)tryExtract).inventory;
	    		   extractSlot = 0;
	    	   }
	    	   else if (tryExtract instanceof TileEntityAlloySmelter)
	    	   {
	    		   aboveInventory = ((TileEntityAlloySmelter)tryExtract).inventory;
	    		   extractSlot = 3;
	    	   }
	    	   
	    	   if (aboveInventory != null)
	    	   {
	    		   if (aboveInventory.get(extractSlot) != null)
	    		   {
	    			   for(int invCount = 0; invCount < 4; invCount++)
	    			   {
	    				   if (this.inventory.get(invCount) == null)
		    			   {
		    				   this.inventory.set(invCount, new ItemInstance(aboveInventory.get(extractSlot).getItem(), 1));
		    				   aboveInventory.remove(extractSlot, 1);
		    				   break;
		    			   }
	    				   else if (this.inventory.get(invCount).getItem().equals(aboveInventory.get(extractSlot).getItem()))
	    				   {
	    					   if (this.inventory.get(invCount).getAmount() > this.inventory.get(invCount).getMaxAmount() - 1)
	    					   {
	    						   // wait for next inventory count
	    						   continue;
	    					   }
	    					   else
	    					   {
		    					   this.inventory.add(invCount, 1);
			    				   aboveInventory.remove(extractSlot, 1);
			    				   break;
	    					   }
	    				   }
		    			   
		    		   }
	    		   }
	    	   }
	    	   
	       }
	       
	       
	       
	       // now we insert stuff
	       TileEntity tryInsert = RockSolidLib.getTileFromPos(x, y - 1, world);
	   		if (tryInsert == null)
	   		{
	   			tryInsert = RockSolidLib.getTileFromPos(x - 1, y, world);
	   		}
	       
	       if (tryInsert != null)
	       {
	    	   ContainerInventory belowInventory = null;
	    	   List<Integer> insertSlots = new ArrayList<Integer>();
	    	   
	    	   if (tryInsert instanceof TileEntityArcFurnace)
	    	   {
	    		   belowInventory = ((TileEntityArcFurnace)tryInsert).inventory;
	    		   insertSlots.add(0);
	    	   }
	    	   else if (tryInsert instanceof TileEntityAlloySmelter)
	    	   {
	    		   belowInventory = ((TileEntityAlloySmelter)tryInsert).inventory;
	    		   insertSlots.add(0);
	    		   insertSlots.add(1);
	    		   insertSlots.add(2);
	    	   }
	       
		       if (belowInventory != null)
		       {
    			   for(int invCount = 0; invCount < 4; invCount++)
    			   {
    				   if (this.inventory.get(invCount) != null)
	    			   {
    					   for (int curInsertSlot = 0; curInsertSlot < insertSlots.size(); curInsertSlot++)
    					   {
    						   if (belowInventory.get(insertSlots.get(curInsertSlot)) == null)
        					   {
        						   belowInventory.set(insertSlots.get(curInsertSlot), new ItemInstance(this.inventory.get(invCount).getItem(), 1));
        						   this.inventory.remove(invCount, 1);
        						   break;
        					   }
        					   else if (belowInventory.get(insertSlots.get(curInsertSlot)).getItem().equals(this.inventory.get(invCount).getItem()))
        					   {
        						   this.inventory.remove(invCount, 1);
            					   belowInventory.add(insertSlots.get(curInsertSlot), 1);
        	    				   break;
        					   }
    					   }
    					   
    					   
    				   }
	    		   }
		       }
	       }
	   	}
    }
    
    @Override
    public void save(final DataSet set, final boolean forSync) {
        super.save(set, forSync);
        if (!forSync) {
            this.inventory.save(set);
        }
    }
    
    @Override
    public void load(final DataSet set, final boolean forSync) {
        super.load(set, forSync);
        if (!forSync) {
            this.inventory.load(set);
        }
    }

}
