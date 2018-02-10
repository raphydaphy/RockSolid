package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.fluid.FluidWater;
import com.raphydaphy.rocksolid.tile.TileBoiler;
import com.raphydaphy.rocksolid.tile.TilePump;

import de.ellpeck.rockbottom.api.tile.Tile;

public class ModTiles
{
	public static Tile BOILER;
	public static Tile PUMP;
	
	public static Tile WATER;

	public static void init()
	{
		BOILER = new TileBoiler();
		PUMP = new TilePump();
		
		WATER = new FluidWater();
	}
}
