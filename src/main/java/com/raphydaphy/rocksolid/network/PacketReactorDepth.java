package com.raphydaphy.rocksolid.network;

import com.raphydaphy.rocksolid.init.ModMisc;
import com.raphydaphy.rocksolid.init.ModTiles;
import com.raphydaphy.rocksolid.tile.machine.TileNuclearReactor;
import com.raphydaphy.rocksolid.tileentity.TileEntityNuclearReactor;
import com.raphydaphy.rocksolid.tileentity.conduit.TileEntityConduit;
import com.raphydaphy.rocksolid.tileentity.conduit.TileEntityConduit.ConduitMode;
import com.raphydaphy.rocksolid.tileentity.conduit.TileEntityConduit.ConduitSide;
import com.raphydaphy.rocksolid.tileentity.conduit.TileEntityConduit.NetworkConnection;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class PacketReactorDepth implements IPacket
{
	private int x;
	private int y;
	private int depth;

	public PacketReactorDepth()
	{

	}

	public PacketReactorDepth(int x, int y, int depth)
	{
		this.x = x;
		this.y = y;
		this.depth = depth;
	}

	@Override
	public void toBuffer(ByteBuf buf)
	{
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(depth);
	}

	@Override
	public void fromBuffer(ByteBuf buf)
	{
		x = buf.readInt();
		y = buf.readInt();
		depth = buf.readInt();
	}

	@Override
	public void handle(IGameInstance game, ChannelHandlerContext context)
	{
		IWorld world = game.getWorld();
		if (!world.isClient())
		{
			TileEntityNuclearReactor reactor = ((TileNuclearReactor)ModTiles.NUCLEAR_REACTOR).getTE(world, world.getState(x, y), x, y);
			if (reactor != null)
			{
				if (depth <= 100 && depth > 0)
				{
					reactor.setDepth(depth);
				}
			}
		}
	}

}
