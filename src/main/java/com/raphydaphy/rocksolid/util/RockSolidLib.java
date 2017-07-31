package com.raphydaphy.rocksolid.util;

import org.newdawn.slick.Color;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.api.util.IHasInventory;
import com.raphydaphy.rocksolid.init.ModFluids;
import com.raphydaphy.rocksolid.init.ModGasses;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;

public class RockSolidLib
{
	public static IResourceName makeRes(String name)
	{
		return RockBottomAPI.createRes(RockSolid.INSTANCE, name);
	}

	public static String getGasLocName(String gas)
	{
		if (gas.equals(ModGasses.gasHydrogen.toString()))
		{
			return "Hydrogen";
		} else if (gas.equals(ModGasses.gasOxygen.toString()))
		{
			return "Oxygen";
		} else if (gas.equals(ModGasses.gasSteam.toString()))
		{
			return "Steam";
		}
		return "Gas";
	}

	public static String getFluidLocName(String fluid)
	{
		if (fluid.equals(ModFluids.fluidWater.toString()))
		{
			return "Water";
		} else if (fluid.equals(ModFluids.fluidLava.toString()))
		{
			return "Lava";
		}
		return "Fluid";
	}

	public static Fluid bucketMetaToFluid(int meta)
	{
		switch (meta)
		{
		case 1:
			return ModFluids.fluidWater;
		case 2:
			return ModFluids.fluidLava;
		}
		return ModFluids.fluidEmpty;
	}

	public static Color getFluidColor(String fluid)
	{
		if (fluid.equals(ModFluids.fluidLava.toString()))
		{
			return Color.orange;
		} else if (fluid.equals(ModFluids.fluidWater.toString()))
		{
			return Color.blue;
		}
		return Color.lightGray;
	}

	public static Color getGasColor(String gas)
	{
		if (gas.equals(ModGasses.gasOxygen.toString()))
		{
			return new Color(224, 255, 255);
		} else if (gas.equals(ModGasses.gasHydrogen.toString()))
		{
			return new Color(200, 147, 216);
		} else if (gas.equals(ModGasses.gasSteam.toString()))
		{
			return new Color(165, 165, 165);
		}
		return new Color(199, 136, 53);
	}

	public static TileEntity getTileFromConduitSide(Pos2 center, int side, IWorld world)
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

	public static Pos2 conduitSideToPos(Pos2 center, int side)
	{
		switch (side)
		{
		case 0:
			// up
			return center.add(0, 1);
		case 1:
			// down
			return center.add(0, -1);
		case 2:
			// left
			return center.add(-1, 0);
		case 3:
			// right
			return center.add(1, 0);
		}
		return null;
	}

	public static int posAndOffsetToConduitSide(Pos2 center, Pos2 side)
	{
		Pos2 difference = center.add(-side.getX(), -side.getY());
		difference.set(-difference.getX(), -difference.getY());
		if (difference.getY() == -1)
		{
			return 1;
		} else if (difference.getY() == 1)
		{
			return 0;
		} else if (difference.getX() == 1)
		{
			return 3;
		} else if (difference.getX() == -1)
		{
			return 2;
		}

		return 0;
	}

	public static int invertSide(int side)
	{
		switch (side)
		{
		case 1:
			return 0;
		case 0:
			return 1;
		case 2:
			return 3;
		case 3:
			return 2;
		}
		return 0;
	}

	public static Inventory insert(IHasInventory container, ItemInstance item)
	{
		Inventory inv = container.getInventory();
		if (item == null || container.getInputs().size() == 0)
		{
			return inv;
		}
		for (int slot : container.getInputs())
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

	public static ItemInstance extract(IHasInventory container, int maxAmount)
	{
		Inventory inv = container.getInventory();
		if (container.getOutputs().size() == 0)
		{
			return null;
		}
		for (int slot : container.getOutputs())
		{
			if (inv.get(slot) != null)
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
		return null;
	}

	public static ItemInstance getToExtract(IHasInventory container, int maxAmount)
	{
		Inventory inv = container.getInventory();
		if (container.getOutputs().size() == 0)
		{
			return null;
		}
		for (int slot : container.getOutputs())
		{
			if (inv.get(slot) != null)
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
		return null;
	}

	public static boolean canInsert(IHasInventory container, ItemInstance item)
	{
		Inventory inv = container.getInventory();
		if (item == null || container.getInputs().size() == 0)
		{
			return false;
		}
		for (int slot : container.getInputs())
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

}
