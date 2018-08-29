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
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.INoiseGen;
import de.ellpeck.rockbottom.api.world.gen.IWorldGenerator;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;
import de.ellpeck.rockbottom.api.world.gen.biome.level.BiomeLevel;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.*;

public class MoonBiomeGenerator implements IWorldGenerator
{
	private final Map<Biome, INoiseGen> b = new HashMap<>();
	private final ListMultimap<BiomeLevel, Biome> c = ArrayListMultimap.create();
	private final Map<BiomeLevel, Integer> d = new HashMap<>();
	private final long[] e = new long[6];
	private final Random rand = new Random();
	private INoiseGen noiseGenerator;

	public MoonBiomeGenerator()
	{
	}

	public void initWorld(IWorld var1)
	{
		this.noiseGenerator = RockBottomAPI.getApiHandler().makeSimplexNoise(Util.scrambleSeed(12396837, var1.getSeed()));
		Random var2 = new Random(Util.scrambleSeed(827398433, var1.getSeed()));

		int var3;
		for (var3 = 0; var3 < 6; ++var3)
		{
			this.e[var3] = var2.nextLong();
		}

		Iterator<Biome> biomeIterator = ModMisc.MOON_BIOME_REGISTRY.values().iterator();

		Iterator var5;
		biomeLoop:
		while (biomeIterator.hasNext())
		{
			Biome var7;
			List<BiomeLevel> var4 = (var7 = biomeIterator.next()).getGenerationLevels(var1);
			var5 = Registries.BIOME_LEVEL_REGISTRY.values().iterator();

			while (true)
			{
				BiomeLevel var6;
				do
				{
					if (!var5.hasNext())
					{
						continue biomeLoop;
					}

					var6 = (BiomeLevel) var5.next();
				} while (!var4.contains(var6) && !var6.getAdditionalGenBiomes(var1).contains(var7));

				this.c.put(var6, var7);
			}
		}

		for (BiomeLevel var8 : this.c.keySet())
		{
			int var10 = 0;

			Biome var11;
			for (var5 = this.c.get(var8).iterator(); var5.hasNext(); var10 += var11.getWeight(var1))
			{
				var11 = (Biome) var5.next();
			}

			this.d.put(var8, var10);
		}

		Preconditions.checkState((var3 = this.c.keySet().size()) == this.d.size(), "BiomesPerLevel and TotalWeights are out of sync!");
		RockSolid.getLogger().info("Initialized " + var3 + " biome levels to generate on the moon");
	}

	public boolean shouldGenerate(IWorld var1, IChunk var2)
	{
		if (var1.getSubName() == null)
		{
			return false;
		}
		return var1.getSubName().equals(ModMisc.MOON_GENERATOR);
	}

	public void generate(IWorld var1, IChunk var2)
	{
		for (int var3 = 0; var3 < 32; ++var3)
		{
			HashMap<TileLayer, Integer> layerMap = new HashMap<>();

			for (TileLayer layer : TileLayer.getAllLayers())
			{
				layerMap.put(layer, var1.getExpectedSurfaceHeight(layer, var2.getX() + var3));
			}

			for (int var10 = 0; var10 < 32; ++var10)
			{
				Biome var11 = this.getExpectedBiome(var1, var2.getX() + var3, var2.getY() + var10, layerMap.get(TileLayer.MAIN));
				var2.setBiomeInner(var3, var10, var11);
				INoiseGen biomeNoiseGen = this.b.computeIfAbsent(var11, (var1x) -> RockBottomAPI.getApiHandler().makeSimplexNoise((var1x).getBiomeSeed(var1)));

				for (TileLayer layer : TileLayer.getAllLayers())
				{
					var2.setStateInner(layer, var3, var10, var11.getState(var1, var2, var3, var10, layer, biomeNoiseGen, layerMap.get(layer)));
				}
			}
		}

	}

	public Biome getExpectedBiome(IWorld world, int x, int y, int surface)
	{
		BiomeLevel var5 = this.getBiomeLevel(world, x, y, surface);
		this.rand.setSeed(Util.scrambleSeed(x, y, world.getSeed()));
		int var6 = this.rand.nextInt(5) - 2;
		if (var5.isForcedSideBySide())
		{
			return this.getExpectedBiome(world, x, y, x + var6, 0, var5, surface);
		} else
		{
			int var7 = this.rand.nextInt(5) - 2;
			return this.getExpectedBiome(world, x, y, x + var6, y + var7, var5, surface);
		}
	}

