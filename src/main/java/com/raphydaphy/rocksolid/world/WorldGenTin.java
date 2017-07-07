package com.raphydaphy.rocksolid.world;

import com.raphydaphy.rocksolid.init.ModBlocks;

import de.ellpeck.rockbottom.api.tile.Tile;
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
	public Tile getOreTile()
	{
		return ModBlocks.oreTin;
	}

	@Override
	public int getOreMeta() 
	{
		return 0;
	}

}
