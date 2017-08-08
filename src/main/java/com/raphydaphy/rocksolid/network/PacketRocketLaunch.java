package com.raphydaphy.rocksolid.network;

import java.io.IOException;

import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.tileentity.TileEntityRocket;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class PacketRocketLaunch implements IPacket
{
	private int x;
	private int y;

	public PacketRocketLaunch(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public PacketRocketLaunch()
	{

	}

	@Override
	public void toBuffer(ByteBuf buf) throws IOException
	{
		buf.writeInt(x);
		buf.writeInt(y);
	}

	@Override
	public void fromBuffer(ByteBuf buf) throws IOException
	{
		this.x = buf.readInt();
		this.y = buf.readInt();
	}

	@Override
	public void handle(IGameInstance game, ChannelHandlerContext context)
	{
		TileEntity rocket = RockSolidAPILib.getTileFromPos(x, y, game.getWorld());
		if (rocket instanceof TileEntityRocket)
		{
			((TileEntityRocket)rocket).launch();
		}
	}

}
