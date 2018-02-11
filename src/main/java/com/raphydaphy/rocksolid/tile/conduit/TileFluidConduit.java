package com.raphydaphy.rocksolid.tile.conduit;

import com.raphydaphy.rocksolid.fluid.IFluidTile;

import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileFluidConduit extends TileConduit
{

	public TileFluidConduit()
	{
		super("fluid_conduit");
	}


	@Override
	public boolean canConnectAbstract(IWorld world, TileEntity te, Pos2 pos, TileState state)
	{
		return (te != null && te instanceof IFluidTile);
	}

}
