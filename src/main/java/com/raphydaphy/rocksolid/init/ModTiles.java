package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.fluid.FluidWater;
import com.raphydaphy.rocksolid.tile.TileCreativeEnergySource;
import com.raphydaphy.rocksolid.tile.TileOre;
import com.raphydaphy.rocksolid.tile.conduit.TileEnergyConduit;
import com.raphydaphy.rocksolid.tile.conduit.TileFluidConduit;
import com.raphydaphy.rocksolid.tile.conduit.TileGasConduit;
import com.raphydaphy.rocksolid.tile.conduit.TileItemConduit;
import com.raphydaphy.rocksolid.tile.multi.*;
import de.ellpeck.rockbottom.api.tile.Tile;

public class ModTiles
{
	public static Tile COPPER_ORE;
	public static Tile TIN_ORE;
	public static Tile IRON_ORE;

	public static Tile BOILER;
	public static Tile PUMP;
	public static Tile CREATIVE_ENERGY_SOURCE;
	public static Tile TURBINE;

	public static Tile SMELTER;
	public static Tile SEPARATOR;
	public static Tile ALLOY_SMELTER;
	public static Tile ARC_FURNACE;

	public static Tile FLUID_CONDUIT;
	public static Tile GAS_CONDUIT;
	public static Tile ENERGY_CONDUIT;
	public static Tile ITEM_CONDUIT;

	public static Tile WATER;

	public static void init()
	{
		COPPER_ORE = new TileOre("ore.copper", 3.5f, 1, ModItems.COPPER_CLUSTER);
		TIN_ORE = new TileOre("ore.tin", 2.5f, 1, ModItems.TIN_CLUSTER);
		IRON_ORE = new TileOre("ore.iron", 4.5f, 3, ModItems.IRON_CLUSTER);

		BOILER = new TileBoiler();
		PUMP = new TilePump();
		CREATIVE_ENERGY_SOURCE = new TileCreativeEnergySource();
		TURBINE = new TileTurbine();


		SMELTER = new TileSmelter();
		SEPARATOR = new TileSeparator();
		ALLOY_SMELTER = new TileAlloySmelter();
		ARC_FURNACE = new TileArcFurnace();

		FLUID_CONDUIT = new TileFluidConduit();
		GAS_CONDUIT = new TileGasConduit();
		ENERGY_CONDUIT = new TileEnergyConduit();
		ITEM_CONDUIT = new TileItemConduit();
		
		WATER = new FluidWater();
	}
}
