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

	public int getHighestOutputPriority(int sendingAmount)
	{
		if (this.isMaster())
		{
			int highest = 0;
			for (int net = 0; net < super.getNetworkLength(); net++)
			{
				Pos2 conduitPos = new Pos2(super.getNetwork()[net][0] + this.getMaster().getX(),
						super.getNetwork()[net][1] + this.getMaster().getY());

				ConduitSide tileSide = ConduitSide.getByID(super.getNetwork()[net][2]);

				TileEntityEnergyConduit conduit = world.getTileEntity(conduitPos.getX(), conduitPos.getY(),
						TileEntityEnergyConduit.class);

				ConduitMode mode = conduit.getSideMode(tileSide);

				if (mode == ConduitMode.OUTPUT)
				{
					TileEntity energyAcceptor = RockSolidAPILib.getTileFromConduitSide(conduitPos, tileSide, world);

					if (energyAcceptor instanceof IEnergyAcceptor)
					{
						if (((IEnergyAcceptor) energyAcceptor).getCurrentEnergy()
								+ sendingAmount <= ((IEnergyAcceptor) energyAcceptor).getMaxEnergy())
						{
							int priority = conduit.getPriority(tileSide);
							if (priority > highest)
							{
								highest = priority;
							}
						}
					}
				}

			}
		} else
		{
			TileEntityEnergyConduit masterConduit = world.getTileEntity(this.getMaster().getX(),
					this.getMaster().getY(), TileEntityEnergyConduit.class);

			if (masterConduit != null)
			{
				return masterConduit.getHighestOutputPriority(sendingAmount);
			}
		}
		return 0;
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
					int highestPriority = this.getHighestOutputPriority(wouldExtract);
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
							if (outputInvUnchecked instanceof IEnergyAcceptor)
							{
								IEnergyAcceptor outputInv = (IEnergyAcceptor) outputInvUnchecked;
								
								if (outputConduit.getPriority(outputInvSide) >= highestPriority)
								{
									if (outputConduit.getSideMode(outputInvSide) == ConduitMode.OUTPUT)
									{
										if (outputInv.getCurrentEnergy() + wouldExtract <= outputInv.getMaxEnergy())
										{
											if (inputInv.removeEnergy(wouldExtract))
											{
												outputInv.addEnergy(wouldExtract);
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
	public boolean canConnectAbstract(Pos2 pos, TileEntity tile)
	{
		return tile instanceof IEnergyTile;
	}

}
