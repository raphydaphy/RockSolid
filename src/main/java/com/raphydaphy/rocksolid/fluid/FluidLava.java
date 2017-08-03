package com.raphydaphy.rocksolid.fluid;

import java.util.ArrayList;

import com.raphydaphy.rocksolid.api.content.RockSolidContent;
import com.raphydaphy.rocksolid.api.fluid.Fluid;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.EntityLiving;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class FluidLava extends Fluid
{

	public FluidLava()
	{
		super("fluidLava");
		this.register();
		// super.setThickness(0.012);
	}

	@Override
	public int getLight(final IWorld world, final int x, final int y, final TileLayer layer)
	{
		return 20;
	}

	@Override
	public ArrayList<Fluid> getEnemyFluids()
	{
		ArrayList<Fluid> enemyFluids = new ArrayList<Fluid>();
		enemyFluids.add(RockSolidContent.fluidWater);
		return enemyFluids;
	}

	@Override
	public void onCollideWithEntity(IWorld world, int x, int y, TileLayer layer, Entity entity)
	{
		super.onCollideWithEntity(world, x, y, layer, entity);
		if (entity instanceof EntityLiving && world.getWorldInfo().totalTimeInWorld % 15 == 0)
		{
			((EntityLiving) entity).takeDamage(3);
		}
	}

	@Override
	public boolean canPlace(IWorld world, int x, int y, TileLayer layer)
	{
		return super.canPlace(world, x, y, layer)
				&& ((world.getState(x, y).getTile() == this) || world.getState(x, y).getTile() == GameContent.TILE_AIR);
	}

}
