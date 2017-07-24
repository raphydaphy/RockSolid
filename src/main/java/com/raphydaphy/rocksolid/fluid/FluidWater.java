package com.raphydaphy.rocksolid.fluid;

import java.util.ArrayList;

import com.raphydaphy.rocksolid.api.Fluid;
import com.raphydaphy.rocksolid.init.ModFluids;

import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class FluidWater extends Fluid {

	public FluidWater() {
		super("fluidWater");
	}

	@Override
	public ArrayList<Fluid> getEnemyFluids() {
		ArrayList<Fluid> enemyFluids = new ArrayList<Fluid>();
		enemyFluids.add(ModFluids.fluidLava);
		return enemyFluids;
	}
	
	@Override
	public void onCollideWithEntity(IWorld world, int x, int y, TileLayer layer, Entity entity)
	{
		super.onCollideWithEntity(world, x, y, layer, entity);
		entity.fallAmount = 0;
	}

}
