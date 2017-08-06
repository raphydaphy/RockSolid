package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.api.RockSolidAPI;
import com.raphydaphy.rocksolid.api.energy.TileEntityPowered;
import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.api.fluid.IMultiFluidAcceptor;
import com.raphydaphy.rocksolid.api.fluid.IMultiFluidProducer;
import com.raphydaphy.rocksolid.api.recipe.RefineryRecipe;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib.ConduitMode;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityRefinery extends TileEntityPowered implements IMultiFluidAcceptor, IMultiFluidProducer
{
	protected int maxFluid = 5000;
	
	protected int processTime;
	protected int maxProcessTime;
	private int lastSmelt;
	
	protected int inputFluidStored = 0;
	protected String inputFluidType = Fluid.EMPTY.getName();
	
	protected int outputFluidStored = 0;
	protected String outputFluidType = Fluid.EMPTY.getName();

	protected int powerStored = 0;
	private boolean shouldSync = false;

	public TileEntityRefinery(final IWorld world, final int x, final int y)
	{
		super(world, x, y, 5000, 50);
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
		if (this.inputFluidStored > 0)
		{
			final RefineryRecipe recipe = RockSolidAPI.getRefineryRecipe(Fluid.getByName(this.inputFluidType), this.inputFluidStored);
			
			if (recipe != null)
			{
				final Fluid recipeOut = recipe.getOutput();
				final Fluid thisOut = Fluid.getByName(this.outputFluidType);
				if (thisOut == Fluid.EMPTY || recipeOut.equals(thisOut))
				{
					if (this.outputFluidStored + recipe.getOutputVolume() <= this.maxFluid)
					{
						hasRecipeAndSpace = true;
						if (this.getCurrentEnergy() >= super.getPowerPerOperation())
						{
							if (RockBottomAPI.getNet().isClient() == false)
							{
								if (this.maxProcessTime <= 0)
								{
									this.maxProcessTime = recipe.getTime();
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
								this.inputFluidStored -= recipe.getInputVolume();
								if (this.inputFluidStored == 0)
								{
									this.setFluidType(Fluid.EMPTY.getName(), 0);
								}
								if (thisOut == Fluid.EMPTY)
								{
									this.outputFluidType = recipeOut.getName();
								}
								this.outputFluidStored += recipe.getOutputVolume();
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
		}
		if (RockBottomAPI.getNet().isClient() == false)
		{
			this.processTime = 0;
			this.maxProcessTime = 0;
			
			if (this.inputFluidStored == 0)
			{
				this.setFluidType(Fluid.EMPTY.getName(), 0);
			}

			shouldSync = true;
		}
		return hasRecipeAndSpace;
	}

	public boolean isActive()
	{
		if (this.inputFluidStored > 0)
		{
			final RefineryRecipe recipe = RockSolidAPI.getRefineryRecipe(Fluid.getByName(this.inputFluidType), this.inputFluidStored);
			
			if (recipe != null)
			{
				final Fluid recipeOut = recipe.getOutput();
				final Fluid thisOut = Fluid.getByName(this.outputFluidType);
				if (thisOut == Fluid.EMPTY || recipeOut.equals(thisOut))
				{
					if (this.outputFluidStored + recipe.getOutputVolume() <= maxFluid)
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void save(final DataSet set, final boolean forSync)
	{
		super.save(set, forSync);
		set.addInt("process", this.processTime);
		set.addInt("max_process", this.maxProcessTime);
		set.addInt("powerStored", this.powerStored);
		set.addBoolean("shouldSync", this.shouldSync);
		set.addInt(Fluid.MAX_KEY, this.maxFluid);
		set.addInt("inputFluidStored", this.inputFluidStored);
		set.addString("inputFluidType", this.inputFluidType);
		set.addInt("outputFluidStored", this.outputFluidStored);
		set.addString("outputFluidType", this.outputFluidType);
	}

	@Override
	public void load(final DataSet set, final boolean forSync)
	{
		super.load(set, forSync);
		this.processTime = set.getInt("process");
		this.maxProcessTime = set.getInt("max_process");
		this.powerStored = set.getInt("powerStored");
		this.shouldSync = set.getBoolean("shouldSync");
		this.maxFluid = set.getInt(Fluid.MAX_KEY);
		this.inputFluidStored = set.getInt("inputFluidStored");
		this.inputFluidType = set.getString("inputFluidType");
		this.outputFluidStored = set.getInt("outputFluidStored");
		this.outputFluidType = set.getString("outputFluidType");
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

	public float getInputFluidTankFullness()
	{
		if (inputFluidStored == 0)
		{
			return 0;
		}
		return (float) this.inputFluidStored / (float) this.maxFluid;
	}
	
	public float getOutputFluidTankFullness()
	{
		if (outputFluidStored == 0)
		{
			return 0;
		}
		return (float) this.outputFluidStored / (float) this.maxFluid;
	}

	@Override
	protected void onActiveChange(boolean active)
	{
		this.world.causeLightUpdate(this.x, this.y);
	}

	@Override
	public int getMaxFluid()
	{
		return this.maxFluid;
	}

	@Override
	public int[] getFluidTanksStorage()
	{
		return new int[] {this.inputFluidStored, this.outputFluidStored};
	}

	@Override
	public String[] getFluidTanksType()
	{
		return new String[] {this.inputFluidType, this.outputFluidType};
	}

	@Override
	public int getSideMode(int posX, int posY)
	{
		if (posY == 0)
		{
			if (posX == 0)
			{
				return ConduitMode.INPUT.getID();
			}
			else if (posX == 2)
			{
				return ConduitMode.OUTPUT.getID();
			}
		}
		return ConduitMode.DISABLED.getID();
	}

	// input tank = 0, output tank = 1
	@Override
	public int getTankNumber(Pos2 tankLocation)
	{
		if (tankLocation.getY() == 0)
		{
			if (tankLocation.getX() == 0)
			{
				return 0;
			}
			else if (tankLocation.getX() == 2)
			{
				return 1;
			}
		}
		return 0;
	}

	@Override
	public boolean removeFluid(int amount, int tank)
	{
		if (tank == 1)
		{
			if (outputFluidStored >= amount)
			{
				if (!RockBottomAPI.getNet().isClient())
				{
					this.outputFluidStored -= amount;
					if (this.outputFluidStored == 0)
					{
						this.outputFluidType = Fluid.EMPTY.getName();
					}
					this.shouldSync = true;
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean addFluid(int amount, String type, int tank)
	{
		if (tank == 0)
		{
			if (inputFluidStored + amount <= maxFluid && inputFluidType.equals(type) || inputFluidType.equals(Fluid.EMPTY.getName()))
			{
				if (!RockBottomAPI.getNet().isClient())
				{
					if (this.inputFluidType.equals(Fluid.EMPTY.getName()))
					{
						this.inputFluidType = type;
					}
					this.inputFluidStored += amount;
					this.shouldSync = true;
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean setFluidType(String type, int tank)
	{
		if (this.getFluidTanksStorage()[tank] == 0)
		{
			if (!RockBottomAPI.getNet().isClient())
			{
				if (tank == 0)
				{
					this.inputFluidType = type;
				}
				else if (tank == 1)
				{
					this.outputFluidType = type;
				}
				
				this.shouldSync = true;
			}
			return true;
		}
		return false;
	}

	@Override
	public float getSmeltPercentage()
	{
		return (float) this.processTime / (float) this.maxProcessTime;
	}

}
