package com.raphydaphy.rocksolid.network;

import java.io.IOException;

import com.raphydaphy.rocksolid.api.util.IConduit;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib.ConduitMode;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib.ConduitSide;
import com.raphydaphy.rocksolid.tileentity.TileEntityItemConduit;

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
	private ConduitSide side;
	private ConduitMode mode;
	private int priority;
	private boolean isWhitelist;

	public PacketConduitUpdate(int x, int y, ConduitSide side, ConduitMode mode, int priority, boolean isWhitelist)
	{
		this.x = x;
		this.y = y;
		this.side = side;
		this.mode = mode;
		this.priority = priority;
		this.isWhitelist = isWhitelist;
	}

	public PacketConduitUpdate()
	{

	}

	@Override
	public void toBuffer(ByteBuf buf) throws IOException
	{
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(side.getID());
		buf.writeInt(mode.getID());
		buf.writeInt(priority);
		buf.writeBoolean(isWhitelist);
	}

	@Override
	public void fromBuffer(ByteBuf buf) throws IOException
	{
		x = buf.readInt();
		y = buf.readInt();
		side = ConduitSide.getByID(buf.readInt());
		mode = ConduitMode.getByID(buf.readInt());
		priority = buf.readInt();
		isWhitelist = buf.readBoolean();
	}

	@Override
	public void handle(IGameInstance game, ChannelHandlerContext context)
	{
		if (game.getWorld().isPosLoaded(x, y))
		{
			TileEntity tileAtPos = RockSolidAPILib.getTileFromPos(x, y, game.getWorld());

			if (tileAtPos instanceof IConduit)
			{
				((IConduit) tileAtPos).setSideMode(side, mode);
				if (tileAtPos instanceof TileEntityItemConduit)
				{
					((TileEntityItemConduit) tileAtPos).setPriority(side, priority);
					((TileEntityItemConduit) tileAtPos).setIsWhitelist(side, isWhitelist);
				}
				if (RockBottomAPI.getNet().isServer())
				{
					RockBottomAPI.getNet().sendToAllPlayers(game.getWorld(),
							new PacketConduitUpdate(x, y, side, mode, priority, isWhitelist));
				}
			}
		}
	}

}
