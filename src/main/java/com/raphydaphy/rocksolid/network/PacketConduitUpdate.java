package com.raphydaphy.rocksolid.network;

import com.raphydaphy.rocksolid.init.ModMisc;
import com.raphydaphy.rocksolid.tileentity.conduit.TileEntityConduit;
import com.raphydaphy.rocksolid.tileentity.conduit.TileEntityConduit.ConduitMode;
import com.raphydaphy.rocksolid.tileentity.conduit.TileEntityConduit.ConduitSide;
import com.raphydaphy.rocksolid.tileentity.conduit.TileEntityConduit.NetworkConnection;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.net.NetUtil;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class PacketConduitUpdate implements IPacket {
    private ResourceName worldName;
    private int x;
    private int y;
    private ConduitMode mode;
    private ConduitSide side;

    public PacketConduitUpdate() {

    }

    public PacketConduitUpdate(ResourceName worldName, int x, int y, ConduitMode mode, ConduitSide side) {
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.mode = mode;
        this.side = side;
    }

    @Override
    public void toBuffer(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(mode.id);
        buf.writeInt(side.id);
        buf.writeBoolean(this.worldName != null);
        if (this.worldName != null) {
            NetUtil.writeStringToBuffer(this.worldName.toString(), buf);
        }
    }

    @Override
    public void fromBuffer(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        mode = ConduitMode.getByID(buf.readInt());
        side = ConduitSide.getByID(buf.readInt());
        if (buf.readBoolean()) {
            worldName = new ResourceName(NetUtil.readStringFromBuffer(buf));
        }
    }

    @Override
    public void handle(IGameInstance game, ChannelHandlerContext context) {
        IWorld world = game.getWorld();
        if (this.worldName != null) {
            world = world.getSubWorld(this.worldName);
        }
        if (!world.isClient()) {
            TileEntityConduit conduit = world.getTileEntity(ModMisc.CONDUIT_LAYER, x, y, TileEntityConduit.class);
            if (conduit != null) {
                NetworkConnection connection = new NetworkConnection(x, y, side, mode);
                conduit.removeConnection(world, connection, true);
                conduit.addConnection(world, connection);

                if (connection.getMode().isConduit()) {
                    NetworkConnection opposite = new NetworkConnection(x + side.offset.getX(), y + side.offset.getY(), side.getOpposite(), mode);

                    conduit.removeConnection(world, opposite, true);
                    conduit.addConnection(world, opposite);
                }
            }
        }
    }

}
