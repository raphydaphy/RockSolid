package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.api.fluid.IFluidAcceptor;
import com.raphydaphy.rocksolid.api.fluid.IFluidProducer;
import com.raphydaphy.rocksolid.api.fluid.IFluidTile;
import com.raphydaphy.rocksolid.api.fluid.IMultiFluidAcceptor;
import com.raphydaphy.rocksolid.api.fluid.IMultiFluidProducer;
import com.raphydaphy.rocksolid.api.fluid.IMultiFluidTile;
import com.raphydaphy.rocksolid.api.util.IConduit;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib.ConduitMode;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib.ConduitSide;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityFluidConduit extends TileEntity implements IConduit, IFluidProducer, IFluidAcceptor
{

	private int modeUp = 0;
	private int modeDown = 0;
	private int modeLeft = 0;
	private int modeRight = 0;

	private int fluidStored = 0;
	private int maxFluid = 1000;
	private String fluidType;

	private int transferRate = 250;

	private boolean shouldSync = false;

	public TileEntityFluidConduit(final IWorld world, final int x, final int y)
	{
		super(world, x, y);

		if (this.fluidType == null)
		{
			this.fluidType = Fluid.EMPTY.getName();
		}
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
		this.shouldSync = false;
	}

	@Override
	public void update(IGameInstance game)
	{
		super.update(game);
		if (RockBottomAPI.getNet().isClient() == false)
		{
			// first we extract stuff from nearby inventories into the pipes
			// inventory
			for (ConduitSide side : ConduitSide.values())
			{
				Pos2 adjacentTilePos = RockSolidAPILib.conduitSideToPos(new Pos2(x, y), side);
				TileEntity adjacentTileEntity = RockSolidAPILib.getTileFromPos(adjacentTilePos.getX(),
						adjacentTilePos.getY(), world);

				if (adjacentTileEntity != null)
				{
					if (adjacentTileEntity instanceof TileEntityFluidConduit)
					{
						if (this.getSideMode(side) != ConduitMode.DISABLED
								&& (((TileEntityFluidConduit) adjacentTileEntity).getFluidType().equals(this.fluidType))
								|| this.fluidType.equals(Fluid.EMPTY.getName()))
						{
							if (((TileEntityFluidConduit) adjacentTileEntity).getCurrentFluid() > this
									.getCurrentFluid())
							{
								String wasFluidType = ((TileEntityFluidConduit) adjacentTileEntity).getFluidType();
								// pull fluid from adjacent conduit
								if (((TileEntityFluidConduit) adjacentTileEntity).removeFluid(transferRate))
								{
									this.addFluid(transferRate, wasFluidType);
									shouldSync = true;
								}
							}
						}
					} else if (IFluidTile.class.isAssignableFrom(adjacentTileEntity.getClass()))
					{
						if (((IFluidTile) adjacentTileEntity).getFluidType().equals(this.fluidType)
								|| this.fluidType.equals(Fluid.EMPTY.getName())
								|| ((IFluidTile) adjacentTileEntity).getFluidType()
										.equals(Fluid.EMPTY.getName()))
						{
							if (IFluidProducer.class.isAssignableFrom(adjacentTileEntity.getClass())
									&& (((IFluidTile) adjacentTileEntity).getFluidType().equals(this.fluidType))
									|| this.fluidType.equals(Fluid.EMPTY.getName()))
							{
								// Conduit is set to input mode
								if (this.getSideMode(side) == ConduitMode.INPUT)
								{

									if (this.fluidStored < (this.maxFluid - transferRate)
											&& ((IFluidProducer) adjacentTileEntity).getCurrentFluid() >= transferRate)
									{
										String wasFluidType = ((IFluidProducer) adjacentTileEntity).getFluidType();
										// pull fluid from adjacent tile
										if (((IFluidProducer) adjacentTileEntity).removeFluid(transferRate))
										{
											this.addFluid(transferRate, wasFluidType);
											this.shouldSync = true;
										}
									}
								}
							}

							if (IFluidAcceptor.class.isAssignableFrom(adjacentTileEntity.getClass()))
							{
								// Conduit is set to output mode
								if (this.getSideMode(side) == ConduitMode.OUTPUT)
								{

									if (this.fluidStored >= transferRate)
									{
										if (((IFluidTile) adjacentTileEntity).getFluidType()
												.equals(Fluid.EMPTY.getName()))
										{
											// set the fluid type in the
											// adjacent tile to match this
											((IFluidAcceptor) adjacentTileEntity).setFluidType(this.fluidType);
										}
										// send fluid to adjacent tile
										if (((IFluidAcceptor) adjacentTileEntity).addFluid(transferRate,
												this.fluidType))
										{
											this.removeFluid(transferRate);
											shouldSync = true;
										}
									}
								}
							}
						}
					} else if (IMultiFluidTile.class.isAssignableFrom(adjacentTileEntity.getClass()))
					{
						IMultiFluidTile adjMultiFluid = (IMultiFluidTile) adjacentTileEntity;
						MultiTile theMultiTile = (MultiTile) world
								.getState(adjacentTilePos.getX(), adjacentTilePos.getY()).getTile();
						Pos2 innerCoord = theMultiTile
								.getInnerCoord(world.getState(adjacentTilePos.getX(), adjacentTilePos.getY()));
						int thisFluidTank = adjMultiFluid.getTankNumber(innerCoord);
						if (thisFluidTank != -1)
						{
							if (((IMultiFluidTile) adjacentTileEntity).getFluidTanksType()[thisFluidTank].equals(
									this.fluidType) || this.fluidType.equals(Fluid.EMPTY.getName())
									|| ((IMultiFluidTile) adjacentTileEntity).getFluidTanksType()[thisFluidTank]
											.equals(Fluid.EMPTY.getName()))
							{
								if (IMultiFluidProducer.class.isAssignableFrom(adjacentTileEntity.getClass()))
								{
									if ((((IMultiFluidTile) adjacentTileEntity).getFluidTanksType()[thisFluidTank]
											.equals(this.fluidType))
											|| this.fluidType.equals(Fluid.EMPTY.getName()))
									{
										// Conduit is set to input mode
										if (this.getSideMode(side) == ConduitMode.INPUT)
										{

											if (this.fluidStored < (this.maxFluid - transferRate)
													&& ((IMultiFluidProducer) adjacentTileEntity)
															.getFluidTanksStorage()[thisFluidTank] >= transferRate)
											{
												String wasFluidType = ((IMultiFluidProducer) adjacentTileEntity)
														.getFluidTanksType()[thisFluidTank];
												// pull fluid from adjacent tile
												if (((IMultiFluidProducer) adjacentTileEntity).removeFluid(transferRate,
														thisFluidTank))
												{
													this.addFluid(transferRate, wasFluidType);
													this.shouldSync = true;
												}
											}
										}
									}
								}

								if (IMultiFluidAcceptor.class.isAssignableFrom(adjacentTileEntity.getClass()))
								{
									// Conduit is set to output mode
									if (this.getSideMode(side) == ConduitMode.OUTPUT)
									{

										if (this.fluidStored >= transferRate)
										{
											if (((IMultiFluidTile) adjacentTileEntity)
													.getFluidTanksType()[thisFluidTank]
															.equals(Fluid.EMPTY.getName()))
											{
												// set the fluid type in the
												// adjacent tile to match this
												((IMultiFluidAcceptor) adjacentTileEntity).setFluidType(this.fluidType,
														thisFluidTank);
											}
											// send fluid to adjacent tile
											if (((IMultiFluidAcceptor) adjacentTileEntity).addFluid(transferRate,
													this.fluidType, thisFluidTank))
											{
												this.removeFluid(transferRate);
												shouldSync = true;
											}
										}
									}
								}
							}
						}
					}

				}
			}
		}
	}

	@Override
	public void setSideMode(ConduitSide side, ConduitMode mode)
	{
		shouldSync = true;
		switch (side.getID())
		{
		case 0:
			// up
			modeUp = mode.getID();
			break;
		case 1:
			// down
			modeDown = mode.getID();
			break;
		case 2:
			// left
			modeLeft = mode.getID();
			break;
		case 3:
			// right
			modeRight = mode.getID();
			break;
		}
	}

	@Override
	public ConduitMode getSideMode(ConduitSide side)
	{
		switch (side.getID())
		{
		case 0:
			return ConduitMode.getByID(modeUp);
		case 1:
			return ConduitMode.getByID(modeDown);
		case 2:
			return ConduitMode.getByID(modeLeft);
		case 3:
			return ConduitMode.getByID(modeRight);
		}
		return ConduitMode.DISABLED;
	}

	@Override
	public void save(final DataSet set, final boolean forSync)
	{
		super.save(set, forSync);
		set.addInt("modeUp", this.modeUp);
		set.addInt("modeDown", this.modeDown);
		set.addInt("modeLeft", this.modeLeft);
		set.addInt("modeRight", this.modeRight);
		set.addInt(Fluid.KEY, this.fluidStored);
		set.addInt(Fluid.MAX_KEY, this.maxFluid);
		set.addInt("transferRate", this.transferRate);
		set.addBoolean("shouldSync", this.shouldSync);
		set.addString(Fluid.TYPE_KEY, this.fluidType);
	}

	@Override
	public void load(final DataSet set, final boolean forSync)
	{
		super.load(set, forSync);
		this.modeUp = set.getInt("modeUp");
		this.modeDown = set.getInt("modeDown");
		this.modeLeft = set.getInt("modeLeft");
		this.modeRight = set.getInt("modeRight");
		this.fluidStored = set.getInt(Fluid.KEY);
		this.maxFluid = set.getInt(Fluid.MAX_KEY);
		this.transferRate = set.getInt("transferRate");
		this.shouldSync = set.getBoolean("shouldSync");
		this.fluidType = set.getString(Fluid.TYPE_KEY);
	}

	@Override
	public boolean canConnectTo(Pos2 pos, TileEntity tile)
	{
		if (tile instanceof IFluidTile)
		{
			return true;
		} else if (tile instanceof IMultiFluidTile)
		{
			Pos2 innerCoord = ((MultiTile) world.getState(pos.getX(), pos.getY()).getTile())
					.getInnerCoord(world.getState(pos.getX(), pos.getY()));
			if (((IMultiFluidTile) tile).getSideMode(innerCoord.getX(), innerCoord.getY()) != 2)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public void setSync()
	{
		if (RockBottomAPI.getNet().isClient() == false)
		{
			shouldSync = true;
		}

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
	public boolean addFluid(int amount, String type)
	{
		if (this.fluidType == null || type.equals(this.fluidType)
				|| this.fluidType.equals(Fluid.EMPTY.getName()))
		{
			if (this.fluidStored + amount <= this.maxFluid)
			{
				if (this.fluidType == null || this.fluidType.equals(Fluid.EMPTY.getName()))
				{
					this.fluidType = type;
				}
				this.fluidStored += amount;
				this.shouldSync = true;
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean removeFluid(int amount)
	{
		if (this.fluidStored >= amount)
		{
			this.fluidStored -= amount;

			if (this.fluidStored == 0)
			{
				this.fluidType = Fluid.EMPTY.getName();
			}
			this.shouldSync = true;
			return true;
		}
		return false;
	}

	@Override
	public String getFluidType()
	{
		return this.fluidType;
	}

	@Override
	public boolean setFluidType(String type)
	{
		if (this.fluidType.equals(Fluid.EMPTY.getName()) || this.fluidStored == 0)
		{
			this.fluidType = type;
			this.shouldSync = true;
			return true;
		}
		return false;
	}

}
