package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.tileentity.base.TileEntityAssemblyConfigurable;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileEntityLaunchPad extends TileEntityAssemblyConfigurable
{
	public TileEntityLaunchPad(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
	}

	@Override
	public boolean doesTick()
	{
		return false;
	}
}
