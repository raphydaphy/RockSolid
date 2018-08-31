package com.raphydaphy.rocksolid.world.moon;

import com.raphydaphy.rocksolid.init.ModMisc;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.IWorldGenerator;

public interface IMoonGenerator extends IWorldGenerator
{
	@Override
	default boolean shouldExistInWorld(IWorld world) {
		return world.getSubName() != null && world.getSubName().equals(ModMisc.MOON_WORLD);
	}
}
