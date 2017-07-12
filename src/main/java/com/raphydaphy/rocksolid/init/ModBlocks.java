package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.block.BlockAllocator;
import com.raphydaphy.rocksolid.block.BlockAlloySmelter;
import com.raphydaphy.rocksolid.block.BlockBase;
import com.raphydaphy.rocksolid.block.BlockBattery;
import com.raphydaphy.rocksolid.block.BlockBlastFurnace;
import com.raphydaphy.rocksolid.block.BlockChest;
import com.raphydaphy.rocksolid.block.BlockCoalGenerator;
import com.raphydaphy.rocksolid.block.BlockElectricAlloySmelter;
import com.raphydaphy.rocksolid.block.BlockElectricBlastFurnace;
import com.raphydaphy.rocksolid.block.BlockElectricSeparator;
import com.raphydaphy.rocksolid.block.BlockElectricSmelter;
import com.raphydaphy.rocksolid.block.BlockEnergyConduit;
import com.raphydaphy.rocksolid.block.BlockOre;
import com.raphydaphy.rocksolid.block.BlockQuarry;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.Tile;

public class ModBlocks 
{
	public static Tile alloySmelter;
	public static Tile blastFurnace;
	
	public static Tile allocator;
	public static Tile energyConduit;
	public static Tile chest;
	public static Tile quarry;
	
	public static Tile coalGenerator;
	public static Tile battery;
	public static Tile electricBlastFurnace;
	public static Tile electricAlloySmelter;
	public static Tile electricSeparator;
	public static Tile electricSmelter;
	
	public static Tile oreTin;
	public static Tile oreIron;
	public static Tile oreMagnesium;
	public static Tile oreRutile;
	
	public static Tile constructionBlockSteel;
	
	public static void init() 
	{
		
		alloySmelter = new BlockAlloySmelter();
		blastFurnace = new BlockBlastFurnace();
		
		allocator = new BlockAllocator();
		energyConduit = new BlockEnergyConduit();
		chest = new BlockChest().register();
		quarry = new BlockQuarry();
		
		coalGenerator = new BlockCoalGenerator();
		battery = new BlockBattery();
		electricBlastFurnace = new BlockElectricBlastFurnace();
		electricAlloySmelter = new BlockElectricAlloySmelter();
		electricSeparator = new BlockElectricSeparator();
		electricSmelter = new BlockElectricSmelter();
		
		oreTin = new BlockOre(RockSolidLib.makeRes("oreTin"), new ItemInstance(ModItems.clusterTin, 1), 10, 2);
		oreIron = new BlockOre(RockSolidLib.makeRes("oreIron"), new ItemInstance(ModItems.clusterIron, 1), 15, 3);
		oreMagnesium = new BlockOre(RockSolidLib.makeRes("oreMagnesium"), new ItemInstance(ModItems.clusterMagnesium, 1), 20, 4);
		oreRutile = new BlockBase(RockSolidLib.makeRes("oreRutile"), 30, 5);
		
		constructionBlockSteel = new BlockBase(RockSolidLib.makeRes("constructionBlockSteel"), 10, 3);
    }
}