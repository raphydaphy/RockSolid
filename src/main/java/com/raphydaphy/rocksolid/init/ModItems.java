package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.item.ItemBase;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.item.Item;

public class ModItems 
{
	public static Item ingotTin;
	public static Item ingotBronze;
	
	public static Item clusterTin;
	
	public static Item gritTin;
	
	public static void init() 
	{
		ingotTin = new ItemBase(RockSolidLib.makeRes("ingotTin")).register();
		ingotBronze = new ItemBase(RockSolidLib.makeRes("ingotBronze")).register();
		
		clusterTin = new ItemBase(RockSolidLib.makeRes("clusterTin")).register();
		
		gritTin = new ItemBase(RockSolidLib.makeRes("gritTin")).register();
    }
}
