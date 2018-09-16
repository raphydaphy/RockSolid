package com.raphydaphy.rocksolid.network;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.container.slot.PlayerInvSlot;
import com.raphydaphy.rocksolid.entity.EntityRocket;
import com.raphydaphy.rocksolid.item.ItemJetpack;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.world.IWorld;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.UUID;

public class PacketJetpackMovement implements IPacket
{
	private UUID player;

	public PacketJetpackMovement()
	{

	}

	public PacketJetpackMovement(UUID player)
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
			ItemInstance jetpack = PlayerInvSlot.getSlotItem(entityPlayer, PlayerInvSlot.JETPACK);
			if (jetpack != null && jetpack.getItem() instanceof ItemJetpack)
			{
				int fuel = jetpack.getItem().getHighestPossibleMeta() - jetpack.getMeta();
				if (fuel > 0)
				{
					if (entityPlayer.world.getTotalTime() % 10 == 0)
					{
						jetpack.setMeta(jetpack.getMeta() + 1);
						DataSet jetpackSet = new DataSet();
						jetpack.save(jetpackSet);
						entityPlayer.getAdditionalData().addDataSet(PlayerInvSlot.JETPACK, jetpackSet);
					}

					if (context != null)
					{
						if (entityPlayer.motionY < 0.5)
						{
							entityPlayer.motionY += 0.05f;
						}
						else
						{
							entityPlayer.motionY = 0.5;
						}
					}

					entityPlayer.fallStartY = entityPlayer.getY();
					entityPlayer.isFalling = false;

					PacketJetpackParticles packet = new PacketJetpackParticles(player);
					if (entityPlayer.world.isServer())
					{
						RockBottomAPI.getNet().sendToAllPlayersAround(entityPlayer.world, packet, entityPlayer.getX(), entityPlayer.getY(), 100);
					}
					if (!game.isDedicatedServer())
					{
						packet.handle(game, null);
					}
				}
			}
		}
	}

}
