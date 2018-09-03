package com.raphydaphy.rocksolid.tile;

import com.raphydaphy.rocksolid.RockSolid;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileCotton extends MultiTile
{
	public TileCotton()
	{
		super(RockSolid.createRes("cotton"));
		setHardness(0);
		setForceDrop();
		register();
	}

	@Override
	protected boolean[][] makeStructure()
	{
		return new boolean[][]{{true}, {true}};
	}

	@Override
	public int getWidth()
	{
		return 1;
	}

	@Override
	public int getHeight()
	{
		return 2;
	}

	@Override
	public int getMainX()
	{
		return 0;
	}

	@Override
	public int getMainY()
	{
		return 0;
	}

	public boolean canStay(IWorld world, int x, int y, TileLayer layer, int changedX, int changedY, TileLayer changedLayer)
	{
		TileState state = world.getState(x,y);
		return state.getTile() == this && canStay(world, x, y, layer, state.get(this.propSubY) == 1);
	}

	public boolean canPlace(IWorld world, int x, int y, TileLayer layer, AbstractEntityPlayer player)
	{
		return world.isPosLoaded((double) x, (double) (y - 1), false) && canStay(world, x, y, layer, false);
	}

	private static boolean canStay(IWorld world, int x, int y, TileLayer layer, boolean top)
	{
		return world.getState(layer, x, y - (top ? 2 : 1)).getTile().canKeepPlants(world, x, y, layer) && world.getState(TileLayer.LIQUIDS, x, y).getTile().isAir();
	}

	public boolean canReplace(IWorld world, int x, int y, TileLayer layer)
	{
		return true;
	}

	public boolean isFullTile()
	{
		return false;
	}

	public BoundBox getBoundBox(IWorld world, int x, int y, TileLayer layer)
	{
		return null;
	}
}
