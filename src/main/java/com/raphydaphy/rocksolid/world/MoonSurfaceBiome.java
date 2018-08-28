package com.raphydaphy.rocksolid.world;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.init.ModTiles;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.INoiseGen;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;
import de.ellpeck.rockbottom.api.world.gen.biome.BiomeBasic;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.Random;

public class MoonSurfaceBiome extends BiomeBasic
{
	public MoonSurfaceBiome()
	{
		super(RockSolid.createRes("moon_biome"), 1000, GameContent.BIOME_LEVEL_SURFACE);
	}

	public static TileState a(TileLayer var0, int var1, int var2, int var3)
	{
		if (var0 == TileLayer.MAIN || var0 == TileLayer.BACKGROUND)
		{
			if (var1 == var2 && var0 == TileLayer.MAIN)
			{
				return GameContent.TILE_GRASS.getDefState();
			}

			if (var1 <= var2)
			{
				if (var1 >= var3)
				{
					//return GameContent.TILE_SOIL.getDefState();
				}

				return ModTiles.MOON_STONE.getDefState();
			}
		}

		return GameContent.TILE_AIR.getDefState();
	}

	public final TileState getState(IWorld var1, IChunk var2, int var3, int var4, TileLayer var5, INoiseGen var6, int var7)
	{
		int var8 = var7 - Util.ceil(var6.make2dNoise((double) (var2.getX() + var3) / 5.0D, 0.0D) * 3.0D) - 2;
		return a(var5, var2.getY() + var4, var7, var8);
	}

	public final boolean hasGrasslandDecoration()
	{
		return false;
	}

	public final float getFlowerChance()
	{
		return 0F;
	}

	public final float getPebbleChance()
	{
		return 0.4F;
	}

	public final boolean canTreeGrow(IWorld var1, IChunk var2, int var3, int var4)
	{
		return var4 > 0 && var2.getStateInner(var3, var4 - 1).getTile().canKeepPlants(var1, var2.getX() + var3, var2.getY() + var4, TileLayer.MAIN);
	}

	public final Biome getVariationToGenerate(IWorld var1, int var2, int var3, int var4, Random var5)
	{
		double var6 = Math.max(0.0D, Math.min(1.0D, (double) (var4 - 20) / 5.0D));
		var5.setSeed(Util.scrambleSeed(var2, var3, var1.getSeed()) + 12382342L);
		//return (Biome)(var5.nextDouble() < var6 ? GameContent.BIOME_COLD_GRASSLAND : this);
		return this;
	}

	@Override
	public final TileState getFillerTile(IWorld var1, IChunk var2, int var3, int var4)
	{
		return ModTiles.MOON_STONE.getDefState();
	}

	public final float getLakeChance(IWorld var1, IChunk var2)
	{
		return 0F;
	}
}
