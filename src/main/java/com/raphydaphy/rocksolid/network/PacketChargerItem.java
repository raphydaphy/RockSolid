package com.raphydaphy.rocksolid.network;

import java.io.IOException;

import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.tileentity.TileEntityCharger;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.net.NetUtil;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class PacketChargerItem implements IPacket
{
	private int x;
	private int y;
	private DataSet item;

	public PacketChargerItem(int x, int y, DataSet item)
	{
		this.x = x;
		this.y = y;
		this.item = item;
	}

	public PacketChargerItem()
	{
		item = new DataSet();
	}

	@Override
	public void toBuffer(ByteBuf buf) throws IOException
	{
		buf.writeInt(x);
		buf.writeInt(y);
		NetUtil.writeSetToBuffer(item, buf);
	}

	@Override
	public void fromBuffer(ByteBuf buf) throws IOException
	{
		x = buf.readInt();
		y = buf.readInt();
		NetUtil.readSetFromBuffer(item, buf);
	}

	@Override
	public void handle(IGameInstance game, ChannelHandlerContext context)
	{
		TileEntity charger = RockSolidAPILib.getTileFromPos(x, y, game.getWorld());

		if (charger instanceof TileEntityCharger)
		{
			((TileEntityCharger) charger).setItem(ItemInstance.load(item));
		}
	}

}
