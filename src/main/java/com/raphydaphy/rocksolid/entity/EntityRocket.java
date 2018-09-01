package com.raphydaphy.rocksolid.entity;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.container.ContainerEmpty;
import com.raphydaphy.rocksolid.gui.GuiRocket;
import com.raphydaphy.rocksolid.network.PacketEnterRocket;
import com.raphydaphy.rocksolid.particle.RocketParticle;
import com.raphydaphy.rocksolid.render.RocketRenderer;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.render.entity.IEntityRenderer;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import org.lwjgl.glfw.GLFW;

import java.util.UUID;

public class EntityRocket extends Entity
{
	public static final ResourceName IN_ROCKET = RockSolid.createRes("player_in_rocket");
	public static final ResourceName PLAYER_ROCKET = RockSolid.createRes("player_rocket_uuid");

	private int fuelVolume = 0;
	public UUID passenger;

	private boolean flying = false;

	public EntityRocket(IWorld world)
	{
		super(world);
	}

	public void launch()
	{
		if (!flying && onGround)
		{
			flying = true;
			sendToClients();
			world.setDirty((int)getX(), (int)getY());
		}
	}

	@Override
	public void save(DataSet set, boolean forFullSync)
	{
		super.save(set, forFullSync);
		set.addInt("fuel_volume", fuelVolume);
		set.addBoolean("flying", flying);
		if (passenger != null)
		{
			set.addUniqueId("rocket_passanger", passenger);
		}
	}

	@Override
	public void load(DataSet set, boolean forFullSync)
	{
		super.load(set, forFullSync);
		fuelVolume = set.getInt("fuel_volume");
		flying = set.getBoolean("flying");
		if (set.hasKey("rocket_passanger"))
		{
			passenger = set.getUniqueId("rocket_passanger");
		}
		else
		{
			passenger = null;
		}
	}

	@Override
	public boolean onInteractWith(AbstractEntityPlayer player, double mouseX, double mouseY)
	{
		if (!(world.isServer() && world.isDedicatedServer()) && !player.getOrCreateAdditionalData().getBoolean(IN_ROCKET))
		{
			if (RockBottomAPI.getGame().getInput().isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT))
			{
				PacketEnterRocket packet = new PacketEnterRocket(player.getUniqueId(), this.getUniqueId());
				if (world.isClient())
				{
					RockBottomAPI.getNet().sendToServer(packet);
				} else
				{
					packet.handle(RockBottomAPI.getGame(), null);
				}
				return false;
			}
		}
		if (!player.getUniqueId().equals(passenger))
		{
			// not holding shift:
			player.openGuiContainer(new GuiRocket(player, this), new ContainerEmpty(player, 0, 41));
			return true;
		}
		return false;
	}

	public void update(IGameInstance game) {
		super.update(game);
		if (passenger != null)
		{
			AbstractEntityPlayer player = world.getPlayer(passenger);

			if (player != null)
			{
				if (player.getY() != getY())
				{
					player.motionY = getY() - 0.25f - player.getY();
				}

				player.jumping = true;
				player.isFalling = false;
			}
		}

		if (flying)
		{
			if (!world.isClient())
			{
				motionY = 0.1f;
				this.sendToClients();
				world.setDirty((int)getX(), (int)getY());
			}
			if (!(world.isServer() && world.isDedicatedServer()))
			{
				for (int i = 0; i < 3; i++)
				{
					double particleX = getX() + (Util.RANDOM.nextFloat() - 0.5) * 0.4f;
					double particleY = getY() - 2.1f;
					game.getParticleManager().addParticle(new RocketParticle(world, particleX, particleY, Util.RANDOM.nextGaussian() * 0.02f, -0.05, 30, 0.2f + (Util.RANDOM.nextFloat() / 20)));
				}
			}
		}
	}

	@Override
	public IEntityRenderer getRenderer() {
		return new RocketRenderer(RockSolid.createRes("entities.rocket"));
	}

	@Override
	public float getHeight() {
		return 4F;
	}

	@Override
	public int getRenderPriority() {
		return (passenger != null && world.getPlayer(passenger).isLocalPlayer() ? 10 : -8);
	}

	public float getFuelFullness()
	{
		return fuelVolume / 250f;
	}

	public int getFuelVolume()
	{
		return fuelVolume;
	}
}
