package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.api.fluid.IFluidAcceptor;
import com.raphydaphy.rocksolid.api.fluid.IFluidProducer;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityTank extends TileEntity implements IFluidAcceptor, IFluidProducer
{

	protected int fluidStored;
	protected int maxFluid;
	protected String fluidType = Fluid.EMPTY.toString();

	public TileEntityTank(final IWorld world, final int x, final int y)
	{
		super(world, x, y);
		maxFluid = 20000;

		sync();
	}

	private void sync()
	{
		this.sendToClients();
		this.onSync();
	}

	public float getFluidTankFullnesss()
	{
		if (fluidStored == 0)
		{
			return 0;
		}
		return (float) this.fluidStored / (float) this.maxFluid;
	}

	@Override
	public void save(final DataSet set, final boolean forSync)
	{
		super.save(set, forSync);
		set.addInt("fluidStored", this.fluidStored);
		set.addInt("maxFluid", this.maxFluid);
		set.addString("fluidType", this.fluidType);
	}

	@Override
	public void load(final DataSet set, final boolean forSync)
	{
		super.load(set, forSync);
		this.fluidStored = set.getInt("fluidStored");
		this.maxFluid = set.getInt("maxFluid");
		this.fluidType = set.getString("fluidType");
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
	public String getFluidType()
	{
		return this.fluidType;
	}

	@Override
	public boolean removeFluid(int amount)
	{
		if (this.fluidStored >= amount)
		{
			this.fluidStored -= amount;

			if (this.fluidStored == 0)
			{
				this.fluidType = Fluid.EMPTY.toString();
			}
			this.sync();
			return true;
		}
		return false;
	}

	@Override
	public boolean addFluid(int amount, String type)
	{
		if (this.fluidType == null || type.equals(this.fluidType)
				|| this.fluidType.equals(Fluid.EMPTY.toString()))
		{
			if (this.fluidStored + amount <= this.maxFluid)
			{
				if (this.fluidType == null || !this.fluidType.equals(type))
				{
					this.fluidType = type;
					RockBottomAPI.getGame().getWorld().causeLightUpdate(x, y);
				}
				this.fluidStored += amount;
				this.sync();
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean setFluidType(String type)
	{
		if (this.fluidType == Fluid.EMPTY.toString() || this.fluidStored == 0)
		{
			this.fluidType = type;
			this.sync();
			return true;
		}
		return false;
	}

}
