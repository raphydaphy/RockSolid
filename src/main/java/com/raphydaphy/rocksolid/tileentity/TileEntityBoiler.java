package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.fluid.IFluidTile;
import com.raphydaphy.rocksolid.gas.Gas;
import com.raphydaphy.rocksolid.gas.IGasTile;
import com.raphydaphy.rocksolid.tile.machine.TileBoiler;
import com.raphydaphy.rocksolid.tileentity.base.TileEntityFueledBase;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.construction.smelting.FuelInput;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.TileLiquid;
import de.ellpeck.rockbottom.api.tile.entity.SyncedInt;
import de.ellpeck.rockbottom.api.tile.entity.TileInventory;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TileEntityBoiler extends TileEntityFueledBase implements IFluidTile<TileEntityBoiler>, IGasTile<TileEntityBoiler>
{
	private final TileInventory inventory = new TileInventory(this, 1, (input) ->
	{
		ArrayList<Integer> avalableSlots = new ArrayList<>(1);
		if (FuelInput.getFuelTime(input) > 0)
		{
			avalableSlots.add(0);
		}
		return avalableSlots;
	}, Collections.emptyList());

	private SyncedInt water = new SyncedInt("water");
	private SyncedInt steam = new SyncedInt("steam");

	public TileEntityBoiler(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
	}

	@Override
	protected void getRecipeAndStart()
	{
		if (this.water.get() >= 1 && this.steam.get() < getCapacity())
		{
			this.maxSmeltTime.set(Math.round(6 / getSpeedModifier()));
			this.water.remove(1);
		}
	}

	@Override
	protected void putOutputItems()
	{
		this.steam.add(1);
		double chance = Math.pow(2, 5 * (getBonusYieldModifier() / 2f)); // Bonus Yield Modifier
		if (this.steam.get() < getCapacity() && (Util.RANDOM.nextDouble() * 100) < chance)
		{
			this.steam.add(1);
		}
	}

	@Override
	public TileInventory getTileInventory()
	{
		return this.inventory;
	}

	@Override
	public void save(DataSet set, boolean forSync)
	{
		super.save(set, forSync);
		water.save(set);
		steam.save(set);
		if (!forSync)
		{
			inventory.save(set);
		}
	}

	@Override
	public void load(DataSet set, boolean forSync)
	{
		super.load(set, forSync);
		water.load(set);
		steam.load(set);
		if (!forSync)
		{
			inventory.load(set);
		}
	}

	@Override
	protected boolean needsSync()
	{
		return water.needsSync() || steam.needsSync();
	}

	@Override
	public void onSync()
	{
		super.onSync();
		water.onSync();
		steam.onSync();
	}

	public float getSteamFullness()
	{
		return (float)this.steam.get() / (float)getCapacity();
	}

	private int getCapacity()
	{
		return Math.round(1000f / getCapacityModifier());
	}

	public float getWaterFullness()
	{
		return (float) this.water.get() / (float)getCapacity();
	}

	@Override
	protected ItemInstance getFuel()
	{
		return this.getTileInventory().get(0);
	}

	@Override
	protected void removeFuel()
	{
		this.getTileInventory().remove(0, 1);
	}

	@Override
	protected void onActiveChange(boolean active)
	{
		world.causeLightUpdate(this.x, this.y);
	}

	@Override
	public boolean addFluid(Pos2 pos, TileLiquid liquid, int ml, boolean simulate)
	{
		if (liquid.equals(GameContent.TILE_WATER) && ml + this.water.get() <= getCapacity())
		{
			if (!simulate)
			{
				this.water.add(ml);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean removeFluid(Pos2 pos, TileLiquid liquid, int ml, boolean simulate)
	{
		return false;
	}

	@Override
	public int getFluidCapacity(IWorld world, Pos2 pos, TileLiquid liquid)
	{
		return liquid.equals(GameContent.TILE_WATER) && getLiquidsAt(world, pos) != null ? getCapacity() : 0;
	}

	@Override
	public List<TileLiquid> getLiquidsAt(IWorld world, Pos2 pos)
	{
		TileState state = world.getState(pos.getX(), pos.getY());

		if (state.getTile() instanceof TileBoiler)
		{
			Pos2 inner = ((TileBoiler) state.getTile()).getInnerCoord(state);

			if (inner.getY() != 4 && inner.getY() != 0)
			{
				return Collections.singletonList(GameContent.TILE_WATER);
			}
		}
		return null;
	}

	@Override
	public boolean addGas(Pos2 pos, Gas liquid, int cc, boolean simulate)
	{
		return false;
	}

	@Override
	public boolean removeGas(Pos2 pos, Gas liquid, int cc, boolean simulate)
	{
		if (liquid.equals(Gas.STEAM) && this.steam.get() - cc > 0)
		{
			if (!simulate)
			{
				this.steam.remove(cc);
			}
			return true;
		}
		return false;
	}

	@Override
	public int getGasCapacity(IWorld world, Pos2 pos, Gas gas)
	{
		return gas.equals(Gas.STEAM) && getGasAt(world, pos) != null ? getCapacity() : 0;
	}

	@Nullable
	@Override
	public List<Gas> getGasAt(IWorld world, Pos2 pos)
	{
		TileState state = world.getState(pos.getX(), pos.getY());

		if (state.getTile() instanceof TileBoiler)
		{
			Pos2 inner = ((TileBoiler) state.getTile()).getInnerCoord(state);

			if (inner.getY() != 4 && inner.getY() != 0)
			{
				return Collections.singletonList(Gas.STEAM);
			}
		}
		return null;
	}

	@Override
	public boolean doesTick()
	{
		return true;
	}
}
