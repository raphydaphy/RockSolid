package com.raphydaphy.rocksolid.tile.conduit;

import java.util.List;

import com.raphydaphy.rocksolid.fluid.IFluidTile;
import com.raphydaphy.rocksolid.tileentity.TileEntityFluidConduit;

import de.ellpeck.rockbottom.api.tile.TileLiquid;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileFluidConduit extends TileConduit
{

	public TileFluidConduit()
	{
		super("fluid_conduit");
	}

	@Override
	public boolean canConnectAbstract(IWorld world, TileEntity te, Pos2 pos, TileState state)
	{
		if (te != null && te instanceof IFluidTile)
		{
			List<TileLiquid> liquids = ((IFluidTile<?>) te).getLiquidsAt(world, pos);

			return liquids != null && liquids.size() > 0;
		}
		return false;
	}

	@Override
	public TileEntity provideTileEntity(IWorld world, int x, int y, TileLayer layer)
	{
		return new TileEntityFluidConduit(world, x, y, layer);
	}

}
