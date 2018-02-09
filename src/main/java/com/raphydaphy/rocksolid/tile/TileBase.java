package com.raphydaphy.rocksolid.tile;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.util.ToolInfo;

import de.ellpeck.rockbottom.api.tile.TileBasic;

public class TileBase extends TileBasic
{
	public TileBase(String name, float hardness, ToolInfo... infos)
	{
		this(name, hardness, false, infos);
	}

	public TileBase(String name, float hardness, boolean forceDrop, ToolInfo... infos)
	{
		super(RockSolid.createRes(name));
		this.setHardness(hardness);

		if (forceDrop)
		{
			this.setForceDrop();
		}

		for (ToolInfo info : infos)
		{
			this.addEffectiveTool(info.getType(), info.getLevel());
		}

		this.register();
	}
}
