package com.raphydaphy.rocksolid.tile;

import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public abstract class TileEntityConduit extends TileEntity
{

	public TileEntityConduit(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
	}

}
