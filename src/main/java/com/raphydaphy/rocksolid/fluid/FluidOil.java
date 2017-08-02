package com.raphydaphy.rocksolid.fluid;

import java.util.ArrayList;

import com.raphydaphy.rocksolid.api.fluid.Fluid;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class FluidOil extends Fluid
{

	public FluidOil()
	{
		super("fluidOil");
		this.register();
	}

	@Override
	public ArrayList<Fluid> getEnemyFluids()
	{
		return null;
	}

	@Override
	public void onCollideWithEntity(IWorld world, int x, int y, TileLayer layer, Entity entity)
	{
		super.onCollideWithEntity(world, x, y, layer, entity);
		if (entity.motionY > 0)
		{
			entity.motionY -= 0.0001;
		}
	}

	@Override
	public boolean canPlace(IWorld world, int x, int y, TileLayer layer)
	{
		return super.canPlace(world, x, y, layer)
				&& ((world.getState(x, y).getTile() == this) || world.getState(x, y).getTile() == GameContent.TILE_AIR);
	}

}
