package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.api.RockSolidAPI;
import com.raphydaphy.rocksolid.api.content.RockSolidContent;
import com.raphydaphy.rocksolid.api.energy.TileEntityPowered;
import com.raphydaphy.rocksolid.api.fluid.IFluidAcceptor;
import com.raphydaphy.rocksolid.api.gas.IMultiGasProducer;
import com.raphydaphy.rocksolid.api.recipe.ElectrolyzerRecipe;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityElectrolyzer extends TileEntityPowered implements IFluidAcceptor, IMultiGasProducer
{
	protected int processTime;
	protected int maxProcessTime;
	private int lastSmelt;

	protected int fluidStored = 0;
	protected int maxFluid = 5000;
	protected String fluidType = RockSolidContent.fluidEmpty.toString();

	protected int gasTank1Storage = 0;
	protected int gasTank2Storage = 0;
	protected int maxGasStorage = 5000;
	protected String gasTank1Type = RockSolidContent.gasVacuum.toString();
	protected String gasTank2Type = RockSolidContent.gasVacuum.toString();

	protected int powerStored = 0;
	private boolean shouldSync = false;

	public TileEntityElectrolyzer(final IWorld world, final int x, final int y)
	{
		super(world, x, y, 5000, 30);
	}

	@Override
	protected boolean needsSync()
	{
		return super.needsSync() || this.lastSmelt != this.processTime || this.shouldSync;
	}

	@Override
	protected void onSync()
	{
		super.onSync();
		shouldSync = false;
		this.lastSmelt = this.processTime;
	}

	@Override
	public boolean tryTickAction()
	{
		boolean hasRecipeAndSpace = false;

		if (this.fluidStored > 0)
		{
			final ElectrolyzerRecipe recipe = RockSolidAPI.getElectrolyzerRecipe(this.gasTank1Type, this.gasTank2Type,
					this.fluidType, this.fluidStored);
			if (recipe != null)
			{
				final String recipeOut1 = recipe.getOutput1();
				final String recipeOut2 = recipe.getOutput2();
				boolean output1Matches = (recipeOut1.equals(this.gasTank1Type)
						|| this.gasTank1Type.equals(RockSolidContent.gasVacuum.toString()));
				boolean output2Matches = (recipeOut2.equals(this.gasTank2Type)
						|| this.gasTank2Type.equals(RockSolidContent.gasVacuum.toString()));
				if (output1Matches && output2Matches
						&& this.gasTank1Storage + recipe.getOutput1Volume() <= this.maxGasStorage
						&& this.gasTank2Storage + recipe.getOutput2Volume() <= this.maxGasStorage)
				{
					hasRecipeAndSpace = true;
					if (this.getCurrentEnergy() >= super.getPowerPerOperation())
					{
						if (RockBottomAPI.getNet().isClient() == false)
						{
							if (this.maxProcessTime <= 0)
							{
								this.maxProcessTime = recipe.getTime() / 5;
							}
							++this.processTime;
							shouldSync = true;
						}
						if (this.processTime < this.maxProcessTime)
						{
							return hasRecipeAndSpace;
						}
						if (RockBottomAPI.getNet().isClient() == false)
						{
							// remove water
							this.fluidStored -= recipe.getFluidVolume();
							if (this.fluidStored == 0)
							{
								this.setFluidType(RockSolidContent.fluidEmpty.toString());
							}

							this.setGasType(recipeOut1, 0);
							this.setGasType(recipeOut2, 1);

							this.gasTank1Storage += recipe.getOutput1Volume();
							this.gasTank2Storage += recipe.getOutput2Volume();
							shouldSync = true;
						}

					} else if (this.processTime > 0)
					{
						if (RockBottomAPI.getNet().isClient() == false)
						{
							this.processTime = Math.max(this.processTime - 2, 0);
							shouldSync = true;
						}
						return hasRecipeAndSpace;
					}
				}
			}
		}
		if (RockBottomAPI.getNet().isClient() == false)
		{
			this.processTime = 0;
			this.maxProcessTime = 0;

			if (this.fluidStored == 0)
			{
				this.setFluidType(RockSolidContent.fluidEmpty.toString());
			}

			if (this.gasTank1Storage == 0)
			{
				this.gasTank1Type = RockSolidContent.gasVacuum.toString();
			}

			if (this.gasTank2Storage == 0)
			{
				this.gasTank2Type = RockSolidContent.gasVacuum.toString();
			}

			shouldSync = true;
		}
		return hasRecipeAndSpace;
	}

	public float getSmeltPercentage()
	{
		return (float) this.processTime / (float) this.maxProcessTime;
	}

	public boolean isActive()
	{
		return this.processTime > 0;
	}

	@Override
	public void save(final DataSet set, final boolean forSync)
	{
		super.save(set, forSync);
		set.addInt("process", this.processTime);
		set.addInt("max_process", this.maxProcessTime);
		set.addInt("powerStored", this.powerStored);
		set.addBoolean("shouldSync", this.shouldSync);
		set.addInt("fluidStored", this.fluidStored);
		set.addInt("maxFluid", this.maxFluid);
		set.addString("fluidType", this.fluidType);
		set.addString("gasTank1Type", this.gasTank1Type);
		set.addString("gasTank2Type", this.gasTank2Type);
		set.addInt("gasTank1Storage", this.gasTank1Storage);
		set.addInt("gasTank2Storage", this.gasTank2Storage);
	}

	@Override
	public void load(final DataSet set, final boolean forSync)
	{
		super.load(set, forSync);
		this.processTime = set.getInt("process");
		this.maxProcessTime = set.getInt("max_process");
		this.powerStored = set.getInt("powerStored");
		this.shouldSync = set.getBoolean("shouldSync");
		this.fluidStored = set.getInt("fluidStored");
		this.maxFluid = set.getInt("maxFluid");
		this.fluidType = set.getString("fluidType");
		this.gasTank1Type = set.getString("gasTank1Type");
		this.gasTank2Type = set.getString("gasTank2Type");
		this.gasTank1Storage = set.getInt("gasTank1Storage");
		this.gasTank2Storage = set.getInt("gasTank2Storage");
	}

	@Override
	protected void setPower(int power)
	{
		this.powerStored = power;
		this.shouldSync = true;
	}

	@Override
	protected int getPower()
	{
		return this.powerStored;
	}

	public float getFluidTankFullness()
	{
		if (fluidStored == 0)
		{
			return 0;
		}
		return (float) this.fluidStored / (float) this.maxFluid;
	}

	public float getGasTank1Fullness()
	{
		if (this.gasTank1Storage == 0)
		{
			return 0;
		} else
		{
			return (float) this.gasTank1Storage / (float) this.maxGasStorage;
		}
	}

	public float getGasTank2Fullness()
	{
		if (this.gasTank2Storage == 0)
		{
			return 0;
		} else
		{
			return (float) this.gasTank2Storage / (float) this.maxGasStorage;
		}
	}

	@Override
	public String[] getGasTanksType()
	{
		return new String[] { this.gasTank1Type, this.gasTank2Type };
	}

	@Override
	public int[] getGasTanksStorage()
	{
		return new int[] { this.gasTank1Storage, this.gasTank2Storage };
	}

	@Override
	protected void onActiveChange(boolean active)
	{
		this.world.causeLightUpdate(this.x, this.y);
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
			if (this.fluidType == null || type.equals(this.fluidType)
					|| this.fluidType.equals(RockSolidContent.fluidEmpty.toString()))
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
		if (this.fluidType == null || this.fluidType.equals(RockSolidContent.fluidEmpty.toString()) || this.fluidStored == 0)
		{
			this.fluidType = type;
			return true;
		}
		return false;
	}

	public boolean setGasType(String type, int tank)
	{
		int gasStorage = this.gasTank1Storage;
		if (tank == 1)
		{
			gasStorage = this.gasTank2Storage;
		}

		String gasType = this.gasTank1Type;
		if (tank == 1)
		{
			gasType = this.gasTank2Type;
		}
		if (gasType == null || gasType.equals(RockSolidContent.gasVacuum.toString()) || gasStorage == 0)
		{
			this.shouldSync = true;
			switch (tank)
			{
			case 0:
				this.gasTank1Type = type;
				return true;
			case 1:
				this.gasTank2Type = type;
				return true;
			}
		}
		return false;
	}

	@Override
	public int getMaxGas()
	{
		return this.maxGasStorage;
	}

	@Override
	// 0 == outputs into conduit, 1 == inputs from conduit, 2 == disabled
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
			if (tankLocation.getX() == 0)
			{
				return 1;
			} else if (tankLocation.getX() == 1)
			{
				return 0;
			}
		}
		return -1;
	}

	@Override
	public boolean removeGas(int amount, int tank)
	{
		if (this.getGasTanksStorage()[tank] >= amount)
		{
			if (tank == 0)
			{
				this.gasTank1Storage -= amount;
			} else if (tank == 1)
			{
				this.gasTank2Storage -= amount;
			}
			this.shouldSync = true;
			return true;

		}
		return false;
	}

}
