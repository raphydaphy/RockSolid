package com.raphydaphy.rocksolid.world;

import com.raphydaphy.rocksolid.init.ModBlocks;

import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.world.gen.WorldGenOre;

public class WorldGenIron extends WorldGenOre
{

	@Override
	public int getPriority() 
	{
		return 210;
	}

	@Override
	public int getHighestGridPos() 
	{
		return -4;
	}
	
	@Override
	public int getLowestGridPos() 
	{
		return -10;
	}

	@Override
	public int getMaxAmount() 
	{
		return 3;
	}

	@Override
	public int getClusterRadiusX() 
	{
		return 8;
	}

	@Override
	public int getClusterRadiusY() 
	{
		return 3;
	}

	@Override
	public TileState getOreState()
	{
		return ModBlocks.oreIron.getDefState();
	}

}
