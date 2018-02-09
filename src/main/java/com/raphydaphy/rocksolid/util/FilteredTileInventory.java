package com.raphydaphy.rocksolid.util;

import java.util.List;

import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.entity.TileInventory;
import de.ellpeck.rockbottom.api.util.Direction;

public class FilteredTileInventory extends TileInventory
{
	private final List<SlotInfo> slots;

	public FilteredTileInventory(TileEntity tile,  List<SlotInfo> slots)
	{
		super(tile, slots.size(), SlotInfo.toInputList(slots), SlotInfo.toOutputList(slots));
		this.slots = slots;
	}

	@Override
	public List<Integer> getInputSlots(ItemInstance instance, Direction dir)
	{
		return SlotInfo.toInputList(slots, instance, dir);
	}
	
	public List<SlotInfo> getSlots()
	{
		return this.slots;
	}
}
