package com.raphydaphy.rocksolid.tile;

import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;

import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.tile.TileBasic;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TilePlaceAnywhere extends TileBasic
{

	public TilePlaceAnywhere(String name, int hardness, int toolLevel)
	{
		super(RockSolidAPILib.makeInternalRes(name));
		this.setHardness((float) hardness);
		this.addEffectiveTool(ToolType.PICKAXE, toolLevel);
		this.register();
	}

	@Override
	public boolean canPlace(IWorld world, int x, int y, TileLayer layer)
	{
		if (!this.canPlaceInLayer(layer))
		{
			return false;
		}

		return true;
	}

	@Override
	public BoundBox getBoundBox(final IWorld world, final int x, final int y)
	{
		return null;
	}

	@Override
	public boolean isFullTile()
	{
		return false;
	}

}
