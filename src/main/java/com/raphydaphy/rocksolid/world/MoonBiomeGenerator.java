package com.raphydaphy.rocksolid.world;

import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.init.ModMisc;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.INoiseGen;
import de.ellpeck.rockbottom.api.world.gen.IWorldGenerator;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;
import de.ellpeck.rockbottom.api.world.gen.biome.level.BiomeLevel;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.*;

public class MoonBiomeGenerator implements IWorldGenerator {
	public static final ResourceName a = RockSolid.createRes("moon_biomes");
	private final Map b = new HashMap();
	private final ListMultimap c = ArrayListMultimap.create();
	private final Map d = new HashMap();
	private final long[] e = new long[6];
	private final Random f = new Random();
	private INoiseGen g;

	public MoonBiomeGenerator() {
	}

	public void initWorld(IWorld var1) {
		this.g = RockBottomAPI.getApiHandler().makeSimplexNoise(Util.scrambleSeed(12396837, var1.getSeed()));
		Random var2 = new Random(Util.scrambleSeed(827398433, var1.getSeed()));

		int var3;
		for(var3 = 0; var3 < 6; ++var3) {
			this.e[var3] = var2.nextLong();
		}

		Iterator var9 = Registries.BIOME_REGISTRY.values().iterator();

		Iterator var5;
		label51:
		while(var9.hasNext()) {
			Biome var7;
			List var4 = (var7 = (Biome)var9.next()).getGenerationLevels(var1);
			var5 = Registries.BIOME_LEVEL_REGISTRY.values().iterator();

			while(true) {
				BiomeLevel var6;
				do {
					if (!var5.hasNext()) {
						continue label51;
					}

					var6 = (BiomeLevel)var5.next();
				} while(!var4.contains(var6) && !var6.getAdditionalGenBiomes(var1).contains(var7));

				this.c.put(var6, var7);
			}
		}

		var9 = this.c.keySet().iterator();

		while(var9.hasNext()) {
			BiomeLevel var8 = (BiomeLevel)var9.next();
			int var10 = 0;

			Biome var11;
			for(var5 = this.c.get(var8).iterator(); var5.hasNext(); var10 += var11.getWeight(var1)) {
				var11 = (Biome)var5.next();
			}

			this.d.put(var8, var10);
		}

		Preconditions.checkState((var3 = this.c.keySet().size()) == this.d.size(), "BiomesPerLevel and TotalWeights are out of sync!");
		RockBottomAPI.logger().info("Initialized " + var3 + " biome levels to generate for world " + var1.getName());
	}

	public boolean shouldGenerate(IWorld var1, IChunk var2) {
		if (var1.getSubName() == null)
		{
			return false;
		}
		return var1.getSubName().equals(ModMisc.MOON_WORLD);
	}

	public void generate(IWorld var1, IChunk var2) {
		for(int var3 = 0; var3 < 32; ++var3) {
			HashMap var4 = new HashMap();
			Iterator var5 = TileLayer.getAllLayers().iterator();

			while(var5.hasNext()) {
				TileLayer var6 = (TileLayer)var5.next();
				var4.put(var6, var1.getExpectedSurfaceHeight(var6, var2.getX() + var3));
			}

			for(int var10 = 0; var10 < 32; ++var10) {
				Biome var11 = this.a(var1, var2.getX() + var3, var2.getY() + var10, (Integer)var4.get(TileLayer.MAIN));
				var2.setBiomeInner(var3, var10, var11);
				INoiseGen var7 = (INoiseGen)this.b.computeIfAbsent(var11, (var1x) -> {
					return RockBottomAPI.getApiHandler().makeSimplexNoise(((Biome)var1x).getBiomeSeed(var1));
				});
				Iterator var8 = TileLayer.getAllLayers().iterator();

				while(var8.hasNext()) {
					TileLayer var9 = (TileLayer)var8.next();
					var2.setStateInner(var9, var3, var10, var11.getState(var1, var2, var3, var10, var9, var7, (Integer)var4.get(var9)));
				}
			}
		}

	}

