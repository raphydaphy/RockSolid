package com.raphydaphy.rocksolid.world;

import com.google.common.base.Preconditions;
import com.raphydaphy.rocksolid.init.ModMisc;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.SubWorldInitializer;
import de.ellpeck.rockbottom.api.world.gen.IWorldGenerator;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;
import de.ellpeck.rockbottom.api.world.gen.biome.level.BiomeLevel;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.Collections;
import java.util.List;

public class MoonWorldInitializer extends SubWorldInitializer
{
	private MoonHeightGenerator heightGenerator;
	private MoonBiomeGenerator biomeGenerator;

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
	public Biome getExpectedBiome(IWorld subWorld, int x, int y)
	{
		return biomeGenerator.getExpectedBiome(subWorld, x, y, 0);
	}

	@Override
	public BiomeLevel getExpectedBiomeLevel(IWorld subWorld, int x, int y)
	{
		return biomeGenerator.getBiomeLevel(subWorld, x, y, 0);
	}

	@Override
	public int getExpectedSurfaceHeight(IWorld subWorld, TileLayer layer, int x)
	{
		MoonHeightGenerator var4 = this.heightGenerator;
		return MoonHeightGenerator.a(layer, x, var4.b, 0, 10, 45);
	}

	@Override
	public void onGeneratorsInitialized(IWorld subWorld)
	{
		biomeGenerator = (MoonBiomeGenerator)Preconditions.checkNotNull(subWorld.getGenerator(ModMisc.MOON_BIOME_GENERATOR), "RockSolid couldn't find the moon biome generator!");
		heightGenerator = (MoonHeightGenerator)Preconditions.checkNotNull(subWorld.getGenerator(ModMisc.MOON_HEIGHTS_GENERATOR), "RockSolid couldn't find the moon height generator!");
	}

	@Override
	public void update(IWorld subWorld, IGameInstance game)
	{

	}

	@Override
	public boolean shouldGenerateHere(IWorld subWorld, ResourceName name, IWorldGenerator generator)
	{
		return name.getDomain().equals("rocksolid");
	}
}
