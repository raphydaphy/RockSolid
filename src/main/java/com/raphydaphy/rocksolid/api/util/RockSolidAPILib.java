package com.raphydaphy.rocksolid.api.util;

import com.raphydaphy.rocksolid.api.RockSolidAPI;
import com.raphydaphy.rocksolid.api.fluid.Fluid;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.entity.IInventoryHolder;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Direction;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;

public class RockSolidAPILib
{
	public static IResourceName makeInternalRes(String name)
	{
		return RockBottomAPI.createRes(RockSolidAPI.RockSolid, name);
	}

	public static Fluid bucketMetaToFluid(int meta)
	{
		for (int curFluid = 0; curFluid < Fluid.values().length; curFluid++)
		{
			if (Fluid.values()[curFluid].getBucketMeta() == meta)
			{
				return Fluid.values()[curFluid];
			}
		}
		return Fluid.EMPTY;
	}

	public static TileEntity getTileFromConduitSide(Pos2 center, ConduitSide side, IWorld world)
	{
		Pos2 tilePos = conduitSideToPos(center, side);

		return getTileFromPos(tilePos.getX(), tilePos.getY(), world);
	}

	public static TileEntity getTileFromPos(int x, int y, IWorld world)
	{
		Tile realTileDown = world.getState(x, y).getTile();
		if (!realTileDown.isAir())
		{
			if (realTileDown instanceof MultiTile)
			{
				Pos2 main = ((MultiTile) realTileDown).getMainPos(x, y, world.getState(x, y));
				return world.getTileEntity(main.getX(), main.getY(), TileEntity.class);
			} else
			{
				return world.getTileEntity(x, y);
			}
		}
		return null;
	}

	public static <T extends TileEntity> TileEntity getTileFromPos(int x, int y, IWorld world, final Class<T> tileClass)
	{
		Tile realTileDown = world.getState(x, y).getTile();

		if (!realTileDown.isAir())
		{
			if (realTileDown instanceof MultiTile)
			{
				Pos2 main = ((MultiTile) realTileDown).getMainPos(x, y, world.getState(x, y));
				return world.getTileEntity(main.getX(), main.getY(), tileClass);
			} else
			{
				return world.getTileEntity(x, y, tileClass);
			}
		}
		return null;
	}

	public static Pos2 conduitSideToPos(Pos2 center, ConduitSide side)
	{
		Pos2 offset = side.getOffset();
		return center.add(offset.getX(), offset.getY());
	}

	public static ConduitSide posAndOffsetToConduitSide(Pos2 center, Pos2 side)
	{
		Pos2 difference = center.add(-side.getX(), -side.getY());
		difference.set(-difference.getX(), -difference.getY());
		if (difference.getY() == -1)
		{
			return ConduitSide.DOWN;
		} else if (difference.getY() == 1)
		{
			return ConduitSide.UP;
		} else if (difference.getX() == 1)
		{
			return ConduitSide.RIGHT;
		} else if (difference.getX() == -1)
		{
			return ConduitSide.LEFT;
		}

		return ConduitSide.UP;
	}

	public static IInventory insert(IInventoryHolder container, ItemInstance item)
	{
		IInventory inv = container.getInventory();
		if (item == null || container.getInputSlots(item, Direction.NONE) == null
				|| container.getInputSlots(item, Direction.NONE).size() == 0)
		{
			return inv;
		}
		for (int slot : container.getInputSlots(item, Direction.NONE))
		{
			if (inv.get(slot) != null)
			{
				// if the slot has the same item and we can fit the stack in
				if (inv.get(slot).getItem().equals(item.getItem()) && inv.get(slot).fitsAmount(1))
				{
					inv.set(slot, new ItemInstance(item.getItem(), item.getAmount() + inv.get(slot).getAmount()));
					return inv;
				}
			} else
			{
				inv.set(slot, item);
				return inv;
			}
		}
		return inv;
	}

