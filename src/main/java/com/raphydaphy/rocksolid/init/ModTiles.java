package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.tile.TileBase;
import com.raphydaphy.rocksolid.util.ToolInfo;

import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.tile.Tile;

public class ModTiles
{
	public static Tile TEST_TILE;

	public static void init()
	{
		TEST_TILE = new TileBase("test", 1f, new ToolInfo(ToolType.PICKAXE, 1));
	}
}
