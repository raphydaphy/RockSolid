package com.raphydaphy.rocksolid.world;

import com.raphydaphy.rocksolid.init.ModTiles;

import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.world.gen.WorldGenOre;

public class WorldGenUranium extends WorldGenOre
{

	@Override
	public int getPriority()
	{
		return 210;
	}

	@Override
	public int getHighestGridPos()
	{
		return -15;
	}

	@Override
	public int getLowestGridPos()
	{
		return -30;
	}

	@Override
	public int getMaxAmount()
	{
		return 3;
	}

	@Override
	public int getClusterRadiusX()
	{
		return 5;
	}

	@Override
	public int getClusterRadiusY()
	{
		return 5;
	}

	@Override
	public TileState getOreState()
	{
		return ModTiles.oreUranium.getDefState();
	}

}
