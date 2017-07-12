package com.raphydaphy.rocksolid.world;

import com.raphydaphy.rocksolid.init.ModBlocks;

import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.world.gen.WorldGenOre;

public class WorldGenRutile extends WorldGenOre
{

	@Override
	public int getPriority() 
	{
		return 210;
	}

	@Override
	public int getHighestGridPos() 
	{
		return -10;
	}
	
	@Override
	public int getLowestGridPos() 
	{
		return -30;
	}

	@Override
	public int getMaxAmount() 
	{
		return 2;
	}

	@Override
	public int getClusterRadiusX() 
	{
		return 3;
	}

	@Override
	public int getClusterRadiusY() 
	{
		return 2;
	}

	@Override
	public Tile getOreTile()
	{
		return ModBlocks.oreRutile;
	}

	@Override
	public int getOreMeta() 
	{
		return 0;
	}

}
