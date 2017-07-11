package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.api.IConduit;
import com.raphydaphy.rocksolid.api.IEnergyAcceptor;
import com.raphydaphy.rocksolid.api.IEnergyBlock;
import com.raphydaphy.rocksolid.api.IEnergyProducer;
import com.raphydaphy.rocksolid.api.RockSolidLib;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityEnergyConduit extends TileEntity implements IConduit, IEnergyProducer, IEnergyAcceptor
{

    private int modeUp = 0;
    private int modeDown = 0;
    private int modeLeft = 0;
    private int modeRight = 0;
    
    private int energyStored = 0;
    private int maxEnergy = 1000;
    
    private int transferRate = 300;
    
    public TileEntityEnergyConduit(final IWorld world, final int x, final int y) 
    {
        super(world, x, y);
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
   		// first we extract stuff from nearby inventories into the pipes inventory
   		for (int adjacentTile = 0; adjacentTile < 4; adjacentTile++)
   		{
   			Pos2 adjacentTilePos = RockSolidLib.conduitSideToPos(new Pos2(x, y), adjacentTile);
   			TileEntity adjacentTileEntity = RockSolidLib.getTileFromPos(adjacentTilePos.getX(), adjacentTilePos.getY(), world);
   			
   			if (adjacentTileEntity != null)
   			{
	   			// return IEnergyBlock.class.isAssignableFrom(adjacentBlock);
   				if (adjacentTileEntity instanceof TileEntityEnergyConduit)
   				{
   					if (this.getSideMode(adjacentTile) != 2)
   					{
	   					if (((TileEntityEnergyConduit)adjacentTileEntity).getCurrentEnergy() > this.getCurrentEnergy())
						{
							if (((TileEntityEnergyConduit)adjacentTileEntity).getCurrentEnergy() >= transferRate)
							{
								if (this.addEnergy(transferRate))
								{
									((TileEntityEnergyConduit)adjacentTileEntity).removeEnergy(transferRate);
								}
							}
						}
   					}
   				}
   				else if (IEnergyBlock.class.isAssignableFrom(adjacentTileEntity.getClass()))
   				{
   					if (IEnergyProducer.class.isAssignableFrom(adjacentTileEntity.getClass()))
   	   				{
   						// Conduit is set to input mode
   	   					if (this.getSideMode(adjacentTile) == 1)
   	   					{
   	   						if (this.energyStored < (this.maxEnergy - transferRate))
   	   						{
	   	   						if (((IEnergyProducer)adjacentTileEntity).removeEnergy(transferRate))
	   	   						{
	   	   							this.energyStored += transferRate;
	   	   						}
   	   						}
   	   					}
   	   				}
   					
   					if (IEnergyAcceptor.class.isAssignableFrom(adjacentTileEntity.getClass()))
   	   				{
   						// Conduit is set to output mode
   	   					if (this.getSideMode(adjacentTile) == 0)
   	   					{
   	   						if (this.energyStored >= transferRate)
   	   						{
	   	   						if (((IEnergyAcceptor)adjacentTileEntity).addEnergy(transferRate))
	   	   						{
	   	   							this.removeEnergy(transferRate);
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
        set.addInt("modeUp", this.modeUp);
        set.addInt("modeDown", this.modeDown);
        set.addInt("modeLeft", this.modeLeft);
        set.addInt("modeRight", this.modeRight);
        set.addInt("energyStored", this.energyStored);
        set.addInt("maxEnergy", this.maxEnergy);
        set.addInt("transferRate", this.transferRate);
    }
    
    @Override
    public void load(final DataSet set, final boolean forSync) {
        super.load(set, forSync);
        this.modeUp = set.getInt("modeUp");
        this.modeDown = set.getInt("modeDown");
        this.modeLeft = set.getInt("modeLeft");
        this.modeRight = set.getInt("modeRight");
        this.energyStored = set.getInt("energyStored");
        this.maxEnergy = set.getInt("maxEnergy");
        this.transferRate = set.getInt("transferRate");
    }

	@Override
	public boolean canConnectTo(Class<?> adjacentBlock) 
	{
		return IEnergyBlock.class.isAssignableFrom(adjacentBlock);
	}

	@Override
	public int getCurrentEnergy() 
	{
		return energyStored;
	}

	@Override
	public int getMaxEnergy() 
	{
		return maxEnergy;
	}

	@Override
	public boolean removeEnergy(int amount) 
	{
		if (this.energyStored >= amount)
		{
			this.energyStored -= amount;
			return true;
		}
		return false;
	}

	@Override
	public boolean addEnergy(int amount) 
	{
		if (this.energyStored <= (this.maxEnergy - amount))
		{
			this.energyStored += amount;
			return true;
		}
		return false;
	}

}
