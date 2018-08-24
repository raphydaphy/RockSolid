package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.energy.IEnergyTile;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.entity.SyncedInt;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileEntityBattery extends TileEntity implements IEnergyTile
{
	private SyncedInt energyStored = new SyncedInt("energy_stored");

	public TileEntityBattery(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
	}

	@Override
	public void save(DataSet set, boolean forSync)
	{
		super.save(set, forSync);
		energyStored.save(set);
	}

	@Override
	public void load(DataSet set, boolean forSync)
	{
		super.load(set, forSync);
		energyStored.load(set);
	}

	@Override
	protected boolean needsSync()
	{
		return this.energyStored.needsSync();
	}

	@Override
	public void onSync()
	{
		super.onSync();
		energyStored.onSync();
	}

	@Override
	public boolean addEnergy(Pos2 pos, int joules, boolean simulate)
	{
		if (joules + energyStored.get() <= getEnergyCapacity(world, pos))
		{
			if (!simulate)
			{
				this.energyStored.add(joules);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean removeEnergy(Pos2 pos, int joules, boolean simulate)
	{
		if (energyStored.get() - joules > 0)
		{
			if (!simulate)
			{
				this.energyStored.remove(joules);
			}
			return true;
		}
		return false;
	}

	@Override
	public int getEnergyCapacity(IWorld world, Pos2 pos)
	{
		return 25000;
	}

	@Override
	public int getMaxTransfer()
	{
		return 150;
	}

	@Override
	public int getEnergyStored()
	{
		return this.energyStored.get();
	}

	public float getEnergyFullness()
	{
		int capacity = getEnergyCapacity(world, null);
		return capacity > 0 ? (float) this.energyStored.get() / (float) capacity : 0.0F;
	}

	@Override
	public boolean doesTick()
	{
		return true;
	}
}
