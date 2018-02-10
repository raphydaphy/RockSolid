package com.raphydaphy.rocksolid.fluid;

import java.util.List;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.render.FluidRenderer;

import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.TileLiquid;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class FluidWater extends TileLiquid
{

	public FluidWater()
	{
		super(RockSolid.createRes("fluid_water"));
		this.register();
	}

	@Override
	protected ITileRenderer<FluidWater> createRenderer(IResourceName name)
	{
		return new FluidRenderer(name, this);
	}

	@Override
	public int getLevels()
	{
		return 12;
	}

	@Override
	public boolean doesFlow()
	{
		return true;
	}

	@Override
	public int getFlowSpeed()
	{
		return 5;
	}

	@Override
	public void onIntersectWithEntity(IWorld world, int x, int y, TileLayer layer, TileState state, BoundBox entityBox,
			BoundBox entityBoxMotion, List<BoundBox> tileBoxes, Entity entity)
	{
		entity.motionX *= 0.95D;
	}

}
