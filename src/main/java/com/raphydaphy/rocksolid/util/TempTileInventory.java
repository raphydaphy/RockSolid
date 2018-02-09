package com.raphydaphy.rocksolid.util;

import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;

public class TempTileInventory extends Inventory {
	public TempTileInventory(TileEntity tile, int slotAmount) {
		super(slotAmount);
		this.addChangeCallback((inv, slot) -> {
			tile.world.setDirty(tile.x, tile.y);
		});
	}
}