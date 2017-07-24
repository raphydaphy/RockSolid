package com.raphydaphy.rocksolid.network;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.world.TileLayer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class PacketTileDestroyed implements IPacket
{
	private UUID uuid;
	private int x;
	private int y;
	private boolean isMain;
	
	public PacketTileDestroyed(UUID uuid, int x, int y, boolean isMain)
	{
		this.uuid = uuid;
		this.x = x;
		this.y = y;
		this.isMain = isMain;
	}
	
	public PacketTileDestroyed()
	{
		
	}
	
	@Override
	public void toBuffer(ByteBuf buf) throws IOException 
	{
		buf.writeBytes(this.uuid.toString().getBytes(StandardCharsets.UTF_8));
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeBoolean(isMain);
	}

	@Override
	public void fromBuffer(ByteBuf buf) throws IOException 
	{
		this.uuid = UUID.fromString(buf.readBytes(36).toString(StandardCharsets.UTF_8));
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.isMain = buf.readBoolean();
	}

	@Override
	public void handle(IGameInstance game, ChannelHandlerContext context) 
	{
		Entity entity = game.getWorld().getEntity(uuid);
		
		if (entity instanceof AbstractEntityPlayer)
		{
			AbstractEntityPlayer player = (AbstractEntityPlayer)entity;
			
			game.getWorld().destroyTile(x, y, isMain ? TileLayer.MAIN : TileLayer.BACKGROUND, player, true);
		}
	}

}
