package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.fluid.IFluidTile;

import de.ellpeck.rockbottom.api.tile.TileLiquid;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileEntityFluidConduit extends TileEntityConduit
{
	public TileEntityFluidConduit(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer, TileEntityFluidConduit.class);
	}

	@Override
	public boolean transfer(IWorld world, int x1, int y1, ConduitSide side1, TileEntity tile1, int x2, int y2,
			ConduitSide side2, TileEntity tile2, boolean simulate)
	{
		if (tile1 != null && tile1 instanceof IFluidTile && tile2 != null && tile2 instanceof IFluidTile && world.isPosLoaded(x1, y1) && world.isPosLoaded(x2, y2))
		{
			IFluidTile<?> f1 = (IFluidTile<?>) tile1;
			IFluidTile<?> f2 = (IFluidTile<?>) tile2;

			TileLiquid toTransfer = null;
			Pos2 pos1 = new Pos2(x1, y1);
			for (TileLiquid liquid : f1.getLiquidsAt(world, new Pos2(x1, y1)))
			{
				if (liquid != null && f1.remove(pos1, liquid, 10, true))
				{
					toTransfer = liquid;
					break;
				}
			}

			if (toTransfer != null)
			{
				Pos2 pos2 = new Pos2(x2, y2);
				if (f2.add(pos2, toTransfer, 10, true))
				{
					if (!simulate)
					{
						f2.add(pos2, toTransfer, 10, false);
						f1.remove(pos1, toTransfer, 10, false);
					}
					return true;
				}

			}
		}
		return false;
	}

}
