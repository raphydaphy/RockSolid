package com.raphydaphy.rocksolid.tileentity;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.raphydaphy.rocksolid.fluid.FluidWater;
import com.raphydaphy.rocksolid.fluid.IFluidTile;
import com.raphydaphy.rocksolid.init.ModTiles;
import com.raphydaphy.rocksolid.tile.multi.TilePump;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.TileLiquid;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileEntityPump extends TileEntity implements IFluidTile<TileEntityPump>
{
	private static final String KEY_LIQUID_VOLUME = "liquid_volume";
	public static final String KEY_LIQUID_TYPE = "liquid_type";

	private int liquidVolume = 0;
	private int lastLiquidVolume = 0;

	private TileLiquid liquidType = null;

	public TileEntityPump(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
	}

	@Override
	public void save(DataSet set, boolean forSync)
	{
		super.save(set, forSync);
		set.addInt(KEY_LIQUID_VOLUME, this.liquidVolume);
		if (liquidType != null)
		{
			set.addString(KEY_LIQUID_TYPE, RockBottomAPI.TILE_REGISTRY.getId(liquidType).toString());
		}
	}

	@Override
	public void load(DataSet set, boolean forSync)
	{
		super.load(set, forSync);
		this.liquidVolume = set.getInt(KEY_LIQUID_VOLUME);
		if (set.hasKey(KEY_LIQUID_TYPE))
		{
			liquidType = (TileLiquid) RockBottomAPI.TILE_REGISTRY
					.get(RockBottomAPI.createInternalRes(set.getString(KEY_LIQUID_TYPE)));
		}
	}

	@Override
	public void update(IGameInstance game)
	{
		super.update(game);
		if (!this.world.isClient())
		{
			if (this.world.getState(TileLayer.LIQUIDS, x, y).getTile().equals(ModTiles.WATER))
			{
				if (new Random().nextInt(100) == 1)
				{
					this.liquidType = (FluidWater) ModTiles.WATER;
					this.liquidVolume += 50;

				}
			}
		}
	}

	@Override
	protected boolean needsSync()
	{
		return this.liquidVolume != this.lastLiquidVolume;
	}

	@Override
	public void onSync()
	{
		super.onSync();
		this.lastLiquidVolume = this.liquidVolume;
	}

	public int getLiquidVolume()
	{
		return this.liquidVolume;
	}

	public TileLiquid getLiquidType()
	{
		return this.liquidType;
	}

	@Override
	public boolean add(Pos2 pos, TileLiquid liquid, int ml, boolean simulate)
	{
		if (liquid != null)
		{
			if (this.liquidType != null && !this.liquidType.equals(liquid))
			{
				return false;
			}

			if (this.liquidVolume + ml >= this.getCapacity(pos, this.liquidType))
			{
				return false;
			}

			if (!simulate)
			{
				if (this.liquidType == null)
				{
					this.liquidType = liquid;
				}
				this.liquidVolume += ml;
			}

			return true;
		}
		return false;
	}

	public float getLiquidFullness()
	{
		return (float) this.liquidVolume / (float) this.getCapacity(new Pos2(this.x, this.y), this.liquidType);
	}

	@Override
	public boolean remove(Pos2 pos, TileLiquid liquid, int ml, boolean simulate)
	{
		System.out.println("Trying to extract  " + ml + " x " + liquid + " from a pump that contains "
				+ this.liquidVolume + " x " + this.liquidType);
		if (this.liquidType != null && liquid != null && liquid.equals(this.liquidType) && this.liquidVolume >= ml)
		{
			System.out.println("Can do!");
			if (!simulate)
			{
				this.liquidVolume -= ml;

				if (this.liquidVolume == 0)
				{
					this.liquidType = null;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public int getCapacity(Pos2 pos, TileLiquid liquid)
	{
		if (liquid != null && this.liquidType != null)
			return liquid.equals(this.liquidType) ? 1000 : 0;
		return 0;
	}

	@Override
	public List<TileLiquid> getLiquidsAt(IWorld world, Pos2 pos)
	{
		TileState state = world.getState(pos.getX(), pos.getY());

		if (state.getTile() instanceof TilePump)
		{
			Pos2 inner = ((TilePump) state.getTile()).getInnerCoord(state);

			if (inner.getY() != 0)
			{
				return Arrays.asList(this.liquidType);
			}
		}
		return null;
	}
}
