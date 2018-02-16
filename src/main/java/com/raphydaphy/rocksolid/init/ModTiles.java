package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.fluid.FluidWater;
import com.raphydaphy.rocksolid.tile.TileCreativeEnergySource;
import com.raphydaphy.rocksolid.tile.conduit.TileEnergyConduit;
import com.raphydaphy.rocksolid.tile.conduit.TileFluidConduit;
import com.raphydaphy.rocksolid.tile.conduit.TileGasConduit;
import com.raphydaphy.rocksolid.tile.conduit.TileItemConduit;
import com.raphydaphy.rocksolid.tile.multi.TileArcFurnace;
import com.raphydaphy.rocksolid.tile.multi.TileBoiler;
import com.raphydaphy.rocksolid.tile.multi.TilePump;

import com.raphydaphy.rocksolid.tile.multi.TileTurbine;
import de.ellpeck.rockbottom.api.tile.Tile;

public class ModTiles
{
	public static Tile BOILER;
	public static Tile PUMP;
	public static Tile CREATIVE_ENERGY_SOURCE;
	public static Tile TURBINE;
	public static Tile ARC_FURNACE;

	public static Tile FLUID_CONDUIT;
	public static Tile GAS_CONDUIT;
	public static Tile ENERGY_CONDUIT;
	public static Tile ITEM_CONDUIT;

	public static Tile WATER;

	public static void init()
	{
		BOILER = new TileBoiler();
		PUMP = new TilePump();
		CREATIVE_ENERGY_SOURCE = new TileCreativeEnergySource();
		TURBINE = new TileTurbine();
		ARC_FURNACE = new TileArcFurnace();

		FLUID_CONDUIT = new TileFluidConduit();
		GAS_CONDUIT = new TileGasConduit();
		ENERGY_CONDUIT = new TileEnergyConduit();
		ITEM_CONDUIT = new TileItemConduit();
		
		WATER = new FluidWater();
	}
}
