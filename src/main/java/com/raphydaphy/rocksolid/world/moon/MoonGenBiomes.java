package com.raphydaphy.rocksolid.world.moon;

import com.raphydaphy.rocksolid.init.ModMisc;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.world.IChunk;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.BiomeGen;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;
import de.ellpeck.rockbottom.api.world.gen.biome.level.BiomeLevel;

import java.util.Set;

public class MoonGenBiomes extends BiomeGen
{
	@Override
	public int getLevelTransition(IWorld world)
	{
		return 7;
	}

	@Override
	public int getBiomeTransition(IWorld world)
	{
		return 5;
	}

	@Override
	public int getBiomeBlobSize(IWorld world)
	{
		return 6;
	}

	@Override
	public int getLayerSeedScramble(IWorld world)
	{
		return 483671233;
	}

	@Override
	public int getNoiseSeedScramble(IWorld world)
	{
		return 937816543;
	}

	@Override
	public Set<Biome> getBiomesToGen(IWorld world)
	{
		return ModMisc.MOON_BIOME_REGISTRY;
	}

	@Override
	public Set<BiomeLevel> getLevelsToGen(IWorld world)
	{
		return Registries.BIOME_LEVEL_REGISTRY.values();
	}

	@Override
	public boolean shouldGenerate(IWorld world, IChunk chunk) {
		return world.getSubName() != null && world.getSubName().equals(ModMisc.MOON_WORLD);
	}
}
