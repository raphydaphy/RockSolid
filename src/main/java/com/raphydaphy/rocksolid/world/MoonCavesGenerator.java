package com.raphydaphy.rocksolid.world;

import com.raphydaphy.rocksolid.init.ModMisc;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.INoiseGen;
import de.ellpeck.rockbottom.api.world.gen.IWorldGenerator;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class MoonCavesGenerator implements IWorldGenerator
{
	private INoiseGen a;

	public MoonCavesGenerator() {
	}

	public void initWorld(IWorld var1) {
		this.a = RockBottomAPI.getApiHandler().makeSimplexNoise(var1.getSeed());
	}

	public boolean shouldGenerate(IWorld world, IChunk var2) {
		return world.getSubName() != null && world.getSubName().equals(ModMisc.MOON_GENERATOR);
	}

	public void generate(IWorld var1, IChunk var2) {
		for(int var11 = 0; var11 < 32; ++var11) {
			for(int var3 = 0; var3 < 32; ++var3) {
				int var4 = var2.getX() + var11;
				int var5 = var2.getY() + var3;
				double var7;
				if ((var7 = Math.min(0.65D, -0.25D * (double)(var5 - var2.getAverageHeight(TileLayer.MAIN)))) > 0.0D && this.a.make2dNoise((double)var4 / 40.0D, (double)var5 / 10.0D) + this.a.make2dNoise((double)var4 / 30.0D, (double)var5 / 50.0D) * 0.5D + this.a.make2dNoise((double)var4 / 10.0D, (double)var5 / 100.0D) * 0.25D <= var7) {
					var2.setStateInner(var11, var3, GameContent.TILE_AIR.getDefState());
				}
			}
		}

	}

	public int getPriority() {
		return 1000;
	}
}
