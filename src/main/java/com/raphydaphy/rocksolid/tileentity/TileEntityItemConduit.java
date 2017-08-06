package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.api.util.IConduit;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib.ConduitMode;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib.ConduitSide;
import com.raphydaphy.rocksolid.gui.inventory.ContainerInventory;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.IInventoryHolder;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Direction;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class TileEntityItemConduit extends TileEntity implements IConduit
{

	// 0 = output to the inventory
	// 1 = input from the inventory
	// 2 = disabled

	public final ContainerInventory inventory;

	private int modeUp = 0;
	private int modeDown = 0;
	private int modeLeft = 0;
	private int modeRight = 0;

	private int priorityUp = 1;
	private int priorityDown = 1;
	private int priorityLeft = 1;
	private int priorityRight = 1;

	private boolean isWhitelistUp = true;
	private boolean isWhitelistDown = true;
	private boolean isWhitelistLeft = true;
	private boolean isWhitelistRight = true;

	private boolean isMaster = true;
	public boolean isDead;
	private int masterX = x;
	private int masterY = y;

	private boolean shouldSync = false;

	// format is {conduitX, conduitY, conduitSide, conduitMode, conduitPriority
	// conduitIsWhitelist)
	private short[][] network = new short[512][7];
	private short networkLength = 0;

	public TileEntityItemConduit(final IWorld world, final int x, final int y)
	{
		super(world, x, y);
		this.inventory = new ContainerInventory(this, 4);
		// onAdded(world, x, y);
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

	public int getPriority(ConduitSide side)
	{
		switch (side.getID())
		{
		case 0:
			return priorityUp;
		case 1:
			return priorityDown;
		case 2:
			return priorityLeft;
		case 3:
			return priorityRight;
		}
		return 1;
	}

	public void setPriority(int priority, ConduitSide side)
	{
		if (world.isClient() == false)
		{
			switch (side.getID())
			{
			case 0:
				this.priorityUp = priority;
				break;
			case 1:
				this.priorityDown = priority;
				break;
			case 2:
				this.priorityLeft = priority;
				break;
			case 3:
				this.priorityRight = priority;
				break;
			}

			Pos2 pos = RockSolidAPILib.conduitSideToPos(new Pos2(this.x, this.y), side);
			this.onChangeAround(world, x, y, TileLayer.MAIN, pos.getX(), pos.getY(), TileLayer.MAIN);

			this.shouldSync = true;
		}
	}

	@Override
	public void update(IGameInstance game)
	{
		super.update(game);
		// run conduit processing code every 10 ticks to prevent lag
		// also causes a less op item processing rate of 1 every 10 ticks
		if (world.getWorldInfo().totalTimeInWorld % 10 == 0 && RockBottomAPI.getNet().isClient() == false)
		{
			if (this.isMaster)
			{
				System.out.println("Network has " + networkLength + " inventories");
				for (int curNet = 0; curNet < networkLength; curNet++)
				{
					Pos2 centerPos = new Pos2(this.network[curNet][0], this.network[curNet][1]);
					TileEntity centerTile = RockSolidAPILib.getTileFromPos(centerPos.getX(), centerPos.getY(), world);
					if (!(centerTile instanceof TileEntityItemConduit))
					{
						this.removeFromNetwork(curNet);
						continue;
					}
					TileEntity tile = RockSolidAPILib.getTileFromConduitSide(centerPos, ConduitSide.getByID(network[curNet][2]), world);
					if (tile != null && tile instanceof IInventoryHolder)
					{
						// if the inventory is set to input into the network (
						// INPUT )
						if (this.network[curNet][3] == ConduitMode.INPUT.getID())
						{
							IInventoryHolder invTile = ((IInventoryHolder) tile);

							for (int curNetOut = 0; curNetOut < networkLength; curNetOut++)
							{
								ItemInstance inputFilter = ((TileEntityItemConduit) centerTile).inventory
										.get(network[curNet][2]);
								ItemInstance wouldInput = RockSolidAPILib.getToExtract(invTile, 1, inputFilter,
										this.network[curNetOut][5] == 1);
								if (wouldInput != null)
								{
									Pos2 centerPosOut = new Pos2(this.network[curNetOut][0],
											this.network[curNetOut][1]);
									TileEntity centerTileOut = RockSolidAPILib.getTileFromPos(centerPosOut.getX(),
											centerPosOut.getY(), world);
									if (!(centerTileOut instanceof TileEntityItemConduit))
									{
										this.removeFromNetwork(curNetOut);
										continue;
									}
									TileEntity tileOut = RockSolidAPILib.getTileFromConduitSide(centerPosOut,
											ConduitSide.getByID(network[curNetOut][2]), world);

									if (tile != null && tile instanceof IInventoryHolder)
									{
										short highestPriority = this.getHighestPriority(wouldInput);
										// if the inventory is set to output
										// from from the network (OUTPUT )
										if (this.network[curNetOut][3] == ConduitMode.OUTPUT.getID()
												&& this.network[curNetOut][4] >= highestPriority
												&& tileOut instanceof IInventoryHolder)
										{
											if (RockSolidAPILib.canInsert((IInventoryHolder) tileOut, wouldInput))
											{
												ItemInstance input = RockSolidAPILib.extract(invTile, 1, inputFilter,
														this.network[curNetOut][5] == 1);
												RockSolidAPILib.insert((IInventoryHolder) tileOut, input);
												// break;
											}
										}
									}
								}
							}
						}
					} else if (tile == null)
					{
						this.removeFromNetwork(curNet);
					}
				}
			} else
			{
				TileEntityItemConduit master = world.getTileEntity(masterX, masterY, TileEntityItemConduit.class);
				if (master == null)
				{
					for (Direction dir : Direction.SURROUNDING)
					{
						Pos2 pos = new Pos2(x + dir.x, y + dir.y);
						if (pos.getX() == masterX && pos.getY() == masterY)
						{
							this.setMaster(new Pos2(x, y));
						}
					}

				}
			}
		}
	}

	public short getHighestPriority(ItemInstance input)
	{
		if (this.isMaster)
		{
			short highest = 0;
			for (int net = 0; net < networkLength; net++)
			{
				if (network[net].length > 4)
				{
					Pos2 centerPosOut = new Pos2(this.network[net][0], this.network[net][1]);
					TileEntity tileOut = RockSolidAPILib.getTileFromConduitSide(centerPosOut, ConduitSide.getByID(network[net][2]), world);
					if (tileOut instanceof IInventoryHolder
							&& RockSolidAPILib.canInsert((IInventoryHolder) tileOut, input))
					{
						// this is the highest priority yet
						if (network[net][4] > highest)
						{
							highest = network[net][4];
						}
					}

				}
			}
			return highest;
		} else
		{
			TileEntityItemConduit master = world.getTileEntity(masterX, masterY, TileEntityItemConduit.class);
			if (master != null)
			{
				return master.getHighestPriority(input);
			}
		}
		return 0;
	}

	public boolean canAccept(int container)
	{
		if (this.isMaster)
		{
			// if the container is within the network limit
			if (container < networkLength)
			{
				TileEntity networkPos = RockSolidAPILib.getTileFromConduitSide(
						new Pos2(network[container][0], network[container][1]), ConduitSide.getByID(network[container][2]), world);

				// if the container actually exists
				if (networkPos != null)
				{

				}
			}
		} else
		{
			TileEntityItemConduit master = world.getTileEntity(masterX, masterY, TileEntityItemConduit.class);
			if (master != null)
			{
				return master.canAccept(container);
			}
		}
		return false;
	}

	public void removeFromNetwork(int id)
	{
		if (RockBottomAPI.getNet().isClient() == false)
		{
			if (this.isMaster)
			{
				this.networkLength--;
				this.network[id] = this.network[networkLength];
				this.network[networkLength] = new short[] { 0, 0, 0, 2, 0, 1 };
				this.shouldSync = true;
			} else
			{
				TileEntityItemConduit master = world.getTileEntity(masterX, masterY, TileEntityItemConduit.class);
				if (master != null)
				{
					master.removeFromNetwork(id);
				}
			}
		}
	}

	public void setSideMode(ConduitSide side, ConduitMode mode)
	{
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
		Pos2 pos = RockSolidAPILib.conduitSideToPos(new Pos2(this.x, this.y), side);
		this.onChangeAround(world, x, y, TileLayer.MAIN, pos.getX(), pos.getY(), TileLayer.MAIN);
		shouldSync = true;
	}

	public boolean getIsWhitelist(ConduitSide side)
	{
		switch (side.getID())
		{
		case 0:
			return isWhitelistUp;
		case 1:
			return isWhitelistDown;
		case 2:
			return isWhitelistLeft;
		case 3:
			return isWhitelistRight;
		}
		return true;
	}

	public void setIsWhitelist(ConduitSide side, boolean isWhitelist)
	{
		if (RockBottomAPI.getNet().isClient() == false)
		{
			switch (side.getID())
			{
			case 0:
				// up
				this.isWhitelistUp = isWhitelist;
				break;
			case 1:
				// down
				this.isWhitelistDown = isWhitelist;
				break;
			case 2:
				// left
				this.isWhitelistLeft = isWhitelist;
				break;
			case 3:
				// right
				this.isWhitelistRight = isWhitelist;
				break;
			}

			Pos2 pos = RockSolidAPILib.conduitSideToPos(new Pos2(this.x, this.y), side);
			this.onChangeAround(world, x, y, TileLayer.MAIN, pos.getX(), pos.getY(), TileLayer.MAIN);
			shouldSync = true;
		}
	}

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
		if (!forSync)
		{
			this.inventory.save(set);
		}
		set.addInt("modeUp", this.modeUp);
		set.addInt("modeDown", this.modeDown);
		set.addInt("modeLeft", this.modeLeft);
		set.addInt("modeRight", this.modeRight);
		set.addBoolean("isMaster", this.isMaster);
		set.addInt("masterX", this.masterX);
		set.addInt("masterY", this.masterY);
		set.addShortShortArray("network", this.network);
		set.addBoolean("shouldSync", this.shouldSync);
		set.addBoolean("isDead", this.isDead);
		set.addShort("networkLength", this.networkLength);
		set.addInt("priorityUp", this.priorityUp);
		set.addInt("priorityDown", this.priorityDown);
		set.addInt("priorityLeft", this.priorityLeft);
		set.addInt("priorityRight", this.priorityRight);
		set.addBoolean("isWhitelistUp", this.isWhitelistUp);
		set.addBoolean("isWhitelistDown", this.isWhitelistDown);
		set.addBoolean("isWhitelistLeft", this.isWhitelistLeft);
		set.addBoolean("isWhitelistRight", this.isWhitelistRight);
	}

	@Override
	public void load(final DataSet set, final boolean forSync)
	{
		super.load(set, forSync);
		if (!forSync)
		{
			this.inventory.load(set);
		}
		this.modeUp = set.getInt("modeUp");
		this.modeDown = set.getInt("modeDown");
		this.modeLeft = set.getInt("modeLeft");
		this.modeRight = set.getInt("modeRight");
		this.isMaster = set.getBoolean("isMaster");
		this.masterX = set.getInt("masterX");
		this.masterY = set.getInt("masterY");
		this.network = set.getShortShortArray("network", 512);
		this.shouldSync = set.getBoolean("shouldSync");
		this.isDead = set.getBoolean("isDead");
		this.networkLength = set.getShort("networkLength");
		this.priorityUp = set.getInt("priorityUp");
		this.priorityDown = set.getInt("priorityDown");
		this.priorityLeft = set.getInt("priorityLeft");
		this.priorityRight = set.getInt("priorityRight");
		this.isWhitelistUp = set.getBoolean("isWhitelistUp");
		this.isWhitelistDown = set.getBoolean("isWhitelistDown");
		this.isWhitelistLeft = set.getBoolean("isWhitelistLeft");
		this.isWhitelistRight = set.getBoolean("isWhitelistRight");
	}

	public void onAdded(IWorld world, int x, int y)
	{
		if (!world.isClient())
		{
			for (Direction dir : Direction.ADJACENT)
			{
				this.onChangeAround(world, x, y, TileLayer.MAIN, x + dir.x, y + dir.y, TileLayer.MAIN);

			}
		}
	}

	public void sendAllToMaster()
	{
		if (RockBottomAPI.getNet().isClient() == false)
		{
			if (isMaster)
			{
				return;
			} else
			{
				TileEntityItemConduit master = world.getTileEntity(masterX, masterY, TileEntityItemConduit.class);
				if (master != null)
				{
					master.updateNetwork(this.network, this.networkLength);
				}
			}
		}
	}

	public void sendToMaster(short[][] sendNetwork, int sendNetworkLength)
	{
		if (RockBottomAPI.getNet().isClient() == false)
		{
			if (isMaster)
			{
				return;
			} else
			{
				TileEntityItemConduit master = world.getTileEntity(masterX, masterY, TileEntityItemConduit.class);
				if (master != null)
				{
					if (sendNetworkLength > 0)
					{
						master.updateNetwork(sendNetwork, sendNetworkLength);
					}
				}
			}
		}
	}

	public void updateNetwork(short[][] newNetwork, int length)
	{
		if (isMaster)
		{
			if (length > 0)
			{
				for (int i = 0; i < length; i++)
				{
					if (newNetwork[i].length > 5)
					{
						this.addToNetwork(newNetwork[i][0], newNetwork[i][1], ConduitSide.getByID(newNetwork[i][2]), ConduitMode.getByID(newNetwork[i][3]),
								newNetwork[i][4], newNetwork[i][5]);
					}
				}
			}
		} else if (isDead)
		{
			return;
		} else
		{
			TileEntityItemConduit master = world.getTileEntity(masterX, masterY, TileEntityItemConduit.class);

			if (master != null && !master.isDead && master != this && (master.x != this.x || master.y != this.y)
					&& master.isMaster)
			{
				// TODO: causes stackoverflow when logging in sometimes... dunno
				// why
				master.updateNetwork(newNetwork, length);
			} else if (master != null && !master.isMaster)
			{
				if (RockBottomAPI.getNet().isClient() == false)
				{
					for (Direction dir : Direction.ADJACENT)
					{
						this.onChangeAround(world, x, y, TileLayer.MAIN, x + dir.x, y + dir.y, TileLayer.MAIN);
					}
				}
			}
		}
	}

	public void resetMasterNetwork()
	{
		if (RockBottomAPI.getNet().isClient() == false)
		{
			if (isMaster)
			{
				this.network = new short[512][3];
			} else
			{
				TileEntityItemConduit master = world.getTileEntity(masterX, masterY, TileEntityItemConduit.class);
				if (master != null)
				{
					master.resetMasterNetwork();
				}
			}
		}
	}

	public void setMaster(Pos2 newMaster)
	{
		if (RockBottomAPI.getNet().isClient() == false)
		{
			TileEntityItemConduit newMasterTile = world.getTileEntity(newMaster.getX(), newMaster.getY(),
					TileEntityItemConduit.class);
			if (newMasterTile != null && !newMasterTile.isDead)
			{
				if (!this.getMaster().equals(newMaster))
				{

					for (Direction dir : Direction.ADJACENT)
					{
						TileEntity found = RockSolidAPILib.getTileFromPos(x + dir.x, y + dir.y, world);
						if (found instanceof IInventoryHolder)
						{
							this.onInventoryChanged(world, x, y, x + dir.x, y + dir.y);
						}
					}

					this.masterX = newMaster.getX();
					this.masterY = newMaster.getY();
					newMasterTile.isMaster = true;
					this.sendAllToMaster();

					if (masterX != this.x || masterY != this.y)
					{
						this.isMaster = false;
					}

					else if (masterX == this.x && masterY == this.y)
					{
						this.isMaster = true;
					}
					for (Direction dir : Direction.ADJACENT)
					{
						TileEntityItemConduit found = world.getTileEntity(x + dir.x, y + dir.y,
								TileEntityItemConduit.class);
						if (found != null && this.canConnectTo(new Pos2(x + dir.x, y + dir.y), found))
						{
							// if the adjacent tile dosent have the same master
							// as
							// this
							if (!found.getMaster().equals(this.getMaster()))
							{
								found.setMaster(newMaster);
							}
						}
					}
					this.shouldSync = true;
				}
			} else if (newMasterTile == null)
			{
				this.setMaster(new Pos2(this.x, this.y));
			}
		}
	}

	public void onInventoryChanged(IWorld world, int x, int y, int changedX, int changedY)
	{
		if (RockBottomAPI.getNet().isClient() == false)
		{
			if (x > Short.MAX_VALUE || y > Short.MAX_VALUE || x < Short.MIN_VALUE || y < Short.MIN_VALUE)
			{
				System.out
						.println("GO BACK YOU WENT TOO FAR YOU CAN ONLY GO " + Short.MAX_VALUE + " BLOCKS PLEASE SIR!");
				System.out.println(0 / 0);
				return;
			}

			TileEntity changedTile = RockSolidAPILib.getTileFromPos(changedX, changedY, world);

			if (changedTile instanceof IInventoryHolder)
			{
				ConduitSide side = RockSolidAPILib.posAndOffsetToConduitSide(new Pos2(x, y), new Pos2(changedX, changedY));
				ConduitMode thisMode = this.getSideMode(side);
				int thisPriority = this.getPriority(side);
				boolean thisWhitelistMode = this.getIsWhitelist(side);
				this.addToNetwork(x, y, side, thisMode, thisPriority, thisWhitelistMode);
			}
		}
	}

	public void onChangeAround(IWorld world, int x, int y, TileLayer layer, int changedX, int changedY,
			TileLayer changedLayer)
	{
		if (RockBottomAPI.getNet().isClient() == false)
		{
			if (x > Short.MAX_VALUE || y > Short.MAX_VALUE || x < Short.MIN_VALUE || y < Short.MIN_VALUE)
			{
				System.out
						.println("GO BACK YOU WENT TOO FAR YOU CAN ONLY GO " + Short.MAX_VALUE + " BLOCKS PLEASE SIR!");
				System.out.println(0 / 0);
				return;
			}

			TileEntity changedTile = RockSolidAPILib.getTileFromPos(changedX, changedY, world);

			if (changedTile instanceof TileEntityItemConduit)
			{
				if (!((TileEntityItemConduit) changedTile).isDead)
				{
					ConduitMode thisMode = this.getSideMode(
							RockSolidAPILib.posAndOffsetToConduitSide(new Pos2(x, y), new Pos2(changedX, changedY)));
					if (thisMode == ConduitMode.DISABLED)
					{
						this.resetMasterNetwork();
						this.setMaster(this.getMaster());
					} else if (getFromNetwork(changedX, changedY)[3] == ConduitMode.DISABLED.getID())
					{
						this.resetMasterNetwork();
						this.setMaster(this.getMaster());
					}
					// if it has a different master
					if (!((TileEntityItemConduit) changedTile).getMaster().equals(this.getMaster()))
					{
						this.setMaster(((TileEntityItemConduit) changedTile).getMaster());
					}
				}
			} else if (changedTile instanceof IInventoryHolder)
			{
				this.onInventoryChanged(world, x, y, changedX, changedY);
			}
		}
	}

	public short[] getFromNetwork(int x, int y)
	{
		short[] result = new short[] { 0, 0, 0, 2, 0, 1 };
		for (int net = 0; net < networkLength; net++)
		{
			if (network[net][0] == x && network[net][1] == y)
			{
				return network[net];
			}
		}
		return result;
	}

	public void addToNetwork(int x, int y, ConduitSide side, ConduitMode mode, int priority, boolean isWhitelist)
	{
		if (world.isClient() == false)
		{
			int isWhitelistInt = 1;
			if (!isWhitelist)
			{
				isWhitelistInt = 0;
			}
			this.addToNetwork(x, y, side, mode, priority, isWhitelistInt);
		}
	}

	public void addToNetwork(int x, int y, ConduitSide side, ConduitMode mode, int priority, int isWhitelist)
	{
		if (world.isClient() == false)
		{
			if (this.isMaster)
			{
				boolean alreadyHad = false;
				for (int curInv = 0; curInv < networkLength; curInv++)
				{
					if (!alreadyHad)
					{
						if (network[curInv][0] == (short) x && network[curInv][1] == (short) y)
						{
							network[curInv][2] = (short) side.getID();
							network[curInv][3] = (short) mode.getID();
							network[curInv][4] = (short) priority;
							network[curInv][5] = (short) isWhitelist;

							this.shouldSync = true;
							alreadyHad = true;
							break;
						}
					}
				}
				if (!alreadyHad)
				{
					network[networkLength] = new short[] { (short) x, (short) y, (short) side.getID(), (short) mode.getID(),
							(short) priority, (short) isWhitelist };
					networkLength++;
					this.shouldSync = true;
				}

			} else
			{
				TileEntityItemConduit master = world.getTileEntity(masterX, masterY, TileEntityItemConduit.class);
				if (master != null && master != this && master.isMaster)
				{
					master.addToNetwork(x, y, side, mode, priority, isWhitelist);
				}
			}
		}
	}

	public Pos2 getMaster()
	{
		return new Pos2(this.masterX, this.masterY);
	}

	public boolean isMaster()
	{
		return this.isMaster;
	}

	public void onRemoved(IWorld world, int x, int y, TileLayer layer)
	{

		if (RockBottomAPI.getNet().isClient() == false)
		{
			this.isDead = true;
			this.shouldSync = true;

			for (Direction dir : Direction.ADJACENT)
			{
				TileEntityItemConduit thisTileHere = world.getTileEntity(x + dir.x, y + dir.y,
						TileEntityItemConduit.class);
				if (canConnectTo(new Pos2(x + dir.x, y + dir.y), thisTileHere))
				{
					thisTileHere.resetMasterNetwork();
					thisTileHere.setMaster(new Pos2(thisTileHere.x, thisTileHere.y));
					// thisTileHere.regenerateAll(true);
				}
			}
		}
	}

	@Override
	public boolean canConnectTo(Pos2 pos, TileEntity tile)
	{
		if (tile instanceof TileEntityItemConduit)
		{
			return ((TileEntityItemConduit) tile)
					.getSideMode(RockSolidAPILib.posAndOffsetToConduitSide(pos, new Pos2(x, y))) != ConduitMode.DISABLED
					&& !((TileEntityItemConduit) tile).isDead;
		}
		return tile instanceof IInventoryHolder;
	}
}
