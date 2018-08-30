package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.tile.*;
import com.raphydaphy.rocksolid.tile.conduit.TileEnergyConduit;
import com.raphydaphy.rocksolid.tile.conduit.TileFluidConduit;
import com.raphydaphy.rocksolid.tile.conduit.TileGasConduit;
import com.raphydaphy.rocksolid.tile.conduit.TileItemConduit;
import com.raphydaphy.rocksolid.tile.liquid.TileOil;
import com.raphydaphy.rocksolid.tile.machine.*;
import com.raphydaphy.rocksolid.util.ToolInfo;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.resource.IResourceRegistry;
import de.ellpeck.rockbottom.api.item.ToolProperty;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.TileLiquid;

@SuppressWarnings("WeakerAccess")
public class ModTiles
{
	public static Tile TIN_ORE;
	public static Tile IRON_ORE;
	public static Tile RUTILE_ORE;
	public static Tile MAGNESIUM_ORE;
	public static Tile URANIUM_ORE;
	public static Tile WOLFRAMITE_ORE;
	public static Tile LUNAR_NICKEL_ORE;
	public static Tile LUNAR_ALUMINUM_ORE;
	public static Tile LUNAR_COBALT_ORE;

	public static TileLiquid OIL;

	public static Tile SEPARATOR;
	public static Tile ALLOY_SMELTER;
	public static Tile BLAST_FURNACE;

	public static Tile ASSEMBLY_STATION;
	public static Tile TEMPSHIFT_PLATE;

	public static Tile BOILER;
	public static Tile PUMP;
	public static Tile CREATIVE_ENERGY_SOURCE;
	public static Tile TURBINE;
	public static Tile NUCLEAR_REACTOR;
	public static Tile BATTERY;

    public static Tile ELECTRIC_FURNACE;
	public static Tile ELECTRIC_SEPARATOR;
	public static Tile ELECTRIC_ALLOY_SMELTER;
	public static Tile ELECTRIC_BLAST_FURNACE;
	public static Tile ELECTRIC_COMPRESSOR;

	public static Tile FLUID_CONDUIT;
	public static Tile GAS_CONDUIT;
	public static Tile ENERGY_CONDUIT;
	public static Tile ITEM_CONDUIT;

	public static Tile MOON_TURF;
	public static Tile MOON_STONE;

	public static String RES_TITANIUM_RAW;
	public static String RES_MOON_STONE;
	public static String RES_MOON_TURF;

	private static IResourceRegistry res()
	{
		return RockBottomAPI.getResourceRegistry();
	}

	public static void init()
	{
		TIN_ORE = new TileOre("ore.tin", 8f, 2, ModItems.TIN_CLUSTER);
		IRON_ORE = new TileOre("ore.iron", 12f, 12, ModItems.IRON_CLUSTER);
		MAGNESIUM_ORE = new TileOre("ore.magnesium", 12f, 17, ModItems.MAGNESIUM_CLUSTER);
		RUTILE_ORE = new TileBase("ore.rutile", 18, new ToolInfo(ToolProperty.PICKAXE, 19)).setMaxAmount(15);
		URANIUM_ORE = new TileOre("ore.uranium", 15f, 25, ModItems.URANIUM_CLUSTER);
		WOLFRAMITE_ORE = new TileOre("ore.wolframite", 25f, 25, ModItems.TUNGSTEN_CLUSTER);
		LUNAR_NICKEL_ORE = new TileOre("ore.lunar.nickel", 13f, 15, ModItems.LUNAR_NICKEL_CLUSTER);
		LUNAR_ALUMINUM_ORE = new TileOre("ore.lunar.aluminum", 18f, 23, ModItems.LUNAR_ALUMINUM_CLUSTER);
		LUNAR_COBALT_ORE = new TileOre("ore.lunar.cobalt", 25f, 28, ModItems.LUNAR_COBALT_CLUSTER);

		OIL = new TileOil();

		SEPARATOR = new TileSeparator().setMaxAmount(1);
		ALLOY_SMELTER = new TileAlloySmelter().setMaxAmount(1);
		BLAST_FURNACE = new TileBlastFurnace().setMaxAmount(1);

		ASSEMBLY_STATION = new TileAssemblyStation().setMaxAmount(1);
		TEMPSHIFT_PLATE = new TileTempshiftPlate().setMaxAmount(5);

		BOILER = new TileBoiler().setMaxAmount(1);
		PUMP = new TilePump().setMaxAmount(1);
		CREATIVE_ENERGY_SOURCE = new TileCreativeEnergySource().setMaxAmount(1);
		TURBINE = new TileTurbine().setMaxAmount(1);
		NUCLEAR_REACTOR = new TileNuclearReactor().setMaxAmount(1);
		BATTERY = new TileBattery().setMaxAmount(1);

		ELECTRIC_FURNACE = new TileElectricFurnace().setMaxAmount(1);
		ELECTRIC_SEPARATOR = new TileElectricSeparator().setMaxAmount(1);
		ELECTRIC_ALLOY_SMELTER = new TileElectricAlloySmelter().setMaxAmount(1);
		ELECTRIC_BLAST_FURNACE = new TileElectricBlastFurnace().setMaxAmount(1);
		ELECTRIC_COMPRESSOR = new TileElectricCompressor().setMaxAmount(1);
		
		FLUID_CONDUIT = new TileFluidConduit().setMaxAmount(25);
		GAS_CONDUIT = new TileGasConduit().setMaxAmount(25);
		ENERGY_CONDUIT = new TileEnergyConduit().setMaxAmount(25);
		ITEM_CONDUIT = new TileItemConduit().setMaxAmount(25);

		MOON_TURF = new TileBase("moon.turf", 7, new ToolInfo(ToolProperty.PICKAXE, 10)).setMaxAmount(50);
		MOON_STONE = new TileBase("moon.stone", 8, new ToolInfo(ToolProperty.PICKAXE, 10)).setMaxAmount(50);

		RES_TITANIUM_RAW = res().addResources("titanium_raw", RUTILE_ORE);
		RES_MOON_STONE = res().addResources("moon_stone", MOON_STONE);
		RES_MOON_TURF = res().addResources("moon_turf", MOON_TURF);
	}
}
