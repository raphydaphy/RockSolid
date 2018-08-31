package com.raphydaphy.rocksolid.world.moon;

import com.google.common.base.Preconditions;
import com.raphydaphy.rocksolid.init.ModMisc;
import com.raphydaphy.rocksolid.render.MoonSkyRenderer;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.SubWorldInitializer;
import de.ellpeck.rockbottom.api.world.gen.BiomeGen;
import de.ellpeck.rockbottom.api.world.gen.HeightGen;
import de.ellpeck.rockbottom.api.world.gen.IWorldGenerator;

import java.util.*;

public class MoonWorldInitializer extends SubWorldInitializer
{
	private MoonSkyRenderer skyRenderer = new MoonSkyRenderer();
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
		return (MoonGenHeights) Preconditions.checkNotNull(subWorld.getGenerator(ModMisc.MOON_HEIGHTS_GENERATOR), "RockSolid failed to initialize the moon height generator");
	}

	@Override
	public BiomeGen initBiomeGen(IWorld subWorld)
	{
		return (MoonGenBiomes) Preconditions.checkNotNull(subWorld.getGenerator(ModMisc.MOON_BIOME_GENERATOR), "RockSolid failed to initialize the moon biome generator");
	}

	@Override
	public void onGeneratorsInitialized(IWorld subWorld)
	{

	}

	@Override
	public void update(IWorld subWorld, IGameInstance game)
	{

	}

	@Override
	public boolean shouldGenerateHere(IWorld subWorld, ResourceName name, IWorldGenerator generator)
	{
		return generator instanceof IMoonGenerator || generator instanceof MoonGenHeights || generator instanceof MoonGenBiomes;
	}

	@Override
	public boolean renderSky(IWorld subWorld, IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, AbstractEntityPlayer player, double width, double height) {
		skyRenderer.a(game, manager, g, world, player);
		return false;
	}
}
