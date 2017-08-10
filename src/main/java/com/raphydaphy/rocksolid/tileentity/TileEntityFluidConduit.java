package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.api.fluid.IFluidAcceptor;
import com.raphydaphy.rocksolid.api.fluid.IFluidProducer;
import com.raphydaphy.rocksolid.api.fluid.IFluidTile;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib.ConduitMode;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib.ConduitSide;
import com.raphydaphy.rocksolid.api.util.TileEntityConduit;

import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityFluidConduit extends TileEntityConduit<TileEntityFluidConduit>
{
	private static final int maxTransfer = 150;

	public TileEntityFluidConduit(final IWorld world, final int x, final int y)
	{
		super(TileEntityFluidConduit.class, world, x, y);
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
						TileEntityFluidConduit outputConduit = world.getTileEntity(outputConduitPos.getX(),
								outputConduitPos.getY(), TileEntityFluidConduit.class);

						ConduitSide outputInvSide = ConduitSide.getByID(super.getNetwork()[outputNet][2]);

						Pos2 outputInvPos = RockSolidAPILib.conduitSideToPos(outputConduitPos, outputInvSide);
						TileEntity outputInvUnchecked = RockSolidAPILib.getTileFromPos(outputInvPos.getX(),
								outputInvPos.getY(), world);

						if (outputInvUnchecked != null && outputConduit != null)
						{
							if (outputInvUnchecked instanceof IFluidAcceptor)
							{
								IFluidAcceptor outputInv = (IFluidAcceptor) outputInvUnchecked;

								if (outputConduit.getSideMode(outputInvSide) == ConduitMode.OUTPUT)
								{
									int maxOutput = outputInv.getCurrentFluid() + maxTransfer <= outputInv.getMaxFluid()
											? maxTransfer
											: outputInv.getCurrentFluid() - outputInv.getCurrentFluid();
									String outputType = outputInv.getFluidType();

									if (extractType.equals(outputType))
									{
										// send the maximum extraction amount as
										// it is smaller
										if (wouldExtract >= maxOutput)
										{
											inputInv.removeFluid(wouldExtract);
											outputInv.addFluid(wouldExtract, extractType);
										}
										// send the max inpupt amount as it is
										// smaller
										else
										{
											inputInv.removeFluid(maxOutput);
											outputInv.addFluid(maxOutput, extractType);
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
		return tile instanceof IFluidTile;
	}

}
