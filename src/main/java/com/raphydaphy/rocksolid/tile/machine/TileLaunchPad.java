package com.raphydaphy.rocksolid.tile.machine;

import com.raphydaphy.rocksolid.tileentity.TileEntityLaunchPad;
import com.raphydaphy.rocksolid.util.ToolInfo;
import de.ellpeck.rockbottom.api.item.ToolProperty;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileLaunchPad extends TileMachineBase<TileEntityLaunchPad>
{
	public TileLaunchPad()
	{
		super("launch_pad", TileEntityLaunchPad.class,30, true, new ToolInfo(ToolProperty.PICKAXE, 11));
	}

	@Override
	public TileEntity makeTE(IWorld world, int x, int y, TileLayer layer)
	{
		return new TileEntityLaunchPad(world, x, y, layer);
	}

	@Override
	protected boolean[][] makeStructure()
	{
		return autoStructure(3, 1);
	}

	@Override
	public int getWidth()
	{
		return 3;
	}

	@Override
	public int getHeight()
	{
		return 1;
	}
}
