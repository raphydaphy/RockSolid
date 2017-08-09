package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.tile.TileAlloySmelter;
import com.raphydaphy.rocksolid.tile.TileAnalyzer;
import com.raphydaphy.rocksolid.tile.TileBase;
import com.raphydaphy.rocksolid.tile.TileBattery;
import com.raphydaphy.rocksolid.tile.TileBlastFurnace;
import com.raphydaphy.rocksolid.tile.TileBoiler;
import com.raphydaphy.rocksolid.tile.TileCharger;
import com.raphydaphy.rocksolid.tile.TileCombustionEngine;
import com.raphydaphy.rocksolid.tile.TileCompressor;
import com.raphydaphy.rocksolid.tile.TileCreativePowerSource;
import com.raphydaphy.rocksolid.tile.TileCustomDrop;
import com.raphydaphy.rocksolid.tile.TileElectricAlloySmelter;
import com.raphydaphy.rocksolid.tile.TileElectricBlastFurnace;
import com.raphydaphy.rocksolid.tile.TileElectricPurifier;
import com.raphydaphy.rocksolid.tile.TileElectricSeparator;
import com.raphydaphy.rocksolid.tile.TileElectricSmelter;
import com.raphydaphy.rocksolid.tile.TileElectrolyzer;
import com.raphydaphy.rocksolid.tile.TileEnergyConduit;
import com.raphydaphy.rocksolid.tile.TileFluidConduit;
import com.raphydaphy.rocksolid.tile.TileFluidPump;
import com.raphydaphy.rocksolid.tile.TileGasConduit;
import com.raphydaphy.rocksolid.tile.TileGasTank;
import com.raphydaphy.rocksolid.tile.TileItemConduit;
import com.raphydaphy.rocksolid.tile.TileLiquidBoiler;
import com.raphydaphy.rocksolid.tile.TileNuclearReactor;
import com.raphydaphy.rocksolid.tile.TileOre;
import com.raphydaphy.rocksolid.tile.TilePlaceAnywhere;
import com.raphydaphy.rocksolid.tile.TileQuarry;
import com.raphydaphy.rocksolid.tile.TileRefinery;
import com.raphydaphy.rocksolid.tile.TileRockCrusher;
import com.raphydaphy.rocksolid.tile.TileRocket;
import com.raphydaphy.rocksolid.tile.TileTank;
import com.raphydaphy.rocksolid.tile.TileTurbine;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.tile.Tile;

public class ModTiles
{
	public static Tile alloySmelter;
	public static Tile blastFurnace;

	public static Tile itemConduit;
	public static Tile energyConduit;
	public static Tile fluidConduit;
	public static Tile gasConduit;

	public static Tile quarry;
	public static Tile creativePowerSource;
	public static Tile charger;

	public static Tile rocketEngine;
	public static Tile rocketFairing;
	public static Tile rocket;
	public static Tile analyzer;

	public static Tile rockLight;
	public static Tile limestone;
	public static Tile clay;

	public static Tile tank;
	public static Tile fluidPump;
	public static Tile gasTank;
	public static Tile electrolyzer;

	public static Tile boiler;
	public static Tile liquidBoiler;
	public static Tile turbine;

	public static Tile combustionEngine;
	public static Tile nuclearReactor;
	public static Tile battery;

	public static Tile rockCrusher;
	public static Tile refinery;
	public static Tile electricPurifier;
	public static Tile electricBlastFurnace;
	public static Tile electricAlloySmelter;
	public static Tile electricSeparator;
	public static Tile electricSmelter;
	public static Tile compressor;

	public static Tile oreTin;
	public static Tile oreIron;
	public static Tile oreMagnesium;
	public static Tile oreRutile;
	public static Tile oreUranium;
	public static Tile oreWolframite;
	public static Tile oreCobalt;
	public static Tile oreNickel;
	public static Tile oreAluminum;

	public static Tile constructionBlockSteel;
	public static Tile constructionBlockTitanium;

	public static void init()
	{

		alloySmelter = new TileAlloySmelter();
		blastFurnace = new TileBlastFurnace();

		itemConduit = new TileItemConduit();
		energyConduit = new TileEnergyConduit();
		fluidConduit = new TileFluidConduit();
		gasConduit = new TileGasConduit();

		quarry = new TileQuarry();
		creativePowerSource = new TileCreativePowerSource();
		charger = new TileCharger();

		rocketEngine = new TilePlaceAnywhere("rocketEngine", 15, 4);
		rocketFairing = new TilePlaceAnywhere("rocketFairing", 20, 4);
		rocket = new TileRocket();
		analyzer = new TileAnalyzer();

		rockLight = new TileCustomDrop("rockLight", new ItemInstance(GameContent.TILE_STONE, 1), 5, 1);
		limestone = new TileBase(RockSolidAPILib.makeInternalRes("limestone"), 10, 1);
		clay = new TileCustomDrop("clay", new ItemInstance(ModItems.clumpClay, 1), 4, 0, ToolType.SHOVEL);

		tank = new TileTank();
		fluidPump = new TileFluidPump();
		gasTank = new TileGasTank();
		electrolyzer = new TileElectrolyzer();

		boiler = new TileBoiler();
		liquidBoiler = new TileLiquidBoiler();
		turbine = new TileTurbine();

		combustionEngine = new TileCombustionEngine();
		nuclearReactor = new TileNuclearReactor();
		battery = new TileBattery();

		rockCrusher = new TileRockCrusher();
		refinery = new TileRefinery();
		electricPurifier = new TileElectricPurifier();
		electricBlastFurnace = new TileElectricBlastFurnace();
		electricAlloySmelter = new TileElectricAlloySmelter();
		electricSeparator = new TileElectricSeparator();
		electricSmelter = new TileElectricSmelter();
		compressor = new TileCompressor();

		oreTin = new TileOre(RockSolidAPILib.makeInternalRes("oreTin"), new ItemInstance(ModItems.clusterTin, 1), 10,
				4);
		oreIron = new TileOre(RockSolidAPILib.makeInternalRes("oreIron"), new ItemInstance(ModItems.clusterIron, 1), 15,
				6);
		oreMagnesium = new TileOre(RockSolidAPILib.makeInternalRes("oreMagnesium"),
				new ItemInstance(ModItems.clusterMagnesium, 1), 20, 7);
		oreRutile = new TileBase(RockSolidAPILib.makeInternalRes("oreRutile"), 30, 8);
		oreUranium = new TileOre(RockSolidAPILib.makeInternalRes("oreUranium"),
				new ItemInstance(ModItems.clusterUranium, 1), 25, 10);
		oreWolframite = new TileOre(RockSolidAPILib.makeInternalRes("oreWolframite"),
				new ItemInstance(ModItems.clusterTungsten, 1), 35, 10);

		oreCobalt = new TileBase(RockSolidAPILib.makeInternalRes("oreCobalt"), 50, 10);
		oreNickel = new TileBase(RockSolidAPILib.makeInternalRes("oreNickel"), 45, 10);
		oreAluminum = new TileBase(RockSolidAPILib.makeInternalRes("oreAluminum"), 40, 10);

		constructionBlockSteel = new TileBase(RockSolidAPILib.makeInternalRes("constructionBlockSteel"), 10, 3);
		constructionBlockTitanium = new TileBase(RockSolidAPILib.makeInternalRes("constructionBlockTitanium"), 25, 4);
	}
}
