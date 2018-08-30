package com.raphydaphy.rocksolid.world.moon;

import com.raphydaphy.rocksolid.init.ModMisc;
import com.raphydaphy.rocksolid.init.ModTiles;
import de.ellpeck.rockbottom.api.Constants;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.Set;

public abstract class MoonGenOre implements IMoonGenerator
{
	protected final Random oreRandom = new Random();
	protected long oreSeed;

	@Override
	public void initWorld(IWorld world) {
		this.oreSeed = Util.scrambleSeed(Registries.WORLD_GENERATORS.getId(this.getClass()).hashCode(), world.getSeed());
	}

	@Override
	public boolean shouldGenerate(IWorld world, IChunk chunk) {
		return world.getSubName() != null && world.getSubName().equals(ModMisc.MOON_WORLD) && chunk.getGridY() <= this.getHighestGridPos() && chunk.getGridY() >= this.getLowestGridPos();
	}

	@Override
	public void generate(IWorld world, IChunk chunk) {
		this.oreRandom.setSeed(Util.scrambleSeed(chunk.getX(), chunk.getY(), this.oreSeed));
		Collection<Biome> allowedBiomes = this.getAllowedBiomes();
		Collection<TileState> allowedTiles = this.getAllwedTiles();

		int amount = this.oreRandom.nextInt(this.getMaxAmount() + 1);
		if (amount > 0) {
			int radX = this.getClusterRadiusX();
			int radY = this.getClusterRadiusY();
			int radXHalf = Util.ceil((double) radX / 2);
			int radYHalf = Util.ceil((double) radY / 2);

			for (int i = 0; i < amount; i++) {
				int startX = chunk.getX() + radX + this.oreRandom.nextInt(Constants.CHUNK_SIZE - radX * 2);
				int startY = chunk.getY() + radY + this.oreRandom.nextInt(Constants.CHUNK_SIZE - radY * 2);

				if (allowedBiomes.contains(world.getBiome(startX, startY))) {
					int thisRadX = this.oreRandom.nextInt(radXHalf) + radXHalf;
					int thisRadY = this.oreRandom.nextInt(radYHalf) + radYHalf;

					for (int x = -thisRadX; x <= thisRadX; x++) {
						for (int y = -thisRadY; y <= thisRadY; y++) {
							if (allowedTiles.contains(world.getState(startX + x, startY + y))) {
								if (this.oreRandom.nextInt(thisRadX) == x || this.oreRandom.nextInt(thisRadY) == y) {
									world.setState(startX + x, startY + y, this.getOreState());
								}
							}
						}
					}
				}
			}
		}
	}

	protected abstract int getHighestGridPos();

	protected int getLowestGridPos() {
		return Integer.MIN_VALUE;
	}

	protected abstract int getMaxAmount();

	protected abstract int getClusterRadiusX();

	protected abstract int getClusterRadiusY();

	protected abstract TileState getOreState();

	protected Set<Biome> getAllowedBiomes() {
		return ModMisc.MOON_BIOME_REGISTRY.values();
	}

	protected Set<TileState> getAllwedTiles() {
		return Collections.singleton(ModTiles.MOON_STONE.getDefState());
	}
}
