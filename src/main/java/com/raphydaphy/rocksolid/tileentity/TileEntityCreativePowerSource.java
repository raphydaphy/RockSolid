package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.api.energy.IEnergyProducer;

import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityCreativePowerSource extends TileEntity implements IEnergyProducer
{

	protected int maxPower;

	public TileEntityCreativePowerSource(final IWorld world, final int x, final int y)
	{
		super(world, x, y);

		maxPower = 2000000;
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

	public float getBatteryFullness()
	{
		return 0.5f;
	}

	@Override
	public void save(final DataSet set, final boolean forSync)
	{
		super.save(set, forSync);
		set.addInt("maxPower", this.maxPower);
	}

	@Override
	public void load(final DataSet set, final boolean forSync)
	{
		super.load(set, forSync);
		this.maxPower = set.getInt("maxPower");
	}

	@Override
	public int getCurrentEnergy()
	{
		return 1000000;
	}

	@Override
	public int getMaxEnergy()
	{
		return this.maxPower;
	}

	@Override
	public boolean removeEnergy(int amount)
	{
		return true;
	}

}
