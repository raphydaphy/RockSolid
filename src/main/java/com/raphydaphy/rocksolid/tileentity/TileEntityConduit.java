package com.raphydaphy.rocksolid.tileentity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.init.ModMisc;
import com.raphydaphy.rocksolid.tile.conduit.TileConduit;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public abstract class TileEntityConduit extends TileEntity
{
	public static String MASTER_X_KEY = "master_x";
	public static String MASTER_Y_KEY = "master_y";

	public static String NETWORK_LENGTH_KEY = "network_length";
	public static String NETWORK_KEY = "network";
	public static String NETWORK_POSITION_KEY = "network_position_";
	public static String POSITION_SIDES_KEY = "position_sides_";
	public static String SIDES_LENGTH_KEY = "sides_length";
	public static String ENTRY_POSITION_X_KEY = "position_x";
	public static String ENTRY_POSITION_Y_KEY = "position_y";

	private Pos2 master;
	private Pos2 lastMaster;

	private Map<Pos2, List<ConduitSide>> network = new HashMap<>();

	public TileEntityConduit(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
	}

	@Override
	public void update(IGameInstance game)
	{
		super.update(game);

		if (!game.getWorld().isClient() && isMaster())
		{
			if (game.getWorld().getTotalTime() % 200 == 0)
			{
				RockSolid.getLogger().info("===========================================");

				RockSolid.getLogger()
						.info("Master Position: " + this.getMaster().getX() + ", " + this.getMaster().getY());

				RockSolid.getLogger().info("Size: " + network.size());
				RockSolid.getLogger().info("Map: ");
				for (Map.Entry<Pos2, List<ConduitSide>> entry : network.entrySet())
				{
					RockSolid.getLogger().info("  @ " + entry.getKey().getX() + ", " + entry.getKey().getY());
					for (ConduitSide side : entry.getValue())
					{
						RockSolid.getLogger().info("    Connection on side " + side.toString());
					}
				}

				RockSolid.getLogger().info("===========================================");
			}
			else if (!this.getMasterTE(game.getWorld()).isMaster())
			{
				System.out.println("SOMETHING IS SERIOUSLY BROKEN");
			}
		}
	}

	public Pos2 getMaster()
	{
		return this.master;
	}

	public TileEntityConduit getMasterTE(IWorld world)
	{
		if (this.master != null)
		{
			return world.getTileEntity(layer, this.getMaster().getX(), this.getMaster().getY(),
					TileEntityConduit.class);
		}
		return null;
	}

	public void rebuildNetwork(IWorld world)
	{
		if (isMaster())
		{
			Map<Pos2, List<ConduitSide>> newNetwork = new HashMap<>();
			for (Pos2 conduit : this.network.keySet())
			{
				Tile unchecked = world.getState(ModMisc.CONDUIT_LAYER, conduit.getX(), conduit.getY()).getTile();
				if (unchecked instanceof TileConduit)
				{
					TileConduit tileConduit = (TileConduit) unchecked;
					List<ConduitSide> connections = new ArrayList<>();
					for (ConduitSide side : ConduitSide.values())
					{
						if (tileConduit.canConnect(world,
								new Pos2(conduit.getX() + side.getOffset().getX(),
										conduit.getY() + side.getOffset().getY()),
								null, world.getState(conduit.getX() + side.getOffset().getX(),
										conduit.getY() + side.getOffset().getY())))
						{
							connections.add(side);
						}
					}
					newNetwork.put(conduit, connections);
				} else
				{
					System.out.println("THIS SHOULD NOT HAPPEN");
				}
			}
			this.network = newNetwork;
		} else
		{
			TileEntityConduit master = this.getMasterTE(world);

			if (master != null)
			{
				master.rebuildNetwork(world);
			}
		}
	}

	/*
	 * returns false if the method is used on
	 * the client or if the conduit was already
	 * in the network
	 */
	public boolean addConduit(IWorld world, Pos2 conduit)
	{
		if (isMaster())
		{
			if (!this.network.containsKey(conduit))
			{
				this.network.put(conduit, new ArrayList<>());
				return true;
			}
		} else
		{
			TileEntityConduit master = this.getMasterTE(world);

			if (master != null)
			{
				return master.addConduit(world, conduit);
			}
		}
		return false;
	}

	/*
	 * returns false if the method is run on
	 * the client or if the conduit was never
	 * in the network
	 */
	public boolean removeConduit(IWorld world, Pos2 conduit)
	{
		if (isMaster())
		{
			if (this.network.containsKey(conduit))
			{
				this.network.remove(conduit);
				return true;
			}
		} else
		{
			TileEntityConduit master = this.getMasterTE(world);

			if (master != null)
			{
				return master.removeConduit(world, conduit);
			}
		}
		return false;
	}

	/*
	 * Returns false if the entry already existed
	 * or the master tile-entity is null, or the
	 * method is used on the client-side
	 */
	public boolean addConnection(IWorld world, NetworkConnection connection)
	{
		if (!world.isClient())
		{
			if (isMaster())
			{
				if (this.network.containsKey(connection.getConduitPos()))
				{
					if (!this.network.get(connection.getConduitPos()).contains(connection.side))
					{
						this.network.get(connection.getConduitPos()).add(connection.side);
						return true;
					}
				}
			} else
			{
				TileEntityConduit master = this.getMasterTE(world);

				if (master != null)
				{
					return master.addConnection(world, connection);
				}
			}
		}
		return false;
	}

	/*
	 * Returns false if the entry was not found
	 * in the network or the method is used on
	 * the client-side
	 */
	public boolean removeConnection(IWorld world, NetworkConnection connection)
	{
		if (!world.isClient())
		{
			if (isMaster())
			{
				if (this.network.containsKey(connection.getConduitPos()))
				{
					if (this.network.get(connection.getConduitPos()).contains(connection.side))
					{
						this.network.get(connection.getConduitPos()).remove(connection.side);
						return true;
					}
				}
			} else
			{
				TileEntityConduit master = this.getMasterTE(world);

				if (master != null)
				{
					return master.removeConnection(world, connection);
				}
			}
		}
		return false;
	}

	public void setMasterRecursivly(IWorld world, Pos2 newMaster)
	{
		if (!world.isClient())
		{
			if (getMaster() != null && !newMaster.equals(getMaster()))
			{
				this.master = newMaster;
				this.addConduit(world, new Pos2(this.x, this.y));
				for (ConduitSide side : ConduitSide.values())
				{
					TileEntityConduit conduit = world.getTileEntity(layer, x + side.getOffset().getX(),
							y + side.getOffset().getY(), TileEntityConduit.class);

					if (conduit != null && conduit.getMaster() != null && !conduit.getMaster().equals(newMaster))
					{
						this.addConduit(world, new Pos2(x + side.getOffset().getX(), y + side.getOffset().getY()));
						conduit.setMasterRecursivly(world, newMaster);
					}
				}
			}
		}
	}

	public boolean isMaster()
	{
		return master != null && getMaster().equals(new Pos2(this.x, this.y));
	}

	public void onAdded(IWorld world, int x, int y, TileLayer layer)
	{
		if (!world.isClient())
		{
			System.out.println("ADDED");
			this.master = new Pos2(x, y);

			// First loop determines the master
			for (ConduitSide side : ConduitSide.values())
			{
				TileEntityConduit conduit = world.getTileEntity(layer, x + side.getOffset().getX(),
						y + side.getOffset().getY(), TileEntityConduit.class);

				if (conduit != null)
				{
					if (this.isMaster())
					{
						this.master = conduit.getMaster();
					} else
					{
						conduit.setMasterRecursivly(world, this.getMaster());
						conduit.rebuildNetwork(world);
					}
				}
			}

			// Add this conduit to the network
			this.addConduit(world, new Pos2(x, y));

			// Second loop adds connected inventories
			for (ConduitSide side : ConduitSide.values())
			{
				TileState state = world.getState(TileLayer.MAIN, x + side.getOffset().getX(),
						y + side.getOffset().getY());
				Pos2 pos = new Pos2(x + side.getOffset().getX(), y + side.getOffset().getY());
				if (((TileConduit) world.getState(layer, x, y).getTile()).canConnect(world, pos, null, state))
				{
					System.out.println("Adding new connection for state " + state);
					this.addConnection(world, new NetworkConnection(new Pos2(x, y),
							ConduitSide.getByOffset(side.getOffset().getX(), side.getOffset().getY())));
				}
			}
		}
	}

	public void onRemoved(IWorld world, int x, int y, TileLayer layer)
	{
		if (!world.isClient())
		{
			System.out.println("REMOVED");

			for (ConduitSide side : ConduitSide.values())
			{
				// It dosen't matter if it is in the network or not, removeFromNetwork handles this
				this.removeConnection(world, new NetworkConnection(new Pos2(x, y), side));

			}
			this.master = null;

			// Second loop to change all surrounding conduits' masters
			for (ConduitSide side : ConduitSide.values())
			{
				TileEntityConduit conduit = world.getTileEntity(layer, x + side.getOffset().getX(),
						y + side.getOffset().getY(), TileEntityConduit.class);

				if (conduit != null)
				{
					conduit.removeConduit(world, new Pos2(x, y));
					conduit.setMasterRecursivly(world, new Pos2(x + side.offset.getX(), y + side.offset.getY()));
					conduit.rebuildNetwork(world);
				}
			}
		}

	}

	public void onChangedAround(IWorld world, int x, int y, TileLayer layer, int changedX, int changedY,
			TileLayer changedLayer)
	{
		if (!world.isClient())
		{
			if (changedLayer.equals(this.layer))
			{
				// tbh we don't need to do anything here since this is handled in onRemoved
				System.out.println("CONDUIT CHANGED AROUND");
			} else
			{
				// see if the removed tile was part of the network
				System.out.println("CHANGED AROUND ON OTHER LAYER: " + world.getState(changedX, changedY));

				TileState changedState = world.getState(changedX, changedY);
				ConduitSide side = ConduitSide.getByOffset(changedX - x, changedY - y);
				if (side != null)
				{
					NetworkConnection changedConnection = new NetworkConnection(x, y, side);
					// Remove the position from the network
					if (changedState.getTile().isAir())
					{
						this.removeConnection(world, changedConnection);
					}
					// Add the position to the network if it is a valid connection
					else
					{
						if (((TileConduit) world.getState(layer, x, y).getTile()).canConnect(world,
								new Pos2(changedX, changedY), null, changedState))
						{
							System.out.println("Adding new connection for state " + changedState);
							this.addConnection(world, changedConnection);
						}
					}
				}
			}
		}
	}

	@Override
	public void save(DataSet set, boolean forSync)
	{
		super.save(set, forSync);
		if (master != null)
		{
			set.addInt(MASTER_X_KEY, this.master.getX());
			set.addInt(MASTER_Y_KEY, this.master.getY());
		}

		if (isMaster() && !forSync && false)
		{
			// The size of the network
			set.addInt(NETWORK_LENGTH_KEY, network.size());

			// Store the entire network on this set
			DataSet networkSet = new DataSet();

			// Current entry number
			int entryNum = 0;
 
			for (Map.Entry<Pos2, List<ConduitSide>> entry : this.network.entrySet())
			{
				// Save the pos2 and sides in a DataSet
				DataSet entrySet = new DataSet();

				// Save the Pos2 X and Y of the conduit
				entrySet.addInt(ENTRY_POSITION_X_KEY, entry.getKey().getX());
				entrySet.addInt(ENTRY_POSITION_Y_KEY, entry.getKey().getY());

				// How many sides have a connection?
				entrySet.addInt(SIDES_LENGTH_KEY, entry.getValue().size());

				// Loop through each side connected to the conduit
				for (int side = 0; side < entry.getValue().size(); side++)
				{
					// Add the side to the entry for the conduit
					entrySet.addInt(POSITION_SIDES_KEY + side, entry.getValue().get(side).getID());
				}

				// Add the conduit entry set to the main dataset
				networkSet.addDataSet(NETWORK_POSITION_KEY + entryNum, entrySet);

				// Increase entry number
				entryNum++;
			}

			set.addDataSet(NETWORK_KEY, networkSet);
		}
	}

	@Override
	public void load(DataSet set, boolean forSync)
	{
		super.load(set, forSync);
		if (set.hasKey(MASTER_X_KEY) && set.hasKey(MASTER_Y_KEY))
		{
			this.master = new Pos2(set.getInt(MASTER_X_KEY), set.getInt(MASTER_Y_KEY));
		}
		if (!forSync)
		{/*
			this.network = new HashMap<>();
			
			for (int entry = 0; entry < set.getInt(NETWORK_LENGTH_KEY); entry++)
			{
				DataSet entrySet = set.getDataSet(NETWORK_POSITION_KEY + entry);
				
				List<ConduitSide> sides = new ArrayList<>();
				
				for (int side = 0; side < entrySet.getInt(SIDES_LENGTH_KEY); side++)
				{
					sides.add(ConduitSide.getByID(entrySet.getInt(POSITION_SIDES_KEY + side)));
				}
				
				Pos2 pos = new Pos2(entrySet.getInt(ENTRY_POSITION_X_KEY), entrySet.getInt(ENTRY_POSITION_Y_KEY));
				
				network.put(pos, sides);
			}
			
			System.out.println("loaded:");
			this.update(RockBottomAPI.getGame());*/
		}
	}

	@Override
	protected boolean needsSync()
	{
		return master != lastMaster || super.needsSync();
	}

	@Override
	public void onSync()
	{
		super.onSync();
		this.lastMaster = this.master;
	}

	public class NetworkConnection
	{
		private final int conduitX;
		private final int conduitY;
		private final ConduitSide side;

		public NetworkConnection(Pos2 conduitPos, ConduitSide side)
		{
			this(conduitPos.getX(), conduitPos.getY(), side);
		}

		public NetworkConnection(int conduitX, int conduitY, ConduitSide side)
		{
			this.conduitX = conduitX;
			this.conduitY = conduitY;
			this.side = side;
		}

		public Pos2 getConduitPos()
		{
			return new Pos2(this.conduitX, this.conduitY);
		}

		public int getConduitX()
		{
			return this.conduitX;
		}

		public int getConduitY()
		{
			return this.conduitY;
		}

		public ConduitSide getSide()
		{
			return this.side;
		}

		@Override
		public int hashCode()
		{
			int hash = 1;
			hash = hash * 17 + side.id;
			hash = hash * 31 + getConduitPos().hashCode();
			return hash;
		}

		@Override
		public boolean equals(Object other)
		{
			if (this == other)
			{
				return true;
			}
			if (other == null || this.getClass() != other.getClass())
			{
				return false;
			}

			NetworkConnection entry2 = (NetworkConnection) other;
			return this.getConduitX() == entry2.getConduitX() && this.getConduitY() == entry2.getConduitY()
					&& this.getSide().getID() == entry2.getSide().getID();
		}
	}

	public enum ConduitSide
	{
		UP(0, new Pos2(0, 1)), DOWN(1, new Pos2(0, -1)), LEFT(2, new Pos2(-1, 0)), RIGHT(3, new Pos2(1, 0));

		private int id;
		private Pos2 offset;

		ConduitSide(int id, Pos2 offset)
		{
			this.id = id;
			this.offset = offset;
		}

		public int getID()
		{
			return id;
		}

		public Pos2 getOffset()
		{
			return offset;
		}

		@Nullable
		public static ConduitSide getByID(int ID)
		{
			for (ConduitSide side : ConduitSide.values())
			{
				if (side.id == ID)
				{
					return side;
				}
			}
			return null;
		}

		@Nullable
		public static ConduitSide getByOffset(int offX, int offY)
		{
			for (ConduitSide side : ConduitSide.values())
			{
				if (side.offset.getX() == offX && side.offset.getY() == offY)
				{
					return side;
				}
			}
			return null;
		}
	}

}
