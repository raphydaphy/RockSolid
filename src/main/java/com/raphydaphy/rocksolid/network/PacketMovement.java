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
	private double fallStartY;
	private boolean isFalling;
	private double playerX;
	private double playerY;

	public PacketMovement(UUID uuid, double motionY, double fallStartY, boolean isFalling, double playerX, double playerY)
	{
		this.uuid = uuid;
		this.motionY = motionY;
		this.fallStartY = fallStartY;
		this.isFalling = isFalling;
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
		buf.writeDouble(this.fallStartY);
		buf.writeBoolean(this.isFalling);
		buf.writeDouble(this.playerX);
		buf.writeDouble(this.playerY);
	}

	@Override
	public void fromBuffer(ByteBuf buf) throws IOException
	{
		this.uuid = UUID.fromString(buf.readBytes(36).toString(StandardCharsets.UTF_8));
		this.motionY = buf.readDouble();
		this.fallStartY = buf.readDouble();
		this.isFalling = buf.readBoolean();
		this.playerX = buf.readDouble();
		this.playerY = buf.readDouble();
	}

	@Override
	public void handle(IGameInstance game, ChannelHandlerContext context)
	{
		Entity entity = game.getWorld().getEntity(uuid);

		if (entity instanceof AbstractEntityPlayer)
		{
			entity.motionY = motionY;
			entity.fallStartY = fallStartY;
			entity.isFalling = isFalling;
			entity.x = this.playerX;
			entity.y = this.playerY;
		}
	}

}
