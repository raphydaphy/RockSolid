package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.item.ItemBase;
import com.raphydaphy.rocksolid.item.ItemPickaxe;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.item.Item;

public class ModItems 
{
	public static Item ingotTin;
	public static Item ingotBronze;
	
	public static Item clusterTin;
	
	public static Item gritTin;
	
	public static Item pickaxeTin;
	public static Item pickaxeBronze;
	public static Item pickaxeCopper;
	
	public static void init() 
	{
		ingotTin = new ItemBase(RockSolidLib.makeRes("ingotTin")).register();
		ingotBronze = new ItemBase(RockSolidLib.makeRes("ingotBronze")).register();
		
		clusterTin = new ItemBase(RockSolidLib.makeRes("clusterTin")).register();
		
		gritTin = new ItemBase(RockSolidLib.makeRes("gritTin")).register();
		
		pickaxeTin = new ItemPickaxe(RockSolidLib.makeRes("pickaxeTin"), 4.5f, 2).register();
		pickaxeBronze = new ItemPickaxe(RockSolidLib.makeRes("pickaxeBronze"), 6, 3).register();
		pickaxeCopper = new ItemPickaxe(RockSolidLib.makeRes("pickaxeCopper"), 4.5f, 2).register();
    }
}
