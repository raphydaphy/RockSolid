package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.block.BlockAllocator;
import com.raphydaphy.rocksolid.block.BlockAlloySmelter;
import com.raphydaphy.rocksolid.block.BlockBase;
import com.raphydaphy.rocksolid.block.BlockBattery;
import com.raphydaphy.rocksolid.block.BlockBlastFurnace;
import com.raphydaphy.rocksolid.block.BlockCharger;
import com.raphydaphy.rocksolid.block.BlockChest;
import com.raphydaphy.rocksolid.block.BlockCoalGenerator;
import com.raphydaphy.rocksolid.block.BlockCompressor;
import com.raphydaphy.rocksolid.block.BlockCreativePowerSource;
import com.raphydaphy.rocksolid.block.BlockCustomDrop;
import com.raphydaphy.rocksolid.block.BlockElectricAlloySmelter;
import com.raphydaphy.rocksolid.block.BlockElectricBlastFurnace;
import com.raphydaphy.rocksolid.block.BlockElectricSeparator;
import com.raphydaphy.rocksolid.block.BlockElectricSmelter;
import com.raphydaphy.rocksolid.block.BlockEnergyConduit;
import com.raphydaphy.rocksolid.block.BlockLamp;
import com.raphydaphy.rocksolid.block.BlockNuclearReactor;
import com.raphydaphy.rocksolid.block.BlockOre;
import com.raphydaphy.rocksolid.block.BlockQuarry;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.tile.Tile;

public class ModBlocks 
{
	public static Tile alloySmelter;
	public static Tile blastFurnace;
	
	public static Tile allocator;
	public static Tile energyConduit;
	public static Tile chest;
	
	public static Tile quarry;
	public static Tile creativePowerSource;
	public static Tile charger;
	
	public static Tile lamp;
	public static Tile rockLight;
	public static Tile limestone;
	public static Tile clay;
	
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
		
		alloySmelter = new BlockAlloySmelter();
		blastFurnace = new BlockBlastFurnace();
		
		allocator = new BlockAllocator();
		energyConduit = new BlockEnergyConduit();
		chest = new BlockChest().register();
		
		quarry = new BlockQuarry();
		creativePowerSource = new BlockCreativePowerSource();
		charger = new BlockCharger();
		
		lamp = new BlockLamp(RockSolidLib.makeRes("lamp"));
		rockLight = new BlockCustomDrop("rockLight", new ItemInstance(GameContent.TILE_ROCK, 1), 5, 1);
		limestone = new BlockBase(RockSolidLib.makeRes("limestone"), 10, 3);
		clay = new BlockBase(RockSolidLib.makeRes("clay"), 8, 3, ToolType.SHOVEL);
		
		coalGenerator = new BlockCoalGenerator();
		nuclearReactor = new BlockNuclearReactor();
		battery = new BlockBattery();
		
		electricBlastFurnace = new BlockElectricBlastFurnace();
		electricAlloySmelter = new BlockElectricAlloySmelter();
		electricSeparator = new BlockElectricSeparator();
		electricSmelter = new BlockElectricSmelter();
		compressor = new BlockCompressor();
		
		oreTin = new BlockOre(RockSolidLib.makeRes("oreTin"), new ItemInstance(ModItems.clusterTin, 1), 10, 2);
		oreIron = new BlockOre(RockSolidLib.makeRes("oreIron"), new ItemInstance(ModItems.clusterIron, 1), 15, 3);
		oreMagnesium = new BlockOre(RockSolidLib.makeRes("oreMagnesium"), new ItemInstance(ModItems.clusterMagnesium, 1), 20, 4);
		oreRutile = new BlockBase(RockSolidLib.makeRes("oreRutile"), 30, 4);
		oreUranium = new BlockOre(RockSolidLib.makeRes("oreUranium"), new ItemInstance(ModItems.clusterUranium, 1), 25, 5);
		
		constructionBlockSteel = new BlockBase(RockSolidLib.makeRes("constructionBlockSteel"), 10, 3);
		constructionBlockTitanium = new BlockBase(RockSolidLib.makeRes("constructionBlockTitanium"), 25, 4);
    }
}