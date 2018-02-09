package com.raphydaphy.rocksolid.tile;

import com.raphydaphy.rocksolid.util.ToolInfo;

import de.ellpeck.rockbottom.api.item.ToolType;

public class TileBoiler extends MultiTileBase
{

	public TileBoiler()
	{
		super("boiler", 3f, new ToolInfo(ToolType.PICKAXE, 1));
	}

	@Override
	public int getWidth()
	{
		return 2;
	}

	public int getHeight()
	{
		return 4;
	}

	@Override
	protected boolean[][] makeStructure()
	{
		return super.autoStructure(4,2);
	}

}
