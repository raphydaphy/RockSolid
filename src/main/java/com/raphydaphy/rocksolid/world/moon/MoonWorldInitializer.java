package com.raphydaphy.rocksolid.world.moon;

import com.google.common.base.Preconditions;
import com.raphydaphy.rocksolid.init.ModMisc;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.SubWorldInitializer;
import de.ellpeck.rockbottom.api.world.gen.BiomeGen;
import de.ellpeck.rockbottom.api.world.gen.HeightGen;
import de.ellpeck.rockbottom.api.world.gen.IWorldGenerator;

import java.util.Collections;
import java.util.List;

public class MoonWorldInitializer extends SubWorldInitializer
{
	private MoonHeightGen heightGenerator;
	private MoonBiomeGen biomeGenerator;

	public MoonWorldInitializer(ResourceName name)
	{
		super(name);
	}

	@Override
	public int getSeedModifier()
	{
		return 247835941;
	}

	@Override
	public List<Pos2> getDefaultPersistentChunks(IWorld subWorld)
	{
		return Collections.emptyList();
	}

	@Override
	public void onSave(IWorld subWorld)
	{

	}

	@Override
	public HeightGen initHeightGen(IWorld subWorld)
	{
		return heightGenerator;
	}

	@Override
	public BiomeGen initBiomeGen(IWorld subWorld)
	{
		return biomeGenerator;
	}

	@Override
	public void onGeneratorsInitialized(IWorld subWorld)
	{
		biomeGenerator = (MoonBiomeGen) Preconditions.checkNotNull(subWorld.getGenerator(ModMisc.MOON_BIOME_GENERATOR), "RockSolid couldn't find the moon biome generator!");
		heightGenerator = (MoonHeightGen) Preconditions.checkNotNull(subWorld.getGenerator(ModMisc.MOON_HEIGHTS_GENERATOR), "RockSolid couldn't find the moon height generator!");
	}

	@Override
	public void update(IWorld subWorld, IGameInstance game)
	{

	}

	@Override
	public boolean shouldGenerateHere(IWorld subWorld, ResourceName name, IWorldGenerator generator)
	{
		return generator instanceof IMoonGenerator;
	}
}
