package com.raphydaphy.rocksolid.entity;

import com.raphydaphy.rocksolid.api.content.RockSolidContent;
import com.raphydaphy.rocksolid.api.gui.ContainerEmpty;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.gui.GuiRocket;
import com.raphydaphy.rocksolid.render.RocketRenderer;
import com.raphydaphy.rocksolid.tileentity.TileEntityRocket;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.EntityItem;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.entity.IEntityRenderer;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;

public class EntityRocket extends Entity
{
	private static BoundBox bb = new BoundBox(0, 0, 1, 4);
	private TileEntityRocket rocketTile;
	private boolean shouldRender = true;
	private int counter = 0;
	// 0 = not taken off, 1 = flying up, 2 = collecting resources, 3 = landing
	private int flightPart = 0;

	public EntityRocket(IWorld world, IResourceName name, int x, int y)
	{
		super(world);
		if (rocketTile == null)
		{
			rocketTile = (TileEntityRocket) RockSolidAPILib.getTileFromPos(x, y, world);
		}
		this.setPos(x, y);
	}

	public EntityRocket(IWorld world)
	{
		super(world);
	}

	@Override
	public BoundBox getBoundingBox()
	{
		return bb;
	}

	public boolean shouldRender()
	{
		return this.shouldRender;
	}

	@Override
	public void save(DataSet set)
	{
		super.save(set);
		DataSet te = new DataSet();
		rocketTile.save(set, false);
		set.addDataSet("te", te);
		set.addBoolean("shouldRender", this.shouldRender);
		set.addInt("counter", this.counter);
		set.addInt("flightPart", this.flightPart);
	}

	@Override
	public void load(DataSet set)
	{
		super.load(set);
		rocketTile = new TileEntityRocket(world, (int) x, (int) y, this);
		rocketTile.load(set.getDataSet("te"), false);
		shouldRender = set.getBoolean("shouldRender");
		counter = set.getInt("counter");
		flightPart = set.getInt("flightPart");
	}

	public void takeoff()
	{
		this.flightPart = 1;
	}

	@Override
	public void update(IGameInstance game)
	{
		super.update(game);
		if (rocketTile.getCurrentFluid() > 0)
		{
			if (this.flightPart == 1)
			{
				if (this.y < 200)
				{
					this.motionY = 0.1;
					this.fallAmount = 0;
					for (int i = 0; i < 10; i++)
					{
						RockBottomAPI.getGame().getParticleManager().addSmokeParticle(
								RockBottomAPI.getGame().getWorld(), this.x + 0.2 + ((Util.RANDOM.nextFloat() / 10) * 6),
								this.y + 0.3, 0, -0.4, RockBottomAPI.getGame().getWorldScale() * 0.0005f);
					}

					rocketTile.update(game);
				} else
				{
					this.flightPart = 2;
					this.shouldRender = false;
					this.counter = 1000;
					System.out.println("Rocket entered space.");
				}
			}
			else if (this.flightPart == 2)
			{
				if (this.counter > 0)
				{
					if (world.getWorldInfo().totalTimeInWorld % 10 == 0)
					{
						System.out.println("Rocket collecting resources" + counter);
					}
					this.counter --;
				}
				else
				{
					this.flightPart = 3;
					this.shouldRender = true;
				}
			}
			else if (this.flightPart == 3)
			{
				this.motionY = -0.25;
				this.fallAmount = 0;
				for (int i = 0; i < 10; i++)
				{
					RockBottomAPI.getGame().getParticleManager().addSmokeParticle(
							RockBottomAPI.getGame().getWorld(), this.x + 0.2 + ((Util.RANDOM.nextFloat() / 10) * 6),
							this.y + 0.3, 0, -0.4, RockBottomAPI.getGame().getWorldScale() * 0.0005f);
				}

				rocketTile.update(game);
			}
		} else
		{
			this.shouldRender = true;
			if (this.onGround)
			{
				EntityItem.spawn(this.world, new ItemInstance(RockSolidContent.rocket), this.x, this.y + 1,
						Util.RANDOM.nextGaussian() * 0.1, Util.RANDOM.nextGaussian() * 0.1);
				this.kill();
			}
		}

	}

	@Override
	public boolean onInteractWith(AbstractEntityPlayer player, double mouseX, double mouseY)
	{
		if (rocketTile != null)
		{
			if (rocketTile.getFluidType() == null)
			{
				rocketTile = new TileEntityRocket(world, (int) x, (int) y, this);
			}
			player.openGuiContainer(new GuiRocket(player, rocketTile), new ContainerEmpty(player));
			return true;
		} else
		{
			System.out.println("o boi the rocket has crashed plz send help");
			return false;
		}
	}

	@Override
	public IEntityRenderer<EntityRocket> getRenderer()
	{
		return new RocketRenderer(RockSolidAPILib.makeInternalRes("rocket"));
	}
}