	private Biome getExpectedBiome(IWorld var1, int var2, int var3, int var4, int var5, BiomeLevel var6, int var7)
	{
		var4 = Util.ceil((double) (Integer) this.d.get(var6) * this.dontKnow(var1, var4, var5));
		Biome var11 = null;
		int var8 = 0;

		for (Biome var10 : this.c.get(var6))
		{
			if ((var8 += var10.getWeight(var1)) >= var4)
			{
				var11 = var10;
				break;
			}
		}

		if (var11 == null)
		{
			RockSolid.getLogger().warning("Couldn't find a biome to generate for " + var2 + ", " + var3 + " with level " + var6.getName());
			var11 = GameContent.BIOME_SKY;
		}

		return var11.getVariationToGenerate(var1, var2, var3, var7, this.rand);
	}

	public BiomeLevel getBiomeLevel(IWorld world, int x, int y, int surfaceHeight)
	{
		BiomeLevel var5;
		int var6;
		int var7;
		if (Math.abs((var6 = (var5 = this.getBiomeLevelWithLessArguments(world, x, y, surfaceHeight)).getMaxY(world, x, y, surfaceHeight)) - y) <= 7)
		{
			var7 = Util.floor(7.0D * this.noiseGenerator.make2dNoise((double) x / 10.0D, (double) var6));
			if (y >= var6 - var7 + Util.ceil(3.5D))
			{
				return this.getBiomeLevelWithLessArguments(world, x, var6 + 1, surfaceHeight);
			}
		} else if (Math.abs((var7 = var5.getMinY(world, x, y, surfaceHeight)) - y) <= 7)
		{
			var6 = Util.ceil(7.0D * (1.0D - this.noiseGenerator.make2dNoise((double) x / 10.0D, (double) var7)));
			if (y <= var7 + var6 - Util.floor(3.5D))
			{
				return this.getBiomeLevelWithLessArguments(world, x, var7 - 1, surfaceHeight);
			}
		}

		return var5;
	}

	private BiomeLevel getBiomeLevelWithLessArguments(IWorld world, int x, int y, int surfaceHeight)
	{
		BiomeLevel var5 = null;
		Iterator<BiomeLevel> var6 = this.c.keySet().iterator();

		while (true)
		{
			BiomeLevel var7;
			do
			{
				do
				{
					do
					{
						if (!var6.hasNext())
						{
							return var5;
						}

						var7 = var6.next();
					} while (y < var7.getMinY(world, x, y, surfaceHeight));
				} while (y > var7.getMaxY(world, x, y, surfaceHeight));
			} while (var5 != null && var7.getPriority() < var5.getPriority());

			var5 = var7;
		}
	}

	private double dontKnow(IWorld var1, int var2, int var3)
	{
		Pos2 var4 = this.dontKnow(var2, var3, var1);
		this.rand.setSeed(Util.scrambleSeed(var4.getX(), var4.getY(), var1.getSeed()) + var1.getSeed());
		return this.rand.nextDouble();
	}

	private Pos2 dontKnow(int var1, int var2, IWorld var3)
	{
		Pos2 var4 = new Pos2(var1, var2);

		for (var2 = 0; var2 < 6; ++var2)
		{
			var4 = this.getExpectedBiome(var4, this.e[var2], var3);
		}

		return var4;
	}

	private Pos2 getExpectedBiome(Pos2 var1, long var2, IWorld var4)
	{
		boolean var5 = (var1.getX() & 1) == 0;
		boolean var6 = (var1.getY() & 1) == 0;
		int var7 = var1.getX() / 2;
		int var8 = var1.getY() / 2;
		if (var5 && var6)
		{
			return new Pos2(var7, var8);
		} else
		{
			this.rand.setSeed(Util.scrambleSeed(var1.getX(), var1.getY(), var4.getSeed()) + var2);
			int var10 = this.rand.nextBoolean() ? (var1.getX() < 0 ? -1 : 1) : 0;
			int var9 = this.rand.nextBoolean() ? (var1.getY() < 0 ? -1 : 1) : 0;
			if (var5)
			{
				return new Pos2(var7, var8 + var9);
			} else
			{
				return var6 ? new Pos2(var7 + var10, var8) : new Pos2(var7 + var10, var8 + var9);
			}
		}
	}

	public int getPriority()
	{
		return 10000;
	}
}

