package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.api.gas.IGasAcceptor;
import com.raphydaphy.rocksolid.api.gas.IGasProducer;
import com.raphydaphy.rocksolid.api.gas.IGasTile;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib.ConduitMode;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib.ConduitSide;
import com.raphydaphy.rocksolid.api.util.TileEntityConduit;

import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityGasConduit extends TileEntityConduit<TileEntityGasConduit>
{
	private static final int maxTransfer = 150;

	public TileEntityGasConduit(final IWorld world, final int x, final int y)
	{
		super(TileEntityGasConduit.class,world, x, y);
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

					for (short outputNet = 0; outputNet < super.getNetworkLength(); outputNet++)
					{
						Pos2 outputConduitPos = new Pos2(super.getNetwork()[outputNet][0] + this.getMaster().getX(),
								super.getNetwork()[outputNet][1] + this.getMaster().getY());
						TileEntityGasConduit outputConduit = world.getTileEntity(outputConduitPos.getX(),
								outputConduitPos.getY(), TileEntityGasConduit.class);

						ConduitSide outputInvSide = ConduitSide.getByID(super.getNetwork()[outputNet][2]);

						Pos2 outputInvPos = RockSolidAPILib.conduitSideToPos(outputConduitPos, outputInvSide);
						TileEntity outputInvUnchecked = RockSolidAPILib.getTileFromPos(outputInvPos.getX(),
								outputInvPos.getY(), world);

						if (outputInvUnchecked != null && outputConduit != null)
						{
							if (outputInvUnchecked instanceof IGasAcceptor)
							{
								IGasAcceptor outputInv = (IGasAcceptor) outputInvUnchecked;

								if (outputConduit.getSideMode(outputInvSide) == ConduitMode.OUTPUT)
								{
									int maxOutput = outputInv.getCurrentGas() + maxTransfer <= outputInv
											.getMaxGas() ? maxTransfer
													: outputInv.getCurrentGas() - outputInv.getCurrentGas();
									String outputType = outputInv.getGasType();
									
									if (extractType.equals(outputType))
									{
										// send the maximum extraction amount as it is smaller
										if (wouldExtract >= maxOutput)
										{
											inputInv.removeGas(wouldExtract);
											outputInv.addGas(wouldExtract, extractType);
										}
										// send the max inpupt amount as it is smaller
										else
										{
											inputInv.removeGas(maxOutput);
											outputInv.addGas(maxOutput, extractType);
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
	public boolean canConnectAbstract(TileEntity tile)
	{
		return tile instanceof IGasTile;
	}
}
