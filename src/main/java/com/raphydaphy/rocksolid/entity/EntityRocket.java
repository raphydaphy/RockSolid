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

	public EntityRocket(IWorld world, IResourceName name, int x, int y)
	{
		super(world);
		if (rocketTile == null)
		{
			rocketTile = (TileEntityRocket)RockSolidAPILib.getTileFromPos(x, y, world);
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

	@Override
	public void save(DataSet set)
	{
		super.save(set);
		DataSet te = new DataSet();
        rocketTile.save(set, false);
        set.addDataSet("te", te);
	}

	@Override
	public void load(DataSet set)
	{
		super.load(set);
		rocketTile = new TileEntityRocket(world, (int)x, (int)y, this);
        rocketTile.load(set.getDataSet("te"), false);
	}

	@Override
	public void update(IGameInstance game)
	{
		super.update(game);
		if (rocketTile.getCurrentFluid() > 0)
		{
			if (this.y < 200)
			{
				this.motionY = 0.1;
				this.fallAmount = 0;
				for (int i = 0; i < 10; i++)
				{
					RockBottomAPI.getGame().getParticleManager().addSmokeParticle(
							RockBottomAPI.getGame().getWorld(), this.x + 0.2+((Util.RANDOM.nextFloat() /10) * 6),
							this.y +0.3, 0, -0.4, RockBottomAPI.getGame().getWorldScale() * 0.0005f);
				}
				
				rocketTile.update(game);
			}
			else
			{
				this.kill();
				System.out.println("Rocket entered space.");
			}
		} else
		{
			if (this.onGround)
			{
				EntityItem.spawn(this.world, new ItemInstance(RockSolidContent.rocket), this.x, this.y + 1, Util.RANDOM.nextGaussian()*0.1, Util.RANDOM.nextGaussian()*0.1);
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
				rocketTile = new TileEntityRocket(world, (int)x, (int)y, this);
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