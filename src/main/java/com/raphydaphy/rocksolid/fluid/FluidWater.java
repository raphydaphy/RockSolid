package com.raphydaphy.rocksolid.fluid;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.render.FluidRenderer;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.TileLiquid;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.List;

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
	public void onIntersectWithEntity(IWorld world, int x, int y, TileLayer layer, TileState state, BoundBox entityBox, BoundBox entityBoxMotion, List<BoundBox> tileBoxes, Entity entity)
	{
		entity.motionX *= 0.95D;
	}

	@Override
	public void onAdded(IWorld world, int x, int y, TileLayer layer)
	{
		if (!world.isClient() && this.doesFlow())
		{
			world.scheduleUpdate(x, y, layer, this.getFlowSpeed());
		}
	}

	@Override
	public void onChangeAround(IWorld world, int x, int y, TileLayer layer, int changedX, int changedY, TileLayer changedLayer){
		if(changedLayer == TileLayer.MAIN && changedX == x && changedY == y){
			TileState state = world.getState(x, y);
			if(state.getTile().isFullTile()){
				world.setState(layer, x, y, GameContent.TILE_AIR.getDefState());
			}
		}

		if(!world.isClient() && this.doesFlow()){
			world.scheduleUpdate(x, y, layer, this.getFlowSpeed());
		}
	}

}
