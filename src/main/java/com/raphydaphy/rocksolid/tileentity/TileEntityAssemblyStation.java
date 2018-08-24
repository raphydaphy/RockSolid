package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.init.ModMisc;
import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.entity.TileInventory;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.ArrayList;
import java.util.Collections;

public class TileEntityAssemblyStation extends TileEntity
{
	private final TileInventory inventory = new TileInventory(this, 3, (input) ->
	{
		ArrayList<Integer> avalableSlots = new ArrayList<>(3);
		if (new ResUseInfo(ModMisc.RES_MACHINE_MATERIALS).containsItem(input))
		{
			avalableSlots.add(0);
		}
		if (new ResUseInfo(ModMisc.RES_ALL_INGOTS).containsItem(input))
		{
			avalableSlots.add(1);
		}
		if (new ResUseInfo(ModMisc.RES_ALL_FUELS).containsItem(input))
		{
			avalableSlots.add(2);
		}
		return avalableSlots;
	}, Collections.emptyList());

	public TileEntityAssemblyStation(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
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
		inventory.save(set);
	}

	@Override
	public void load(DataSet set, boolean forSync)
	{
		super.load(set, forSync);
		inventory.load(set);
	}

	@Override
	public boolean doesTick()
	{
		return false;
	}
}
