package com.raphydaphy.rocksolid.tileentity.conduit;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.init.ModMisc;
import com.raphydaphy.rocksolid.tile.conduit.TileConduit;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Direction;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import javax.annotation.Nullable;
import java.util.*;

public abstract class TileEntityConduit extends TileEntity
{
	private static final String MASTER_X_KEY = "master_x";
	private static final String MASTER_Y_KEY = "master_y";

	private static final String NETWORK_LENGTH_KEY = "network_length";
	private static final String NETWORK_KEY = "network";
	private static final String NETWORK_POSITION_KEY = "network_position_";
	private static final String POSITION_SIDES_KEY = "position_sides_";
	private static final String POSITION_MODES_KEY = "position_modes_";
	private static final String SIDES_LENGTH_KEY = "sides_length";
	private static final String ENTRY_POSITION_X_KEY = "position_x";
	private static final String ENTRY_POSITION_Y_KEY = "position_y";

	private final int refreshRate;
	private final Class<? extends TileEntityConduit> classIn;
	private boolean networkChanged = false;
	private Pos2 master;
	private Pos2 lastMaster;
	private Map<Pos2, List<ConduitModeAndSide>> network = new HashMap<>();

	public TileEntityConduit(IWorld world, int x, int y, TileLayer layer, Class<? extends TileEntityConduit> classIn, int refreshRate)
	{
		super(world, x, y, layer);
		this.classIn = classIn;
		this.refreshRate = refreshRate;
	}

	// At this position, return all the possible sides
	public List<ConduitSide> getConduits(int x, int y)
	{
		List<ConduitSide> sides = new ArrayList<>();

		for (ConduitSide innerSide : ConduitSide.values())
		{
			if (network.containsKey(new Pos2(x + innerSide.offset.getX(), y + innerSide.offset.getY())))
			{
				sides.add(innerSide);
			}
		}

		return sides;
	}

	public TraverseInfo traversePath(TraverseInfo info, int curDist, int x, int y)
	{
		// Only the serverside-master stores the network
		if (!world.isClient() && isMaster())
		{
			// Main conduit position
			Pos2 main = new Pos2(x, y);

			// The new conduit position
			for (ConduitSide side : ConduitSide.values())
			{
				// Position 1 block away from start pos
				Pos2 sidePos = new Pos2(x + side.offset.getX(), y + side.offset.getY());
				ConduitModeAndSide pair = new ConduitModeAndSide(side, ConduitMode.ALL_INV);
				// If we have not explored this point and there is a conduit at it
				if (info.toExplore.contains(sidePos) && this.network.get(main).contains(pair) && this.network.get(main).get(this.network.get(main).indexOf(pair)).mode.shouldRender())
				{
					// We have now explored the point
					info.toExplore.remove(sidePos);

					// Traverse from this point forwards
					info = traversePath(info, curDist + 1, sidePos.getX(), sidePos.getY());
				}
				// This side has a connection on it
				else if (network.containsKey(main) && network.get(main).contains(pair) && network.get(main).get(network.get(main).indexOf(pair)).mode.canInsert())
				{
					if (!sidePos.equals(info.startInv))
					{
						// Add this position to the distance map
						info.distances.put(sidePos, curDist);
						if (info.closest == null || info.distances.get(info.closest) > curDist)
						{
							TileEntity thisInv = getTileEntityAt(sidePos.getX(), sidePos.getY());
							if (this.transfer(world, info.startInv.getX(), info.startInv.getY(), info.startSide, info.startTE, sidePos.getX(), sidePos.getY(), side, thisInv, true))
							{
								info.closest = sidePos;
								info.closestSide = side;
								info.closestTE = thisInv;
							}
						}
					}
				}
			}
		}
		return info;
	}

