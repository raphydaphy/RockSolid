package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.api.gas.Gas;
import com.raphydaphy.rocksolid.api.gas.IGasAcceptor;
import com.raphydaphy.rocksolid.api.gas.IGasProducer;
import com.raphydaphy.rocksolid.api.gas.IGasTile;
import com.raphydaphy.rocksolid.api.gas.IMultiGasAcceptor;
import com.raphydaphy.rocksolid.api.gas.IMultiGasProducer;
import com.raphydaphy.rocksolid.api.gas.IMultiGasTile;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib.ConduitMode;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib.ConduitSide;
import com.raphydaphy.rocksolid.api.util.TileEntityConduit;

import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityGasConduit extends TileEntityConduit<TileEntityGasConduit>
{
	private static final int maxTransfer = 150;

	public TileEntityGasConduit(final IWorld world, final int x, final int y)
	{
		super(TileEntityGasConduit.class, 5, world, x, y);
	}
	
	private void tryOutput(String type, int amount, TileEntity inputInv, Pos2 center, ConduitSide side, int inputTank)
	{
		TileEntityGasConduit outputConduit = world.getTileEntity(center.getX(),
				center.getY(), TileEntityGasConduit.class);

		

		Pos2 outputInvPos = RockSolidAPILib.conduitSideToPos(center, side);
		TileEntity outputInvUnchecked = RockSolidAPILib.getTileFromPos(outputInvPos.getX(),
				outputInvPos.getY(), world);

		if (outputInvUnchecked != null && outputConduit != null)
		{
			if (outputInvUnchecked instanceof IGasAcceptor)
			{
				IGasAcceptor outputInv = (IGasAcceptor) outputInvUnchecked;

				if (outputConduit.getSideMode(side) == ConduitMode.OUTPUT)
				{
					String outputType = outputInv.getGasType();
					
					if (type.equals(outputType) || outputType.equals(Gas.VACCUM.getName()))
					{
						if (outputInv.getCurrentGas() + amount <= outputInv.getMaxGas())
						{
							boolean didRemove = false;
							if (inputInv instanceof IGasProducer)
							{
								didRemove = ((IGasProducer) inputInv).removeGas(amount);
							} else if (inputInv instanceof IMultiGasProducer)
							{
								didRemove = ((IMultiGasProducer) inputInv).removeGas(amount, inputTank);
							}
							if (didRemove)
							{
								outputInv.addGas(amount, type);
								
								if (outputInv.getGasType().equals(Gas.VACCUM.getName()))
								{
									outputInv.setGasType(type);
								}
							}
						}
					}
				}
			} else if (outputInvUnchecked instanceof IMultiGasAcceptor)
			{
				IMultiGasAcceptor outputInv = (IMultiGasAcceptor) outputInvUnchecked;

				if (outputConduit.getSideMode(side) == ConduitMode.OUTPUT)
				{
					MultiTile theMultiTile = (MultiTile) world
							.getState(outputInvPos.getX(), outputInvPos.getY()).getTile();
					Pos2 innerCoord = theMultiTile
							.getInnerCoord(world.getState(outputInvPos.getX(), outputInvPos.getY()));
					int tank = outputInv.getTankNumber(innerCoord);
					
					if (tank != -1)
					{
						String outputType = outputInv.getGasTanksType()[tank];
						
						if (type.equals(outputType) || outputType.equals(Gas.VACCUM.getName()))
						{
							if (outputInv.getGasTanksStorage()[tank] + amount <= outputInv.getMaxGas())
							{
								
								
								
								boolean didRemove = false;
								if (inputInv instanceof IGasProducer)
								{
									didRemove = ((IGasProducer) inputInv).removeGas(amount);
								} else if (inputInv instanceof IMultiGasProducer)
								{
									didRemove = ((IMultiGasProducer) inputInv).removeGas(amount, inputTank);
								}
								if (didRemove)
								{
									outputInv.addGas(amount, type, tank);
									
									if (outputInv.getGasTanksType()[tank].equals(Gas.VACCUM.getName()))
									{
										outputInv.setGasType(type, tank);
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
	public void tryInput(Pos2 center, ConduitSide side)
	{
		TileEntityGasConduit inputConduit = world.getTileEntity(center.getX(), center.getY(),
				TileEntityGasConduit.class);

		Pos2 inputInvPos = RockSolidAPILib.conduitSideToPos(center, side);
		TileEntity inputInvUnchecked = RockSolidAPILib.getTileFromPos(inputInvPos.getX(), inputInvPos.getY(), world);

		if (inputInvUnchecked != null && inputConduit != null)
		{
			if (inputInvUnchecked instanceof IGasProducer)
			{
				IGasProducer inputInv = (IGasProducer) inputInvUnchecked;

				if (inputConduit.getSideMode(side) == ConduitMode.INPUT)
				{
					int wouldExtract = inputInv.getCurrentGas() >= maxTransfer ? maxTransfer
							: inputInv.getCurrentGas();
					
					String extractType = inputInv.getGasType();

					if (!extractType.equals(Gas.VACCUM.getName()))
					{
						for (short outputNet = 0; outputNet < super.getNetworkLength(); outputNet++)
						{
							Pos2 outputConduitPos = new Pos2(super.getNetwork()[outputNet][0] + this.getMaster().getX(),
									super.getNetwork()[outputNet][1] + this.getMaster().getY());
							ConduitSide outputInvSide = ConduitSide.getByID(super.getNetwork()[outputNet][2]);
							
							this.tryOutput(extractType, wouldExtract, inputInvUnchecked, outputConduitPos, outputInvSide, -1);
						}
					}
				}
			} else if (inputInvUnchecked instanceof IMultiGasProducer)
			{
				IMultiGasProducer inputInv = (IMultiGasProducer) inputInvUnchecked;

				if (inputConduit.getSideMode(side) == ConduitMode.INPUT)
				{
					MultiTile theMultiTile = (MultiTile) world
							.getState(inputInvPos.getX(), inputInvPos.getY()).getTile();
					Pos2 innerCoord = theMultiTile
							.getInnerCoord(world.getState(inputInvPos.getX(), inputInvPos.getY()));
					int tank = inputInv.getTankNumber(innerCoord);
					
					if (tank != -1)
					{
						int wouldExtract = inputInv.getGasTanksStorage()[tank] >= maxTransfer ? maxTransfer
								: inputInv.getGasTanksStorage()[tank];
						
						String extractType = inputInv.getGasTanksType()[tank];
	
						if (!extractType.equals(Gas.VACCUM.getName()))
						{
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
	}

	@Override
	public boolean canConnectAbstract(Pos2 pos, TileEntity tile)
	{
		if (tile instanceof IGasTile)
		{
			return true;
		} else if (tile instanceof IMultiGasTile)
		{
			Pos2 innerCoord = ((MultiTile) world.getState(pos.getX(), pos.getY()).getTile())
					.getInnerCoord(world.getState(pos.getX(), pos.getY()));
			if (((IMultiGasTile) tile).getSideMode(innerCoord.getX(), innerCoord.getY()) != 2)
			{
				return true;
			}
		}
		return false;
	}
}
