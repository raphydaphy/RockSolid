package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.api.energy.IEnergyAcceptor;
import com.raphydaphy.rocksolid.api.energy.IEnergyProducer;
import com.raphydaphy.rocksolid.api.energy.IEnergyTile;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib.ConduitMode;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib.ConduitSide;
import com.raphydaphy.rocksolid.api.util.TileEntityConduit;

import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityEnergyConduit extends TileEntityConduit<TileEntityEnergyConduit>
{
	private static final int maxTransfer = 300;

	public TileEntityEnergyConduit(final IWorld world, final int x, final int y)
	{
		super(TileEntityEnergyConduit.class, 1, world, x, y);
	}

	@Override
	public void tryInput(Pos2 center, ConduitSide side)
	{
		TileEntityEnergyConduit inputConduit = world.getTileEntity(center.getX(), center.getY(),
				TileEntityEnergyConduit.class);

		Pos2 inputInvPos = RockSolidAPILib.conduitSideToPos(center, side);
		TileEntity inputInvUnchecked = RockSolidAPILib.getTileFromPos(inputInvPos.getX(), inputInvPos.getY(), world);
		if (inputInvUnchecked != null && inputConduit != null)
		{
			if (inputInvUnchecked instanceof IEnergyProducer)
			{
				IEnergyProducer inputInv = (IEnergyProducer) inputInvUnchecked;

				if (inputConduit.getSideMode(side) == ConduitMode.INPUT)
				{
					
					int wouldExtract = inputInv.getCurrentEnergy() >= maxTransfer ? maxTransfer
							: inputInv.getCurrentEnergy();

					System.out.println("Found a power input at " + inputInvPos.getX() + ", " + inputInvPos.getY() + " able to supply " + wouldExtract +" energy this tick.");
					for (short outputNet = 0; outputNet < super.getNetworkLength(); outputNet++)
					{
						Pos2 outputConduitPos = new Pos2(super.getNetwork()[outputNet][0] + this.getMaster().getX(),
								super.getNetwork()[outputNet][1] + this.getMaster().getY());
						TileEntityEnergyConduit outputConduit = world.getTileEntity(outputConduitPos.getX(),
								outputConduitPos.getY(), TileEntityEnergyConduit.class);

						ConduitSide outputInvSide = ConduitSide.getByID(super.getNetwork()[outputNet][2]);

						Pos2 outputInvPos = RockSolidAPILib.conduitSideToPos(outputConduitPos, outputInvSide);
						TileEntity outputInvUnchecked = RockSolidAPILib.getTileFromPos(outputInvPos.getX(),
								outputInvPos.getY(), world);

						if (outputInvUnchecked != null && outputConduit != null)
						{
							System.out.println("There might be somewhere to send powah! Found at " + outputInvUnchecked.x + ", " + outputInvUnchecked.y);
							if (outputInvUnchecked instanceof IEnergyAcceptor)
							{
								IEnergyAcceptor outputInv = (IEnergyAcceptor) outputInvUnchecked;
								System.out.println("Found an acceptor!");
								if (outputConduit.getSideMode(outputInvSide) == ConduitMode.OUTPUT)
								{
									System.out.println("ITS THE CORRECT MODE");
									int maxOutput = outputInv.getCurrentEnergy() + maxTransfer <= outputInv
											.getMaxEnergy() ? maxTransfer
													: outputInv.getMaxEnergy() - outputInv.getCurrentEnergy();
									System.out.println("Sending " + maxOutput + " or " + wouldExtract + " power!");
									// send the maximum extraction amount as it
									// is smaller
									if (wouldExtract >= maxOutput)
									{
										if (outputInv.getCurrentEnergy() + wouldExtract <= outputInv.getMaxEnergy())
										{
											if (inputInv.removeEnergy(wouldExtract))
											{
												outputInv.addEnergy(wouldExtract);
											}
										}
									}
									// send the max input amount as it is
									// smaller
									else
									{
										if (outputInv.getCurrentEnergy() + maxOutput <= outputInv.getMaxEnergy())
										{
											if (inputInv.removeEnergy(maxOutput))
											{
												outputInv.addEnergy(maxOutput);
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
	public boolean canConnectAbstract( TileEntity tile)
	{
		return tile instanceof IEnergyTile;
	}

}