	@Override
	public void update(IGameInstance game)
	{
		super.update(game);

		if (!world.isClient() && isMaster())
		{
			if (world.getTotalTime() % refreshRate == 0)
			{
				for (Map.Entry<Pos2, List<ConduitModeAndSide>> entry1 : network.entrySet())
				{

					int x1 = entry1.getKey().getX();
					int y1 = entry1.getKey().getY();

					for (ConduitModeAndSide pair1 : entry1.getValue())
					{
						int sideX1 = x1 + pair1.side.offset.getX();
						int sideY1 = y1 + pair1.side.offset.getY();

						if (this.getMode(new Pos2(x1, y1), pair1.side, false).canExtract())
						{
							// find nearest inventory
							TraverseInfo info = new TraverseInfo(new Pos2(x1, y1), new Pos2(sideX1, sideY1));

							info = this.traversePath(info, 1, x1, y1);

							if (info.closest != null)
							{
								transfer(world, sideX1, sideY1, pair1.side, info.startTE, info.closest.getX(), info.closest.getY(), info.closestSide, info.closestTE, false);

							}
						}
					}

				}
			}
		}
	}

	public TileEntity getTileEntityAt(int x, int y)
	{
		TileState state = world.getState(x, y);
		if (state.getTile() instanceof MultiTile)
		{
			Pos2 main = ((MultiTile) state.getTile()).getMainPos(x, y, state);
			return world.getTileEntity(main.getX(), main.getY());
		} else
		{
			return world.getTileEntity(x, y);
		}
	}

	public abstract boolean transfer(IWorld world, int x1, int y1, ConduitSide side1, TileEntity tile1, int x2, int y2, ConduitSide side2, TileEntity state2, boolean simulate);

	public void printNetwork()
	{
		if (!world.isClient())
		{
			RockSolid.getLogger().info("===========================================");

			RockSolid.getLogger().info("Master Position: " + this.getMaster().getX() + ", " + this.getMaster().getY());

			RockSolid.getLogger().info("Size: " + network.size());
			RockSolid.getLogger().info("Map: ");
			for (Map.Entry<Pos2, List<ConduitModeAndSide>> entry : network.entrySet())
			{
				RockSolid.getLogger().info("  @ " + entry.getKey().getX() + ", " + entry.getKey().getY());
				for (ConduitModeAndSide pair : entry.getValue())
				{
					RockSolid.getLogger().info("    Connection on side " + pair.side.toString() + " with mode " + pair.mode.toString());
				}
			}
		}

		RockSolid.getLogger().info("===========================================");
	}

	public Pos2 getMaster()
	{
		return this.master;
	}

	public TileEntityConduit getMasterTE(IWorld world)
	{
		if (this.master != null)
		{
			return world.getTileEntity(layer, this.getMaster().getX(), this.getMaster().getY(), classIn);
		}
		return null;
	}

	public void setMode(ConduitSide side, ConduitMode mode)
	{
		this.setMode(new Pos2(this.x, this.y), side, mode);
	}

	private void setMode(Pos2 pos, ConduitSide side, ConduitMode mode)
	{
		if (!world.isClient())
		{
			if (isMaster())
			{
				if (this.network.containsKey(pos))
				{
					// one day someone will find this and think im an absolute idiot
					ConduitModeAndSide pair = new ConduitModeAndSide(side, mode);
					if (this.network.get(pos).contains(pair))
					{
						this.network.get(pos).remove(pair);
						this.network.get(pos).add(pair);

						this.networkChanged = true;
					}
				}
			} else
			{
				TileEntityConduit master = getMasterTE(world);

				if (master != null)
				{
					master.setMode(pos, side, mode);
				}
			}
		}
	}

	@Nullable
	public ConduitMode getMode(Pos2 pos, ConduitSide side, boolean noMaster)
	{
		if (side != null)
		{
			if ((noMaster && this.master == null) || isMaster())
			{
				ConduitModeAndSide pair = new ConduitModeAndSide(side, ConduitMode.ALL_INV);
				if (this.network.containsKey(pos))
				{
					if (this.network.get(pos).contains(pair))
					{
						return this.network.get(pos).get(this.network.get(pos).indexOf(pair)).mode;
					}
				}
			} else
			{
				TileEntityConduit master = getMasterTE(world);

				if (master != null)
				{
					return master.getMode(pos, side, noMaster);
				}
			}
		}
		return null;
	}

