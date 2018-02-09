package com.raphydaphy.rocksolid.tileentity;

import java.util.Random;

import com.raphydaphy.rocksolid.util.FilteredTileInventory;
import com.raphydaphy.rocksolid.util.SlotInfo;
import com.raphydaphy.rocksolid.util.SlotInfo.SimpleSlotInfo;
import com.raphydaphy.rocksolid.util.SlotInfo.SlotType;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileEntityBoiler extends TileEntity
{
	private static final String KEY_STEAM = "steam";
	public static final String KEY_ACTIVE = "active";

	private int steam = 0;
	private int lastSteam = 0;

	private boolean active = false;
	private boolean lastActive = false;

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
	public void update(IGameInstance game)
	{
		super.update(game);
		if (!this.world.isClient())
		{
			int rand = new Random().nextInt(500);

			if (rand == 1)
			{
				this.steam++;
				this.active = !this.isActive();
			}
		}
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
		set.addBoolean(KEY_ACTIVE, this.active);
		inventory.save(set);
	}

	@Override
	public void load(DataSet set, boolean forSync)
	{
		super.load(set, forSync);
		this.steam = set.getInt(KEY_STEAM);
		this.active = set.getBoolean(KEY_ACTIVE);
		inventory.load(set);
	}

	@Override
	protected boolean needsSync()
	{
		return this.lastSteam != this.steam || this.lastActive != this.active || super.needsSync();
	}

	@Override
	public void onSync()
	{
		super.onSync();
		this.lastSteam = this.steam;
		this.lastActive = this.active;
	}

	public int getSteam()
	{
		return this.steam;
	}

	public boolean isActive()
	{
		return this.active;
	}
}
