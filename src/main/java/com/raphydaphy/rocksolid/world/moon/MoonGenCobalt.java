package com.raphydaphy.rocksolid.world.moon;

import com.raphydaphy.rocksolid.init.ModMisc;
import com.raphydaphy.rocksolid.init.ModTiles;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;

import java.util.Collections;
import java.util.Set;

public class MoonGenCobalt extends MoonGenOre {
    public MoonGenCobalt() {
    }

    protected int getHighestGridPos() {
        return -3;
    }

    protected int getMaxAmount() {
        return 4;
    }

    protected int getClusterRadiusX() {
        return 4;
    }

    protected int getClusterRadiusY() {
        return 3;
    }

    protected TileState getOreState() {
        return ModTiles.LUNAR_COBALT_ORE.getDefState();
    }

    public int getPriority() {
        return 0;
    }

    protected Set<Biome> getAllowedBiomes() {
        return Collections.singleton(ModMisc.MOON_UNDERGROUND);
    }
}
