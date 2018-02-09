package com.raphydaphy.rocksolid.tile;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.util.ToolInfo;

import de.ellpeck.rockbottom.api.tile.MultiTile;

public abstract class MultiTileBase extends MultiTile
{

	public MultiTileBase(String name, float hardness, ToolInfo... infos)
	{
		this(name, hardness, false, infos);
	}

	public MultiTileBase(String name, float hardness, boolean forceDrop, ToolInfo... infos)
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

	public boolean[][] autoStructure(int width, int height)
	{
		boolean[][] struct = new boolean[width][height];

		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				struct[x][y] = true;
			}
		}
		System.out.println(struct.toString());
		return struct;
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
