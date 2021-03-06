package com.raphydaphy.rocksolid.network;

import com.raphydaphy.rocksolid.container.ContainerAssemblyStation;
import com.raphydaphy.rocksolid.init.ModRecipes;
import com.raphydaphy.rocksolid.recipe.AssemblyRecipe;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.net.NetUtil;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.UUID;

public class PacketAssemblyRecipeChanged implements IPacket {
    private UUID playerUUID;
    private ResourceName recipeName;

    public PacketAssemblyRecipeChanged(UUID player, ResourceName recipeName) {
        this.playerUUID = player;
        this.recipeName = recipeName;
    }

    public PacketAssemblyRecipeChanged() {
    }

    public void toBuffer(ByteBuf buf) {
        buf.writeLong(this.playerUUID.getMostSignificantBits());
        buf.writeLong(this.playerUUID.getLeastSignificantBits());
        NetUtil.writeStringToBuffer(this.recipeName.toString(), buf);
    }

    public void fromBuffer(ByteBuf buf) {
        this.playerUUID = new UUID(buf.readLong(), buf.readLong());
        this.recipeName = new ResourceName(NetUtil.readStringFromBuffer(buf));
    }

    public void handle(IGameInstance game, ChannelHandlerContext ctx) {
        AbstractEntityPlayer abstractPlayer;
        AssemblyRecipe recipe;
        if (game.getWorld() != null && (abstractPlayer = game.getWorld().getPlayer(this.playerUUID)) != null && (recipe = ModRecipes.ASSEMBLY_STATION_RECIPES.get(this.recipeName)) != null) {
            ItemContainer container = abstractPlayer.getContainer();
            if (container instanceof ContainerAssemblyStation) {
                ((ContainerAssemblyStation) container).metalSlot.setMetal(recipe.getMetal());
            }
        }

    }
}

