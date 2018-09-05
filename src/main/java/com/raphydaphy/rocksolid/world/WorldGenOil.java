package com.raphydaphy.rocksolid.world;


import com.raphydaphy.rocksolid.init.ModTiles;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.IWorldGenerator;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.Random;

public class WorldGenOil implements IWorldGenerator
{
	private final Random a = new Random();

	public WorldGenOil()
	{
	}

	public boolean shouldGenerate(IWorld var1, IChunk var2)
	{
		return var2.getMostProminentBiome() == GameContent.BIOME_UNDERGROUND && var2.getFlatness(TileLayer.MAIN) >= 0.5F;
	}

	public void generate(IWorld var1, IChunk var2)
	{
		this.a.setSeed(Util.scrambleSeed(var2.getGridX() + 4931, var2.getGridY() - 3947, var1.getSeed() + 398473) - 1293473);
		if (this.a.nextBoolean() && this.a.nextFloat() >= 0.7f)
		{
			int var3 = this.a.nextInt(15) + 8;
			int var4;
			int var5 = (var4 = this.a.nextInt(32 - var3 - 2) + 1) + var3;
			int var6;
			if ((var6 = Math.min(var2.getHeightInner(TileLayer.MAIN, var4 - 1), var2.getHeightInner(TileLayer.MAIN, var5 + 1)) - 1) > 2 && var6 < 29)
			{
				int var7 = Util.clamp(this.a.nextInt(var6 - 2), 3, 5);

				for (int var8 = var4; var8 <= var5; ++var8)
				{
					int var9 = Util.ceil((double) ((float) var7 * (1.0F - Math.abs((float) (var8 - var4) - (float) var3 / 2.0F) / (float) var3 * 2.0F))) - this.a.nextInt(2);

					int var10;
					for (var10 = var6 - var9 + 1; var10 <= var6; ++var10)
					{
						var2.setStateInner(var8, var10, GameContent.TILE_AIR.getDefState());
						var2.setStateInner(TileLayer.LIQUIDS, var8, var10, ModTiles.OIL.getFullState());
					}

					if (var2.getStateInner(var8, var6 - var9).getTile().canLiquidSpreadInto(var1, var8, var6 - var9, ModTiles.OIL))
					{
						var2.setStateInner(var8, var6 - var9, var2.getBiomeInner(var8, var6 - var9).getFillerTile(var1, var2, var8, var6 - var9));
					}

					for (var10 = this.a.nextInt(3) + 1; var10 > 0; --var10)
					{
						var2.setStateInner(var8, var6 + var10, GameContent.TILE_AIR.getDefState());
					}
				}
			}
		}

	}

	public int getPriority()
	{
		return 200;
	}
}