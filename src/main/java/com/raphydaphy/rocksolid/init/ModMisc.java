package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.network.PacketAssemblyConstruct;
import com.raphydaphy.rocksolid.network.PacketConduitDestroyed;
import com.raphydaphy.rocksolid.network.PacketConduitUpdate;
import com.raphydaphy.rocksolid.util.ConduitTileLayer;
import com.raphydaphy.rocksolid.world.WorldGenModOres;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.resource.IResourceRegistry;
import de.ellpeck.rockbottom.api.construction.resource.ItemUseInfo;
import de.ellpeck.rockbottom.api.construction.resource.ResInfo;
import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.construction.smelting.FuelInput;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class ModMisc
{
	public static final TileLayer CONDUIT_LAYER;

	public static String RES_MACHINE_MATERIALS;
	public static String RES_ALL_INGOTS;
	public static String RES_ALL_FUELS;

	public static String RES_SANDSTONE;

	static
	{
		CONDUIT_LAYER = new ConduitTileLayer();
	}
	public static void init()
	{
		WorldGenModOres.registerOreGen(new WorldGenModOres.OreGen(ModTiles.TIN_ORE, -1, -4, 2, 9, 5, 3));
		WorldGenModOres.registerOreGen(new WorldGenModOres.OreGen(ModTiles.IRON_ORE, -4, -10, 1, 5, 3, 5));

		Registries.WORLD_GENERATORS.register(RockSolid.createRes("world_gen_ores"), WorldGenModOres.class);

		Registries.PACKET_REGISTRY.register(Registries.PACKET_REGISTRY.getNextFreeId(), PacketConduitUpdate.class);
		Registries.PACKET_REGISTRY.register(Registries.PACKET_REGISTRY.getNextFreeId(), PacketConduitDestroyed.class);
		Registries.PACKET_REGISTRY.register(Registries.PACKET_REGISTRY.getNextFreeId(), PacketAssemblyConstruct.class);

		new FuelInput(new ItemUseInfo(new ItemInstance(ModItems.COKE)), 3600).register();

		RES_ALL_INGOTS = res().addResources("all_ingots", new ResInfo(GameContent.ITEM_COPPER_INGOT), new ResInfo(ModItems.TIN_INGOT), new ResInfo(ModItems.BRONZE_INGOT), new ResInfo(ModItems.IRON_INGOT), new ResInfo(ModItems.STEEL_INGOT));
		RES_MACHINE_MATERIALS = res().addResources("machine_materials", new ResInfo(GameContent.TILE_STONE), new ResInfo(GameContent.TILE_SANDSTONE), new ResInfo(GameContent.TILE_SOIL), new ResInfo(GameContent.TILE_LOG));
		RES_ALL_FUELS = res().addResources("all_fuels", new ResInfo(GameContent.TILE_COAL), new ResInfo(ModItems.COKE));

		RES_SANDSTONE = res().addResources("sandstone", new ResInfo(GameContent.TILE_SANDSTONE));
	}

	private static IResourceRegistry res()
	{
		return RockBottomAPI.getResourceRegistry();
	}
}
