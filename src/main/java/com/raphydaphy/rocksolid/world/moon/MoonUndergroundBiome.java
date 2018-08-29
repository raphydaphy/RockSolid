package com.raphydaphy.rocksolid.world.moon;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.init.ModTiles;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.INoiseGen;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class MoonUndergroundBiome extends MoonBiome
{
	public MoonUndergroundBiome()
	{
		super(RockSolid.createRes("moon_underground_biome"), 1000, GameContent.BIOME_LEVEL_UNDERGROUND);
	}

	@Override
	public final TileState getState(IWorld var1, IChunk var2, int var3, int var4, TileLayer var5, INoiseGen var6, int var7) {
		return var5 != TileLayer.MAIN && var5 != TileLayer.BACKGROUND ? GameContent.TILE_AIR.getDefState() : ModTiles.MOON_STONE.getDefState();
	}

	@Override
	public final TileState getFillerTile(IWorld var1, IChunk var2, int var3, int var4) {
		return ModTiles.MOON_STONE.getDefState();
	}
}
