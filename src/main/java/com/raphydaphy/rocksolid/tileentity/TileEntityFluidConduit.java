package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.fluid.IFluidTile;

import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.TileLiquid;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileEntityFluidConduit extends TileEntityConduit
{
	public TileEntityFluidConduit(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
	}

	@Override
	public void transfer(IWorld world, int x1, int y1, TileState state1, int x2, int y2, TileState state2)
	{
		TileEntity tile1 = null;
		if (state1.getTile() instanceof MultiTile)
		{
			Pos2 main = ((MultiTile) state1.getTile()).getMainPos(x1, y1, state1);
			tile1 = world.getTileEntity(main.getX(), main.getY());
		} else
		{
			tile1 = world.getTileEntity(x1, y1);
		}
		if (tile1 != null && tile1 instanceof IFluidTile)
		{
			TileEntity tile2 = null;
			if (state1.getTile() instanceof MultiTile)
			{
				Pos2 main = ((MultiTile) state1.getTile()).getMainPos(x2, y2, state1);
				tile2 = world.getTileEntity(main.getX(), main.getY());

			} else
			{
				tile2 = world.getTileEntity(x2, y2);
			}

			if (tile2 != null && tile2 instanceof IFluidTile)
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
					}
				}

				if (toTransfer != null)
				{
					Pos2 pos2 = new Pos2(x2, y2);
					if (f2.add(pos2, toTransfer, 10, true))
					{
						f2.add(pos2, toTransfer, 10, false);
					}
				}
			}
		}
	}

}
