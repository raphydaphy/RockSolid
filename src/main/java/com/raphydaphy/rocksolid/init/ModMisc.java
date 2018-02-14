package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.network.PacketConduitUpdate;
import com.raphydaphy.rocksolid.util.ConduitTileLayer;
import com.raphydaphy.rocksolid.world.WorldGenLakes;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class ModMisc
{
	public static TileLayer CONDUIT_LAYER;

	public static void init()
	{
		RockBottomAPI.WORLD_GENERATORS.register(RockSolid.createRes("world_gen_lakes"), WorldGenLakes.class);
		
		CONDUIT_LAYER = new ConduitTileLayer();
		
		RockBottomAPI.PACKET_REGISTRY.register(RockBottomAPI.PACKET_REGISTRY.getNextFreeId(), PacketConduitUpdate.class);
	}
}
