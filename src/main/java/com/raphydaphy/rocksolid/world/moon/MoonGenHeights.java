package com.raphydaphy.rocksolid.world.moon;

import com.raphydaphy.rocksolid.init.ModMisc;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.HeightGen;

public class MoonGenHeights extends HeightGen {
    @Override
    public int getMinHeight(IWorld world) {
        return 0;
    }

    @Override
    public int getMaxHeight(IWorld world) {
        return 20;
    }

    @Override
    public int getMaxMountainHeight(IWorld world) {
        return 50;
    }

    @Override
    public int getNoiseScramble(IWorld world) {
        return 48381723;
    }

    @Override
    public int getPriority() {
        return 15000;
    }

    @Override
    public boolean shouldGenerate(IWorld world, IChunk chunk) {
        return world.getSubName() != null && world.getSubName().equals(ModMisc.MOON_WORLD);
    }
}
