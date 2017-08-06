package com.raphydaphy.rocksolid.entity;

import com.raphydaphy.rocksolid.api.gui.ContainerEmpty;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.gui.GuiRocket;
import com.raphydaphy.rocksolid.render.RocketRenderer;
import com.raphydaphy.rocksolid.tileentity.TileEntityRocket;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.render.entity.IEntityRenderer;
import de.ellpeck.rockbottom.api.util.BoundBox;
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
		this.motionY = 0.05;
		this.fallAmount = 0;
		rocketTile.update(game);
		
	}

	@Override
	public boolean onInteractWith(AbstractEntityPlayer player, double mouseX, double mouseY)
	{
		if (rocketTile != null)
		{
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