package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.network.PacketMovement;

import de.ellpeck.rockbottom.api.RockBottomAPI;

public class ModPackets {
	
	public static int movementPacket = 0;
	public static void init()
	{
		movementPacket = RockBottomAPI.PACKET_REGISTRY.getNextFreeId();
		RockBottomAPI.PACKET_REGISTRY.register(movementPacket, PacketMovement.class);
	}
}
	