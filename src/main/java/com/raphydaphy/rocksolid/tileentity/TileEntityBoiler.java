package com.raphydaphy.rocksolid.tileentity;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileEntityBoiler extends TileEntity
{
	private static final String KEY_STEAM = "steam";

	private int steam = 0;
	private int lastSteam = 0;

	public TileEntityBoiler(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
	}

	public void update(IGameInstance game)
	{
		super.update(game);
		if (!this.world.isClient())
		{
			if (world.getWorldInfo().totalTimeInWorld % 50 == 0)
			{
				this.steam++;
			}
		}
	}

	@Override
	public void save(DataSet set, boolean forSync)
	{
		super.save(set, forSync);
		set.addInt(KEY_STEAM, this.steam);
	}

	@Override
	public void load(DataSet set, boolean forSync)
	{
		super.load(set, forSync);
		this.steam = set.getInt(KEY_STEAM);
	}

	@Override
	protected boolean needsSync()
	{
		return this.lastSteam != this.steam && super.needsSync();
	}

	@Override
	public void onSync()
	{
		super.onSync();
		this.lastSteam = this.steam;
	}

	public int getSteam()
	{
		return this.steam;
	}
}
