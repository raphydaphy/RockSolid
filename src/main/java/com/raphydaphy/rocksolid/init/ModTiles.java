package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tile.TileCreativeEnergySource;
import com.raphydaphy.rocksolid.tile.TileOre;
import com.raphydaphy.rocksolid.tile.conduit.TileEnergyConduit;
import com.raphydaphy.rocksolid.tile.conduit.TileFluidConduit;
import com.raphydaphy.rocksolid.tile.conduit.TileGasConduit;
import com.raphydaphy.rocksolid.tile.conduit.TileItemConduit;
import com.raphydaphy.rocksolid.tile.machine.*;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.resource.IResourceRegistry;
import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.TileBasic;

public class ModTiles
{
	public static Tile TIN_ORE;
	public static Tile IRON_ORE;
	public static Tile RUTILE_ORE;
	public static Tile MAGNESIUM_ORE;
	public static Tile URANIUM_ORE;

	public static Tile SEPARATOR;
	public static Tile ALLOY_SMELTER;
	public static Tile BLAST_FURNACE;

	public static Tile ASSEMBLY_STATION;

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

	public static String RES_TITANIUM_RAW;

	private static IResourceRegistry res()
	{
		return RockBottomAPI.getResourceRegistry();
	}

	public static void init()
	{
		TIN_ORE = new TileOre("ore.tin", 8f, 2, ModItems.TIN_CLUSTER);
		IRON_ORE = new TileOre("ore.iron", 12f, 12, ModItems.IRON_CLUSTER);
		MAGNESIUM_ORE = new TileOre("ore.magnesium", 12f, 17, ModItems.MAGNESIUM_CLUSTER);
		RUTILE_ORE = new TileBasic(RockSolid.createRes("ore.rutile")).setHardness(18f).addEffectiveTool(ToolType.PICKAXE, 19).register().setMaxAmount(15);
		URANIUM_ORE = new TileOre("ore.uranium", 15f, 25, ModItems.URANIUM_CLUSTER);

		SEPARATOR = new TileSeparator().setMaxAmount(1);
		ALLOY_SMELTER = new TileAlloySmelter().setMaxAmount(1);
		BLAST_FURNACE = new TileBlastFurnace().setMaxAmount(1);

		ASSEMBLY_STATION = new TileAssemblyStation().setMaxAmount(1);

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

		RES_TITANIUM_RAW = res().addResources("titanium_raw", RUTILE_ORE);
	}
}
