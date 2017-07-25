package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.api.IConduit;
import com.raphydaphy.rocksolid.api.IFluidAcceptor;
import com.raphydaphy.rocksolid.api.IFluidProducer;
import com.raphydaphy.rocksolid.api.IFluidTile;
import com.raphydaphy.rocksolid.init.ModFluids;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityFluidConduit extends TileEntity implements IConduit, IFluidProducer, IFluidAcceptor
{

    private int modeUp = 0;
    private int modeDown = 0;
    private int modeLeft = 0;
    private int modeRight = 0;
    
    private int fluidStored = 0;
    private int maxFluid = 3000;
    private String fluidType;
    
    private int transferRate = 250;
    
    private boolean shouldSync = false;
    
    public TileEntityFluidConduit(final IWorld world, final int x, final int y) 
    {
        super(world, x, y);
        
        if (this.fluidType == null)
        {
        	this.fluidType = ModFluids.fluidEmpty.toString();
        }
    }
    
    @Override
    protected boolean needsSync() 
    {
    	return super.needsSync() || shouldSync;
    }
    
    @Override
    protected void onSync()
    {
    	super.onSync();
    	this.shouldSync = false;
    }
    @Override
    public void update(IGameInstance game) 
    {
    	super.update(game);
    	if (RockBottomAPI.getNet().isClient() == false)
    	{
	   		// first we extract stuff from nearby inventories into the pipes inventory
	   		for (int adjacentTile = 0; adjacentTile < 4; adjacentTile++)
	   		{
	   			Pos2 adjacentTilePos = RockSolidLib.conduitSideToPos(new Pos2(x, y), adjacentTile);
	   			TileEntity adjacentTileEntity = RockSolidLib.getTileFromPos(adjacentTilePos.getX(), adjacentTilePos.getY(), world);
	   			
	   			if (adjacentTileEntity != null)
	   			{
	   				if (adjacentTileEntity instanceof TileEntityFluidConduit)
	   				{
	   					if (this.getSideMode(adjacentTile) != 2 && ((TileEntityFluidConduit)adjacentTileEntity).getFluidType().equals(this.fluidType))
	   					{
		   					if (((TileEntityFluidConduit)adjacentTileEntity).getCurrentFluid() > this.getCurrentFluid())
							{
		   						System.out.println("can pull fluid, adjacent conduit has more. This conduit has " + this.fluidStored);
								if (((TileEntityFluidConduit)adjacentTileEntity).getCurrentFluid() <= transferRate)
								{
									System.out.println("conduit has enough fluid to pull");
									String wasFluidType = ((TileEntityFluidConduit)adjacentTileEntity).getFluidType();
									// pull fluid from adjacent conduit
									if (((TileEntityFluidConduit)adjacentTileEntity).removeFluid(transferRate))
									{
										this.addFluid(transferRate, wasFluidType);
										System.out.println("Pulled " + wasFluidType + " from adjacent conduit at position #" + adjacentTile);
										shouldSync = true;
									}
								}
							}
	   					}
	   				}
	   				else if (IFluidTile.class.isAssignableFrom(adjacentTileEntity.getClass()))
	   				{
	   					if (((IFluidTile)adjacentTileEntity).getFluidType().equals(this.fluidType) || this.fluidType.equals(ModFluids.fluidEmpty.toString()) || ((IFluidTile)adjacentTileEntity).getFluidType().equals(ModFluids.fluidEmpty.toString()))
	   					{
		   					if (IFluidProducer.class.isAssignableFrom(adjacentTileEntity.getClass()) && (((IFluidTile)adjacentTileEntity).getFluidType().equals(this.fluidType)) || this.fluidType.equals(ModFluids.fluidEmpty.toString()))
		   	   				{
		   						// Conduit is set to input mode
		   	   					if (this.getSideMode(adjacentTile) == 1)
		   	   					{
		   	   						
		   	   						if (this.fluidStored < (this.maxFluid - transferRate) && ((IFluidProducer)adjacentTileEntity).getCurrentFluid() >= transferRate)
		   	   						{
		   	   							System.out.println("found a tile to pull fluid from. Currently holding " + this.fluidStored + " liquid.");
		   	   							String wasFluidType = ((IFluidProducer)adjacentTileEntity).getFluidType();
		   	   							// pull fluid from adjacent tile
		   	   							if (((IFluidProducer)adjacentTileEntity).removeFluid(transferRate))
		   	   							{
		   	   								this.addFluid(transferRate, wasFluidType);
		   	   								System.out.println("pulled " + wasFluidType + " from adjacent tile at position " + adjacentTile);
		   	   								this.shouldSync = true;
		   	   							}
		   	   						}
		   	   					}
		   	   				}
		   					
		   					if (IFluidAcceptor.class.isAssignableFrom(adjacentTileEntity.getClass()))
		   	   				{
		   						// Conduit is set to output mode
		   	   					if (this.getSideMode(adjacentTile) == 0)
		   	   					{
			   	   					
		   	   						if (this.fluidStored >= transferRate)
		   	   						{
			   	   						if (((IFluidTile)adjacentTileEntity).getFluidType().equals(ModFluids.fluidEmpty.toString()))
			   	   						{
				   	   						// set the fluid type in the adjacent tile to match this
			   	   							((IFluidAcceptor)adjacentTileEntity).setFluidType(this.fluidType);
			   	   						}
		   	   							System.out.println("Found a tile to push fluid to. Currently holding " + this.fluidStored + " fluid.");
		   	   							// send fluid to adjacent tile
		   	   							if (((IFluidAcceptor)adjacentTileEntity).addFluid(transferRate, this.fluidType))
		   	   							{
		   	   								System.out.println("Sent " + this.fluidType + " to adjacent tile #" + adjacentTile);
		   	   								this.removeFluid(transferRate);
		   	   								shouldSync = true;
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
    
    @Override
    public void setSideMode(int side, int mode)
    {
    	shouldSync = true;
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
    
    @Override
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
    public void save(final DataSet set, final boolean forSync) 
    {
        super.save(set, forSync);
        set.addInt("modeUp", this.modeUp);
        set.addInt("modeDown", this.modeDown);
        set.addInt("modeLeft", this.modeLeft);
        set.addInt("modeRight", this.modeRight);
        set.addInt("fluidStored", this.fluidStored);
        set.addInt("maxFluid", this.maxFluid);
        set.addInt("transferRate", this.transferRate);
        set.addBoolean("shouldSync", this.shouldSync);
        set.addString("fluidType", this.fluidType);
    }
    
    @Override
    public void load(final DataSet set, final boolean forSync) 
    {
        super.load(set, forSync);
        this.modeUp = set.getInt("modeUp");
        this.modeDown = set.getInt("modeDown");
        this.modeLeft = set.getInt("modeLeft");
        this.modeRight = set.getInt("modeRight");
        this.fluidStored = set.getInt("fluidStored");
        this.maxFluid = set.getInt("maxFluid");
        this.transferRate = set.getInt("transferRate");
        this.shouldSync = set.getBoolean("shouldSync");
        this.fluidType = set.getString("fluidType");
    }
    
	@Override
	public boolean canConnectTo(Class<?> adjacentBlock) 
	{
		return IFluidTile.class.isAssignableFrom(adjacentBlock);
	}

	@Override
	public void setSync() {
		if (RockBottomAPI.getNet().isClient() == false)
		{
			shouldSync = true;
		}
		
	}

	@Override
	public int getCurrentFluid() 
	{
		return this.fluidStored;
	}

	@Override
	public int getMaxFluid() 
	{
		return this.maxFluid;
	}

	@Override
	public boolean addFluid(int amount, String type) 
	{
		if (this.fluidType == null || type.equals(this.fluidType) || this.fluidType.equals(ModFluids.fluidEmpty.toString()))
		{
			if (this.fluidStored + amount <= this.maxFluid)
			{
				if (this.fluidType == null || this.fluidType.equals(ModFluids.fluidEmpty.toString()))
				{
					System.out.println("a new type of fluid was added to a conduit, the type was set accordingly");
					this.fluidType = type;
				}
				this.fluidStored += amount;
				this.shouldSync = true;
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean removeFluid(int amount) 
	{
		if (this.fluidStored >= amount)
		{
			this.fluidStored -= amount;
			
			if (this.fluidStored == 0)
			{
				System.out.println("The last fluid was removed from a conduit!");
				this.fluidType = ModFluids.fluidEmpty.toString();
			}
			this.shouldSync = true;
			return true;
		}
		return false;
	}
	
	@Override
	public String getFluidType()
	{
		return this.fluidType;
	}

	@Override
	public boolean setFluidType(String type) 
	{
		if (this.fluidType.equals(ModFluids.fluidEmpty.toString()) || this.fluidStored == 0)
		{
			this.fluidType = type;
			this.shouldSync = true;
			return true;
		}
		return false;
	}


}
