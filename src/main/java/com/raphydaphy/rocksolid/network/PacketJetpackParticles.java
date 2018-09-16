package com.raphydaphy.rocksolid.network;

import com.raphydaphy.rocksolid.container.slot.PlayerInvSlot;
import com.raphydaphy.rocksolid.init.ModItems;
import com.raphydaphy.rocksolid.particle.RocketParticle;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.smelting.FuelInput;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IWorld;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.UUID;

public class PacketJetpackParticles implements IPacket
{
	private UUID player;

	public PacketJetpackParticles()
	{

	}

	public PacketJetpackParticles(UUID player)
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
		if (!(RockBottomAPI.getNet().isServer() && RockBottomAPI.getGame().isDedicatedServer()))
		{
			AbstractEntityPlayer entityPlayer = world.getPlayer(player);
			for (int i = 0; i < Util.RANDOM.nextInt(10); i++)
			{
				double particleX = entityPlayer.getX() + (Util.RANDOM.nextFloat() - 0.5) * 0.6f;
				double particleY = entityPlayer.getY() - 0.7f;
				RockBottomAPI.getGame().getParticleManager().addParticle(new RocketParticle(entityPlayer.world, particleX, particleY, Util.RANDOM.nextGaussian() * 0.02f, -0.05, 30, 0.2f + (Util.RANDOM.nextFloat() / 20)));
			}
		}
	}

}
