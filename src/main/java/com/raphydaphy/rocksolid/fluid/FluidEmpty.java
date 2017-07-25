package com.raphydaphy.rocksolid.fluid;

import java.util.ArrayList;

import com.raphydaphy.rocksolid.api.Fluid;

import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class FluidEmpty extends Fluid {

	public FluidEmpty() {
		super("fluidEmpty");
		//super.setThickness(0.012);
	}
	
	@Override
    public int getLight(final IWorld world, final int x, final int y, final TileLayer layer) 
	{
		return 0;
    }
	
	@Override
	public ArrayList<Fluid> getEnemyFluids() {
		return new ArrayList<Fluid>();
	}
	
	@Override
	public void onCollideWithEntity(IWorld world, int x, int y, TileLayer layer, Entity entity)
	{
		super.onCollideWithEntity(world, x, y, layer, entity);
	}
	
	@Override
	public boolean canPlace(IWorld world, int x, int y, TileLayer layer)
	{
		return false;
	}
}
