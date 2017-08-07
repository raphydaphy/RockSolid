package com.raphydaphy.rocksolid.entity;

import com.raphydaphy.rocksolid.api.content.RockSolidContent;
import com.raphydaphy.rocksolid.api.gui.ContainerEmpty;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.gui.GuiEntityRocket;
import com.raphydaphy.rocksolid.gui.inventory.ContainerInventory;
import com.raphydaphy.rocksolid.render.RocketRenderer;
import com.raphydaphy.rocksolid.tileentity.TileEntityRocket;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.EntityItem;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.entity.IEntityRenderer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class EntityRocket extends Entity
{
	private static BoundBox bb = new BoundBox(0, 0, 1, 4);
	private int fuel;
	private boolean shouldRender = true;
	private int counter = 0;
	private RocketStage flightPart;
	private ContainerInventory inv;
	
	
	private static Tile[] ores = new Tile[] {RockSolidContent.oreIron, RockSolidContent.oreMagnesium, RockSolidContent.oreRutile, RockSolidContent.oreTin, RockSolidContent.oreUranium, RockSolidContent.oreWolframite, GameContent.TILE_COPPER_ORE, GameContent.TILE_GLOW_ORE, GameContent.TILE_COAL_ORE};
	                                     
	public enum RocketStage
	{
		LANDED(0), FLYING(1), COLLECTING(2), LANDING(3);
		
		private int id;
		RocketStage(int id)
		{
			this.id = id;
		}
		
		public int getID()
		{
			return this.id;
		}
		
		public static RocketStage getFromID(int id)
		{
			for (RocketStage stage : RocketStage.values())
			{
				if (stage.getID() == id)
				{
					return stage;
				}
			}
			return RocketStage.LANDED;
		}
	}
	
	public EntityRocket(IWorld world, IResourceName name, int x, int y, int fuel, ContainerInventory oldInv)
	{
		this(world);
		this.inv = oldInv;
		this.fuel = fuel;
		this.setPos(x, y);
		if (flightPart == null)
		{
			this.flightPart = RocketStage.FLYING;
		}
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
		this.inv.save(set);
		set.addBoolean("shouldRender", this.shouldRender);
		set.addInt("counter", this.counter);
		set.addInt("flightPart", this.flightPart.getID());
	}

	@Override
	public void load(DataSet set)
	{
		super.load(set);
		this.inv.load(set);
		shouldRender = set.getBoolean("shouldRender");
		counter = set.getInt("counter");
		flightPart = RocketStage.getFromID(set.getInt("flightPart"));
	}

	@Override
	public void update(IGameInstance game)
	{
		world.getChunkFromGridCoords(this.chunkX, this.chunkY);
		super.update(game);
		if (fuel > 0)
		{
			if (this.flightPart == RocketStage.FLYING)
			{
				System.out.println("FLYING");
				if (this.y < 100)
				{
					this.motionY = 0.2;
					this.fallAmount = 0;
					for (int i = 0; i < 10; i++)
					{
						RockBottomAPI.getGame().getParticleManager().addSmokeParticle(
								RockBottomAPI.getGame().getWorld(), this.x + 0.2 + ((Util.RANDOM.nextFloat() / 10) * 6),
								this.y + 0.3, 0, -0.4, RockBottomAPI.getGame().getWorldScale() * 0.0005f);
					}

					if (this.fuel <= 0)
					{
						int newX = (int) Math.floor(this.x);
						int newY = (int) Math.round(this.y);
						RockSolidContent.rocket.doPlace(world, newX, newY, TileLayer.MAIN, null, null);
						world.removeTileEntity(newX, newY);
						world.addTileEntity(new TileEntityRocket(world, newX, newY));
						this.kill();
					} else if (world.getWorldInfo().totalTimeInWorld % 10 == 0)
					{
						this.fuel -= 1;
					}
				} else
				{
					this.flightPart = RocketStage.COLLECTING;
					this.shouldRender = false;
					this.counter = 300;
					
					for (int slot = 0; slot < this.inv.getSlotAmount(); slot++)
					{
						this.inv.set(slot, new ItemInstance(ores[Util.RANDOM.nextInt(ores.length)], Util.RANDOM.nextInt(100)));
					}
				}
			} else if (this.flightPart == RocketStage.COLLECTING)
			{
				this.motionY = 0;
				if (this.counter > 0)
				{
					if (world.getWorldInfo().totalTimeInWorld % 10 == 0)
					{
						System.out.println("Rocket collecting resources" + counter);
					}
					this.counter--;
					
					
				} else
				{
					this.flightPart = RocketStage.LANDING;
					this.shouldRender = true;
				}
			} else if (this.flightPart == RocketStage.LANDING)
			{
				System.out.println("LANDING");
				this.shouldRender = true;
				this.motionY = -0.01;
				this.fallAmount = 0;
				for (int i = 0; i < 10; i++)
				{
					RockBottomAPI.getGame().getParticleManager().addSmokeParticle(RockBottomAPI.getGame().getWorld(),
							this.x + 0.2 + ((Util.RANDOM.nextFloat() / 10) * 6), this.y + 0.3, 0, 0.4,
							RockBottomAPI.getGame().getWorldScale() * 0.0005f);
				}

				int newX = (int) Math.floor(this.x);
				int newY = (int) Math.round(this.y);
				if (this.fuel == 0)
				{
					if (RockSolidContent.rocket.canPlace(world, newX, newY, TileLayer.MAIN))
					{
						RockSolidContent.rocket.doPlace(world, newX, newY, TileLayer.MAIN, null, null);
						world.removeTileEntity(newX, newY);
						world.addTileEntity(new TileEntityRocket(world, newX, newY, this));
						this.kill();
					}
				} else if (world.getWorldInfo().totalTimeInWorld % 10 == 0)
				{
					this.fuel -= 1;
				}
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

	public int getFuel()
	{
		return this.fuel;
	}
	
	public ContainerInventory getInv()
	{
		return this.inv;
	}

	public float getTankFullness()
	{
		if (fuel == 0)
		{
			return 0;
		}
		return (float) this.fuel / (float) 10000;
	}

	@Override
	public boolean onInteractWith(AbstractEntityPlayer player, double mouseX, double mouseY)
	{
		player.openGuiContainer(new GuiEntityRocket(player, this), new ContainerEmpty(player));
		return true;
	}

	@Override
	public IEntityRenderer<EntityRocket> getRenderer()
	{
		return new RocketRenderer(RockSolidAPILib.makeInternalRes("rocket"));
	}
}