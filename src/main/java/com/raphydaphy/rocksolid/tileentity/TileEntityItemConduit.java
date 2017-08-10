package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib.ConduitMode;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib.ConduitSide;
import com.raphydaphy.rocksolid.api.util.TileEntityConduit;
import com.raphydaphy.rocksolid.gui.inventory.ContainerInventory;

import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.IInventoryHolder;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Direction;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityItemConduit extends TileEntityConduit<TileEntityItemConduit>
{
	public final ContainerInventory inventory;

	private boolean[] whitelistModes = new boolean[] { true, true, true, true };

	public TileEntityItemConduit(final IWorld world, final int x, final int y)
	{
		super(TileEntityItemConduit.class, 10, world, x, y);
		this.inventory = new ContainerInventory(this, 4);
	}

	public Inventory getInventory()
	{
		return this.inventory;
	}

	@Override
	public void tryInput(Pos2 center, ConduitSide side)
	{
		TileEntityItemConduit inputConduit = world.getTileEntity(center.getX(),
				center.getY(), TileEntityItemConduit.class);
		
		Pos2 inputInvPos = RockSolidAPILib.conduitSideToPos(center, side);
		TileEntity inputInvUnchecked = RockSolidAPILib.getTileFromPos(inputInvPos.getX(), inputInvPos.getY(),
				world);
		
		if (inputInvUnchecked != null && inputConduit != null)
		{
			if (inputInvUnchecked instanceof IInventoryHolder)
			{
				IInventoryHolder inputInv = (IInventoryHolder) inputInvUnchecked;

				if (inputConduit.getSideMode(side) == ConduitMode.INPUT)
				{
					ItemInstance wouldInput = RockSolidAPILib.getToExtract(inputInv, 1,
							inputConduit.getInventory().get(side.getID()),
							inputConduit.getIsWhitelist(side));

					int highestOutput = this.getHighestOutputPriority(inputInv);

					for (short outputNet = 0; outputNet < super.getNetworkLength(); outputNet++)
					{
						Pos2 outputConduitPos = new Pos2(super.getNetwork()[outputNet][0] + this.getMaster().getX(),
								super.getNetwork()[outputNet][1] + this.getMaster().getY());
						TileEntityItemConduit outputConduit = world.getTileEntity(outputConduitPos.getX(),
								outputConduitPos.getY(), TileEntityItemConduit.class);

						ConduitSide outputInvSide = ConduitSide.getByID(super.getNetwork()[outputNet][2]);

						if (outputConduit.getPriority(outputInvSide) >= highestOutput)
						{
							Pos2 outputInvPos = RockSolidAPILib.conduitSideToPos(outputConduitPos, outputInvSide);
							TileEntity outputInvUnchecked = RockSolidAPILib.getTileFromPos(outputInvPos.getX(),
									outputInvPos.getY(), world);

							if (outputInvUnchecked != null && outputConduit != null)
							{
								if (outputInvUnchecked instanceof IInventoryHolder)
								{
									IInventoryHolder outputInv = (IInventoryHolder) outputInvUnchecked;

									if (outputConduit.getSideMode(outputInvSide) == ConduitMode.OUTPUT)
									{
										if (outputConduit.canAccept(wouldInput, outputInvSide))
										{
											if (RockSolidAPILib.canInsert(outputInv, wouldInput))
											{
												ItemInstance extractedItem = RockSolidAPILib.extract(inputInv, 1,
														inputInv.getInventory().get(side.getID()),
														inputConduit.getIsWhitelist(side));
												RockSolidAPILib.insert(outputInv, extractedItem);
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

	public int getHighestOutputPriority(IInventoryHolder inputInv)
	{
		if (this.isMaster())
		{
			int highest = 0;
			for (short outputNet = 0; outputNet < super.getNetworkLength(); outputNet++)
			{
				Pos2 outputConduitPos = new Pos2(super.getNetwork()[outputNet][0] + this.getMaster().getX(),
						super.getNetwork()[outputNet][1] + this.getMaster().getY());
				TileEntityItemConduit outputConduit = world.getTileEntity(outputConduitPos.getX(),
						outputConduitPos.getY(), TileEntityItemConduit.class);

				ConduitSide outputInvSide = ConduitSide.getByID(super.getNetwork()[outputNet][2]);
				
				if (outputConduit.getSideMode(outputInvSide) == ConduitMode.OUTPUT && outputConduit.getPriority(outputInvSide) > highest)
				{
					Pos2 outputInvPos = RockSolidAPILib.conduitSideToPos(outputConduitPos, outputInvSide);
					TileEntity outputInvUnchecked = RockSolidAPILib.getTileFromPos(outputInvPos.getX(),
							outputInvPos.getY(), world);
	
					if (outputInvUnchecked != null && outputConduit != null)
					{
						if (outputInvUnchecked instanceof IInventoryHolder)
						{
							IInventoryHolder outputInv = (IInventoryHolder) outputInvUnchecked;
							
							
							for (int slot : inputInv.getOutputSlots(Direction.NONE))
							{
								if (outputConduit.canAccept(inputInv.getInventory().get(slot), outputInvSide))
								{
									if (RockSolidAPILib.canInsert(outputInv, inputInv.getInventory().get(slot)))
									{
										highest = outputConduit.getPriority(outputInvSide);
									}
								}
							}
							
						}
					}
				}
			}
			
			return highest;
		} else
		{
			TileEntityItemConduit masterConduit = world.getTileEntity(this.getMaster().getX(),
					this.getMaster().getY(), TileEntityItemConduit.class);

			if (masterConduit != null)
			{
				return masterConduit.getHighestOutputPriority(inputInv);
			}
		}
		return 1;
	}

	public boolean canAccept(ItemInstance item, ConduitSide side)
	{
		ItemInstance filter = this.getInventory().get(side.getID());
		boolean isWhitelist = this.getIsWhitelist(side);
		
		
		if (item != null)
		{
			if (filter == null)
			{
				return true;
			} else
			{
				if (isWhitelist)
				{
					if (filter.getItem().equals(item.getItem()))
					{
						return true;
					}
				} else
				{
					if (!filter.getItem().equals(item.getItem()))
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
		if (!forSync)
		{
			this.inventory.save(set);
		}
		set.addBoolean("isWhitelistUp", this.whitelistModes[ConduitSide.UP.getID()]);
		set.addBoolean("isWhitelistDown", this.whitelistModes[ConduitSide.DOWN.getID()]);
		set.addBoolean("isWhitelistLeft", this.whitelistModes[ConduitSide.LEFT.getID()]);
		set.addBoolean("isWhitelistRight", this.whitelistModes[ConduitSide.RIGHT.getID()]);
	}

	@Override
	public void load(final DataSet set, final boolean forSync)
	{
		super.load(set, forSync);
		if (!forSync)
		{
			this.inventory.load(set);
		}
		this.whitelistModes[ConduitSide.UP.getID()] = set.getBoolean("isWhitelistUp");
		this.whitelistModes[ConduitSide.DOWN.getID()] = set.getBoolean("isWhitelistDown");
		this.whitelistModes[ConduitSide.LEFT.getID()] = set.getBoolean("isWhitelistLeft");
		this.whitelistModes[ConduitSide.RIGHT.getID()] = set.getBoolean("isWhitelistRight");
	}

	public boolean getIsWhitelist(ConduitSide side)
	{
		return this.whitelistModes[side.getID()];
	}

	public void setIsWhitelist(ConduitSide side, boolean isWhitelist)
	{
		if (!world.isClient())
		{
			this.whitelistModes[side.getID()] = isWhitelist;
			super.shouldSync();
		}
	}
	
	@Override
	public boolean canConnectAbstract(TileEntity tile)
	{
		return tile instanceof IInventoryHolder;
	}
}
