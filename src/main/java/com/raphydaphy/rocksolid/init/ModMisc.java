package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.network.PacketConduitDestroyed;
import com.raphydaphy.rocksolid.network.PacketConduitUpdate;
import com.raphydaphy.rocksolid.util.ConduitTileLayer;
import com.raphydaphy.rocksolid.world.WorldGenLakes;
import com.raphydaphy.rocksolid.world.WorldGenModOres;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class ModMisc
{
	public static TileLayer CONDUIT_LAYER;

	public static void init()
	{
		CONDUIT_LAYER = new ConduitTileLayer();

		RockBottomAPI.WORLD_GENERATORS.register(RockSolid.createRes("world_gen_lakes"), WorldGenLakes.class);

		WorldGenModOres.registerOreGen(new WorldGenModOres.OreGen(ModTiles.COPPER_ORE, -2, -5, 2, 6, 3));
		WorldGenModOres.registerOreGen(new WorldGenModOres.OreGen(ModTiles.TIN_ORE, -1, -4, 2, 8, 4));
		WorldGenModOres.registerOreGen(new WorldGenModOres.OreGen(ModTiles.IRON_ORE, -4, -9, 1, 9, 4));

		RockBottomAPI.WORLD_GENERATORS.register(RockSolid.createRes("world_gen_ores"), WorldGenModOres.class);

		RockBottomAPI.PACKET_REGISTRY.register(RockBottomAPI.PACKET_REGISTRY.getNextFreeId(), PacketConduitUpdate.class);
		RockBottomAPI.PACKET_REGISTRY.register(RockBottomAPI.PACKET_REGISTRY.getNextFreeId(), PacketConduitDestroyed.class);
	}
}
