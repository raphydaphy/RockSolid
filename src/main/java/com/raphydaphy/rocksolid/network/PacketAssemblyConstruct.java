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
import de.ellpeck.rockbottom.api.world.IWorld;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.UUID;

public class PacketAssemblyConstruct implements IPacket
{
	private UUID playerUUID;
	private ResourceName recipeName;
	private int amount;

	public PacketAssemblyConstruct(UUID player, ResourceName recipeName, int recipeAmount)
	{
		this.playerUUID = player;
		this.recipeName = recipeName;
		this.amount = recipeAmount;
	}

	public PacketAssemblyConstruct()
	{
	}

	public void toBuffer(ByteBuf buf)
	{
		buf.writeLong(this.playerUUID.getMostSignificantBits());
		buf.writeLong(this.playerUUID.getLeastSignificantBits());
		NetUtil.writeStringToBuffer(this.recipeName.toString(), buf);
		buf.writeInt(this.amount);
	}

	public void fromBuffer(ByteBuf buf)
	{
		this.playerUUID = new UUID(buf.readLong(), buf.readLong());
		this.recipeName = new ResourceName(NetUtil.readStringFromBuffer(buf));
		this.amount = buf.readInt();
	}

	public void handle(IGameInstance game, ChannelHandlerContext ctx)
	{
		AbstractEntityPlayer abstractPlayer;
		AssemblyRecipe recipe;
		IWorld world = game.getWorld();
		if (world != null && (abstractPlayer = world.getPlayer(this.playerUUID)) != null && (recipe = ModRecipes.ASSEMBLY_STATION_RECIPES.get(this.recipeName)) != null && recipe.isKnown(abstractPlayer))
		{
			ItemContainer container = abstractPlayer.getContainer();
			if (container instanceof ContainerAssemblyStation)
			{
				recipe.playerConstruct(abstractPlayer, ((ContainerAssemblyStation) container).te, this.amount);
			}
		}

	}
}