	public static ItemInstance extract(IInventoryHolder container, int maxAmount, ItemInstance filterItem,
			boolean isWhitelist)
	{
		IInventory inv = container.getInventory();
		if (container.getOutputSlots(Direction.NONE) == null || container.getOutputSlots(Direction.NONE).size() == 0)
		{
			return null;
		}
		for (int slot : container.getOutputSlots(Direction.NONE))
		{
			if (inv.get(slot) != null)
			{
				boolean matchesFilter = false;
				if (filterItem == null)
				{
					matchesFilter = true;
				} else
				{
					matchesFilter = inv.get(slot).getItem().equals(filterItem.getItem());
					if (!isWhitelist)
					{
						matchesFilter = !matchesFilter;
					}
				}
				if (matchesFilter)
				{
					// If we can pull the max amount
					if (inv.get(slot).getAmount() > maxAmount)
					{
						ItemInstance output = inv.get(slot).copy().setAmount(maxAmount);
						inv.remove(slot, maxAmount);
						return output;
					}
					// if we have to pull only a portion of the max amount
					else
					{
						ItemInstance output = inv.get(slot).copy();
						inv.set(slot, null);
						return output;
					}
				}
			}
		}
		return null;
	}

	public static ItemInstance getToExtract(IInventoryHolder container, int maxAmount, ItemInstance filterItem,
			boolean isWhitelist)
	{
		IInventory inv = container.getInventory();
		if (container.getOutputSlots(Direction.NONE) == null || container.getOutputSlots(Direction.NONE).size() == 0)
		{
			return null;
		}
		for (int slot : container.getOutputSlots(Direction.NONE))
		{
			if (inv.get(slot) != null)
			{
				boolean matchesFilter = false;
				if (filterItem == null)
				{
					matchesFilter = true;
				} else
				{
					matchesFilter = inv.get(slot).getItem().equals(filterItem.getItem());
					if (!isWhitelist)
					{
						matchesFilter = !matchesFilter;
					}
				}
				if (matchesFilter)
				{
					// If we can pull the max amount
					if (inv.get(slot).getAmount() > maxAmount)
					{
						return inv.get(slot).copy().setAmount(maxAmount);
					}
					// if we have to pull only a portion of the max amount
					else
					{
						return inv.get(slot).copy();
					}
				}
			}
		}
		return null;
	}

	public static boolean canInsert(IInventoryHolder container, ItemInstance item)
	{
		IInventory inv = container.getInventory();
		if (item == null || container.getInputSlots(item, Direction.NONE) == null
				|| container.getInputSlots(item, Direction.NONE).size() == 0)
		{
			return false;
		}
		for (int slot : container.getInputSlots(item, Direction.NONE))
		{
			if (inv.get(slot) != null)
			{
				// if the slot has the same item and we can fit the stack in
				if (inv.get(slot).getItem().equals(item.getItem()) && inv.get(slot).fitsAmount(1))
				{
					return true;
				}
			} else
			{
				return true;
			}
		}
		return false;
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

			RockSolidAPI.CONDUIT_SIDES.put(id, this);
		}

		public int getID()
		{
			return id;
		}

		public Pos2 getOffset()
		{
			return offset;
		}

		public static ConduitSide getByID(int ID)
		{
			return RockSolidAPI.CONDUIT_SIDES.get(ID);
		}
	}

	public enum ConduitMode
	{
		OUTPUT(0, "Output", "Outputs into the connected block"), INPUT(1, "Input",
				"Pulls contents into the inventory"), DISABLED(2, "Disabled", "The conduit won't connect on this side");

		private int id;
		private String desc;
		private String name;

		ConduitMode(int id, String name, String desc)
		{
			this.id = id;
			this.name = name;
			this.desc = desc;

			RockSolidAPI.CONDUIT_MODES.put(id, this);
		}

		public int getID()
		{
			return this.id;
		}

		public String getName()
		{
			return this.name;
		}

		public String getDesc()
		{
			return this.desc;
		}

		public static ConduitMode getByID(int ID)
		{
			return RockSolidAPI.CONDUIT_MODES.get(ID);
		}
	}

}
