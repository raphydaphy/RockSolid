package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.tile.TileAllocator;
import com.raphydaphy.rocksolid.tile.TileAlloySmelter;
import com.raphydaphy.rocksolid.tile.TileBase;
import com.raphydaphy.rocksolid.tile.TileBattery;
import com.raphydaphy.rocksolid.tile.TileBlastFurnace;
import com.raphydaphy.rocksolid.tile.TileCharger;
import com.raphydaphy.rocksolid.tile.TileChest;
import com.raphydaphy.rocksolid.tile.TileCoalGenerator;
import com.raphydaphy.rocksolid.tile.TileCompressor;
import com.raphydaphy.rocksolid.tile.TileCreativePowerSource;
import com.raphydaphy.rocksolid.tile.TileCustomDrop;
import com.raphydaphy.rocksolid.tile.TileElectricAlloySmelter;
import com.raphydaphy.rocksolid.tile.TileElectricBlastFurnace;
import com.raphydaphy.rocksolid.tile.TileElectricSeparator;
import com.raphydaphy.rocksolid.tile.TileElectricSmelter;
import com.raphydaphy.rocksolid.tile.TileEnergyConduit;
import com.raphydaphy.rocksolid.tile.TileFluidConduit;
import com.raphydaphy.rocksolid.tile.TileLamp;
import com.raphydaphy.rocksolid.tile.TileNuclearReactor;
import com.raphydaphy.rocksolid.tile.TileOre;
import com.raphydaphy.rocksolid.tile.TileQuarry;
import com.raphydaphy.rocksolid.tile.TileTank;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.tile.Tile;

public class ModTiles 
{
	public static Tile alloySmelter;
	public static Tile blastFurnace;
	
	public static Tile allocator;
	public static Tile energyConduit;
	public static Tile fluidConduit;
	public static Tile chest;
	
	public static Tile quarry;
	public static Tile creativePowerSource;
	public static Tile charger;
	
	public static Tile lamp;
	public static Tile rockLight;
	public static Tile limestone;
	public static Tile clay;
	
	public static Tile tank;
	
	public static Tile coalGenerator;
	public static Tile nuclearReactor;
	public static Tile battery;
	
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
	
	public static Tile constructionBlockSteel;
	public static Tile constructionBlockTitanium;
	
	public static void init() 
	{
		
		alloySmelter = new TileAlloySmelter();
		blastFurnace = new TileBlastFurnace();
		
		allocator = new TileAllocator();
		energyConduit = new TileEnergyConduit();
		fluidConduit = new TileFluidConduit();
		chest = new TileChest().register();
		
		quarry = new TileQuarry();
		creativePowerSource = new TileCreativePowerSource();
		charger = new TileCharger();
		
		lamp = new TileLamp(RockSolidLib.makeRes("lamp"));
		rockLight = new TileCustomDrop("rockLight", new ItemInstance(GameContent.TILE_ROCK, 1), 5, 1);
		limestone = new TileBase(RockSolidLib.makeRes("limestone"), 10, 3);
		clay = new TileCustomDrop("clay", new ItemInstance(ModItems.clumpClay, 1), 8, 3, ToolType.SHOVEL);
		
		tank = new TileTank();
		
		coalGenerator = new TileCoalGenerator();
		nuclearReactor = new TileNuclearReactor();
		battery = new TileBattery();
		
		electricBlastFurnace = new TileElectricBlastFurnace();
		electricAlloySmelter = new TileElectricAlloySmelter();
		electricSeparator = new TileElectricSeparator();
		electricSmelter = new TileElectricSmelter();
		compressor = new TileCompressor();
		
		oreTin = new TileOre(RockSolidLib.makeRes("oreTin"), new ItemInstance(ModItems.clusterTin, 1), 10, 2);
		oreIron = new TileOre(RockSolidLib.makeRes("oreIron"), new ItemInstance(ModItems.clusterIron, 1), 15, 3);
		oreMagnesium = new TileOre(RockSolidLib.makeRes("oreMagnesium"), new ItemInstance(ModItems.clusterMagnesium, 1), 20, 4);
		oreRutile = new TileBase(RockSolidLib.makeRes("oreRutile"), 30, 4);
		oreUranium = new TileOre(RockSolidLib.makeRes("oreUranium"), new ItemInstance(ModItems.clusterUranium, 1), 25, 5);
		
		constructionBlockSteel = new TileBase(RockSolidLib.makeRes("constructionBlockSteel"), 10, 3);
		constructionBlockTitanium = new TileBase(RockSolidLib.makeRes("constructionBlockTitanium"), 25, 4);
    }
}