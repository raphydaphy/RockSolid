package com.raphydaphy.rocksolid.world;

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

public class MoonHeightGenerator implements IWorldGenerator
{
	public static final ResourceName a = RockSolid.createRes("heights");
	public INoiseGen b;

	public MoonHeightGenerator() {
	}

	public static int a(TileLayer var0, int var1, INoiseGen var2, int var3, int var4, int var5) {
		double var6 = (double)var1 / 3125.0D;
		double var8 = ((var8 = 0.23D * var2.make2dNoise(var6 * 2.0D, 0.0D)) + 0.17D * var2.make2dNoise(var6 * 4.0D, 0.0D) + 1.0D * var2.make2dNoise(var6 * 16.0D, 0.0D)) / 1.4D;
		var3 = (int)(var8 * 1.8D * var8 * var8 * var8 * var8 * var8 * 45.0D);
		var8 = var2.make2dNoise((double)var1 / 100.0D, 0.0D) + var2.make2dNoise((double)var1 / 20.0D, 0.0D) * 2.0D;
		var3 = Math.min(45, Math.max(0, var3 + (int)(var8 / 3.5D * 10.0D)));
		if (var0 == TileLayer.BACKGROUND) {
			var3 -= Util.ceil(var2.make2dNoise((double)var1 / 10.0D, 0.0D) * 3.0D);
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
		for(int var11 = 0; var11 < 32; ++var11) {
			for(int var3 = 0; var3 < 32; ++var3) {
				int var4 = var2.getX() + var11;
				int var5 = var2.getY() + var3;
				double var7;
				if ((var7 = Math.min(0.65D, -0.25D * (double)(var5 - var2.getAverageHeight(TileLayer.MAIN)))) > 0.0D && this.b.make2dNoise((double)var4 / 40.0D, (double)var5 / 10.0D) + this.b.make2dNoise((double)var4 / 30.0D, (double)var5 / 50.0D) * 0.5D + this.b.make2dNoise((double)var4 / 10.0D, (double)var5 / 100.0D) * 0.25D <= var7) {
					var2.setStateInner(var11, var3, GameContent.TILE_AIR.getDefState());
				}
			}
		}

	}

	public int getPriority() {
		return 1000;
	}
}
