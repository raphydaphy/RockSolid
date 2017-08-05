package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.api.content.RockSolidContent;
import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.api.fluid.IMultiFluidAcceptor;
import com.raphydaphy.rocksolid.api.gas.IGasProducer;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityLiquidBoiler extends TileEntity implements IGasProducer, IMultiFluidAcceptor
{
	private boolean shouldSync = false;

	public static final int productionPerTick = 3;
	public static final int[] fluidConsumptionPerTick = { 3, 4 };

	protected int gasStored;
	protected int maxGas = 5000;
	protected String gasType = RockSolidContent.gasVacuum.toString();

	// tank 0 is water tank 1 is lava
	protected int[] fluidStored = new int[] { 0, 0 };
	protected int maxFluid = 5000;
	protected String[] fluidType = new String[] { Fluid.EMPTY.toString(),
			Fluid.EMPTY.toString() };

	public TileEntityLiquidBoiler(final IWorld world, final int x, final int y)
	{
		super(world, x, y);

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
		shouldSync = false;
	}

	@Override
	public void update(IGameInstance game)
	{
		super.update(game);
		if (this.gasStored < (this.maxGas - productionPerTick - 1) && this.fluidStored[0] >= fluidConsumptionPerTick[0]
				&& this.fluidStored[1] >= fluidConsumptionPerTick[1]
				&& this.fluidType[0].equals(Fluid.WATER.toString())
				&& this.fluidType[1].equals(Fluid.LAVA.toString()))
		{
			if (RockBottomAPI.getNet().isClient() == false)
			{
				if (this.gasType.equals(RockSolidContent.gasVacuum.toString()))
				{
					this.gasType = RockSolidContent.gasSteam.toString();
				}

				this.fluidStored[0] -= fluidConsumptionPerTick[0];
				this.fluidStored[1] -= fluidConsumptionPerTick[1];
				this.gasStored += productionPerTick;

				if (this.fluidStored[0] == 0)
				{
					this.fluidType[0] = Fluid.EMPTY.toString();
				}

				if (this.fluidStored[1] == 0)
				{
					this.fluidType[1] = Fluid.EMPTY.toString();
				}
				shouldSync = true;

			}
		}
	}

	public float getFluidTank0Fullness()
	{
		if (fluidStored[0] == 0)
		{
			return 0;
		}
		return (float) this.fluidStored[0] / (float) this.maxFluid;
	}

	public float getFluidTank1Fullness()
	{
		if (fluidStored[1] == 0)
		{
			return 0;
		}
		return (float) this.fluidStored[1] / (float) this.maxFluid;
	}

	protected void onActiveChange(final boolean active)
	{
		this.world.causeLightUpdate(this.x, this.y);
	}

	public boolean isActive()
	{
		return (this.gasStored < (this.maxGas - productionPerTick - 1)
				&& this.fluidStored[0] >= fluidConsumptionPerTick[0]
				&& this.fluidStored[1] >= fluidConsumptionPerTick[1]
				&& this.fluidType[0].equals(Fluid.WATER.toString())
				&& this.fluidType[1].equals(Fluid.LAVA.toString()));
	}

	public float getGeneratorFullness()
	{
		if (this.gasStored == 0)
		{
			return 0;
		}
		return (float) this.gasStored / (float) this.maxGas;
	}

	@Override
	public void save(final DataSet set, final boolean forSync)
	{
		super.save(set, forSync);
		set.addBoolean("shouldSync", this.shouldSync);
		set.addString("gasType", this.gasType);
		set.addInt("gasStored", this.gasStored);
		set.addInt("maxGas", this.maxGas);
		set.addString("fluidType0", this.fluidType[0]);
		set.addString("fluidType1", this.fluidType[1]);
		set.addIntArray("fluidStored", this.fluidStored);
		set.addInt("maxFluid", this.maxFluid);
	}

	@Override
	public void load(final DataSet set, final boolean forSync)
	{
		super.load(set, forSync);
		this.shouldSync = set.getBoolean("shouldSync");
		this.gasType = set.getString("gasType");
		this.gasStored = set.getInt("gasStored");
		this.maxGas = set.getInt("maxGas");
		this.fluidType[0] = set.getString("fluidType0");
		this.fluidType[1] = set.getString("fluidType1");
		this.fluidStored = set.getIntArray("fluidStored", 2);
		this.maxFluid = set.getInt("maxFluid");
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
			if (RockBottomAPI.getNet().isClient() == false)
			{
				this.gasStored -= amount;

				if (this.gasStored == 0)
				{
					this.gasType = RockSolidContent.gasVacuum.toString();
				}
				this.shouldSync = true;
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean addFluid(int amount, String type, int tank)
	{
		if (tank == 1 && !type.equals(Fluid.LAVA.toString()))
		{
			return false;
		}
		if (tank == 0 && !type.equals(Fluid.WATER.toString()))
		{
			return false;
		}

		if (this.fluidStored[tank] + amount <= this.maxFluid)
		{
			if (this.fluidType[tank] == null || type.equals(this.fluidType[tank])
					|| this.fluidType[tank].equals(Fluid.EMPTY.toString()))
			{
				this.fluidType[tank] = type;
				this.fluidStored[tank] += amount;
				this.shouldSync = true;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean setFluidType(String type, int tank)
	{
		if (tank == 1 && !type.equals(Fluid.LAVA.toString()))
		{
			return false;
		}
		if (tank == 0 && !type.equals(Fluid.WATER.toString()))
		{
			return false;
		}

		if (this.fluidType[tank] == null || this.fluidType[tank].equals(Fluid.EMPTY.toString())
				|| this.fluidStored[tank] == 0)
		{
			this.fluidType[tank] = type;
			return true;
		}
		return false;
	}

	@Override
	public int[] getFluidTanksStorage()
	{
		return this.fluidStored;
	}

	@Override
	public int getMaxFluid()
	{
		return this.maxFluid;
	}

	@Override
	public String[] getFluidTanksType()
	{
		return this.fluidType;
	}

	@Override
	public int getSideMode(int posX, int posY)
	{
		if (posY == 0)
		{
			return 0;
		}
		return 2;
	}

	@Override
	public int getTankNumber(Pos2 tankLocation)
	{
		if (tankLocation.getY() == 0)
		{
			return tankLocation.getX();
		}
		return -1;
	}

}
