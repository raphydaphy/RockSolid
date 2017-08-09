package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.api.energy.IEnergyProducer;
import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.api.fluid.IFluidAcceptor;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityCombustionEngine extends TileEntity implements IFluidAcceptor, IEnergyProducer
{

	protected int fluidStored = 0;
	protected int maxFluid = 5000;
	protected String fluidType = Fluid.EMPTY.getName();
	public static final int fluidConsumptionPerTick = 5;
	public static final int productionPerTick = 80;

	protected int powerStored = 0;
	private boolean shouldSync = false;

	public TileEntityCombustionEngine(final IWorld world, final int x, final int y)
	{
		super(world, x, y);
	}

	@Override
	protected boolean needsSync()
	{
		return super.needsSync() || this.shouldSync;
	}

	@Override
	protected void onSync()
	{
		super.onSync();
		shouldSync = false;
	}

	@Override
	public void update(IGameInstance game)
	{
		super.update(game);
		if (RockBottomAPI.getNet().isClient() == false)
		{
			if (this.getCurrentEnergy() + productionPerTick <= this.getMaxEnergy())
			{
				if (this.fluidStored > fluidConsumptionPerTick && this.fluidType.equals(Fluid.OIL.getName()))
				{
					this.fluidStored -= fluidConsumptionPerTick;
					this.powerStored += productionPerTick;
					this.shouldSync = true;
				}
			}

			if (this.fluidStored == 0)
			{
				this.setFluidType(Fluid.EMPTY.getName());
			}

			shouldSync = true;
		}
	}

	@Override
	public void save(final DataSet set, final boolean forSync)
	{
		super.save(set, forSync);
		set.addInt("powerStored", this.powerStored);
		set.addBoolean("shouldSync", this.shouldSync);
		set.addInt(Fluid.KEY, this.fluidStored);
		set.addInt(Fluid.MAX_KEY, this.maxFluid);
		set.addString(Fluid.TYPE_KEY, this.fluidType);
	}

	@Override
	public void load(final DataSet set, final boolean forSync)
	{
		super.load(set, forSync);
		this.powerStored = set.getInt("powerStored");
		this.shouldSync = set.getBoolean("shouldSync");
		this.fluidStored = set.getInt(Fluid.KEY);
		this.maxFluid = set.getInt(Fluid.MAX_KEY);
		this.fluidType = set.getString(Fluid.TYPE_KEY);
	}

	public float getFluidTankFullness()
	{
		if (fluidStored == 0)
		{
			return 0;
		}
		return (float) this.fluidStored / (float) this.maxFluid;
	}

	public float getEnergyFullness()
	{
		if (this.getCurrentEnergy() == 0)
		{
			return 0;
		}
		return (float) this.getCurrentEnergy() / (float) this.getMaxEnergy();
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
	public boolean addFluid(int amount, String type)
	{
		if (this.fluidStored + amount <= this.maxFluid)
		{
			if (this.fluidType == null || type.equals(this.fluidType) || this.fluidType.equals(Fluid.EMPTY.getName()))
			{
				this.fluidType = type;
				this.fluidStored += amount;
				this.shouldSync = true;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean setFluidType(String type)
	{
		if (this.fluidType == null || this.fluidType.equals(Fluid.EMPTY.getName()) || this.fluidStored == 0)
		{
			this.fluidType = type;
			return true;
		}
		return false;
	}

	@Override
	public int getCurrentEnergy()
	{
		return this.powerStored;
	}

	@Override
	public int getMaxEnergy()
	{
		return 25000;
	}

	@Override
	public boolean removeEnergy(int amount)
	{
		if (this.getCurrentEnergy() >= amount)
		{
			if (RockBottomAPI.getNet().isClient() == false)
			{
				this.powerStored -= amount;
				this.shouldSync = true;
			}
			return true;
		}
		return false;
	}

}
