package com.raphydaphy.rocksolid.network;

import com.raphydaphy.rocksolid.init.ModItems;
import com.raphydaphy.rocksolid.init.ModMisc;
import com.raphydaphy.rocksolid.item.ItemWrench;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.world.IWorld;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class PacketConduitDestroyed implements IPacket
{
	private int x;
	private int y;
	private UUID uuid;

	public PacketConduitDestroyed()
	{

	}

	public PacketConduitDestroyed(int x, int y, UUID uuid)
	{
		this.x = x;
		this.y = y;
		this.uuid = uuid;
	}

	@Override
	public void toBuffer(ByteBuf buf)
	{
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeBytes(this.uuid.toString().getBytes(StandardCharsets.UTF_8));
	}

	@Override
	public void fromBuffer(ByteBuf buf)
	{
		x = buf.readInt();
		y = buf.readInt();
		this.uuid = UUID.fromString(buf.readBytes(36).toString(StandardCharsets.UTF_8));
	}

	@Override
	public void handle(IGameInstance game, ChannelHandlerContext context)
	{
		IWorld world = game.getWorld();
		if (!world.isClient())
		{
			Entity entity = game.getWorld().getEntity(uuid);

			if (entity instanceof AbstractEntityPlayer)
			{
				ItemInstance held = ((AbstractEntityPlayer) entity).getInv().get(((AbstractEntityPlayer) entity).getSelectedSlot());
				System.out.println(held);
				if (held != null && held.getItem() == ModItems.WRENCH)
				{
					((ItemWrench)held.getItem()).takeDamage(held, (AbstractEntityPlayer)entity, 1);
				}
				world.destroyTile(x,y,ModMisc.CONDUIT_LAYER,entity,true);
			}
		}
	}

}
