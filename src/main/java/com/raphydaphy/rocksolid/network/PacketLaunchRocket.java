package com.raphydaphy.rocksolid.network;

import com.raphydaphy.rocksolid.entity.EntityRocket;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.world.IWorld;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.UUID;

public class PacketLaunchRocket implements IPacket {
    private UUID player;

    public PacketLaunchRocket() {

    }

    public PacketLaunchRocket(UUID player) {
        this.player = player;
    }

    @Override
    public void toBuffer(ByteBuf buf) {
        buf.writeLong(this.player.getMostSignificantBits());
        buf.writeLong(this.player.getLeastSignificantBits());
    }

    @Override
    public void fromBuffer(ByteBuf buf) {
        this.player = new UUID(buf.readLong(), buf.readLong());
    }

    @Override
    public void handle(IGameInstance game, ChannelHandlerContext context) {
        IWorld world = game.getWorld();
        if (!world.isClient()) {
            AbstractEntityPlayer entityPlayer = world.getPlayer(player);
            if (entityPlayer.hasAdditionalData() && entityPlayer.getAdditionalData().getBoolean(EntityRocket.IN_ROCKET)) {
                EntityRocket rocket = (EntityRocket) entityPlayer.world.getEntity(entityPlayer.getAdditionalData().getUniqueId(EntityRocket.PLAYER_ROCKET));
                if (rocket != null) {
                    rocket.launch();
                } else {
                    entityPlayer.getAdditionalData().addBoolean(EntityRocket.IN_ROCKET, false);
                }
            }
        }
    }

}
