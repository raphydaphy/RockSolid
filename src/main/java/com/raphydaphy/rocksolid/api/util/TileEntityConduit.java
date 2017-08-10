package com.raphydaphy.rocksolid.api.util;

import com.raphydaphy.rocksolid.api.util.RockSolidAPILib.ConduitMode;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib.ConduitSide;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public abstract class TileEntityConduit<E extends TileEntityConduit<E>> extends TileEntity implements IConduit
{
	private ConduitMode[] modes = new ConduitMode[] { ConduitMode.OUTPUT, ConduitMode.OUTPUT, ConduitMode.OUTPUT,
			ConduitMode.OUTPUT };
	private int[] priorities = new int[] { 1, 1, 1, 1 };
	private Pos2 master = new Pos2(x, y);
	private boolean isDead = false;
	private boolean shouldSync = false;

	// [0] Conduit X Position Relative to Master X
	// [1] Conduit Y Position Relative to Master Y
	// [2] ConduitSide ID of the inventory

	private short[][] network = new short[512][3];
	private short networkLength = 0;
	
	private final Class<E> tileClass;

	public TileEntityConduit(Class<E> tileClass, final IWorld world, final int x, final int y)
	{
		super(world, x, y);
		this.tileClass = tileClass;
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
	
	public short[][] getNetwork()
	{
		if (this.isMaster())
		{
			return this.network;
		} else
		{
			TileEntityConduit<?> masterConduit = world.getTileEntity(this.getMaster().getX(), this.getMaster().getY(),
					tileClass);

			if (masterConduit != null)
			{
				return masterConduit.getNetwork();
			}
		}
		
		return new short[512][3];
	}
	
	public short getNetworkLength()
	{
		if (this.isMaster())
		{
			return this.networkLength;
		} else
		{
			TileEntityConduit <?> masterConduit = world.getTileEntity(this.getMaster().getX(), this.getMaster().getY(),
					tileClass);

			if (masterConduit != null)
			{
				return masterConduit.getNetworkLength();
			}
		}
		
		return 0;
	}
	
	public void shouldSync()
	{
		this.shouldSync = true;
	}

	public abstract void tryInput(Pos2 center, ConduitSide side);

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

				ConduitSide inputTileSide = ConduitSide.getByID(network[inputNet][2]);

				this.tryInput(inputConduitPos, inputTileSide);
			}
		}
	}

	@Override
	public void save(final DataSet set, final boolean forSync)
	{
		super.save(set, forSync);
		set.addInt("modeUp", this.modes[ConduitSide.UP.getID()].getID());
		set.addInt("modeDown", this.modes[ConduitSide.DOWN.getID()].getID());
		set.addInt("modeLeft", this.modes[ConduitSide.LEFT.getID()].getID());
		set.addInt("modeRight", this.modes[ConduitSide.RIGHT.getID()].getID());
		set.addBoolean("shouldSync", this.shouldSync);
		set.addInt("priorityUp", this.priorities[ConduitSide.UP.getID()]);
		set.addInt("priorityDown", this.priorities[ConduitSide.DOWN.getID()]);
		set.addInt("priorityLeft", this.priorities[ConduitSide.LEFT.getID()]);
		set.addInt("priorityRight", this.priorities[ConduitSide.RIGHT.getID()]);
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
		this.modes[ConduitSide.UP.getID()] = ConduitMode.getByID(set.getInt("modeUp"));
		this.modes[ConduitSide.DOWN.getID()] = ConduitMode.getByID(set.getInt("modeDown"));
		this.modes[ConduitSide.LEFT.getID()] = ConduitMode.getByID(set.getInt("modeLeft"));
		this.modes[ConduitSide.RIGHT.getID()] = ConduitMode.getByID(set.getInt("modeRight"));
		this.shouldSync = set.getBoolean("shouldSync");
		this.priorities[ConduitSide.UP.getID()] = set.getInt("priorityUp");
		this.priorities[ConduitSide.DOWN.getID()] = set.getInt("priorityDown");
		this.priorities[ConduitSide.LEFT.getID()] = set.getInt("priorityLeft");
		this.priorities[ConduitSide.RIGHT.getID()] = set.getInt("priorityRight");
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
					
					if (adjSide != null)
					{
						if (adjSide.getClass().equals(tileClass))
						{
							TileEntityConduit <?> adjConduit = (TileEntityConduit<?>) adjSide;
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

				if (adjSide != null)
				{
					if (this.canConnectAbstract(adjSide))
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
			}
		} else
		{
			TileEntityConduit <E> masterConduit = world.getTileEntity(this.getMaster().getX(), this.getMaster().getY(),
					tileClass);

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
			TileEntityConduit <?> masterConduit = world.getTileEntity(this.getMaster().getX(), this.getMaster().getY(),
					tileClass);

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
				TileEntityConduit <?> conduit = world.getTileEntity(relativeConduitX + this.getMaster().getX(),
						relativeConduitY + this.getMaster().getY(), tileClass);

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
			TileEntityConduit <?> masterConduit = world.getTileEntity(this.getMaster().getX(), this.getMaster().getY(),
					tileClass);

			if (masterConduit != null && masterConduit.getClass().equals(this.tileClass) && masterConduit.getMaster().equals(this.getMaster()))
			{
				masterConduit.addAdjacentInventories(world, relativeConduitX, relativeConduitY);
			} else if (masterConduit != null && masterConduit.getClass().equals(this.tileClass))
			{
				System.out.println("MASTER CONDUIT AT " + this.getMaster().getX() + ", " + this.getMaster().getY() +"IS LOOPING! THIS SHOULD NOT HAPPEN!");
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
				if (this.canConnectAbstract(changedTile))
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

				if (adjSide != null)
				{
					if (adjSide.getClass().equals(tileClass))
					{
						((TileEntityConduit <?>) adjSide).setMaster(new Pos2(adjSide.x, adjSide.y));
					}
				}
			}
		}
	}
	
	public abstract boolean canConnectAbstract(TileEntity tile);

	@Override
	public boolean canConnectTo(Pos2 pos, TileEntity tile)
	{
		if (tile != null)
		{
			if (tile.getClass().equals(tileClass))
			{
				return ((TileEntityConduit<?>) tile).getSideMode(
						RockSolidAPILib.posAndOffsetToConduitSide(pos, new Pos2(x, y))) != ConduitMode.DISABLED;
			}
			return canConnectAbstract(tile);
		} else
		{
			System.out.println("Null tile found rip");
		}
		return false;
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
			TileEntityConduit<?> master = world.getTileEntity(this.getMaster().getX(), this.getMaster().getY(),
					tileClass);
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

				if (adjSide != null)
				{
					// if its an item conduit with a different master
					if (adjSide.getClass().equals(tileClass)
							&& !((TileEntityConduit<?>) adjSide).getMaster().equals(this.getMaster())
							&& !((TileEntityConduit<?>) adjSide).isDead)
					{
						((TileEntityConduit<?>) adjSide).setMaster(this.getMaster());
					}
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

	public void setPriority(ConduitSide side, int priority)
	{
		if (!world.isClient())
		{
			this.priorities[side.getID()] = priority;
			this.shouldSync = true;
		}
	}
}
