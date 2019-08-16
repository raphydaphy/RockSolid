package com.raphydaphy.rocksolid.world.moon;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.init.ModTiles;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.INoiseGen;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class MoonSurfaceBiome extends MoonBiome {
    public MoonSurfaceBiome() {
        super(RockSolid.createRes("moon_surface_biome"), 1000, GameContent.BIOME_LEVEL_SURFACE);
    }

    public static TileState a(TileLayer var0, int var1, int var2, int var3) {
        if (var0 == TileLayer.MAIN || var0 == TileLayer.BACKGROUND) {
            if (var1 == var2 && var0 == TileLayer.MAIN) {
                return ModTiles.MOON_TURF.getDefState();
            }

            if (var1 <= var2) {
                if (var1 >= var3) {
                    return ModTiles.MOON_TURF.getDefState();
                }

                return ModTiles.MOON_STONE.getDefState();
            }
        }

        return GameContent.TILE_AIR.getDefState();
    }

    public final TileState getState(IWorld var1, IChunk var2, int var3, int var4, TileLayer var5, INoiseGen var6, int var7) {
        int var8 = var7 - Util.ceil(var6.make2dNoise((double) (var2.getX() + var3) / 5.0D, 0.0D) * 3.0D) - 2;
        return a(var5, var2.getY() + var4, var7, var8);
    }

    @Override
    public final float getPebbleChance() {
        return 0.25F;
    }

    @Override
    public final TileState getFillerTile(IWorld var1, IChunk var2, int var3, int var4) {
        return ModTiles.MOON_TURF.getDefState();
    }

    @Override
    public final float getLakeChance(IWorld var1, IChunk var2) {
        return 0F;
    }

}
