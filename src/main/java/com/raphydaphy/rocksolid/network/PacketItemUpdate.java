package com.raphydaphy.rocksolid.network;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.net.NetUtil;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class PacketItemUpdate implements IPacket
{
	private UUID uuid;
	private String name;
	private DataSet item = new DataSet();

	public PacketItemUpdate(UUID uuid, String name, DataSet item)
	{
		this.uuid = uuid;
		this.name = name;
		this.item = item;
	}

	public PacketItemUpdate()
	{

	}

	@Override
	public void toBuffer(ByteBuf buf) throws IOException
	{
		buf.writeBytes(this.uuid.toString().getBytes(StandardCharsets.UTF_8));
		NetUtil.writeStringToBuffer(name, buf);
		NetUtil.writeSetToBuffer(item, buf);

	}

	@Override
	public void fromBuffer(ByteBuf buf) throws IOException
	{
		this.uuid = UUID.fromString(buf.readBytes(36).toString(StandardCharsets.UTF_8));
		name = NetUtil.readStringFromBuffer(buf);
		NetUtil.readSetFromBuffer(item, buf);
	}

	@Override
	public void handle(IGameInstance game, ChannelHandlerContext context)
	{
		Entity entity = game.getWorld().getEntity(uuid);

		if (entity instanceof AbstractEntityPlayer)
		{
			AbstractEntityPlayer player = (AbstractEntityPlayer) entity;
			DataSet playerData = player.getAdditionalData();
			playerData.addDataSet(name, item);
		}
	}

}
