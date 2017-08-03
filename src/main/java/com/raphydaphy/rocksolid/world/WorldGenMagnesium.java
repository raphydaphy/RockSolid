package com.raphydaphy.rocksolid.world;

import com.raphydaphy.rocksolid.api.content.RockSolidContent;

import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.world.gen.WorldGenOre;

public class WorldGenMagnesium extends WorldGenOre
{

	@Override
	public int getPriority()
	{
		return 210;
	}

	@Override
	public int getHighestGridPos()
	{
		return -8;
	}

	@Override
	public int getLowestGridPos()
	{
		return -20;
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
	public TileState getOreState()
	{
		return RockSolidContent.oreMagnesium.getDefState();
	}
}
