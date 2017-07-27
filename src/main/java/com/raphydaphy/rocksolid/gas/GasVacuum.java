package com.raphydaphy.rocksolid.gas;

import com.raphydaphy.rocksolid.api.gas.Gas;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class GasVacuum extends Gas
{
	public GasVacuum() 
	{
		super("gasVacuum");
	}
	
	@Override
	public boolean canPlace(IWorld world, int x, int y, TileLayer layer)
	{
		return super.canPlace(world, x, y, layer) && ((world.getState(x, y).getTile() == this) || world.getState(x, y).getTile() == GameContent.TILE_AIR);
	}

}
