package com.raphydaphy.rocksolid.world;

import com.raphydaphy.rocksolid.init.ModTiles;

import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.world.gen.WorldGenOre;

public class WorldGenTin extends WorldGenOre
{

	@Override
	public int getPriority() 
	{
		return 210;
	}

	@Override
	public int getHighestGridPos() 
	{
		return -1;
	}
	
	@Override
	public int getLowestGridPos() 
	{
		return -4;
	}

	@Override
	public int getMaxAmount() 
	{
		return 2;
	}

	@Override
	public int getClusterRadiusX() 
	{
		return 8;
	}

	@Override
	public int getClusterRadiusY() 
	{
		return 4;
	}

	@Override
	public TileState getOreState()
	{
		return ModTiles.oreTin.getDefState();
	}

}
