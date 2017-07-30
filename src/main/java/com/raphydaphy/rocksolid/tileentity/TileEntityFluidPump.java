package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.api.energy.TileEntityPowered;
import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.api.fluid.IFluidProducer;
import com.raphydaphy.rocksolid.init.ModFluids;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityFluidPump extends TileEntityPowered implements IFluidProducer
{

	private int curX;
	private int curY;
	private int mineTick;

	protected int powerStored = 0;
	private boolean shouldSync = false;

	protected int fluidStored = 0;
	protected int maxFluid = 10000;
	protected String fluidType = ModFluids.fluidEmpty.toString();

	public TileEntityFluidPump(final IWorld world, final int x, final int y)
	{
		super(world, x, y, 10000, 40);

		this.curX = x - 9;
		this.curY = y;
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

	public boolean isActive()
	{
		return this.powerStored >= super.getPowerPerOperation();
	}

	@Override
	public boolean tryTickAction()
	{
		if (curY < (this.y - 100))
		{
			curY = y;
		}
		boolean ableToDig = false;

		if (this.fluidStored == 0 && !(this.fluidType.equals(ModFluids.fluidEmpty.toString())))
		{
			this.fluidType = ModFluids.fluidEmpty.toString();
		}

		if (curX > this.x + 11)
		{
			curX = this.x - 9;
			curY--;
		}

		TileState thisTile = world.getState(curX, curY);
		while (!(thisTile.getTile() instanceof Fluid))
		{
			curX++;
			if (curX > this.x + 11)
			{
				curX = this.x - 9;
				curY--;
			}

			if (curY < (this.y - 100))
			{
				return false;
			}

			thisTile = world.getState(curX, curY);
		}

		String tileType = ModFluids.fluidEmpty.toString();
		if (thisTile.getTile().equals(ModFluids.fluidWater))
		{
			tileType = ModFluids.fluidWater.toString();
		} else if (thisTile.getTile().equals(ModFluids.fluidLava))
		{
			tileType = ModFluids.fluidLava.toString();
		} else
		{
			return false;
		}
		if ((this.fluidType.equals(tileType) || this.fluidType.equals(ModFluids.fluidEmpty.toString()))
				&& world.isPosLoaded(curX, curY) && this.getCurrentFluid() <= (this.getMaxFluid() - 1000))
		{
			if (this.getCurrentEnergy() >= super.getPowerPerOperation())
			{
				if (world.getState(curX, curY).getTile() instanceof Fluid)
				{
					ableToDig = true;
					if (mineTick == 10 && RockBottomAPI.getNet().isClient() == false)
					{

						if (thisTile.get(Fluid.fluidLevel) >= Fluid.BUCKET_VOLUME)
						{
							if (thisTile.get(Fluid.fluidLevel) - Fluid.BUCKET_VOLUME >= 1)
							{
								world.setState(curX, curY, thisTile.prop(Fluid.fluidLevel,
										thisTile.get(Fluid.fluidLevel) - Fluid.BUCKET_VOLUME));
							} else
							{
								world.setState(curX, curY, GameContent.TILE_AIR.getDefState());
								curX++;

							}

							if (this.fluidType.equals(ModFluids.fluidEmpty.toString()))
							{
								this.fluidType = tileType;

							}
							this.fluidStored += 1000;
							this.mineTick = 0;
						} else
						{
							curX++;
						}
					} else if (RockBottomAPI.getNet().isClient() == false)
					{
						mineTick++;
					}
					shouldSync = true;

				}

				return ableToDig;
			}
		} else if (!this.fluidType.equals(tileType))
		{
			curX++;
			if (curX > this.x + 11)
			{
				curX = this.x - 9;
				curY--;
			}

			if (curY < (this.y - 100))
			{
				return false;
			}
		}
		return ableToDig;
	}

	@Override
	public void save(final DataSet set, final boolean forSync)
	{
		super.save(set, forSync);
		set.addInt("powerStored", this.powerStored);
		set.addInt("curX", this.curX);
		set.addInt("curY", this.curY);
		set.addInt("mineTick", this.mineTick);
		set.addBoolean("shouldSync", this.shouldSync);
		set.addInt("fluidStored", this.fluidStored);
		set.addInt("maxFluid", this.maxFluid);
		set.addString("fluidType", this.fluidType);
	}

	@Override
	public void load(final DataSet set, final boolean forSync)
	{
		super.load(set, forSync);
		this.powerStored = set.getInt("powerStored");
		this.curX = set.getInt("curX");
		this.curY = set.getInt("curY");
		this.mineTick = set.getInt("mineTick");
		this.shouldSync = set.getBoolean("shouldSync");
		this.fluidStored = set.getInt("fluidStored");
		this.maxFluid = set.getInt("maxFluid");
		this.fluidType = set.getString("fluidType");
	}

	@Override
	protected void setPower(int power)
	{
		this.powerStored = power;
	}

	@Override
	protected int getPower()
	{
		return this.powerStored;
	}

	@Override
	protected void onActiveChange(boolean active)
	{
		this.world.causeLightUpdate(this.x, this.y);
	}

	@Override
	public float getSmeltPercentage()
	{
		if (mineTick == 0)
		{
			return 0;
		} else
		{
			return mineTick / 10;
		}
	}

	public float getFluidTankFullness()
	{
		if (this.fluidStored == 0)
		{
			return 0;
		}
		return (float) this.fluidStored / (float) this.maxFluid;
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
				this.fluidType = ModFluids.fluidEmpty.toString();
			}
			this.shouldSync = true;
			return true;
		}
		return false;
	}

}
