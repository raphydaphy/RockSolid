package com.raphydaphy.rocksolid.world;

import com.raphydaphy.rocksolid.api.content.RockSolidContent;

import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.world.gen.WorldGenOre;

public class WorldGenWolframite extends WorldGenOre
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
		return -50;
	}

	@Override
	public int getMaxAmount()
	{
		return 3;
	}

	@Override
	public int getClusterRadiusX()
	{
		return 4;
	}

	@Override
	public int getClusterRadiusY()
	{
		return 3;
	}

	@Override
	public TileState getOreState()
	{
		return RockSolidContent.oreWolframite.getDefState();
	}

}
