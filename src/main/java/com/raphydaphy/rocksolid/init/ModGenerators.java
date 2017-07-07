package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.world.WorldGenIron;
import com.raphydaphy.rocksolid.world.WorldGenTin;

import de.ellpeck.rockbottom.api.RockBottomAPI;

public class ModGenerators 
{
	public static void init()
	{
		RockBottomAPI.WORLD_GENERATORS.add(WorldGenTin.class);
		RockBottomAPI.WORLD_GENERATORS.add(WorldGenIron.class);
	}
}
