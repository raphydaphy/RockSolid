package com.raphydaphy.rocksolid.network;

import java.io.IOException;

import com.raphydaphy.rocksolid.init.ModMisc;
import com.raphydaphy.rocksolid.tileentity.conduit.TileEntityConduit;
import com.raphydaphy.rocksolid.tileentity.conduit.TileEntityConduit.ConduitMode;
import com.raphydaphy.rocksolid.tileentity.conduit.TileEntityConduit.ConduitSide;
import com.raphydaphy.rocksolid.tileentity.conduit.TileEntityConduit.NetworkConnection;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.world.IWorld;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class PacketConduitUpdate implements IPacket
{
	private int x;
	private int y;
	private ConduitMode mode;
	private ConduitSide side;

	public PacketConduitUpdate()
	{

	}

	public PacketConduitUpdate(int x, int y, ConduitMode mode, ConduitSide side)
	{
		this.x = x;
		this.y = y;
		this.mode = mode;
		this.side = side;
	}

	@Override
	public void toBuffer(ByteBuf buf) throws IOException
	{
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(mode.id);
		buf.writeInt(side.id);
	}

	@Override
	public void fromBuffer(ByteBuf buf) throws IOException
	{
		x = buf.readInt();
		y = buf.readInt();
		mode = ConduitMode.getByID(buf.readInt());
		side = ConduitSide.getByID(buf.readInt());
	}

	@Override
	public void handle(IGameInstance game, ChannelHandlerContext context)
	{
		IWorld world = game.getWorld();
		if (!world.isClient())
		{
			TileEntityConduit conduit = world.getTileEntity(ModMisc.CONDUIT_LAYER, x, y, TileEntityConduit.class);
			if (conduit != null)
			{
				NetworkConnection connection = new NetworkConnection(x, y, side, mode);
				conduit.removeConnection(world, connection, true);
				conduit.addConnection(world, connection);
				
				if (connection.getMode().isConduit())
				{
					NetworkConnection opposite = new NetworkConnection(x + side.offset.getX(), y + side.offset.getY(), side.getOpposite(), mode);
					
					conduit.removeConnection(world, opposite, true);
					conduit.addConnection(world, opposite);
				}
			}
		}
	}

}
