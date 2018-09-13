package com.raphydaphy.rocksolid.network;

import com.raphydaphy.rocksolid.entity.EntityRocket;
import com.raphydaphy.rocksolid.item.ItemJetpack;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.world.IWorld;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.UUID;

public class PacketJetpack implements IPacket
{
	private UUID player;

	public PacketJetpack()
	{

	}

	public PacketJetpack(UUID player)
	{
		this.player = player;
	}

	@Override
	public void toBuffer(ByteBuf buf)
	{
		buf.writeLong(this.player.getMostSignificantBits());
		buf.writeLong(this.player.getLeastSignificantBits());
	}

	@Override
	public void fromBuffer(ByteBuf buf)
	{
		this.player = new UUID(buf.readLong(), buf.readLong());
	}

	@Override
	public void handle(IGameInstance game, ChannelHandlerContext context)
	{
		IWorld world = game.getWorld();
		if (!world.isClient())
		{
			AbstractEntityPlayer entityPlayer = world.getPlayer(player);
			ItemInstance held = entityPlayer.getInv().get(entityPlayer.getSelectedSlot());
			if (held != null && held.getItem() instanceof ItemJetpack)
			{
				int fuel = held.getItem().getHighestPossibleMeta() - held.getMeta();
				if (fuel > 0)
				{
					if (entityPlayer.world.getTotalTime() % 10 == 0)
					{
						held.setMeta(held.getMeta() + 1);
					}

					if (context != null)
					{
						entityPlayer.motionY += 0.05f;
					}

					entityPlayer.fallStartY = entityPlayer.getY();
					entityPlayer.isFalling = false;
				}
			}
		}
	}

}
