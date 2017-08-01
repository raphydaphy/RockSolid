package com.raphydaphy.rocksolid.network;

import java.io.IOException;

import com.raphydaphy.rocksolid.api.util.IConduit;
import com.raphydaphy.rocksolid.tileentity.TileEntityItemConduit;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class PacketConduitUpdate implements IPacket
{
	private int x;
	private int y;
	private int side;
	private int mode;
	private int priority;

	public PacketConduitUpdate(int x, int y, int side, int mode, int priority)
	{
		this.x = x;
		this.y = y;
		this.side = side;
		this.mode = mode;
		this.priority = priority;
	}

	public PacketConduitUpdate()
	{

	}

	@Override
	public void toBuffer(ByteBuf buf) throws IOException
	{
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(side);
		buf.writeInt(mode);
		buf.writeInt(priority);
	}

	@Override
	public void fromBuffer(ByteBuf buf) throws IOException
	{
		x = buf.readInt();
		y = buf.readInt();
		side = buf.readInt();
		mode = buf.readInt();
		priority = buf.readInt();
	}

	@Override
	public void handle(IGameInstance game, ChannelHandlerContext context)
	{
		if (game.getWorld().isPosLoaded(x, y))
		{
			TileEntity tileAtPos = RockSolidLib.getTileFromPos(x, y, game.getWorld());

			if (tileAtPos instanceof IConduit)
			{
				((IConduit) tileAtPos).setSideMode(side, mode);
				if (tileAtPos instanceof TileEntityItemConduit)
				{
					((TileEntityItemConduit) tileAtPos).setPriority(priority, side);
				}
				if (RockBottomAPI.getNet().isServer())
				{
					RockBottomAPI.getNet().sendToAllPlayers(game.getWorld(), new PacketConduitUpdate(x, y, side, mode, priority));
				}
			}
		}
	}

}
