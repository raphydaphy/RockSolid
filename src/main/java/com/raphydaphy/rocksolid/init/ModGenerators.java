package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.world.WorldGenLakes;

import de.ellpeck.rockbottom.api.RockBottomAPI;

public class ModGenerators
{
	public static void init()
	{
		RockBottomAPI.WORLD_GENERATORS.register(RockSolid.createRes("world_gen_lakes"), WorldGenLakes.class);
	}
}
