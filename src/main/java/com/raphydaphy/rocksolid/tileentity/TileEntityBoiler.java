package com.raphydaphy.rocksolid.tileentity;

import java.util.Random;

import com.raphydaphy.rocksolid.util.FilteredTileInventory;
import com.raphydaphy.rocksolid.util.SlotInfo;
import com.raphydaphy.rocksolid.util.SlotInfo.SimpleSlotInfo;
import com.raphydaphy.rocksolid.util.SlotInfo.SlotType;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileEntityBoiler extends TileEntityFueledBase
{
	private static final String KEY_STEAM = "steam";

	private int steam = 0;
	private int lastSteam = 0;

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

		inventory.save(set);
	}

	@Override
	public void load(DataSet set, boolean forSync)
	{
		super.load(set, forSync);
		this.steam = set.getInt(KEY_STEAM);
		inventory.load(set);
	}

	@Override
	protected boolean needsSync()
	{
		return this.lastSteam != this.steam || super.needsSync();
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

	@Override
	protected boolean tryTickAction()
	{
		if (this.coalTime > 0)
		{
			if (!this.world.isClient())
			{
				int rand = new Random().nextInt(500);

				if (rand == 1)
				{
					this.steam++;
				}
			}
		}
		return true;
	}

	@Override
	protected float getFuelModifier()
	{
		return 0.5f;
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
}
