package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.gui.inventory.ContainerInventory;
import com.raphydaphy.rocksolid.init.ModItems;
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
	   		TileEntity tileUp = RockSolidLib.getTileFromPos(x, y + 1, world);
	       
	       if (tileUp != null)
	       {
	    	   if (tileUp instanceof TileEntityArcFurnace)
	    	   {
	    		   ContainerInventory aboveInventory = ((TileEntityArcFurnace)tileUp).inventory;
	    		   if (aboveInventory.get(1) != null)
	    		   {
		    		   if (aboveInventory.get(1).getItem().equals(ModItems.gemCoke))
		    		   {
		    			   for(int invCount = 0; invCount < 4; invCount++)
		    			   {
		    				   if (this.inventory.get(invCount) == null)
			    			   {
			    				   this.inventory.set(invCount, new ItemInstance(aboveInventory.get(1).getItem(), 1));
			    				   aboveInventory.remove(1, 1);
			    				   break;
			    			   }
		    				   else if (this.inventory.get(invCount).getItem().equals(aboveInventory.get(1).getItem()))
		    				   {
		    					   if (this.inventory.get(invCount).getAmount() > this.inventory.get(invCount).getMaxAmount() - 1)
		    					   {
		    						   // wait for next inventory count
		    						   continue;
		    					   }
		    					   else
		    					   {
			    					   this.inventory.add(invCount, 1);
				    				   aboveInventory.remove(1, 1);
				    				   break;
		    					   }
		    				   }
		    			   }
		    			   
		    		   }
	    		   }
	    	   }
	       }
	       TileEntity tileDown = RockSolidLib.getTileFromPos(x, y - 1, world);
	       
	       if (tileDown != null)
	       {
	    	   if (tileDown instanceof TileEntityArcFurnace)
	    	   {
	    		   ContainerInventory belowInventory = ((TileEntityArcFurnace)tileDown).inventory;
    			   for(int invCount = 0; invCount < 4; invCount++)
    			   {
    				   if (this.inventory.get(invCount) != null)
	    			   {
    					   if (belowInventory.get(0) == null)
    					   {
    						   System.out.println("we have located the suspect");
    						   belowInventory.set(0, new ItemInstance(this.inventory.get(invCount).getItem(), 1));
    						   this.inventory.remove(invCount, 1);
    						   break;
    					   }
    					   else if (belowInventory.get(0).getItem().equals(this.inventory.get(invCount).getItem()))
    					   {
    						   this.inventory.remove(invCount, 1);
        					   belowInventory.add(0, 1);
    	    				   break;
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
