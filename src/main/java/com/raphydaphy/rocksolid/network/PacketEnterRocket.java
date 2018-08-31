package com.raphydaphy.rocksolid.network;

import com.raphydaphy.rocksolid.entity.EntityRocket;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.world.IWorld;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.UUID;

public class PacketEnterRocket implements IPacket
{
	private UUID player;
	private UUID rocket;

	public PacketEnterRocket()
	{

	}

	public PacketEnterRocket(UUID player, UUID rocket)
	{
		this.player = player;
		this.rocket = rocket;
	}

	@Override
	public void toBuffer(ByteBuf buf)
	{
		buf.writeLong(this.player.getMostSignificantBits());
		buf.writeLong(this.player.getLeastSignificantBits());
		buf.writeLong(this.rocket.getMostSignificantBits());
		buf.writeLong(this.rocket.getLeastSignificantBits());
	}

	@Override
	public void fromBuffer(ByteBuf buf)
	{
		this.player = new UUID(buf.readLong(), buf.readLong());
		this.rocket = new UUID(buf.readLong(), buf.readLong());
	}

	@Override
	public void handle(IGameInstance game, ChannelHandlerContext context)
	{
		IWorld world = game.getWorld();
		if (!world.isClient())
		{
			AbstractEntityPlayer entityPlayer = world.getPlayer(player);
			EntityRocket entityRocket = (EntityRocket)entityPlayer.world.getEntity(rocket);

			entityPlayer.getOrCreateAdditionalData().addBoolean(EntityRocket.IN_ROCKET, true);
			entityPlayer.getAdditionalData().addUniqueId(EntityRocket.PLAYER_ROCKET, rocket);

			entityRocket.passenger = player;

			entityPlayer.world.setDirty((int)entityRocket.getX(), (int)entityRocket.getY());

			entityPlayer.setPos(entityRocket.getX(), entityRocket.getY());

		}
	}

}
