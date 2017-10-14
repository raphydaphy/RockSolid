package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.world.WorldGenIron;
import com.raphydaphy.rocksolid.world.WorldGenMagnesium;
import com.raphydaphy.rocksolid.world.WorldGenRutile;
import com.raphydaphy.rocksolid.world.WorldGenTin;
import com.raphydaphy.rocksolid.world.WorldGenUranium;
import com.raphydaphy.rocksolid.world.WorldGenWolframite;

import de.ellpeck.rockbottom.api.RockBottomAPI;

public class ModGenerators
{
	public static void init()
	{
		RockBottomAPI.WORLD_GENERATORS.register(RockSolidAPILib.makeInternalRes("worldGenTin"), WorldGenTin.class);
		RockBottomAPI.WORLD_GENERATORS.register(RockSolidAPILib.makeInternalRes("worldGenIron"), WorldGenIron.class);
		RockBottomAPI.WORLD_GENERATORS.register(RockSolidAPILib.makeInternalRes("worldGenMagnesium"),
				WorldGenMagnesium.class);
		RockBottomAPI.WORLD_GENERATORS.register(RockSolidAPILib.makeInternalRes("worldGenRutile"),
				WorldGenRutile.class);
		RockBottomAPI.WORLD_GENERATORS.register(RockSolidAPILib.makeInternalRes("worldGenUranium"),
				WorldGenUranium.class);
		RockBottomAPI.WORLD_GENERATORS.register(RockSolidAPILib.makeInternalRes("worldGenWolframite"),
				WorldGenWolframite.class);

		//RockBottomAPI.WORLD_GENERATORS.register(RockSolidAPILib.makeInternalRes("worldGenLakes"), WorldGenLakes.class);
		//RockBottomAPI.WORLD_GENERATORS.register(RockSolidAPILib.makeInternalRes("worldGenCaves"), WorldGenCaves.class);

	}
}
