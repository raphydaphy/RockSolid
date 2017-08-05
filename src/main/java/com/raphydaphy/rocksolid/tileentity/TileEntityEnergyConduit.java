package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.api.energy.IEnergyAcceptor;
import com.raphydaphy.rocksolid.api.energy.IEnergyProducer;
import com.raphydaphy.rocksolid.api.energy.IEnergyTile;
import com.raphydaphy.rocksolid.api.util.IConduit;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
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

	private boolean shouldSync = false;

	public TileEntityEnergyConduit(final IWorld world, final int x, final int y)
	{
		super(world, x, y);
	}

	@Override
	protected boolean needsSync()
	{
		return super.needsSync() || shouldSync;
	}

	@Override
	public void update(IGameInstance game)
	{
		super.update(game);
		if (RockBottomAPI.getNet().isClient() == false)
		{
			// first we extract stuff from nearby inventories into the pipes
			// inventory
			for (int adjacentTile = 0; adjacentTile < 4; adjacentTile++)
			{
				Pos2 adjacentTilePos = RockSolidAPILib.conduitSideToPos(new Pos2(x, y), adjacentTile);
				TileEntity adjacentTileEntity = RockSolidAPILib.getTileFromPos(adjacentTilePos.getX(),
						adjacentTilePos.getY(), world);

				if (adjacentTileEntity != null)
				{
					// return
					// IEnergyBlock.class.isAssignableFrom(adjacentBlock);
					if (adjacentTileEntity instanceof TileEntityEnergyConduit)
					{
						if (this.getSideMode(adjacentTile) != 2)
						{
							if (((TileEntityEnergyConduit) adjacentTileEntity).getCurrentEnergy() > this
									.getCurrentEnergy())
							{
								if (((TileEntityEnergyConduit) adjacentTileEntity).getCurrentEnergy() >= transferRate)
								{
									if (this.addEnergy(transferRate))
									{
										((TileEntityEnergyConduit) adjacentTileEntity).removeEnergy(transferRate);
										shouldSync = true;
									}
								}
							}
						}
					} else if (IEnergyTile.class.isAssignableFrom(adjacentTileEntity.getClass()))
					{
						if (IEnergyProducer.class.isAssignableFrom(adjacentTileEntity.getClass()))
						{
							// Conduit is set to input mode
							if (this.getSideMode(adjacentTile) == 1)
							{
								if (this.energyStored < (this.maxEnergy - transferRate))
								{
									if (((IEnergyProducer) adjacentTileEntity).removeEnergy(transferRate))
									{
										this.energyStored += transferRate;
										shouldSync = true;
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
									if (((IEnergyAcceptor) adjacentTileEntity).addEnergy(transferRate))
									{
										this.removeEnergy(transferRate);
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

	public void setSideMode(int side, int mode)
	{
		shouldSync = true;
		switch (side)
		{
		case 0:
			// up
			modeUp = mode;
			break;
		case 1:
			// down
			modeDown = mode;
			break;
		case 2:
			// left
			modeLeft = mode;
			break;
		case 3:
			// right
			modeRight = mode;
			break;
		}
	}

	public int getSideMode(int side)
	{
		switch (side)
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
		set.addInt("energyStored", this.energyStored);
		set.addInt("maxEnergy", this.maxEnergy);
		set.addInt("transferRate", this.transferRate);
		set.addBoolean("shouldSync", this.shouldSync);
	}

	@Override
	public void load(final DataSet set, final boolean forSync)
	{
		super.load(set, forSync);
		this.modeUp = set.getInt("modeUp");
		this.modeDown = set.getInt("modeDown");
		this.modeLeft = set.getInt("modeLeft");
		this.modeRight = set.getInt("modeRight");
		this.energyStored = set.getInt("energyStored");
		this.maxEnergy = set.getInt("maxEnergy");
		this.transferRate = set.getInt("transferRate");
		this.shouldSync = set.getBoolean("shouldSync");
	}

	@Override
	public boolean canConnectTo(Pos2 pos, TileEntity tile)
	{
		return tile instanceof IEnergyTile;
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
			if (RockBottomAPI.getNet().isClient() == false)
			{
				this.energyStored -= amount;
				shouldSync = true;
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean addEnergy(int amount)
	{
		if (this.energyStored <= (this.maxEnergy - amount))
		{
			if (RockBottomAPI.getNet().isClient() == false)
			{
				this.energyStored += amount;
				shouldSync = true;
			}
			return true;
		}
		return false;
	}

	@Override
	public void setSync()
	{
		if (RockBottomAPI.getNet().isClient() == false)
		{
			shouldSync = true;
		}

	}

}
