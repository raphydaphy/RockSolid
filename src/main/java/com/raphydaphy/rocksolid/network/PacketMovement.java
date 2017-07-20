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
	private double playerX;
	private double playerY;
	
	public PacketMovement(UUID uuid, double motionY, int fallAmount, double playerX, double playerY)
	{
		this.uuid = uuid;
		this.motionY = motionY;
		this.fallAmount = fallAmount;
		this.playerX = playerX;
		this.playerY = playerY;
	}
	
	public PacketMovement()
	{
		
	}
	
	@Override
	public void toBuffer(ByteBuf buf) throws IOException 
	{
		buf.writeBytes(this.uuid.toString().getBytes(StandardCharsets.UTF_8));
		buf.writeDouble(this.motionY);
		buf.writeInt(this.fallAmount);
		buf.writeDouble(this.playerX);
		buf.writeDouble(this.playerY);
	}

	@Override
	public void fromBuffer(ByteBuf buf) throws IOException 
	{
		this.uuid = UUID.fromString(buf.readBytes(36).toString(StandardCharsets.UTF_8));
		this.motionY = buf.readDouble();
		this.fallAmount = buf.readInt();
		this.playerX = buf.readDouble();
		this.playerY = buf.readDouble();
	}

	@Override
	public void handle(IGameInstance game, ChannelHandlerContext context) 
	{
		Entity entity = game.getWorld().getEntity(uuid);
		
		if (entity instanceof AbstractEntityPlayer)
		{
			System.out.println("handled it!");
			entity.motionY = motionY;
			entity.fallAmount = fallAmount;
			entity.x = this.playerX;
			entity.y = this.playerY;
		}
	}

}
