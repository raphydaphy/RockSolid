package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.init.ModMisc;
import com.raphydaphy.rocksolid.util.FilteredTileInventory;
import com.raphydaphy.rocksolid.util.SlotInfo;
import com.raphydaphy.rocksolid.util.SlotInfo.SimpleSlotInfo;
import com.raphydaphy.rocksolid.util.SlotInfo.SlotType;
import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileEntityAssemblyStation extends TileEntity
{
	public final FilteredTileInventory inventory = new FilteredTileInventory(this, SlotInfo.makeList(
			new SimpleSlotInfo(SlotType.INPUT, instance -> new ResUseInfo(ModMisc.RES_MACHINE_MATERIALS).containsItem(instance)),
			new SimpleSlotInfo(SlotType.INPUT, instance -> new ResUseInfo(ModMisc.RES_ALL_INGOTS).containsItem(instance)),
			new SimpleSlotInfo(SlotType.INPUT, instance -> new ResUseInfo(ModMisc.RES_ALL_FUELS).containsItem(instance))
	));

	public TileEntityAssemblyStation(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
	}

	public FilteredTileInventory getInvHidden()
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