	private void rebuildNetwork(IWorld world)
	{
		if (isMaster())
		{
			Map<Pos2, List<ConduitModeAndSide>> newNetwork = new HashMap<>();
			for (Pos2 conduit : this.network.keySet())
			{
				Tile unchecked = world.getState(ModMisc.CONDUIT_LAYER, conduit.getX(), conduit.getY()).getTile();
				if (unchecked instanceof TileConduit)
				{
					TileConduit tileConduit = (TileConduit) unchecked;
					List<ConduitModeAndSide> connections = new ArrayList<>();
					for (ConduitSide side : ConduitSide.values())
					{

						Pos2 sidePos = new Pos2(conduit.getX() + side.offset.getX(), conduit.getY() + side.offset.getY());
						TileEntityConduit te = world.getTileEntity(ModMisc.CONDUIT_LAYER, sidePos.getX(), sidePos.getY(), classIn);
						if (tileConduit.canConnect(world, sidePos, null, world.getState(conduit.getX() + side.offset.getX(), conduit.getY() + side.offset.getY())))
						{
							ConduitModeAndSide pair = new ConduitModeAndSide(side, ConduitMode.ALL_INV);
							if (this.network.get(conduit).contains(pair))
							{
								pair = this.network.get(conduit).get(this.network.get(conduit).indexOf(pair));
							}
							connections.add(pair);
						} else if (te != null && te.getMaster() != null)
						{
							ConduitModeAndSide pair = new ConduitModeAndSide(side, ConduitMode.ALL_CONDUIT);
							if (this.network.get(conduit).contains(pair))
							{
								pair = this.network.get(conduit).get(this.network.get(conduit).indexOf(pair));
							}
							connections.add(pair);
						}
					}
					newNetwork.put(conduit, connections);
				}
			}
			this.network = newNetwork;
			this.networkChanged = true;
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
				List<ConduitModeAndSide> list = new ArrayList<>();

				for (ConduitSide side : ConduitSide.values())
				{
					Pos2 pos = new Pos2(conduit.getX() + side.offset.getX(), conduit.getY() + side.offset.getY());

					// There is a conduit at this position
					if (this.network.containsKey(pos))
					{
						list.add(new ConduitModeAndSide(side, ConduitMode.ALL_CONDUIT));
						this.addConnection(world, new NetworkConnection(pos, side.getOpposite(), ConduitMode.ALL_CONDUIT));
					}
				}
				this.network.put(conduit, list);
				this.networkChanged = true;
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

				for (ConduitSide side : ConduitSide.values())
				{
					Pos2 pos = new Pos2(conduit.getX() + side.offset.getX(), conduit.getY() + side.offset.getY());
					if (this.network.containsKey(pos))
					{
						this.removeConnection(world, new NetworkConnection(pos.getX(), pos.getY(), side.getOpposite(), ConduitMode.ALL_CONDUIT), true);
					}
				}
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
					ConduitModeAndSide pair = new ConduitModeAndSide(connection.side, connection.mode);
					if (!this.network.get(connection.getConduitPos()).contains(pair))
					{
						this.network.get(connection.getConduitPos()).add(pair);
						this.networkChanged = true;
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
	 * the client-side or the connection is a
	 * conduit and removeConduit is not true
	 */
	public boolean removeConnection(IWorld world, NetworkConnection connection, boolean removeConduit)
	{
		if (!world.isClient())
		{
			if (isMaster())
			{
				if (this.network.containsKey(connection.getConduitPos()))
				{
					ConduitModeAndSide pair = new ConduitModeAndSide(connection.side, connection.mode);

					if (this.network.get(connection.getConduitPos()).contains(pair))
					{
						ConduitModeAndSide existing = this.network.get(connection.getConduitPos()).get(this.network.get(connection.getConduitPos()).indexOf(pair));
						if (existing.mode.isInv() || removeConduit)
						{
							this.network.get(connection.getConduitPos()).remove(pair);
							this.networkChanged = true;
							return true;
						}
					}
				}
			} else
			{
				TileEntityConduit master = this.getMasterTE(world);

				if (master != null)
				{
					return master.removeConnection(world, connection, removeConduit);
				}
			}
		}
		return false;
	}

	// Last chance to move things from old master to new
	public void setMasterRecursivly(IWorld world, Pos2 newMaster)
	{
		if (!world.isClient() && getMaster() != null && !newMaster.equals(getMaster()))
		{
			TileEntityConduit oldMaster = this.getMasterTE(world);
			this.master = newMaster;
			this.addConduit(world, new Pos2(this.x, this.y));
			Pos2 thisPos = new Pos2(x, y);
			for (ConduitSide side : ConduitSide.values())
			{
				TileEntityConduit conduit = world.getTileEntity(layer, x + side.offset.getX(), y + side.offset.getY(), classIn);

				if (conduit != null && conduit.getMaster() != null && !conduit.getMaster().equals(newMaster))
				{
					this.addConduit(world, new Pos2(x + side.offset.getX(), y + side.offset.getY()));
					if (oldMaster.getMode(thisPos, side, true) != null)
					{
						NetworkConnection mainToSide = new NetworkConnection(x, y, side, oldMaster.getMode(thisPos, side, true));
						this.removeConnection(world, mainToSide, true);
						this.addConnection(world, mainToSide);

						NetworkConnection sideToMain = new NetworkConnection(x + side.offset.getX(), y + side.offset.getY(), side.getOpposite(), oldMaster.getMode(new Pos2(x + side.offset.getX(), y + side.offset.getY()), side.getOpposite(), true));
						this.removeConnection(world, sideToMain, true);
						this.addConnection(world, sideToMain);
					}
					conduit.setMasterRecursivly(world, newMaster);
				} else if (((TileConduit) world.getState(ModMisc.CONDUIT_LAYER, x, y).getTile()).canConnect(world, new Pos2(this.x + side.offset.getX(), this.y + side.offset.getY()), null, world.getState(this.x + side.offset.getX(), this.y + side.offset.getY())) && oldMaster.getMode(thisPos, side, true) != null)
				{
					NetworkConnection mainToSide = new NetworkConnection(x, y, side, oldMaster.getMode(thisPos, side, true));
					this.addConnection(world, mainToSide);

				}
			}
		}
	}

	public boolean isMaster()
	{
		return master != null && getMaster().equals(new Pos2(this.x, this.y));
	}

	public void doPlace(IWorld world, int x, int y, TileLayer layer, ItemInstance instance, AbstractEntityPlayer placer)
	{
		if (!world.isClient())
		{
			this.master = new Pos2(x, y);

			// First loop determines the master
			for (ConduitSide side : ConduitSide.values())
			{
				TileEntityConduit conduit = world.getTileEntity(layer, x + side.offset.getX(), y + side.offset.getY(), classIn);

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
				TileState mainState = world.getState(TileLayer.MAIN, x + side.offset.getX(), y + side.offset.getY());
				Pos2 pos = new Pos2(x + side.offset.getX(), y + side.offset.getY());
				ConduitMode existing = this.getMode(pos, side.getOpposite(), false);
				if (((TileConduit) world.getState(layer, x, y).getTile()).canConnect(world, pos, null, mainState))
				{
					this.addConnection(world, new NetworkConnection(new Pos2(x, y), ConduitSide.getByOffset(side.offset.getX(), side.offset.getY()), ConduitMode.ALL_INV));
				} else if (existing != null && existing.isConduit())
				{
					this.addConnection(world, new NetworkConnection(new Pos2(x, y), ConduitSide.getByOffset(side.offset.getX(), side.offset.getY()), existing));
				}
			}
		}
	}

	public void onRemoved(IWorld world, int x, int y, TileLayer layer)
	{
		if (!world.isClient())
		{

			for (ConduitSide side : ConduitSide.values())
			{
				// It dosen't matter if it is in the network or not, removeFromNetwork handles this
				this.removeConnection(world, new NetworkConnection(new Pos2(x, y), side, ConduitMode.ALL_INV), true);

			}
			this.master = null;

			// Second loop to change all surrounding conduits' masters
			for (ConduitSide side : ConduitSide.values())
			{
				TileEntityConduit conduit = world.getTileEntity(layer, x + side.offset.getX(), y + side.offset.getY(), classIn);

				if (conduit != null)
				{
					conduit.removeConduit(world, new Pos2(x, y));
					conduit.setMasterRecursivly(world, new Pos2(x + side.offset.getX(), y + side.offset.getY()));
					conduit.rebuildNetwork(world);
				}
			}
		}

	}

	public void onChangedAround(IWorld world, int x, int y, TileLayer layer, int changedX, int changedY, TileLayer changedLayer)
	{
		if (!world.isClient())
		{
			if (changedLayer.equals(this.layer))
			{
				// tbh we don't need to do anything here since this is handled in onRemoved
			} else if (changedLayer.equals(TileLayer.MAIN))
			{
				// see if the removed tile was part of the network

				TileState changedState = world.getState(changedX, changedY);
				ConduitSide side = ConduitSide.getByOffset(changedX - x, changedY - y);
				if (side != null)
				{
					NetworkConnection changedConnection = new NetworkConnection(x, y, side, ConduitMode.ALL_INV);

					// Remove the position from the network
					if (changedState.getTile().isAir())
					{
						this.removeConnection(world, changedConnection, false);
					}
					// Add the position to the network if it is a valid connection
					else
					{
						if (((TileConduit) world.getState(layer, x, y).getTile()).canConnect(world, new Pos2(changedX, changedY), null, changedState))
						{
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

		if (isMaster())
		{
			// The size of the network
			set.addInt(NETWORK_LENGTH_KEY, network.size());

			// Store the entire network on this set
			DataSet networkSet = new DataSet();

			// Current entry number
			int entryNum = 0;

			for (Map.Entry<Pos2, List<ConduitModeAndSide>> entry : this.network.entrySet())
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
					entrySet.addInt(POSITION_SIDES_KEY + side, entry.getValue().get(side).side.id);
					entrySet.addInt(POSITION_MODES_KEY + side, entry.getValue().get(side).mode.id);
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
		this.network.clear();

		DataSet networkSet = set.getDataSet(NETWORK_KEY);
		for (int entry = 0; entry < set.getInt(NETWORK_LENGTH_KEY); entry++)
		{
			DataSet entrySet = networkSet.getDataSet(NETWORK_POSITION_KEY + entry);

			List<ConduitModeAndSide> sides = new ArrayList<>();

			for (int side = 0; side < entrySet.getInt(SIDES_LENGTH_KEY); side++)
			{
				sides.add(new ConduitModeAndSide(ConduitSide.getByID(entrySet.getInt(POSITION_SIDES_KEY + side)), ConduitMode.getByID(entrySet.getInt(POSITION_MODES_KEY + side))));
			}

			Pos2 pos = new Pos2(entrySet.getInt(ENTRY_POSITION_X_KEY), entrySet.getInt(ENTRY_POSITION_Y_KEY));
			network.put(pos, sides);
		}

	}

	@Override
	protected boolean needsSync()
	{
		return master != lastMaster || this.networkChanged || super.needsSync();
	}

	@Override
	public void onSync()
	{
		super.onSync();
		this.lastMaster = this.master;
		this.networkChanged = false;
	}

	public enum ConduitMode
	{
		ALL_INV(0), INPUT_INV(1), OUTPUT_INV(2), NONE_INV(3), ALL_CONDUIT(4), NONE_CONDUIT(5);

		public final int id;
		public final IResourceName name;

		ConduitMode(int id)
		{
			this.id = id;
			this.name = RockSolid.createRes("conduit_mode." + this.toString().toLowerCase());
		}

		public static List<ConduitMode> getInvModes()
		{
			return Arrays.asList(ALL_INV, INPUT_INV, OUTPUT_INV, NONE_INV);
		}

		public static List<ConduitMode> getConduitModes()
		{
			return Arrays.asList(ALL_CONDUIT, NONE_CONDUIT);
		}

		public static ConduitMode getByID(int id)
		{
			for (ConduitMode mode : ConduitMode.values())
			{
				if (mode.id == id)
				{
					return mode;
				}
			}
			return null;
		}

		public boolean canInsert()
		{
			return this == ALL_INV || this == INPUT_INV;
		}

		public boolean canExtract()
		{
			return this == ALL_INV || this == OUTPUT_INV;
		}

		public boolean isInv()
		{
			return canExtract() || canInsert();
		}

		public boolean isConduit()
		{
			return this == ALL_CONDUIT || this == NONE_CONDUIT;
		}

		public boolean shouldRender()
		{
			return this != NONE_INV && this != NONE_CONDUIT;
		}
	}

	public enum ConduitSide
	{
		UP(0, new Pos2(0, 1), Direction.UP), DOWN(1, new Pos2(0, -1), Direction.DOWN), LEFT(2, new Pos2(-1, 0), Direction.LEFT), RIGHT(3, new Pos2(1, 0), Direction.RIGHT);

		public final int id;
		public final Pos2 offset;
		public final Direction direction;

		ConduitSide(int id, Pos2 offset, Direction dir)
		{
			this.id = id;
			this.offset = offset;
			this.direction = dir;
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

		@Nullable
		public static ConduitSide getByDirection(Direction dir)
		{
			for (ConduitSide side : ConduitSide.values())
			{
				if (side.direction.equals(dir))
				{
					return side;
				}
			}
			return null;
		}

		public ConduitSide getOpposite()
		{
			switch (this)
			{
				case UP:
					return DOWN;
				case DOWN:
					return UP;
				case LEFT:
					return RIGHT;
				case RIGHT:
					return LEFT;
			}
			return null;
		}
	}

	public static class NetworkConnection
	{
		private final int conduitX;
		private final int conduitY;
		private final ConduitSide side;
		private final ConduitMode mode;

		public NetworkConnection(Pos2 conduitPos, ConduitSide side, ConduitMode mode)
		{
			this(conduitPos.getX(), conduitPos.getY(), side, mode);
		}

		public NetworkConnection(int conduitX, int conduitY, ConduitSide side, ConduitMode mode)
		{
			this.conduitX = conduitX;
			this.conduitY = conduitY;
			this.side = side;
			this.mode = mode;
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

		public ConduitMode getMode()
		{
			return this.mode;
		}

		@Override
		public int hashCode()
		{
			int hash = 1;
			hash = hash * 17 + side.id;
			hash = hash * 31 + getConduitPos().hashCode();
			return hash;
		}

		// NOTE: equals and hashcode DO NOT take into account the mode
		// this is completely intentional

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
			return this.getConduitX() == entry2.getConduitX() && this.getConduitY() == entry2.getConduitY() && this.getSide().id == entry2.getSide().id;
		}
	}

	private class TraverseInfo
	{
		public final List<Pos2> toExplore;
		public final Map<Pos2, Integer> distances;
		public final Pos2 startInv;
		public final ConduitSide startSide;
		public final TileEntity startTE;
		public Pos2 closest;
		public ConduitSide closestSide;
		public TileEntity closestTE;

		public TraverseInfo(Pos2 startConduit, Pos2 startInv)
		{
			toExplore = new ArrayList<>();
			toExplore.addAll(network.keySet());

			toExplore.remove(startConduit);
			this.startInv = startInv;

			distances = new HashMap<>();

			startTE = getTileEntityAt(startInv.getX(), startInv.getY());

			startSide = ConduitSide.getByOffset(startInv.getX() - startConduit.getX(), startInv.getY() - startConduit.getY());

			closest = null;
			closestSide = null;
		}
	}

	public class ConduitModeAndSide
	{
		public ConduitSide side;
		public ConduitMode mode;

		public ConduitModeAndSide(ConduitSide side, ConduitMode mode)
		{
			this.side = side;
			this.mode = mode;
		}

		// NOTE: equals and hashcode DO NOT take into account the mode
		// this is completely intentional

		@Override
		public int hashCode()
		{
			int hash = 1;
			hash = hash * 17 + side.id;
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

			ConduitModeAndSide entry2 = (ConduitModeAndSide) other;
			return this.side.id == entry2.side.id;
		}
	}

	@Override
	public boolean doesTick()
	{
		return true;
	}

}
