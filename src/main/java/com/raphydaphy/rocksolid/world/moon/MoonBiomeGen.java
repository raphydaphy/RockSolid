package com.raphydaphy.rocksolid.world.moon;

import com.raphydaphy.rocksolid.init.ModMisc;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.gen.BiomeGen;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;
import de.ellpeck.rockbottom.api.world.gen.biome.level.BiomeLevel;

import java.util.Set;

public class MoonBiomeGen extends BiomeGen implements IMoonGenerator
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
		return Set.of(GameContent.BIOME_LEVEL_SKY, GameContent.BIOME_LEVEL_SURFACE, GameContent.BIOME_LEVEL_UNDERGROUND);
	}
}
