package com.raphydaphy.rocksolid.world.moon;

import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.HeightGen;

public class MoonHeightGen extends HeightGen implements IMoonGenerator
{
	@Override
	public int getMinHeight(IWorld world)
	{
		return 0;
	}

	@Override
	public int getMaxHeight(IWorld world)
	{
		return 30;
	}

	@Override
	public int getMaxMountainHeight(IWorld world)
	{
		return 50;
	}

	@Override
	public int getNoiseScramble(IWorld world)
	{
		return 48381723;
	}

	@Override
	public int getPriority()
	{
		return 15000;
	}
}
