package com.raphydaphy.rocksolid.world.moon;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.init.ModMisc;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.INoiseGen;
import de.ellpeck.rockbottom.api.world.gen.IWorldGenerator;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class MoonHeightGenerator implements IMoonGenerator
{
	public static final ResourceName a = RockSolid.createRes("heights");
	public INoiseGen b;

	public MoonHeightGenerator() {
	}

	public static int estimate(TileLayer var0, int x, INoiseGen var2, int var3, int var4, int var5) {
		double var6 = (double)x / 3125.0D;
		double var8 = ((var8 = 0.23D * var2.make2dNoise(var6 * 2.0D, 0.0D)) + 0.17D * var2.make2dNoise(var6 * 4.0D, 0.0D) + 1.0D * var2.make2dNoise(var6 * 16.0D, 0.0D)) / 1.4D;
		var3 = (int)(var8 * 1.8D * var8 * var8 * var8 * var8 * var8 * 45.0D);
		var8 = var2.make2dNoise((double)x / 100.0D, 0.0D) + var2.make2dNoise((double)x / 20.0D, 0.0D) * 2.0D;
		var3 = Math.min(45, Math.max(0, var3 + (int)(var8 / 3.5D * 20.0D)));
		if (var0 == TileLayer.BACKGROUND) {
			var3 -= Util.ceil(var2.make2dNoise((double)x / 10.0D, 0.0D) * 3.0D);
		}

		return var3;
	}

	public void initWorld(IWorld var1) {
		this.b = RockBottomAPI.getApiHandler().makeSimplexNoise(var1.getSeed());
	}

	public boolean shouldGenerate(IWorld world, IChunk var2) {
		return world.getSubName() != null && world.getSubName().equals(ModMisc.MOON_WORLD);
	}

	public void generate(IWorld var1, IChunk var2) {
		for(int x = 0; x < 32; ++x) {
			for(int y = 0; y < 32; ++y) {
				int realX = var2.getX() + x;
				int realY = var2.getY() + y;
				if (var1.getExpectedBiomeLevel(realX, realY) == GameContent.BIOME_LEVEL_UNDERGROUND)
				{
					double threshold;
					if ((threshold = Math.min(0.65D, -0.25D * (double) (realY - var2.getAverageHeight(TileLayer.MAIN)))) > 0.0D &&
							this.b.make2dNoise(
									(double) realX / 40.0D,
									(double) realY / 10.0D)
									+ this.b.make2dNoise(
									(double) realX / 30.0D,
									(double) realY / 50.0D) * 0.5D
									+ this.b.make2dNoise(
									(double) realX / 10.0D,
									(double) realY / 100.0D) * 0.25D <= threshold)
					{
						var2.setStateInner(x, y, GameContent.TILE_AIR.getDefState());
					}
				}
			}
		}

	}

	public int getPriority() {
		return 1000;
	}
}
