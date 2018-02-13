package com.raphydaphy.rocksolid.tileentity;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.raphydaphy.rocksolid.fluid.IFluidTile;
import com.raphydaphy.rocksolid.init.ModTiles;
import com.raphydaphy.rocksolid.tile.multi.TileBoiler;
import com.raphydaphy.rocksolid.util.FilteredTileInventory;
import com.raphydaphy.rocksolid.util.SlotInfo;
import com.raphydaphy.rocksolid.util.SlotInfo.SimpleSlotInfo;
import com.raphydaphy.rocksolid.util.SlotInfo.SlotType;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.TileLiquid;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileEntityBoiler extends TileEntityFueledBase implements IFluidTile<TileEntityBoiler>
{
	private static final String KEY_STEAM = "steam";
	public static final String KEY_WATER = "water";

	private int steam = 0;
	private int lastSteam = 0;

	private int water = 0;
	private int lastWater = 0;

	public final FilteredTileInventory inventory = new FilteredTileInventory(this,
			SlotInfo.makeList(new SimpleSlotInfo(SlotType.INPUT, instance ->
			{
				return instance.getItem().equals(GameContent.TILE_COAL.getItem());
			})));

	public TileEntityBoiler(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
	}

	@Override
	public FilteredTileInventory getInventory()
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

	public int getSteam()
	{
		return this.steam;
	}

	public float getWaterFullness()
	{
		return (float) this.water / (float) this.getCapacity(new Pos2(this.x, this.y), (TileLiquid) ModTiles.WATER);
	}

	@Override
	protected boolean tryTickAction()
	{
		if (this.water > 0)
		{
			if (this.coalTime > 0)
			{
				if (!this.world.isClient())
				{
					int rand = new Random().nextInt(500);

					if (rand == 1)
					{
						this.steam++;
						this.water--;
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
		return this.getInventory().get(0);
	}

	@Override
	protected void removeFuel()
	{
		this.getInventory().remove(0, 1);
	}

	@Override
	protected void onActiveChange(boolean active)
	{
		world.causeLightUpdate(this.x, this.y);
	}

	@Override
	public boolean add(Pos2 pos, TileLiquid liquid, int ml, boolean simulate)
	{
		if (liquid.equals((TileLiquid) ModTiles.WATER) && ml + this.water <= this.getCapacity(pos, liquid))
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
	public boolean remove(Pos2 pos, TileLiquid liquid, int ml, boolean simulate)
	{
		return false;
	}

	@Override
	public int getCapacity(Pos2 pos, TileLiquid liquid)
	{
		return liquid.equals((TileLiquid) ModTiles.WATER) ? 5000 : 0;
	}

	@Override
	public List<TileLiquid> getLiquidsAt(IWorld world, Pos2 pos)
	{
		TileState state = world.getState(pos.getX(), pos.getY());
		
		if (state.getTile() instanceof TileBoiler)
		{
			Pos2 inner = ((TileBoiler)state.getTile()).getInnerCoord(state);
			
			if (inner.getY() != 4 && inner.getY() != 0)
			{
				return Arrays.asList((TileLiquid) ModTiles.WATER);
			}
		}
		return null;
	}
}
