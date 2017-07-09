package com.raphydaphy.rocksolid.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.raphydaphy.rocksolid.gui.inventory.ContainerInventory;
import com.raphydaphy.rocksolid.gui.inventory.IHasInventory;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class TileEntityAllocator extends TileEntity implements IHasInventory
{

	public static final int INPUT = 0;
    public static final int OUTPUT = 1;
    public final ContainerInventory inventory;
    
    // 0 = output to the inventory
    // 1 = input from the inventory
    // 2 = disabled
    
    private int modeUp = 0;
    private int modeDown = 0;
    private int modeLeft = 0;
    private int modeRight = 0;
    
    private boolean isMaster;
    private int masterX;
    private int masterY;
    
    private short[][] inputs = new short[][]{};
    private short[][] outputs = new short[][]{};
    
    public TileEntityAllocator(final IWorld world, final int x, final int y) 
    {
        super(world, x, y);
        this.inventory = new ContainerInventory(this, 6);
    }
    
    @Override
    protected boolean needsSync() 
    {
    	return super.needsSync();
    }
    
    @Override
    protected void onSync() 
    {
        super.onSync();
    }
    
    @Override
    public void update(IGameInstance game) 
    {
    	// run conduit processing code every 10 ticks to prevent lag
    	// also causes a less op item processing rate of 1 every 10 ticks
	   	if (world.getWorldInfo().totalTimeInWorld % 10 == 0)
	   	{
	   		// first we extract stuff from nearby inventories into the pipes inventory
	   		for (int adjacentTiles = 0; adjacentTiles < 4; adjacentTiles++)
	   		{
	   			// if the selected adjacent tile is specified for input into the conduits
	   			if (this.getSideMode(adjacentTiles) == 1)
	   			{
	   				// try to get a tileentity from the selected adjacent side block
	   				TileEntity tryExtract = RockSolidLib.getTileFromConduitSide(new Pos2(x, y), adjacentTiles, world);
	   				
	   				if (tryExtract != null)
	   				{
	   		    	   Inventory extractInventory = null;
	   		    	   List<Integer> extractSlots = new ArrayList<Integer>();
	   		    	   
	   		    	   if (tryExtract instanceof IHasInventory)
	   		    	   {
	   		    		extractInventory =  ((IHasInventory)tryExtract).getInventory();
	   		    		   extractSlots = ((IHasInventory)tryExtract).getOutputs();
	   		    		   
	   		    		   if (extractSlots == null)
	   		    		   {
	   		    			extractInventory = null;
	   		    		   }
	   		    	   }
	   		    	   
	   		    	   if (extractInventory != null)
	   		    	   {
	   		    		   for (int curExtractSlot = 0; curExtractSlot < extractSlots.size(); curExtractSlot++)
	   		    		   {
	   		    			   if (extractInventory.get(extractSlots.get(curExtractSlot)) != null)
		   		    		   {
		   		    			   for(int invCount = 0; invCount < 4; invCount++)
		   		    			   {
		   		    				   if (this.inventory.get(invCount) == null)
		   			    			   {
		   			    				   this.inventory.set(invCount, new ItemInstance(extractInventory.get(extractSlots.get(curExtractSlot)).getItem(), 1));
		   			    				extractInventory.remove(extractSlots.get(curExtractSlot), 1);
		   			    				   break;
		   			    			   }
		   		    				   else if (this.inventory.get(invCount).getItem().equals(extractInventory.get(extractSlots.get(curExtractSlot)).getItem()))
		   		    				   {
		   		    					   if (this.inventory.get(invCount).getAmount() > this.inventory.get(invCount).getMaxAmount() - 1)
		   		    					   {
		   		    						   // wait for next inventory count
		   		    						   continue;
		   		    					   }
		   		    					   else
		   		    					   {
		   			    					   this.inventory.add(invCount, 1);
		   			    					   extractInventory.remove(extractSlots.get(curExtractSlot), 1);
		   				    				   break;
		   		    					   }
		   		    				   }
		   			    			   
		   			    		   }
		   		    		   }
	   		    		   }
	   		    		   
	   		    	   }
	   		    	   
	   		       }
	   			}
	   		}
	   		
	       // now we insert stuff
	   		for (int adjacentTiles = 0; adjacentTiles < 4; adjacentTiles++)
	   		{
	   			// if the selected adjacent tile is specified for output from the conduits
	   			if (this.getSideMode(adjacentTiles) == 0)
	   			{
	   				// try to get a tileentity from the selected adjacent side block
	   				TileEntity tryInsert = RockSolidLib.getTileFromConduitSide(new Pos2(x, y), adjacentTiles, world);
	   				
	   				if (tryInsert != null)
	   		       {
	   		    	   Inventory insertInventory = null;
	   		    	   List<Integer> insertSlots = new ArrayList<Integer>();
	   		    	   
	   		    	   if (tryInsert instanceof IHasInventory)
	   		    	   {
	   		    		insertInventory =  ((IHasInventory)tryInsert).getInventory();
	   		    		   insertSlots = ((IHasInventory)tryInsert).getInputs();
	   		    		   
	   		    		   if (insertSlots == null)
	   		    		   {
	   		    			insertInventory = null;
	   		    		   }
	   		    	   }
	   		       
	   			       if (insertInventory != null)
	   			       {
	   	    			   for(int invCount = 0; invCount < 4; invCount++)
	   	    			   {
	   	    				   if (this.inventory.get(invCount) != null)
	   		    			   {
	   	    					   for (int curInsertSlot = 0; curInsertSlot < insertSlots.size(); curInsertSlot++)
	   	    					   {
	   	    						   if (insertInventory.get(insertSlots.get(curInsertSlot)) == null)
	   	        					   {
	   	    							insertInventory.set(insertSlots.get(curInsertSlot), new ItemInstance(this.inventory.get(invCount).getItem(), 1));
	   	        						   this.inventory.remove(invCount, 1);
	   	        						   break;
	   	        					   }
	   	        					   else if (insertInventory.get(insertSlots.get(curInsertSlot)).getItem().equals(this.inventory.get(invCount).getItem()))
	   	        					   {
	   	        						   this.inventory.remove(invCount, 1);
	   	        						insertInventory.add(insertSlots.get(curInsertSlot), 1);
	   	        	    				   break;
	   	        					   }
	   	    					   }
	   	    					   
	   	    					   
	   	    				   }
	   		    		   }
	   			       }
	   		       }
	   			}
	   		}
	       
	   	}
    }
    
    public void setSideMode(int side, int mode)
    {
    	switch(side)
    	{
    	case 0:
    		//up
    		modeUp = mode;
    		break;
    	case 1:
    		//down
    		modeDown = mode;
    		break;
    	case 2:
    		//left
    		modeLeft = mode;
    		break;
    	case 3:
    		//right
    		modeRight = mode;
    		break;
    	}
    }
    
    public int getSideMode(int side)
    {
    	switch(side)
    	{
    	case 0:
    		return modeUp;
    	case 1:
    		return modeDown;
    	case 2:
    		return modeLeft;
    	case 3:
    		return modeRight;
    	}
    	return 0;
    }
    
    @Override
    public void save(final DataSet set, final boolean forSync) {
        super.save(set, forSync);
        if (!forSync) {
            this.inventory.save(set);
        }
        set.addInt("modeUp", this.modeUp);
        set.addInt("momdeDown", this.modeDown);
        set.addInt("modeLeft", this.modeLeft);
        set.addInt("modeRight", this.modeRight);
        set.addBoolean("isMaster", this.isMaster);
        set.addInt("masterX", this.masterX);
        set.addInt("masterY", this.masterY);
        set.addShortShortArray("inputs", this.inputs);
        set.addShortShortArray("outputs", this.outputs);
    }
    
    @Override
    public void load(final DataSet set, final boolean forSync) {
        super.load(set, forSync);
        if (!forSync) {
            this.inventory.load(set);
        }
        this.modeUp = set.getInt("modeUp");
        this.modeDown = set.getInt("modeDown");
        this.modeLeft = set.getInt("modeLeft");
        this.modeRight = set.getInt("modeRight");
        this.isMaster = set.getBoolean("isMaster");
        this.masterX = set.getInt("masterX");
        this.masterY = set.getInt("masterY");
        this.inputs = set.getShortShortArray("inputs", 1024);
        this.outputs = set.getShortShortArray("outputs", 1024);
    }

	@Override
	public Inventory getInventory() {
		return this.inventory;
	}

	@Override
	public List<Integer> getInputs() 
	{
		return null;
	}

	@Override
	public List<Integer> getOutputs() 
	{

		List<Integer> extractSlots = new ArrayList<Integer>();
		extractSlots.add(0);
		extractSlots.add(1);
		extractSlots.add(2);
		extractSlots.add(3);
		extractSlots.add(4);
		extractSlots.add(5);
		return extractSlots;
	}
	
	public void onAdded(IWorld world, int x, int y, TileLayer layer)
	{
		TileEntityAllocator adjacentTile = null;
		if (RockSolidLib.getTileFromPos(x, y + 1, world) != null && RockSolidLib.getTileFromPos(x, y + 1, world) instanceof TileEntityAllocator)
		{
			adjacentTile = (TileEntityAllocator)RockSolidLib.getTileFromPos(x, y + 1, world);
			this.isMaster = false;
			this.masterX = adjacentTile.getMaster().getX();
			this.masterY = adjacentTile.getMaster().getY();
		}
		else if (RockSolidLib.getTileFromPos(x, y - 1, world) != null && RockSolidLib.getTileFromPos(x, y - 1, world) instanceof TileEntityAllocator)
		{
			adjacentTile = (TileEntityAllocator)RockSolidLib.getTileFromPos(x, y - 1, world);
			this.isMaster = false;
			this.masterX = adjacentTile.getMaster().getX();
			this.masterY = adjacentTile.getMaster().getY();
		}
		else if (RockSolidLib.getTileFromPos(x - 1, y, world) != null && RockSolidLib.getTileFromPos(x - 1, y, world) instanceof TileEntityAllocator)
		{
			adjacentTile = (TileEntityAllocator)RockSolidLib.getTileFromPos(x - 1, y, world);
			this.isMaster = false;
			this.masterX = adjacentTile.getMaster().getX();
			this.masterY = adjacentTile.getMaster().getY();
		}
		else if (RockSolidLib.getTileFromPos(x + 1, y, world) != null && RockSolidLib.getTileFromPos(x + 1, y, world) instanceof TileEntityAllocator)
		{
			adjacentTile = (TileEntityAllocator)RockSolidLib.getTileFromPos(x + 1, y, world);
			this.isMaster = false;
			this.masterX = adjacentTile.getMaster().getX();
			this.masterY = adjacentTile.getMaster().getY();
		}
		else
		{
			this.isMaster = true;
		}
	}
	
	
	public void addToMaster(Pos2 inventory, boolean isInput)
	{
		
	}
	
	public void removeFromMaster(Pos2 inventory, boolean isInput)
	{
		
	}
	
	public Pos2 getMaster()
	{
		return new Pos2(masterX, masterY);
	}
	
	public void onChangedAround(IWorld world, int x, int y, TileLayer layer, int changedX, int changedY, TileLayer changedLayer)
	{
		TileEntity changedTile = RockSolidLib.getTileFromPos(changedX, changedY, world);
		if (changedTile != null)
		{
			updateSide(RockSolidLib.posAndOffsetToConduitSide(new Pos2(x,y), new Pos2(changedX, changedY)), world, new Pos2(x,y), new Pos2(changedX, changedY));
		}
	}
	
	public void updateSide(int side, IWorld world, Pos2 center, Pos2 changed)
	{
		TileEntity changedTile = RockSolidLib.getTileFromPos(changed.getX(), changed.getY(), world);
		if (changedTile instanceof TileEntityAllocator)
		{
			switch(side)
			{
			case 0:
				//up
				System.out.println("updating upwards");
				
				break;
			case 1:
				//down
				System.out.println("updating downwards");
				break;
			case 2:
				//left
				System.out.println("updating to the left");
				break;
			case 3:
				//right
				System.out.println("updating to the right");
				break;
			}
		}
		else if (changedTile instanceof IHasInventory)
		{
			switch(side)
			{
			case 0:
				//up
				System.out.println("updating upwards");
				
				break;
			case 1:
				//down
				System.out.println("updating downwards");
				break;
			case 2:
				//left
				System.out.println("updating to the left");
				break;
			case 3:
				//right
				System.out.println("updating to the right");
				break;
			}
		}
	}

}
