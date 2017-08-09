package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.api.util.IConduit;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib.ConduitMode;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib.ConduitSide;
import com.raphydaphy.rocksolid.gui.inventory.ContainerInventory;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.IInventoryHolder;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Direction;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class TileEntityItemConduit extends TileEntity implements IConduit
{
	public final ContainerInventory inventory;

	private ConduitMode[] modes = new ConduitMode[] { ConduitMode.OUTPUT, ConduitMode.OUTPUT, ConduitMode.OUTPUT,
			ConduitMode.OUTPUT };
	private int[] priorities = new int[] { 1, 1, 1, 1 };
	private boolean[] whitelistModes = new boolean[] { true, true, true, true };
	private Pos2 master = new Pos2(x, y);
	private boolean isDead = false;
	private boolean shouldSync = false;

	// [0] Conduit X Position Relative to Master X
	// [1] Conduit Y Position Relative to Master Y
	// [2] ConduitSide ID of the inventory

	private short[][] network = new short[512][3];
	private short networkLength = 0;

	public TileEntityItemConduit(final IWorld world, final int x, final int y)
	{
		super(world, x, y);
		this.inventory = new ContainerInventory(this, 4);
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

	@Override
	public void setSync()
	{
		if (RockBottomAPI.getNet().isClient() == false)
		{
			shouldSync = true;
		}
	}
	
	public Inventory getInventory()
	{
		return this.inventory;
	}

	@Override
	public void update(IGameInstance game)
	{
		super.update(game);
		if (world.getWorldInfo().totalTimeInWorld % 10 == 0 && this.isMaster())
		{
			for (short inputNet = 0; inputNet < networkLength; inputNet++)
			{
				Pos2 inputConduitPos = new Pos2(network[inputNet][0] + this.getMaster().getX(),
						network[inputNet][1] + this.getMaster().getY());
				TileEntityItemConduit inputConduit = world.getTileEntity(inputConduitPos.getX(),
						inputConduitPos.getY(), TileEntityItemConduit.class);

				ConduitSide inputInvSide = ConduitSide.getByID(network[inputNet][2]);
				Pos2 inputInvPos = RockSolidAPILib.conduitSideToPos(inputConduitPos, inputInvSide);
				TileEntity inputInvUnchecked = RockSolidAPILib.getTileFromPos(inputInvPos.getX(), inputInvPos.getY(),
						world);

				if (inputInvUnchecked != null && inputConduit != null)
				{
					if (inputInvUnchecked instanceof IInventoryHolder)
					{
						IInventoryHolder inputInv = (IInventoryHolder) inputInvUnchecked;

						if (inputConduit.getSideMode(inputInvSide) == ConduitMode.INPUT)
						{
							ItemInstance wouldInput = RockSolidAPILib.getToExtract(inputInv, 1,
									inputInv.getInventory().get(inputInvSide.getID()),
									inputConduit.getIsWhitelist(inputInvSide));
							
							int highestOutput = this.getHighestOutputPriority(inputInv);

							for (short outputNet = 0; outputNet < networkLength; outputNet++)
							{
								Pos2 outputConduitPos = new Pos2(network[outputNet][0] + this.getMaster().getX(),
										network[outputNet][1] + this.getMaster().getY());
								TileEntityItemConduit outputConduit = world.getTileEntity(outputConduitPos.getX(),
										outputConduitPos.getY(), TileEntityItemConduit.class);

								ConduitSide outputInvSide = ConduitSide.getByID(network[outputNet][2]);
								
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
																inputInv.getInventory().get(inputInvSide.getID()),
																inputConduit.getIsWhitelist(inputInvSide));
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
		}
	}

	public int getHighestOutputPriority(IInventoryHolder inputInv)
	{
		if (this.isMaster())
		{
			int highest = 0;
			for (short outputNet = 0; outputNet < networkLength; outputNet++)
			{
				Pos2 outputConduitPos = new Pos2(network[outputNet][0] + this.getMaster().getX(),
						network[outputNet][1] + this.getMaster().getY());
				TileEntityItemConduit outputConduit = world.getTileEntity(outputConduitPos.getX(),
						outputConduitPos.getY(), TileEntityItemConduit.class);

				ConduitSide outputInvSide = ConduitSide.getByID(network[outputNet][2]);
				
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
		set.addInt("modeUp", this.modes[ConduitSide.UP.getID()].getID());
		set.addInt("modeDown", this.modes[ConduitSide.DOWN.getID()].getID());
		set.addInt("modeLeft", this.modes[ConduitSide.LEFT.getID()].getID());
		set.addInt("modeRight", this.modes[ConduitSide.RIGHT.getID()].getID());
		set.addBoolean("shouldSync", this.shouldSync);
		set.addInt("priorityUp", this.priorities[ConduitSide.UP.getID()]);
		set.addInt("priorityDown", this.priorities[ConduitSide.DOWN.getID()]);
		set.addInt("priorityLeft", this.priorities[ConduitSide.LEFT.getID()]);
		set.addInt("priorityRight", this.priorities[ConduitSide.RIGHT.getID()]);
		set.addBoolean("isWhitelistUp", this.whitelistModes[ConduitSide.UP.getID()]);
		set.addBoolean("isWhitelistDown", this.whitelistModes[ConduitSide.DOWN.getID()]);
		set.addBoolean("isWhitelistLeft", this.whitelistModes[ConduitSide.LEFT.getID()]);
		set.addBoolean("isWhitelistRight", this.whitelistModes[ConduitSide.RIGHT.getID()]);
		set.addInt("masterX", this.master.getX());
		set.addInt("masterY", this.master.getY());
		set.addBoolean("isDead", this.isDead);
		set.addShortShortArray("network", this.network);
		set.addShort("networkLength", this.networkLength);
	}

	@Override
	public void load(final DataSet set, final boolean forSync)
	{
		super.load(set, forSync);
		if (!forSync)
		{
			this.inventory.load(set);
		}
		this.modes[ConduitSide.UP.getID()] = ConduitMode.getByID(set.getInt("modeUp"));
		this.modes[ConduitSide.DOWN.getID()] = ConduitMode.getByID(set.getInt("modeDown"));
		this.modes[ConduitSide.LEFT.getID()] = ConduitMode.getByID(set.getInt("modeLeft"));
		this.modes[ConduitSide.RIGHT.getID()] = ConduitMode.getByID(set.getInt("modeRight"));
		this.shouldSync = set.getBoolean("shouldSync");
		this.priorities[ConduitSide.UP.getID()] = set.getInt("priorityUp");
		this.priorities[ConduitSide.DOWN.getID()] = set.getInt("priorityDown");
		this.priorities[ConduitSide.LEFT.getID()] = set.getInt("priorityLeft");
		this.priorities[ConduitSide.RIGHT.getID()] = set.getInt("priorityRight");
		this.whitelistModes[ConduitSide.UP.getID()] = set.getBoolean("isWhitelistUp");
		this.whitelistModes[ConduitSide.DOWN.getID()] = set.getBoolean("isWhitelistDown");
		this.whitelistModes[ConduitSide.LEFT.getID()] = set.getBoolean("isWhitelistLeft");
		this.whitelistModes[ConduitSide.RIGHT.getID()] = set.getBoolean("isWhitelistRight");
		this.master = new Pos2(set.getInt("masterX"), set.getInt("masterY"));
		this.isDead = set.getBoolean("isDead");
		this.network = set.getShortShortArray("network", 512);
		this.networkLength = set.getShort("networkLength");
	}

	public void onAdded(IWorld world, int x, int y)
	{
		if (!world.isClient())
		{
			if (this.isMaster())
			{
				// determine if this should be the master or not
				for (ConduitSide side : ConduitSide.values())
				{
					TileEntity adjSide = RockSolidAPILib.getTileFromConduitSide(new Pos2(x, y), side, world);
					if (adjSide instanceof TileEntityItemConduit)
					{
						TileEntityItemConduit adjConduit = (TileEntityItemConduit) adjSide;
						// different master to this
						if (!adjConduit.getMaster().equals(this.master))
						{
							// we haven't set the master of this yet
							if (this.isMaster())
							{
								this.setMaster(adjConduit.getMaster());
							} else
							{
								adjConduit.setMaster(this.master);
							}
						}
					}
				}

				Pos2 masterOffsetPos = new Pos2(x - this.getMaster().getX(), y - this.getMaster().getY());

				this.addAdjacentInventories(world, (short) masterOffsetPos.getX(), (short) masterOffsetPos.getY());
			}
		}
	}

	public void addInventory(IWorld world, short relativeConduitX, short relativeConduitY, ConduitSide side)
	{
		if (this.isMaster())
		{
			if (!world.isClient())
			{
				TileEntity adjSide = RockSolidAPILib.getTileFromPos(
						this.getMaster().getX() + relativeConduitX + side.getOffset().getX(),
						this.getMaster().getY() + relativeConduitY + side.getOffset().getY(), world);

				short[] networkEntry = this.getFromNetwork(new Pos2(relativeConduitX, relativeConduitY), side);

				if (adjSide instanceof IInventoryHolder)
				{
					// it isn't already contained in the network
					if (networkEntry[0] == 0)
					{
						this.network[networkLength] = new short[] { relativeConduitX, relativeConduitY,
								(short) side.getID() };
						networkLength++;
						this.shouldSync = true;
					}
					// we already have this inventory in the network
					else
					{
						// do nothing?
					}
				}
				// if it used to contain a inventory
				else if (networkEntry[0] == 1)
				{
					this.removeFromNetwork(networkEntry[1]);
				}
			}
		} else
		{
			TileEntityItemConduit masterConduit = world.getTileEntity(this.getMaster().getX(),
					this.getMaster().getY(), TileEntityItemConduit.class);

			if (masterConduit != null)
			{
				masterConduit.addInventory(world, relativeConduitX, relativeConduitY, side);
			}
		}
	}

	public void removeFromNetwork(int id)
	{
		if (this.isMaster())
		{
			if (!world.isClient())
			{
				networkLength--;
				this.network[id] = this.network[networkLength];
				this.network[networkLength] = new short[] { 0, 0, 0 };
				this.shouldSync = true;
			}
		} else
		{
			TileEntityItemConduit masterConduit = world.getTileEntity(this.getMaster().getX(),
					this.getMaster().getY(), TileEntityItemConduit.class);

			if (masterConduit != null)
			{
				masterConduit.removeFromNetwork(id);
			}
		}
	}

	public void addAdjacentInventories(IWorld world, short relativeConduitX, short relativeConduitY)
	{
		if (this.isMaster())
		{
			if (!world.isClient())
			{
				TileEntityItemConduit conduit = world.getTileEntity(relativeConduitX + this.getMaster().getX(),
						relativeConduitY + this.getMaster().getY(), TileEntityItemConduit.class);

				if (conduit != null)
				{
					for (ConduitSide side : ConduitSide.values())
					{
						this.addInventory(world, relativeConduitX, relativeConduitY, side);
					}
				}
			}
		} else
		{
			TileEntityItemConduit masterConduit = world.getTileEntity(this.getMaster().getX(),
					this.getMaster().getY(), TileEntityItemConduit.class);

			if (masterConduit != null)
			{
				masterConduit.addAdjacentInventories(world, relativeConduitX, relativeConduitY);
			}
		}
	}

	public void onChangeAround(IWorld world, int x, int y, TileLayer layer, int changedX, int changedY,
			TileLayer changedLayer)
	{
		if (!world.isClient())
		{
			TileEntity changedTile = RockSolidAPILib.getTileFromPos(changedX, changedY, world);

			ConduitSide changedSide = RockSolidAPILib.posAndOffsetToConduitSide(new Pos2(x, y),
					new Pos2(changedX, changedY));
			Pos2 conduitRelativePos = new Pos2(x - this.getMaster().getX(), y - this.getMaster().getY());

			if (changedTile != null)
			{
				if (changedTile instanceof IInventoryHolder)
				{
					this.addInventory(world, (short) conduitRelativePos.getX(), (short) conduitRelativePos.getY(),
							changedSide);
				}
			} else
			{
				short[] oldListing = this.getFromNetwork(conduitRelativePos, changedSide);

				// used to contain an inventory
				if (oldListing[0] == 1)
				{
					this.removeFromNetwork(oldListing[1]);
				}
			}
		}
	}

	public void onRemoved(IWorld world, int x, int y, TileLayer layer)
	{
		if (!world.isClient())
		{
			this.isDead = true;
			this.shouldSync = true;

			for (ConduitSide side : ConduitSide.values())
			{
				TileEntity adjSide = RockSolidAPILib.getTileFromConduitSide(new Pos2(x, y), side, world);

				if (adjSide instanceof TileEntityItemConduit)
				{
					((TileEntityItemConduit) adjSide).setMaster(new Pos2(adjSide.x, adjSide.y));
				}
			}
		}
	}

	@Override
	public boolean canConnectTo(Pos2 pos, TileEntity tile)
	{
		if (tile instanceof TileEntityItemConduit)
		{
			return ((TileEntityItemConduit) tile).getSideMode(
					RockSolidAPILib.posAndOffsetToConduitSide(pos, new Pos2(x, y))) != ConduitMode.DISABLED;
		}
		return tile instanceof IInventoryHolder;
	}

	// Dosen't actually return the value from the network.
	// the short[] returned has two entries
	// [0] If the container exists in the network. 1 = true
	// [1] The container's position in the network array

	public short[] getFromNetwork(Pos2 relativeConduitPos, ConduitSide side)
	{
		if (this.isMaster())
		{
			if (networkLength > 0)
			{
				for (short net = 0; net < networkLength; net++)
				{
					if (network[net][0] == relativeConduitPos.getX() && network[net][1] == relativeConduitPos.getY()
							&& network[net][2] == side.getID())
					{
						return new short[] { 1, net };
					}
				}
			}
			return new short[] { 0, 0 };
		} else
		{
			TileEntityItemConduit master = world.getTileEntity(this.getMaster().getX(), this.getMaster().getY(),
					TileEntityItemConduit.class);
			if (master != null)
			{
				return master.getFromNetwork(relativeConduitPos, side);
			}
		}
		return new short[] { 0, 0 };

	}

	public Pos2 getMaster()
	{
		return this.master;
	}

	public boolean isMaster()
	{
		return this.getMaster().getX() == this.x && this.getMaster().getY() == this.y;
	}

	public boolean isDead()
	{
		return this.isDead;
	}

	@Override
	public ConduitMode getSideMode(ConduitSide side)
	{
		return this.modes[side.getID()];
	}

	public boolean getIsWhitelist(ConduitSide side)
	{
		return this.whitelistModes[side.getID()];
	}

	public int getPriority(ConduitSide side)
	{
		return this.priorities[side.getID()];
	}

	public void setMaster(Pos2 master)
	{
		if (!world.isClient())
		{
			this.master = master;

			// wipe non-master networks
			if (!this.isMaster())
			{
				this.network = new short[512][3];
				this.shouldSync = true;
			}

			this.addAdjacentInventories(world, (short) (this.x - this.getMaster().getX()),
					(short) (this.y - this.getMaster().getY()));

			for (ConduitSide side : ConduitSide.values())
			{
				TileEntity adjSide = RockSolidAPILib.getTileFromConduitSide(new Pos2(this.x, this.y), side, world);

				// if its an item conduit with a different master
				if (adjSide instanceof TileEntityItemConduit
						&& !((TileEntityItemConduit) adjSide).getMaster().equals(this.getMaster())
						&& !((TileEntityItemConduit) adjSide).isDead)
				{
					((TileEntityItemConduit) adjSide).setMaster(this.getMaster());
				}
			}
			this.shouldSync = true;
		}
	}

	@Override
	public void setSideMode(ConduitSide side, ConduitMode mode)
	{
		if (!world.isClient())
		{
			this.modes[side.getID()] = mode;
			this.shouldSync = true;
		}
	}

	public void setIsWhitelist(ConduitSide side, boolean isWhitelist)
	{
		if (!world.isClient())
		{
			this.whitelistModes[side.getID()] = isWhitelist;
			this.shouldSync = true;
		}
	}

	public void setPriority(ConduitSide side, int priority)
	{
		if (!world.isClient())
		{
			this.priorities[side.getID()] = priority;
			this.shouldSync = true;
		}
	}
}
