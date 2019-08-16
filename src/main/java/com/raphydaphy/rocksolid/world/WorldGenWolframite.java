package com.raphydaphy.rocksolid.world;

import com.raphydaphy.rocksolid.init.ModTiles;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.world.gen.WorldGenOre;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;

import java.util.Collections;
import java.util.Set;

public class WorldGenWolframite extends WorldGenOre {
    public WorldGenWolframite() {
    }

    protected int getHighestGridPos() {
        return -6;
    }

    protected int getMaxAmount() {
        return 5;
    }

    protected int getClusterRadiusX() {
        return 4;
    }

    protected int getClusterRadiusY() {
        return 3;
    }

    protected TileState getOreState() {
        return ModTiles.WOLFRAMITE_ORE.getDefState();
    }

    public int getPriority() {
        return 0;
    }

    protected Set<Biome> getAllowedBiomes() {
        return Collections.singleton(GameContent.BIOME_UNDERGROUND);
    }
}
