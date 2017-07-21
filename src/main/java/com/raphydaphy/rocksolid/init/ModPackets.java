package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.network.PacketBlockDestroyed;
import com.raphydaphy.rocksolid.network.PacketChargerItem;
import com.raphydaphy.rocksolid.network.PacketConduitUpdate;
import com.raphydaphy.rocksolid.network.PacketItemUpdate;
import com.raphydaphy.rocksolid.network.PacketMovement;

import de.ellpeck.rockbottom.api.RockBottomAPI;

public class ModPackets {
	
	// To server
	public static int movementPacket = 0;
	public static int itemUpdatePacket = 0;
	public static int blockDestroyedPacket = 0;
	public static int conduitUpdatePacket = 0;
	
	// To client
	public static int chargerItemPacket = 0;
	
	public static void init()
	{
		movementPacket = RockBottomAPI.PACKET_REGISTRY.getNextFreeId();
		RockBottomAPI.PACKET_REGISTRY.register(movementPacket, PacketMovement.class);
		
		itemUpdatePacket = RockBottomAPI.PACKET_REGISTRY.getNextFreeId();
		RockBottomAPI.PACKET_REGISTRY.register(itemUpdatePacket, PacketItemUpdate.class);
		
		blockDestroyedPacket = RockBottomAPI.PACKET_REGISTRY.getNextFreeId();
		RockBottomAPI.PACKET_REGISTRY.register(blockDestroyedPacket, PacketBlockDestroyed.class);
		
		conduitUpdatePacket = RockBottomAPI.PACKET_REGISTRY.getNextFreeId();
		RockBottomAPI.PACKET_REGISTRY.register(conduitUpdatePacket, PacketConduitUpdate.class);
		
		chargerItemPacket = RockBottomAPI.PACKET_REGISTRY.getNextFreeId();
		RockBottomAPI.PACKET_REGISTRY.register(chargerItemPacket, PacketChargerItem.class);
	}
}
		