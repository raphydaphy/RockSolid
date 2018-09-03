package com.raphydaphy.rocksolid.tile;

import com.raphydaphy.rocksolid.RockSolid;
import de.ellpeck.rockbottom.api.tile.MultiTile;
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
	public BoundBox getBoundBox(IWorld world, int x, int y, TileLayer layer)
	{
		return null;
	}

	@Override
	public boolean isFullTile()
	{
		return false;
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
}
