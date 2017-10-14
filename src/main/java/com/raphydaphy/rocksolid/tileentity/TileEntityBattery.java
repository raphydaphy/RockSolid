package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.api.energy.IEnergyAcceptor;
import com.raphydaphy.rocksolid.api.energy.IEnergyProducer;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileEntityBattery extends TileEntity implements IEnergyAcceptor, IEnergyProducer
{

	protected int powerStored;
	protected int maxPower;

	public TileEntityBattery(final IWorld world, final int x, final int y, TileLayer layer)
	{
		super(world, x, y, layer);

		maxPower = 1000000;
	}

	private void sync()
	{
		this.sendToClients();
		this.onSync();
	}

	public float getBatteryFullness()
	{
		if (powerStored == 0)
		{
			return 0;
		}
		return (float) this.powerStored / (float) this.maxPower;
	}

	@Override
	public void save(final DataSet set, final boolean forSync)
	{
		super.save(set, forSync);
		set.addInt("powerStored", this.powerStored);
		set.addInt("maxPower", this.maxPower);
	}

	@Override
	public void load(final DataSet set, final boolean forSync)
	{
		super.load(set, forSync);
		this.powerStored = set.getInt("powerStored");
		this.maxPower = set.getInt("maxPower");
	}

	@Override
	public int getCurrentEnergy()
	{
		return this.powerStored;
	}

	@Override
	public int getMaxEnergy()
	{
		return this.maxPower;
	}

	@Override
	public boolean removeEnergy(int amount)
	{
		if (this.powerStored >= amount)
		{
			if (RockBottomAPI.getNet().isClient() == false)
			{
				this.powerStored -= amount;
				this.sync();
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean addEnergy(int amount)
	{
		if (this.powerStored <= (this.maxPower - amount))
		{
			if (RockBottomAPI.getNet().isClient() == false)
			{
				this.powerStored += amount;
				this.sync();
			}
			return true;
		}
		return false;
	}

}
