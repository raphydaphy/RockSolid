package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.block.BlockAllocator;
import com.raphydaphy.rocksolid.block.BlockAlloySmelter;
import com.raphydaphy.rocksolid.block.BlockArcFurnace;
import com.raphydaphy.rocksolid.block.BlockBattery;
import com.raphydaphy.rocksolid.block.BlockChest;
import com.raphydaphy.rocksolid.block.BlockCoalGenerator;
import com.raphydaphy.rocksolid.block.BlockElectricAlloySmelter;
import com.raphydaphy.rocksolid.block.BlockElectricArcFurnace;
import com.raphydaphy.rocksolid.block.BlockEnergyConduit;
import com.raphydaphy.rocksolid.block.BlockOre;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.Tile;

public class ModBlocks 
{
	public static Tile alloySmelter;
	public static Tile arcFurnace;
	
	public static Tile allocator;
	public static Tile energyConduit;
	public static Tile chest;
	
	public static Tile coalGenerator;
	public static Tile battery;
	public static Tile electricArcFurnace;
	public static Tile electricAlloySmelter;
	
	public static Tile oreTin;
	public static Tile oreIron;
	
	public static void init() 
	{
		
		alloySmelter = new BlockAlloySmelter();
		arcFurnace = new BlockArcFurnace();
		
		allocator = new BlockAllocator();
		energyConduit = new BlockEnergyConduit();
		chest = new BlockChest().register();
		
		coalGenerator = new BlockCoalGenerator();
		battery = new BlockBattery();
		electricArcFurnace = new BlockElectricArcFurnace();
		electricAlloySmelter = new BlockElectricAlloySmelter();
		
		oreTin = new BlockOre(RockSolidLib.makeRes("oreTin"), new ItemInstance(ModItems.clusterTin, 1), 10, 2).register();
		oreIron = new BlockOre(RockSolidLib.makeRes("oreIron"), new ItemInstance(ModItems.clusterIron, 1), 15, 3).register();
    }
}