package com.raphydaphy.rocksolid.world;

import de.ellpeck.rockbottom.api.Constants;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.IWorldGenerator;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;

import java.util.*;

public class WorldGenModOres implements IWorldGenerator
{
	private static final List<OreGen> ores = new ArrayList<>();
	private final Random oreRandom = new Random();

	public static void registerOreGen(OreGen gen)
	{
		ores.add(gen);
	}

	@Override
	public boolean shouldGenerate(IWorld world, IChunk chunk)
	{
		return true;
	}

	@Override
	public void generate(IWorld world, IChunk chunk)
	{
		for (OreGen ore : ores)
		{
			if (chunk.getGridY() <= ore.highestGridPos && chunk.getGridY() >= ore.lowestGridPos)
			{
				int oreNum = (ores.indexOf(ore) + 1) * 7919;
				this.oreRandom.setSeed(Util.scrambleSeed(chunk.getX() * oreNum, chunk.getY() * oreNum, world.getSeed() * oreNum));

				if (oreRandom.nextInt(ore.rarity) == 0)
				{
					Collection<Biome> allowedBiomes = this.getAllowedBiomes();

					int amount = this.oreRandom.nextInt(ore.maxAmount) + 1;
					if (amount > 0)
					{
						int radX = ore.radiusX;
						int radY = ore.radiusY;
						int radXHalf = Util.ceil((double) radX / 2);
						int radYHalf = Util.ceil((double) radY / 2);

						for (int i = 0; i < amount; i++)
						{
							int startX = chunk.getX() + radX + this.oreRandom.nextInt(Constants.CHUNK_SIZE - radX * 2);
							int startY = chunk.getY() + radY + this.oreRandom.nextInt(Constants.CHUNK_SIZE - radY * 2);

							Biome biome = world.getBiome(startX, startY);
							if (allowedBiomes.contains(biome))
							{
								int thisRadX = this.oreRandom.nextInt(radXHalf) + radXHalf;
								int thisRadY = this.oreRandom.nextInt(radYHalf) + radYHalf;

								for (int x = -thisRadX; x <= thisRadX; x++)
								{
									for (int y = -thisRadY; y <= thisRadY; y++)
									{
										if ((this.oreRandom.nextInt(thisRadX) == x || this.oreRandom.nextInt(thisRadY) == y) && world.getState(startX + x, startY + y).getTile().equals(GameContent.TILE_STONE))
										{
											world.setState(startX + x, startY + y, ore.ore);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public int getPriority()
	{
		return -210;
	}

	private Set<Biome> getAllowedBiomes()
	{
		return Registries.BIOME_REGISTRY.getUnmodifiable().values();
	}

	public static class OreGen
	{
		private final TileState ore;
		private final int highestGridPos;
		private final int lowestGridPos;
		private final int maxAmount;
		private final int radiusX;
		private final int radiusY;
		private final int rarity;


		public OreGen(Tile ore, int highestGridPos, int lowestGridPos, int maxAmount, int radiusX, int radiusY, int rarity)
		{
			this(ore.getDefState(), highestGridPos, lowestGridPos, maxAmount, radiusX, radiusY, rarity);
		}

		public OreGen(TileState ore, int highestGridPos, int lowestGridPos, int maxAmount, int radiusX, int radiusY, int rarity)
		{
			this.ore = ore;
			this.highestGridPos = highestGridPos;
			this.lowestGridPos = lowestGridPos;
			this.maxAmount = maxAmount;
			this.radiusX = radiusX;
			this.radiusY = radiusY;
			this.rarity = rarity;
		}
	}
}