	public final Biome a(IWorld var1, int var2, int var3, int var4) {
		BiomeLevel var5 = this.b(var1, var2, var3, var4);
		this.f.setSeed(Util.scrambleSeed(var2, var3, var1.getSeed()));
		int var6 = this.f.nextInt(5) - 2;
		if (var5.isForcedSideBySide()) {
			return this.a(var1, var2, var3, var2 + var6, 0, var5, var4);
		} else {
			int var7 = this.f.nextInt(5) - 2;
			return this.a(var1, var2, var3, var2 + var6, var3 + var7, var5, var4);
		}
	}

	private Biome a(IWorld var1, int var2, int var3, int var4, int var5, BiomeLevel var6, int var7) {
		var4 = Util.ceil((double)(Integer)this.d.get(var6) * this.a(var1, var4, var5));
		Biome var11 = null;
		int var8 = 0;
		Iterator var9 = this.c.get(var6).iterator();

		while(var9.hasNext()) {
			Biome var10 = (Biome)var9.next();
			if ((var8 += var10.getWeight(var1)) >= var4) {
				var11 = var10;
				break;
			}
		}

		if (var11 == null) {
			RockBottomAPI.logger().warning("Couldn't find a biome to generate for " + var2 + ", " + var3 + " with level " + var6.getName());
			var11 = GameContent.BIOME_SKY;
		}

		return var11.getVariationToGenerate(var1, var2, var3, var7, this.f);
	}

	public final BiomeLevel b(IWorld var1, int var2, int var3, int var4) {
		BiomeLevel var5;
		int var6;
		int var7;
		if (Math.abs((var6 = (var5 = this.c(var1, var2, var3, var4)).getMaxY(var1, var2, var3, var4)) - var3) <= 7) {
			var7 = Util.floor(7.0D * this.g.make2dNoise((double)var2 / 10.0D, (double)var6));
			if (var3 >= var6 - var7 + Util.ceil(3.5D)) {
				return this.c(var1, var2, var6 + 1, var4);
			}
		} else if (Math.abs((var7 = var5.getMinY(var1, var2, var3, var4)) - var3) <= 7) {
			var6 = Util.ceil(7.0D * (1.0D - this.g.make2dNoise((double)var2 / 10.0D, (double)var7)));
			if (var3 <= var7 + var6 - Util.floor(3.5D)) {
				return this.c(var1, var2, var7 - 1, var4);
			}
		}

		return var5;
	}

	private BiomeLevel c(IWorld var1, int var2, int var3, int var4) {
		BiomeLevel var5 = null;
		Iterator var6 = this.c.keySet().iterator();

		while(true) {
			BiomeLevel var7;
			do {
				do {
					do {
						if (!var6.hasNext()) {
							return var5;
						}

						var7 = (BiomeLevel)var6.next();
					} while(var3 < var7.getMinY(var1, var2, var3, var4));
				} while(var3 > var7.getMaxY(var1, var2, var3, var4));
			} while(var5 != null && var7.getPriority() < var5.getPriority());

			var5 = var7;
		}
	}

	private double a(IWorld var1, int var2, int var3) {
		Pos2 var4 = this.a(var2, var3, var1);
		this.f.setSeed(Util.scrambleSeed(var4.getX(), var4.getY(), var1.getSeed()) + var1.getSeed());
		return this.f.nextDouble();
	}

	private Pos2 a(int var1, int var2, IWorld var3) {
		Pos2 var4 = new Pos2(var1, var2);

		for(var2 = 0; var2 < 6; ++var2) {
			var4 = this.a(var4, this.e[var2], var3);
		}

		return var4;
	}

	private Pos2 a(Pos2 var1, long var2, IWorld var4) {
		boolean var5 = (var1.getX() & 1) == 0;
		boolean var6 = (var1.getY() & 1) == 0;
		int var7 = var1.getX() / 2;
		int var8 = var1.getY() / 2;
		if (var5 && var6) {
			return new Pos2(var7, var8);
		} else {
			this.f.setSeed(Util.scrambleSeed(var1.getX(), var1.getY(), var4.getSeed()) + var2);
			int var10 = this.f.nextBoolean() ? (var1.getX() < 0 ? -1 : 1) : 0;
			int var9 = this.f.nextBoolean() ? (var1.getY() < 0 ? -1 : 1) : 0;
			if (var5) {
				return new Pos2(var7, var8 + var9);
			} else {
				return var6 ? new Pos2(var7 + var10, var8) : new Pos2(var7 + var10, var8 + var9);
			}
		}
	}

	public int getPriority() {
		return 10000;
	}
}

