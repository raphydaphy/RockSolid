package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.fluid.IFluidTile;
import com.raphydaphy.rocksolid.gas.Gas;
import com.raphydaphy.rocksolid.gas.IGasTile;
import com.raphydaphy.rocksolid.init.ModTiles;
import com.raphydaphy.rocksolid.tile.multi.TileBoiler;
import com.raphydaphy.rocksolid.util.FilteredTileInventory;
import com.raphydaphy.rocksolid.util.ModUtils;
import com.raphydaphy.rocksolid.util.SlotInfo;
import com.raphydaphy.rocksolid.util.SlotInfo.SimpleSlotInfo;
import com.raphydaphy.rocksolid.util.SlotInfo.SlotType;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.TileLiquid;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class TileEntityBoiler extends TileEntityFueledBase implements IFluidTile<TileEntityBoiler>, IGasTile<TileEntityBoiler>
{
	public static final String KEY_WATER = "water";
	private static final String KEY_STEAM = "steam";
	public final FilteredTileInventory inventory = new FilteredTileInventory(this, SlotInfo.makeList(new SimpleSlotInfo(SlotType.INPUT, instance -> ModUtils.getFuelValue(instance) > 0)));
	private int steam = 0;
	private int lastSteam = 0;
	private int water = 0;
	private int lastWater = 0;

	public TileEntityBoiler(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
	}

	@Override
	public FilteredTileInventory getTileInventory()
	{
		return this.inventory;
	}

	@Override
	public void save(DataSet set, boolean forSync)
	{
		super.save(set, forSync);
		set.addInt(KEY_STEAM, this.steam);
		set.addInt(KEY_WATER, this.water);
		inventory.save(set);
	}

	@Override
	public void load(DataSet set, boolean forSync)
	{
		super.load(set, forSync);
		this.steam = set.getInt(KEY_STEAM);
		this.water = set.getInt(KEY_WATER);
		inventory.load(set);
	}

	@Override
	protected boolean needsSync()
	{
		return this.lastSteam != this.steam || this.lastWater != this.water || super.needsSync();
	}

	@Override
	public void onSync()
	{
		super.onSync();
		this.lastSteam = this.steam;
		this.lastWater = this.water;
	}

	public float getSteamFullness()
	{
		return this.steam / 1000f;
	}

	public float getWaterFullness()
	{
		return (float) this.water / 1000f;
	}

	private final IResourceName BOILER_SOUND = RockSolid.createRes("boiler");
	private int lastPlayed = -1;

	@Override
	protected boolean tryTickAction()
	{
		if (this.water >= 1 && this.steam < 1000)
		{
			if (this.coalTime > 0)
			{
				if (!this.world.isClient())
				{
					if (world.getTotalTime() % 6 == 0)
					{
						this.steam += 1;
						this.water -= 1;
					}
				}
				if (!(this.world.isDedicatedServer() && this.world.isServer()))
				{
					if (lastPlayed == -1 || world.getTotalTime() - lastPlayed >= 320)
					{
						world.playSound(BOILER_SOUND, x + 0.5d, y + 0.5d, layer.index(), 1, 4);
						lastPlayed = world.getTotalTime();
					}
				}
			}
			return true;
		}
		return false;
	}

	@Override
	protected float getFuelModifier()
	{
		return 1f;
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
		if (liquid.equals(ModTiles.WATER) && ml + this.water <= 1000)
		{
			if (!simulate)
			{
				this.water += ml;
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
		return liquid.equals(ModTiles.WATER) && getLiquidsAt(world, pos) != null ? 1000 : 0;
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
				return Collections.singletonList((TileLiquid) ModTiles.WATER);
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
		if (liquid.equals(Gas.STEAM) && this.steam - cc > 0)
		{
			if (!simulate)
			{
				this.steam -= cc;
			}
			return true;
		}
		return false;
	}

	@Override
	public int getGasCapacity(IWorld world, Pos2 pos, Gas gas)
	{
		return gas.equals(Gas.STEAM) && getGasAt(world, pos) != null ? 1000 : 0;
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
