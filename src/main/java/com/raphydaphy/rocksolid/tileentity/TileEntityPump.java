package com.raphydaphy.rocksolid.tileentity;

import java.util.Random;

import com.raphydaphy.rocksolid.fluid.FluidWater;
import com.raphydaphy.rocksolid.init.ModTiles;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.TileLiquid;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileEntityPump extends TileEntity
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
				if (new Random().nextInt(1000) == 1)
				{
					this.liquidType = (FluidWater) ModTiles.WATER;
					this.liquidVolume = 50;
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
}
