package com.raphydaphy.rocksolid.tileentity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.raphydaphy.rocksolid.api.energy.TileEntityPowered;
import com.raphydaphy.rocksolid.api.fluid.FluidTile;
import com.raphydaphy.rocksolid.api.gas.GasTile;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.gui.inventory.ContainerInventory;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.IInventoryHolder;
import de.ellpeck.rockbottom.api.util.Direction;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class TileEntityQuarry extends TileEntityPowered implements IInventoryHolder
{

	public static final int OUTPUT = 0;
	public final ContainerInventory inventory;

	private int curX;
	private int curY;
	private int mineTick;

	protected int powerStored = 0;
	private boolean shouldSync = false;

	public TileEntityQuarry(final IWorld world, final int x, final int y)
	{
		super(world, x, y, 20000, 100);
		this.inventory = new ContainerInventory(this, 1);

		this.curX = x - 9;
		this.curY = y - 2;
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

	public boolean isActive()
	{
		return this.powerStored >= super.getPowerPerOperation();
	}

	@Override
	public boolean tryTickAction()
	{
		boolean ableToDig = false;
		final ItemInstance output = this.inventory.get(0);
		if (curX > this.x + 11)
		{
			curX = this.x - 9;
			curY--;
		}

		while (world.getState(curX, curY).getTile() == GameContent.TILE_AIR
				|| world.getState(curX, curY).getTile() instanceof FluidTile
				|| world.getState(curX, curY).getTile() instanceof GasTile)
		{
			curX++;
			if (curX > this.x + 11)
			{
				curX = this.x - 9;
				curY--;
			}
		}
		if ((output == null || output.getItem().equals(world.getState(curX, curY).getTile()
				.getDrops(world, curX, curY, TileLayer.MAIN, null).get(0).getItem())) && world.isPosLoaded(curX, curY))
		{
			if (this.getCurrentEnergy() >= super.getPowerPerOperation())
			{
				ableToDig = true;
				if (mineTick == 10 && RockBottomAPI.getNet().isClient() == false)
				{
					if (world.getState(curX, curY).getTile().canBreak(world, curX, curY, TileLayer.MAIN)
							&& RockSolidAPILib.getTileFromPos(curX, curY, world) == null)
					{
						List<ItemInstance> curTile = world.getState(curX, curY).getTile().getDrops(world, curX, curY,
								TileLayer.MAIN, null);
						if (output == null)
						{
							this.inventory.set(0, new ItemInstance(curTile.get(0).getItem(), 1));
						} else if (output.getItem().equals(curTile.get(0).getItem()))
						{
							this.inventory.get(0).addAmount(1);
						}
						world.setState(curX, curY, GameContent.TILE_AIR.getDefState());
					}
					curX++;
					mineTick = 0;
					shouldSync = true;

				} else if (RockBottomAPI.getNet().isClient() == false)
				{
					mineTick++;
					shouldSync = true;
				}
				return ableToDig;
			}
		}
		return ableToDig;
	}

	@Override
	public void save(final DataSet set, final boolean forSync)
	{
		super.save(set, forSync);
		if (!forSync)
		{
			this.inventory.save(set);
		}
		set.addInt("powerStored", this.powerStored);
		set.addInt("curX", this.curX);
		set.addInt("curY", this.curY);
		set.addInt("mineTick", this.mineTick);
		set.addBoolean("shouldSync", this.shouldSync);
	}

	@Override
	public void load(final DataSet set, final boolean forSync)
	{
		super.load(set, forSync);
		if (!forSync)
		{
			this.inventory.load(set);
		}
		this.powerStored = set.getInt("powerStored");
		this.curX = set.getInt("curX");
		this.curY = set.getInt("curY");
		this.mineTick = set.getInt("mineTick");
		this.shouldSync = set.getBoolean("shouldSync");
	}

	@Override
	public ContainerInventory getInventory()
	{
		return this.inventory;
	}

	@Override
	public List<Integer> getInputSlots(ItemInstance input, Direction dir)
	{
		return new ArrayList<Integer>();
	}

	@Override
	public List<Integer> getOutputSlots(Direction dir)
	{
		return Arrays.asList(0);
	}

	@Override
	protected void setPower(int power)
	{
		this.powerStored = power;
	}

	@Override
	protected int getPower()
	{
		return this.powerStored;
	}

	@Override
	protected void onActiveChange(boolean active)
	{
		this.world.causeLightUpdate(this.x, this.y);
	}

	@Override
	public float getSmeltPercentage()
	{
		if (mineTick == 0)
		{
			return 0;
		} else
		{
			return mineTick / 10;
		}
	}

}
