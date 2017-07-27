package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.api.gas.IGasAcceptor;
import com.raphydaphy.rocksolid.api.gas.IGasProducer;
import com.raphydaphy.rocksolid.api.gas.IGasTile;
import com.raphydaphy.rocksolid.api.util.IConduit;
import com.raphydaphy.rocksolid.init.ModGasses;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityGasConduit extends TileEntity implements IConduit, IGasProducer, IGasAcceptor
{

    private int modeUp = 0;
    private int modeDown = 0;
    private int modeLeft = 0;
    private int modeRight = 0;
    
    private int gasStored = 0;
    private int maxGas = 1000;
    private String gasType;
    
    private int transferRate = 250;
    
    private boolean shouldSync = false;
    
    public TileEntityGasConduit(final IWorld world, final int x, final int y) 
    {
        super(world, x, y);
        
        if (this.gasType == null)
        {
        	this.gasType = ModGasses.gasVacuum.toString();
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
	   				if (adjacentTileEntity instanceof TileEntityGasConduit)
	   				{
	   					if (this.getSideMode(adjacentTile) != 2 && (((TileEntityGasConduit)adjacentTileEntity).getGasType().equals(this.gasType)) || this.gasType.equals(ModGasses.gasVacuum.toString()))
	   					{
		   					if (((TileEntityGasConduit)adjacentTileEntity).getCurrentGas() > this.gasStored)
							{
								String wasFluidType = ((TileEntityGasConduit)adjacentTileEntity).getGasType();
								// pull fluid from adjacent conduit
								if (((TileEntityGasConduit)adjacentTileEntity).removeGas(transferRate))
								{
									this.addGas(transferRate, wasFluidType);
									shouldSync = true;
								}
							}
	   					}
	   				}
	   				else if (IGasTile.class.isAssignableFrom(adjacentTileEntity.getClass()))
	   				{
	   					if (((IGasTile)adjacentTileEntity).getGasType().equals(this.gasType) || this.gasType.equals(ModGasses.gasVacuum.toString()) || ((IGasTile)adjacentTileEntity).getGasType().equals(ModGasses.gasVacuum.toString()))
	   					{
		   					if (IGasProducer.class.isAssignableFrom(adjacentTileEntity.getClass()) && (((IGasTile)adjacentTileEntity).getGasType().equals(this.gasType)) || this.gasType.equals(ModGasses.gasVacuum.toString()))
		   	   				{
		   						// Conduit is set to input mode
		   	   					if (this.getSideMode(adjacentTile) == 1)
		   	   					{
		   	   						
		   	   						if (this.gasStored < (this.maxGas - transferRate) && ((IGasProducer)adjacentTileEntity).getCurrentGas() >= transferRate)
		   	   						{
		   	   							String wasGasType = ((IGasProducer)adjacentTileEntity).getGasType();
		   	   							// pull fluid from adjacent tile
		   	   							if (((IGasProducer)adjacentTileEntity).removeGas(transferRate))
		   	   							{
		   	   								this.addGas(transferRate, wasGasType);
		   	   								this.shouldSync = true;
		   	   							}
		   	   						}
		   	   					}
		   	   				}
		   					
		   					if (IGasAcceptor.class.isAssignableFrom(adjacentTileEntity.getClass()))
		   	   				{
		   						// Conduit is set to output mode
		   	   					if (this.getSideMode(adjacentTile) == 0)
		   	   					{
			   	   					
		   	   						if (this.gasStored >= transferRate)
		   	   						{
			   	   						if (((IGasTile)adjacentTileEntity).getGasType().equals(ModGasses.gasVacuum.toString()))
			   	   						{
				   	   						// set the fluid type in the adjacent tile to match this
			   	   							((IGasAcceptor)adjacentTileEntity).setGasType(this.gasType);
			   	   						}
		   	   							// send fluid to adjacent tile
		   	   							if (((IGasAcceptor)adjacentTileEntity).addGas(transferRate, this.gasType))
		   	   							{
		   	   								this.removeGas(transferRate);
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
        set.addInt("gasStored", this.gasStored);
        set.addInt("maxGas", this.maxGas);
        set.addInt("transferRate", this.transferRate);
        set.addBoolean("shouldSync", this.shouldSync);
        set.addString("gasType", this.gasType);
    }
    
    @Override
    public void load(final DataSet set, final boolean forSync) 
    {
        super.load(set, forSync);
        this.modeUp = set.getInt("modeUp");
        this.modeDown = set.getInt("modeDown");
        this.modeLeft = set.getInt("modeLeft");
        this.modeRight = set.getInt("modeRight");
        this.gasStored = set.getInt("gasStored");
        this.maxGas = set.getInt("maxGas");
        this.transferRate = set.getInt("transferRate");
        this.shouldSync = set.getBoolean("shouldSync");
        this.gasType = set.getString("gasType");
    }
    
	@Override
	public boolean canConnectTo(Class<?> adjacentBlock) 
	{
		return IGasTile.class.isAssignableFrom(adjacentBlock);
	}

	@Override
	public void setSync() 
	{
		if (RockBottomAPI.getNet().isClient() == false)
		{
			shouldSync = true;
		}
		
	}

	@Override
	public int getCurrentGas() 
	{
		return this.gasStored;
	}

	@Override
	public int getMaxGas() 
	{
		return this.maxGas;
	}

	@Override
	public boolean addGas(int amount, String type) 
	{
		if (this.gasType == null || type.equals(this.gasType) || this.gasType.equals(ModGasses.gasVacuum.toString()))
		{
			if (this.gasStored + amount <= this.maxGas)
			{
				if (this.gasType == null || this.gasType.equals(ModGasses.gasVacuum.toString()))
				{
					this.gasType = type;
				}
				this.gasStored += amount;
				this.shouldSync = true;
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean removeGas(int amount) 
	{
		if (this.gasStored >= amount)
		{
			this.gasStored -= amount;
			
			if (this.gasStored == 0)
			{
				this.gasType = ModGasses.gasVacuum.toString();
			}
			this.shouldSync = true;
			return true;
		}
		return false;
	}
	
	@Override
	public String getGasType()
	{
		return this.gasType;
	}

	@Override
	public boolean setGasType(String type) 
	{
		if (this.gasType.equals(ModGasses.gasVacuum.toString()) || this.gasStored == 0)
		{
			this.gasType = type;
			this.shouldSync = true;
			return true;
		}
		return false;
	}


}
