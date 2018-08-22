package com.raphydaphy.rocksolid.network;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.construction.IRecipe;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.net.NetUtil;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
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
		IRecipe iRecipe;
		if (game.getWorld() != null && (abstractPlayer = game.getWorld().getPlayer(this.playerUUID)) != null && (iRecipe = IRecipe.forName(this.recipeName)) != null && iRecipe.isKnown(abstractPlayer))
		{
			iRecipe.playerConstruct(abstractPlayer, this.amount);
		}

	}
}

