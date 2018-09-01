package com.raphydaphy.rocksolid.entity;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.container.ContainerEmpty;
import com.raphydaphy.rocksolid.gui.GuiRocket;
import com.raphydaphy.rocksolid.network.PacketEnterRocket;
import com.raphydaphy.rocksolid.particle.RocketParticle;
import com.raphydaphy.rocksolid.render.RocketRenderer;
import com.raphydaphy.rocksolid.tile.machine.TileLaunchPad;
import com.raphydaphy.rocksolid.tileentity.TileEntityLaunchPad;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.render.entity.IEntityRenderer;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import org.lwjgl.glfw.GLFW;

import java.util.UUID;

public class EntityRocket extends Entity
{
	private final ResourceName ROCKET_SOUND = RockSolid.createRes("rocket");
	public static final ResourceName IN_ROCKET = RockSolid.createRes("player_in_rocket");
	public static final ResourceName PLAYER_ROCKET = RockSolid.createRes("player_rocket_uuid");

	private int fuelVolume = 0;
	public UUID passenger;
	private boolean flying = false;
	private int timeFlying;

	public Pos2 launchPad = null;

	private int lastPlayed = -1;

	public EntityRocket(IWorld world)
	{
		super(world);
	}

	public void launch()
	{
		if (launchPad != null && !flying && onGround)
		{
			flying = true;
			sendToClients();
			world.setDirty((int)getX(), (int)getY());
			timeFlying = -1000;
			if (!(world.isServer() && world.isDedicatedServer()))
			{
				world.playSound(ROCKET_SOUND, getX(), getY(), 1, 0.5f, 2);
				lastPlayed = world.getTotalTime();
			}

			TileEntityLaunchPad pad = world.getTileEntity(launchPad.getX(), launchPad.getY(), TileEntityLaunchPad.class);
			if (pad != null)
			{
				pad.setRocket(null);
				launchPad = null;
			}
		}
	}

	@Override
	public void save(DataSet set, boolean forFullSync)
	{
		super.save(set, forFullSync);
		set.addInt("fuel_volume", fuelVolume);
		set.addBoolean("flying", flying);
		set.addInt("time_flying", timeFlying);
		if (passenger != null)
		{
			set.addUniqueId("rocket_passanger", passenger);
		}
		if (launchPad != null)
		{
			set.addInt("launch_pad_x", launchPad.getX());
			set.addInt("launch_pad_y", launchPad.getY());
		}
	}

	@Override
	public void load(DataSet set, boolean forFullSync)
	{
		super.load(set, forFullSync);
		fuelVolume = set.getInt("fuel_volume");
		flying = set.getBoolean("flying");
		timeFlying = set.getInt("time_flying");
		if (set.hasKey("rocket_passanger"))
		{
			passenger = set.getUniqueId("rocket_passanger");
		}
		else
		{
			passenger = null;
		}
		if (set.hasKey("launch_pad_x"))
		{
			launchPad = new Pos2(set.getInt("launch_pad_x"), set.getInt("launch_pad_y"));
		}
		else
		{
			launchPad = null;
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
		if (passenger != null && !world.isClient())
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
				player.sendToClients();
			}
		}

		if (flying)
		{
			if (!world.isClient())
			{
				timeFlying++;
				this.sendToClients();
				world.setDirty((int)getX(), (int)getY());

				if (timeFlying > 0)
				{
					motionY = 0.1f;
				}
			}

			if (!(world.isServer() && world.isDedicatedServer()))
			{
				for (int i = 0; i < 3; i++)
				{
					double particleX = getX() + (Util.RANDOM.nextFloat() - 0.5) * 0.4f;
					double particleY = getY() - 2.1f;
					game.getParticleManager().addParticle(new RocketParticle(world, particleX, particleY, timeFlying > 0 ? Util.RANDOM.nextGaussian() * 0.02f : Util.RANDOM.nextGaussian() * 0.01f, timeFlying > 0 ? -0.05 : -0.001, 30, 0.2f + (Util.RANDOM.nextFloat() / 20)));
				}
				if (lastPlayed == -1 || world.getTotalTime() - lastPlayed >= 180)
				{
					world.playSound(ROCKET_SOUND, getX(), getY(), 1, timeFlying < 0 ? (timeFlying + 200f) / 200f : 1, 2);
					lastPlayed = world.getTotalTime();
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
