package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.block.BlockAlloySmelter;
import com.raphydaphy.rocksolid.block.BlockOre;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.Tile;

public class ModBlocks 
{
	public static Tile alloySmelter;
	
	public static Tile oreTin;
	
	public static void init() 
	{
		
		alloySmelter = new BlockAlloySmelter(RockSolidLib.makeRes("alloySmelter")).register();
		
		oreTin = new BlockOre(RockSolidLib.makeRes("oreTin"), new ItemInstance(ModItems.clusterTin, 1), 10, 2).register();
    }
}