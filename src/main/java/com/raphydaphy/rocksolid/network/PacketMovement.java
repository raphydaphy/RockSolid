package com.raphydaphy.rocksolid.network;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class PacketMovement implements IPacket
{
	private UUID uuid;
	private double motionY;
	private int fallAmount;
	
	public PacketMovement(UUID uuid, double motionY, int fallAmount)
	{
		this.uuid = uuid;
		this.motionY = motionY;
		this.fallAmount = fallAmount;
	}
	
	@Override
	public void toBuffer(ByteBuf buf) throws IOException 
	{
		buf.writeBytes(uuid.toString().getBytes(StandardCharsets.UTF_8));
		buf.writeDouble(motionY);
		buf.writeInt(fallAmount);
	}

	@Override
	public void fromBuffer(ByteBuf buf) throws IOException 
	{
		uuid = UUID.fromString(buf.readBytes(36).toString(StandardCharsets.UTF_8));
		motionY = buf.readDouble();
		fallAmount = buf.readInt();
	}

	@Override
	public void handle(IGameInstance game, ChannelHandlerContext context) 
	{
		Entity entity = game.getWorld().getEntity(uuid);
		
		if (entity instanceof AbstractEntityPlayer)
		{
			entity.motionY = motionY;
			entity.fallAmount = fallAmount;
		}
	}

}
