package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.api.gas.Gas;
import com.raphydaphy.rocksolid.api.gas.IGasAcceptor;
import com.raphydaphy.rocksolid.api.gas.IGasProducer;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityGasTank extends TileEntity implements IGasAcceptor, IGasProducer
{

	protected int gasStored;
	protected int maxGas;
	protected String gasType = Gas.VACCUM.getName();

	public TileEntityGasTank(final IWorld world, final int x, final int y)
	{
		super(world, x, y);
		maxGas = 10000;

		sync();
	}

	private void sync()
	{
		this.sendToClients();
		this.onSync();
	}

	public float getGasTankFullnesss()
	{
		if (gasStored == 0)
		{
			return 0;
		}
		return (float) this.gasStored / (float) this.maxGas;
	}

	@Override
	public void save(final DataSet set, final boolean forSync)
	{
		super.save(set, forSync);
		set.addInt(Gas.KEY, this.gasStored);
		set.addInt(Gas.MAX_KEY, this.maxGas);
		set.addString(Gas.TYPE_KEY, this.gasType);
	}

	@Override
	public void load(final DataSet set, final boolean forSync)
	{
		super.load(set, forSync);
		this.gasStored = set.getInt(Gas.KEY);
		this.maxGas = set.getInt(Gas.MAX_KEY);
		this.gasType = set.getString(Gas.TYPE_KEY);
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
	public String getGasType()
	{
		return this.gasType;
	}

	@Override
	public boolean removeGas(int amount)
	{
		if (this.gasStored >= amount)
		{
			this.gasStored -= amount;

			if (this.gasStored == 0)
			{
				this.gasType = Gas.VACCUM.getName();
			}
			this.sync();
			return true;
		}
		return false;
	}

	@Override
	public boolean addGas(int amount, String type)
	{
		if (this.gasType == null || type.equals(this.gasType)
				|| this.gasType.equals(Gas.VACCUM.getName()))
		{
			if (this.gasStored + amount <= this.maxGas)
			{
				if (this.gasType == null || !this.gasType.equals(type))
				{
					this.gasType = type;
					RockBottomAPI.getGame().getWorld().causeLightUpdate(x, y);
				}
				this.gasStored += amount;
				this.sync();
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean setGasType(String type)
	{
		if (this.gasType.equals(Gas.VACCUM.getName()) || this.gasStored == 0)
		{
			this.gasType = type;
			this.sync();
			return true;
		}
		return false;
	}

}
