package com.raphydaphy.rocksolid.fluid;

import java.util.ArrayList;

import com.raphydaphy.rocksolid.api.content.BaseFluids;
import com.raphydaphy.rocksolid.api.fluid.Fluid;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class FluidWater extends Fluid
{

	public FluidWater()
	{
		super("fluidWater");
		this.register();
	}

	@Override
	public ArrayList<Fluid> getEnemyFluids()
	{
		ArrayList<Fluid> enemyFluids = new ArrayList<Fluid>();
		enemyFluids.add(BaseFluids.fluidLava);
		return enemyFluids;
	}

	@Override
	public void onCollideWithEntity(IWorld world, int x, int y, TileLayer layer, Entity entity)
	{
		super.onCollideWithEntity(world, x, y, layer, entity);
		entity.fallAmount = 0;
	}

	@Override
	public boolean canPlace(IWorld world, int x, int y, TileLayer layer)
	{
		return super.canPlace(world, x, y, layer)
				&& ((world.getState(x, y).getTile() == this) || world.getState(x, y).getTile() == GameContent.TILE_AIR);
	}

}
