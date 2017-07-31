package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.api.util.IConduit;
import com.raphydaphy.rocksolid.api.util.IHasInventory;
import com.raphydaphy.rocksolid.init.ModTiles;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Direction;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class TileEntityAllocator extends TileEntity implements IConduit
{

	// 0 = output to the inventory
	// 1 = input from the inventory
	// 2 = disabled

	private int modeUp = 0;
	private int modeDown = 0;
	private int modeLeft = 0;
	private int modeRight = 0;

	private boolean isMaster;
	public boolean isDead;
	private int masterX;
	private int masterY;

	private boolean shouldSync = false;

	// format is {x, y, mode}
	private short[][] network = new short[512][3];
	private short networkLength = 0;

	public TileEntityAllocator(final IWorld world, final int x, final int y)
	{
		super(world, x, y);
		this.masterX = x;
		this.masterY = y;
		this.isMaster = true;
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
				for (int curNet = 0; curNet < networkLength; curNet++)
				{
					Pos2 tilePos = new Pos2(this.network[curNet][0], this.network[curNet][1]);
					TileEntity tile = RockSolidLib.getTileFromPos(tilePos.getX(), tilePos.getY(), world);
					if (tile != null && tile instanceof IHasInventory)
					{
						// if the inventory is set to input into the network (
						// INPUT )
						if (this.network[curNet][2] == 1)
						{
							IHasInventory invTile = ((IHasInventory) tile);
							ItemInstance wouldInput = RockSolidLib.getToExtract(invTile, 1);
							if (wouldInput != null)
							{
								for (int curNetOut = 0; curNetOut < networkLength; curNetOut++)
								{
									Pos2 tilePosOut = new Pos2(this.network[curNetOut][0], this.network[curNetOut][1]);
									TileEntity tileOut = RockSolidLib.getTileFromPos(tilePosOut.getX(),
											tilePosOut.getY(), world);
									if (tile != null && tile instanceof IHasInventory)
									{
										// if the inventory is set to output
										// into from the network (OUTPUT )
										if (this.network[curNetOut][2] == 0 && tileOut instanceof IHasInventory)
										{
											if (RockSolidLib.canInsert((IHasInventory) tileOut, wouldInput))
											{
												ItemInstance input = RockSolidLib.extract(invTile, 1);
												RockSolidLib.insert((IHasInventory) tileOut, input);
												break;
											}
										}
									}
								}

							}
						}
					}
					else if (tile == null)
					{
						this.removeFromNetwork(curNet);
					}
				}
			}
			else
			{
				TileEntityAllocator master = world.getTileEntity(masterX, masterY, TileEntityAllocator.class);
				if (master == null)
				{
					for (Direction dir : Direction.SURROUNDING)
					{
						Pos2 pos = new Pos2(x + dir.x, y + dir.y);
						
						if (pos.getX() == masterX && pos.getY() == masterY)
						{
							System.out.println("Found old master at " + masterX + ", " + masterY + ", moving to here at " + x + ", " + y);
							this.setMaster(new Pos2(x, y));
						}
					}
					
				}
			}
		}
	}
	
	public void removeFromNetwork(int id)
	{
		if (RockBottomAPI.getNet().isClient() == false)
		{
			if (this.isMaster)
			{
				this.networkLength--;
				this.network[id] = this.network[networkLength];
				this.network[networkLength] = new short[]{0,0,2};
			}
			else
			{
				TileEntityAllocator master = world.getTileEntity(masterX, masterY, TileEntityAllocator.class);
				if (master != null)
				{
					master.removeFromNetwork(id);
				}
			}
		}
	}

	public void setSideMode(int side, int mode)
	{
		switch (side)
		{
		case 0:
			// up
			modeUp = mode;
			break;
		case 1:
			// down
			modeDown = mode;
			break;
		case 2:
			// left
			modeLeft = mode;
			break;
		case 3:
			// right
			modeRight = mode;
			break;
		}
		Pos2 pos = RockSolidLib.conduitSideToPos(new Pos2(this.x, this.y), side);
		this.onChangeAround(world, x, y, TileLayer.MAIN, pos.getX(), pos.getY(), TileLayer.MAIN);
		shouldSync = true;
	}

	public int getSideMode(int side)
	{
		switch (side)
		{
		case 0:
			return modeUp;
		case 1:
			return modeDown;
		case 2:
			return modeLeft;
		case 3:
			return modeRight;
		}
		return 0;
	}

	@Override
	public void save(final DataSet set, final boolean forSync)
	{
		super.save(set, forSync);
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
	}

	@Override
	public void load(final DataSet set, final boolean forSync)
	{
		super.load(set, forSync);
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
		this.networkLength = set.getShort("inputLength");
	}

	public void onAdded(IWorld world, int x, int y)
	{
		if (!world.isClient())
		{
			System.out.println("new allocator added.");
			for (Direction dir : Direction.ADJACENT)
			{
				this.onChangeAround(world, x, y, TileLayer.MAIN, x + dir.x, y + dir.y, TileLayer.MAIN);
				/*
				TileEntity thisTile = world.getTileEntity(x + dir.x, y + dir.y);

				if (thisTile instanceof TileEntityAllocator
						&& this.canConnectTo(new Pos2(x + dir.x, y + dir.y), thisTile))
				{
					TileEntityAllocator allocator = (TileEntityAllocator) thisTile;

					// if not the same master
					if (!(this.getMaster().equals(allocator.getMaster())))
					{
						System.out.println("New allocator is not the master");
						this.setMaster(allocator.getMaster());
					}
				} else if (thisTile instanceof IHasInventory)
				{
					int thisMode = this.getSideMode(
							RockSolidLib.posAndOffsetToConduitSide(new Pos2(x, y), new Pos2(x + dir.x, y + dir.y)));
					this.addToNetwork((short) thisTile.x, (short) thisTile.y, (short) thisMode);
				}*/

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
				TileEntityAllocator master = world.getTileEntity(masterX, masterY, TileEntityAllocator.class);
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
				TileEntityAllocator master = world.getTileEntity(masterX, masterY, TileEntityAllocator.class);
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
					this.addToNetwork(newNetwork[i][0], newNetwork[i][1], newNetwork[i][2]);
				}
			}
		} else if (isDead)
		{
			return;
		} else
		{
			TileEntityAllocator master = world.getTileEntity(masterX, masterY, TileEntityAllocator.class);

			if (master != null && master != this)
			{
				master.updateNetwork(newNetwork, length);
			}
		}
	}

	public void setMaster(Pos2 newMaster)
	{
		if (RockBottomAPI.getNet().isClient() == false)
		{
			TileEntityAllocator newMasterTile = world.getTileEntity(newMaster.getX(), newMaster.getY(),
					TileEntityAllocator.class);
			TileState masterState = world.getState(newMaster.getX(), newMaster.getY());
			if (newMasterTile != null && !newMasterTile.isDead && masterState.getTile() == ModTiles.allocator)
			{
				if (!this.getMaster().equals(newMaster))
				{
					System.out.println("setting the allocator at " + this.x + ", " + this.y +" to a new master at " + newMaster.getX() + ", " + newMaster.getY());
					this.masterX = newMaster.getX();
					this.masterY = newMaster.getY();
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
						TileEntityAllocator found = world.getTileEntity(x + dir.x, y + dir.y,
								TileEntityAllocator.class);
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
			}
			else
			{
				this.setMaster(new Pos2(this.x, this.y));
			}
		}
	}

	public void onChangeAround(IWorld world, int x, int y, TileLayer layer, int changedX, int changedY,
			TileLayer changedLayer)
	{
		if (RockBottomAPI.getNet().isClient() == false)
		{
			System.out.println("The thing changed at " + changedX + ", " + changedY);
			if (x > Short.MAX_VALUE || y > Short.MAX_VALUE || x < Short.MIN_VALUE || y < Short.MIN_VALUE)
			{
				System.out
						.println("GO BACK YOU WENT TOO FAR YOU CAN ONLY GO " + Short.MAX_VALUE + " BLOCKS PLEASE SIR!");
				return;
			}

			TileEntity changedTile = RockSolidLib.getTileFromPos(changedX, changedY, world);

			if (changedTile instanceof TileEntityAllocator)
			{
				if (!((TileEntityAllocator) changedTile).isDead)
				{
					// if it has a different master
					if (!((TileEntityAllocator) changedTile).getMaster().equals(this.getMaster()))
					{
						this.setMaster(((TileEntityAllocator) changedTile).getMaster());
					}
				}
			} else if (changedTile instanceof IHasInventory)
			{
				int thisMode = this.getSideMode(
						RockSolidLib.posAndOffsetToConduitSide(new Pos2(x, y), new Pos2(changedX, changedY)));
				System.out.println(
						"A new inventory was placed near an allocator to a side with mode " + thisMode + " on side "
								+ RockSolidLib.posAndOffsetToConduitSide(new Pos2(x, y), new Pos2(changedX, changedY)));
				this.addToNetwork(changedX, changedY, thisMode);
			}
		}
	}

	public void addToNetwork(int x, int y, int mode)
	{
		if (world.isClient() == false)
		{
			System.out.println("Adding to network #" + networkLength);
			if (this.isMaster)
			{
				boolean alreadyHad = false;
				for (int curInv = 0; curInv < networkLength; curInv++)
				{
					if (!alreadyHad)
					{
						if (network[curInv][0] == (short) x && network[curInv][1] == (short) y)
						{
							network[curInv][2] = (short) mode;
							this.shouldSync = true;
							alreadyHad = true;
							break;
						}
					}
				}
				if (!alreadyHad)
				{
					network[networkLength] = new short[] { (short) x, (short) y, (short) mode };
					networkLength++;
					this.shouldSync = true;
				}

			} else
			{
				TileEntityAllocator master = world.getTileEntity(masterX, masterY, TileEntityAllocator.class);
				if (master != null)
				{
					master.addToNetwork(x, y, mode);
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
			System.out.println("I was removed :( Goodbye");
			this.isDead = true;
			this.shouldSync = true;
			
			for (Direction dir : Direction.ADJACENT)
			{
				TileEntityAllocator thisTileHere = world.getTileEntity(x + dir.x, y + dir.y, TileEntityAllocator.class);
				if (canConnectTo(new Pos2(x + dir.x, y + dir.y), thisTileHere))
				{
					thisTileHere.setMaster(new Pos2(thisTileHere.x, thisTileHere.y));
				}
			}
		}
	}

	@Override
	public boolean canConnectTo(Pos2 pos, TileEntity tile)
	{
		if (tile instanceof TileEntityAllocator)
		{
			return ((TileEntityAllocator) tile)
					.getSideMode(RockSolidLib.posAndOffsetToConduitSide(pos, new Pos2(x, y))) != 2 && !((TileEntityAllocator)tile).isDead;
		}
		return tile instanceof IHasInventory;
	}
}
