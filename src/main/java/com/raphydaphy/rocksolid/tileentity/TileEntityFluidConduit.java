package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.api.fluid.IFluidAcceptor;
import com.raphydaphy.rocksolid.api.fluid.IFluidProducer;
import com.raphydaphy.rocksolid.api.fluid.IFluidTile;
import com.raphydaphy.rocksolid.api.fluid.IMultiFluidAcceptor;
import com.raphydaphy.rocksolid.api.fluid.IMultiFluidProducer;
import com.raphydaphy.rocksolid.api.fluid.IMultiFluidTile;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib.ConduitMode;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib.ConduitSide;
import com.raphydaphy.rocksolid.api.util.TileEntityConduit;

import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityFluidConduit extends TileEntityConduit<TileEntityFluidConduit>
{
	private static final int maxTransfer = 150;

	public TileEntityFluidConduit(final IWorld world, final int x, final int y)
	{
		super(TileEntityFluidConduit.class, 5, world, x, y);
	}
	
	private void tryOutput(String type, int amount, TileEntity inputInv, Pos2 center, ConduitSide side, int inputTank)
	{
		TileEntityFluidConduit outputConduit = world.getTileEntity(center.getX(),
				center.getY(), TileEntityFluidConduit.class);
		Pos2 outputInvPos = RockSolidAPILib.conduitSideToPos(center, side);
		TileEntity outputInvUnchecked = RockSolidAPILib.getTileFromPos(outputInvPos.getX(),
				outputInvPos.getY(), world);

		if (outputInvUnchecked != null && outputConduit != null)
		{
			if (outputInvUnchecked instanceof IFluidAcceptor)
			{
				IFluidAcceptor outputInv = (IFluidAcceptor) outputInvUnchecked;

				if (outputConduit.getSideMode(side) == ConduitMode.OUTPUT)
				{
					String outputType = outputInv.getFluidType();

					if (type.equals(outputType) || outputType.equals(Fluid.EMPTY.getName()))
					{
						if (outputInv.getCurrentFluid() + amount <= outputInv.getMaxFluid())
						{
							boolean didRemove = false;
							
							if (inputInv instanceof IFluidProducer)
							{
								didRemove = ((IFluidProducer)inputInv).removeFluid(amount);
							} else if (inputInv instanceof IMultiFluidProducer)
							{
								didRemove = ((IMultiFluidProducer) inputInv).removeFluid(amount, inputTank);
							}
							
							if (didRemove)
							{
								outputInv.addFluid(amount, type);
							}
						}
					}
				}
			} else if (outputInvUnchecked instanceof IMultiFluidAcceptor)
			{
				IMultiFluidAcceptor outputInv = (IMultiFluidAcceptor) outputInvUnchecked;

				if (outputConduit.getSideMode(side) == ConduitMode.OUTPUT)
				{
					MultiTile theMultiTile = (MultiTile) world
							.getState(outputInvPos.getX(), outputInvPos.getY()).getTile();
					Pos2 innerCoord = theMultiTile
							.getInnerCoord(world.getState(outputInvPos.getX(), outputInvPos.getY()));
					int tank = outputInv.getTankNumber(innerCoord);
					
					if (tank != -1)
					{
						String outputType = outputInv.getFluidTanksType()[tank];
	
						if (type.equals(outputType) || outputType.equals(Fluid.EMPTY.getName()))
						{
							if (outputInv.getFluidTanksStorage()[tank] + amount <= outputInv.getMaxFluid())
							{
								boolean didRemove = false;
								
								if (inputInv instanceof IFluidProducer)
								{
									didRemove = ((IFluidProducer)inputInv).removeFluid(amount);
								} else if (inputInv instanceof IMultiFluidProducer)
								{
									didRemove = ((IMultiFluidProducer) inputInv).removeFluid(amount, inputTank);
								}
								
								if (didRemove)
								{
									outputInv.addFluid(amount, type, tank);
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void tryInput(Pos2 center, ConduitSide side)
	{
		TileEntityFluidConduit inputConduit = world.getTileEntity(center.getX(), center.getY(),
				TileEntityFluidConduit.class);

		Pos2 inputInvPos = RockSolidAPILib.conduitSideToPos(center, side);
		TileEntity inputInvUnchecked = RockSolidAPILib.getTileFromPos(inputInvPos.getX(), inputInvPos.getY(), world);

		if (inputInvUnchecked != null && inputConduit != null)
		{
			if (inputInvUnchecked instanceof IFluidProducer)
			{
				IFluidProducer inputInv = (IFluidProducer) inputInvUnchecked;

				if (inputConduit.getSideMode(side) == ConduitMode.INPUT)
				{
					int wouldExtract = inputInv.getCurrentFluid() >= maxTransfer ? maxTransfer
							: inputInv.getCurrentFluid();

					String extractType = inputInv.getFluidType();

					for (short outputNet = 0; outputNet < super.getNetworkLength(); outputNet++)
					{
						Pos2 outputConduitPos = new Pos2(super.getNetwork()[outputNet][0] + this.getMaster().getX(),
								super.getNetwork()[outputNet][1] + this.getMaster().getY());
						
						ConduitSide outputInvSide = ConduitSide.getByID(super.getNetwork()[outputNet][2]);

						this.tryOutput(extractType, wouldExtract, inputInvUnchecked, outputConduitPos, outputInvSide, -1);
					}
				}
			} else if (inputInvUnchecked instanceof IMultiFluidProducer)
			{
				IMultiFluidProducer inputInv = (IMultiFluidProducer) inputInvUnchecked;

				if (inputConduit.getSideMode(side) == ConduitMode.INPUT)
				{
					MultiTile theMultiTile = (MultiTile) world
							.getState(inputInvPos.getX(), inputInvPos.getY()).getTile();
					Pos2 innerCoord = theMultiTile
							.getInnerCoord(world.getState(inputInvPos.getX(), inputInvPos.getY()));
					int tank = inputInv.getTankNumber(innerCoord);
					
					if (tank != -1)
					{
						int wouldExtract = inputInv.getFluidTanksStorage()[tank] >= maxTransfer ? maxTransfer
								: inputInv.getFluidTanksStorage()[tank];
	
						String extractType = inputInv.getFluidTanksType()[tank];
	
						for (short outputNet = 0; outputNet < super.getNetworkLength(); outputNet++)
						{
							Pos2 outputConduitPos = new Pos2(super.getNetwork()[outputNet][0] + this.getMaster().getX(),
									super.getNetwork()[outputNet][1] + this.getMaster().getY());
							
							ConduitSide outputInvSide = ConduitSide.getByID(super.getNetwork()[outputNet][2]);
	
							this.tryOutput(extractType, wouldExtract, inputInvUnchecked, outputConduitPos, outputInvSide, tank);
						}
					}
				}
			}
		}
	}
	
	@Override
	public boolean canConnectAbstract(Pos2 pos, TileEntity tile)
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

}
