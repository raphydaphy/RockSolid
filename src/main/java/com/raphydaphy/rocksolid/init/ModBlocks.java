package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.block.BlockAllocator;
import com.raphydaphy.rocksolid.block.BlockAlloySmelter;
import com.raphydaphy.rocksolid.block.BlockArcFurnace;
import com.raphydaphy.rocksolid.block.BlockChest;
import com.raphydaphy.rocksolid.block.BlockOre;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.Tile;

public class ModBlocks 
{
	public static Tile alloySmelter;
	public static Tile arcFurnace;
	public static Tile allocator;
	public static Tile chest;
	
	public static Tile oreTin;
	public static Tile oreIron;
	
	public static void init() 
	{
		
		alloySmelter = new BlockAlloySmelter(RockSolidLib.makeRes("alloySmelter")).register();
		arcFurnace = new BlockArcFurnace(RockSolidLib.makeRes("arcFurnace")).register();
		allocator = new BlockAllocator(RockSolidLib.makeRes("allocator")).register();
		chest = new BlockChest(RockSolidLib.makeRes("chest")).register();
		
		oreTin = new BlockOre(RockSolidLib.makeRes("oreTin"), new ItemInstance(ModItems.clusterTin, 1), 10, 2).register();
		oreIron = new BlockOre(RockSolidLib.makeRes("oreIron"), new ItemInstance(ModItems.clusterIron, 1), 15, 3).register();
    }
}