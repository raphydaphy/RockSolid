package com.raphydaphy.rocksolid.world;

import com.raphydaphy.rocksolid.init.ModTiles;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.world.gen.WorldGenOre;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;

import java.util.Collections;
import java.util.Set;

public class WorldGenMagnesium extends WorldGenOre {
    public WorldGenMagnesium() {
    }

    protected int getHighestGridPos() {
        return -5;
    }

    protected int getMaxAmount() {
        return 7;
    }

    protected int getClusterRadiusX() {
        return 4;
    }

    protected int getClusterRadiusY() {
        return 3;
    }

    protected TileState getOreState() {
        return ModTiles.MAGNESIUM_ORE.getDefState();
    }

    public int getPriority() {
        return 0;
    }

    protected Set<Biome> getAllowedBiomes() {
        return Collections.singleton(GameContent.BIOME_UNDERGROUND);
    }
}
