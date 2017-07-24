package com.raphydaphy.rocksolid.tile;

import java.util.Collections;
import java.util.List;

import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class TileCustomDrop extends TileBase
{
	private final ItemInstance itemDrop;
	public TileCustomDrop(String name, ItemInstance drop, int oreHardness, int toolLevel) {
		super(RockSolidLib.makeRes(name), oreHardness, toolLevel);
		itemDrop = drop;
	}
	
	@Override
    public List<ItemInstance> getDrops(final IWorld world, final int x, final int y, final TileLayer layer, final Entity destroyer) {
        return Collections.singletonList(itemDrop);
    }

}
