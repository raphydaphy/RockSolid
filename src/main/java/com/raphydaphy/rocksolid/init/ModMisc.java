package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.item.ItemBucket;
import com.raphydaphy.rocksolid.network.*;
import com.raphydaphy.rocksolid.recipe.loader.AlloyingRecipeLoader;
import com.raphydaphy.rocksolid.recipe.loader.BlastingRecipeLoader;
import com.raphydaphy.rocksolid.recipe.loader.CompressingRecipeLoader;
import com.raphydaphy.rocksolid.recipe.loader.SeparatingRecipeLoader;
import com.raphydaphy.rocksolid.tile.layer.ConduitTileLayer;
import com.raphydaphy.rocksolid.tile.layer.TempshiftTileLayer;
import com.raphydaphy.rocksolid.world.*;
import com.raphydaphy.rocksolid.world.moon.*;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.resource.IResourceRegistry;
import de.ellpeck.rockbottom.api.construction.resource.ItemUseInfo;
import de.ellpeck.rockbottom.api.construction.resource.ResInfo;
import de.ellpeck.rockbottom.api.construction.smelting.FuelInput;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.gen.biome.Biome;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("WeakerAccess")
public class ModMisc
{
	public static final Set<Biome> MOON_BIOME_REGISTRY = new HashSet<>();

	public static final TileLayer CONDUIT_LAYER;
	public static final TileLayer TEMPSHIFT_LAYER;

	public static final ResourceName MOON_WORLD = RockSolid.createRes("moon");
	public static final ResourceName MOON_BIOME_GENERATOR = RockSolid.createRes("moon_biome_generator");
	public static final ResourceName MOON_HEIGHTS_GENERATOR = RockSolid.createRes("moon_heights_generator");

	public static Biome MOON_SURFACE;
	public static Biome MOON_UNDERGROUND;

	public static String RES_MACHINE_MATERIALS;
	public static String RES_ALL_INGOTS;
	public static String RES_ALL_FUELS;

	public static String RES_SANDSTONE;

	static
	{
		CONDUIT_LAYER = new ConduitTileLayer();
		TEMPSHIFT_LAYER = new TempshiftTileLayer();

		MOON_BIOME_REGISTRY.add(GameContent.BIOME_SKY);
	}
	public static void init()
	{
		new MoonWorldInitializer(MOON_WORLD).register();

		MOON_SURFACE = new MoonSurfaceBiome().register();
		MOON_UNDERGROUND = new MoonUndergroundBiome().register();

		Registries.WORLD_GENERATORS.register(RockSolid.createRes("moon_pebbles_generator"), MoonGenPebbles.class);
		Registries.WORLD_GENERATORS.register(RockSolid.createRes("moon_nickel_generator"), MoonGenNickel.class);
		Registries.WORLD_GENERATORS.register(RockSolid.createRes("moon_aluminum_generator"), MoonGenAluminum.class);
		Registries.WORLD_GENERATORS.register(RockSolid.createRes("moon_cobalt_generator"), MoonGenCobalt.class);
		Registries.WORLD_GENERATORS.register(MOON_HEIGHTS_GENERATOR, MoonGenHeights.class);
		Registries.WORLD_GENERATORS.register(MOON_BIOME_GENERATOR, MoonGenBiomes.class);

		Registries.WORLD_GENERATORS.register(RockSolid.createRes("world_gen_tin"), WorldGenTin.class);
		Registries.WORLD_GENERATORS.register(RockSolid.createRes("world_gen_iron"), WorldGenIron.class);
		Registries.WORLD_GENERATORS.register(RockSolid.createRes("world_gen_magnesium"), WorldGenMagnesium.class);
		Registries.WORLD_GENERATORS.register(RockSolid.createRes("world_gen_rutile"), WorldGenRutile.class);
		Registries.WORLD_GENERATORS.register(RockSolid.createRes("world_gen_uranium"), WorldGenUranium.class);
		Registries.WORLD_GENERATORS.register(RockSolid.createRes("world_gen_wolframite"), WorldGenWolframite.class);
		Registries.WORLD_GENERATORS.register(RockSolid.createRes("world_gen_oil"), WorldGenOil.class);

		Registries.PACKET_REGISTRY.register(Registries.PACKET_REGISTRY.getNextFreeId(), PacketConduitUpdate.class);
		Registries.PACKET_REGISTRY.register(Registries.PACKET_REGISTRY.getNextFreeId(), PacketConduitDestroyed.class);
		Registries.PACKET_REGISTRY.register(Registries.PACKET_REGISTRY.getNextFreeId(), PacketAssemblyConstruct.class);
		Registries.PACKET_REGISTRY.register(Registries.PACKET_REGISTRY.getNextFreeId(), PacketAssemblyRecipeChanged.class);

		new AlloyingRecipeLoader().register();
		new SeparatingRecipeLoader().register();
		new BlastingRecipeLoader().register();
		new CompressingRecipeLoader().register();

		new FuelInput(new ItemUseInfo(new ItemInstance(ModItems.COKE)), 3600).register();

		RES_ALL_INGOTS = res().addResources("all_ingots", new ResInfo(GameContent.ITEM_COPPER_INGOT), new ResInfo(ModItems.TIN_INGOT), new ResInfo(ModItems.BRONZE_INGOT), new ResInfo(ModItems.IRON_INGOT), new ResInfo(ModItems.NICKEL_INGOT), new ResInfo(ModItems.STEEL_INGOT), new ResInfo(ModItems.MAGNESIUM_INGOT), new ResInfo(ModItems.IMPURE_TITANIUM_INGOT), new ResInfo(ModItems.TITANIUM_INGOT), new ResInfo(ModItems.TUNGSTEN_INGOT), new ResInfo(ModItems.NICKEL_TUNGSTEN_INGOT), new ResInfo(ModItems.NICKEL_TUNGSTEN_CARBIDE_INGOT));
		RES_MACHINE_MATERIALS = res().addResources("machine_materials", new ResInfo(GameContent.TILE_STONE), new ResInfo(GameContent.TILE_SANDSTONE), new ResInfo(GameContent.TILE_SOIL), new ResInfo(GameContent.TILE_LOG), new ResInfo(ModTiles.MOON_STONE), new ResInfo(ModTiles.MOON_TURF));
		RES_ALL_FUELS = res().addResources("all_fuels", new ResInfo(GameContent.TILE_COAL), new ResInfo(ModItems.COKE));

		RES_SANDSTONE = res().addResources("sandstone", new ResInfo(GameContent.TILE_SANDSTONE));

		new ItemBucket.BucketType("water", GameContent.TILE_WATER);
		new ItemBucket.BucketType("oil", ModTiles.OIL);
		new ItemBucket.BucketType("fuel", ModTiles.FUEL);
	}

	private static IResourceRegistry res()
	{
		return RockBottomAPI.getResourceRegistry();
	}
}
