package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.network.PacketAssemblyConstruct;
import com.raphydaphy.rocksolid.network.PacketConduitDestroyed;
import com.raphydaphy.rocksolid.network.PacketConduitUpdate;
import com.raphydaphy.rocksolid.util.ConduitTileLayer;
import com.raphydaphy.rocksolid.world.WorldGenModOres;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.construction.resource.ItemUseInfo;
import de.ellpeck.rockbottom.api.construction.smelting.FuelInput;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class ModMisc
{
	public static final TileLayer CONDUIT_LAYER;
	private static FuelInput COKE_INPUT;

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

		COKE_INPUT = new FuelInput(new ItemUseInfo(new ItemInstance(ModItems.COKE)), 3600).register();
	}
}
