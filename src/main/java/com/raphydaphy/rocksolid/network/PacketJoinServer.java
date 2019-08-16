package com.raphydaphy.rocksolid.network;

import com.raphydaphy.rocksolid.container.slot.PlayerInvSlot;
import com.raphydaphy.rocksolid.init.ModItems;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class PacketJoinServer implements IPacket {
    public PacketJoinServer() {
    }

    @Override
    public void toBuffer(ByteBuf buf) {
    }

    @Override
    public void fromBuffer(ByteBuf buf) {
    }

    @Override
    public void handle(IGameInstance game, ChannelHandlerContext context) {
        AbstractEntityPlayer entityPlayer = game.getPlayer();
        System.out.println(entityPlayer);
        if (entityPlayer != null) {
            entityPlayer.getInvContainer().addSlot(new PlayerInvSlot(entityPlayer, 0, PlayerInvSlot.JETPACK, (instance) -> instance != null && instance.getItem() == ModItems.JETPACK, 137, 20));
            entityPlayer.getInvContainer().addSlot(new PlayerInvSlot(entityPlayer, 1, PlayerInvSlot.LANTERN, (instance) -> instance != null && instance.getItem() == ModItems.LANTERN, 137, 40));
            entityPlayer.getInvContainer().addSlot(new PlayerInvSlot(entityPlayer, 2, PlayerInvSlot.LANTERN_FUEL, (instance) -> instance != null && (instance.getItem() == GameContent.TILE_COAL.getItem() || instance.getItem() == ModItems.COKE), 137, 60));
        }
    }

}
