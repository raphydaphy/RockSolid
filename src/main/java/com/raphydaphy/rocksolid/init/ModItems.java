package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.api.RockSolidLib;
import com.raphydaphy.rocksolid.item.ItemBase;
import com.raphydaphy.rocksolid.item.ItemPickaxe;
import com.raphydaphy.rocksolid.item.ItemWrench;

import de.ellpeck.rockbottom.api.item.Item;

public class ModItems 
{
	public static Item ingotTin;
	public static Item ingotBronze;
	public static Item ingotIron;
	public static Item ingotSteel;
	
	public static Item gemCoke;
	
	public static Item clusterTin;
	public static Item clusterIron;
	
	public static Item gritTin;
	public static Item gritIron;
	
	public static Item pickaxeTin;
	public static Item pickaxeBronze;
	public static Item pickaxeCopper;
	public static Item pickaxeIron;
	public static Item pickaxeSteel;
	
	public static Item wrench;
	
	public static void init() 
	{
		ingotTin = new ItemBase(RockSolidLib.makeRes("ingotTin")).register();
		ingotBronze = new ItemBase(RockSolidLib.makeRes("ingotBronze")).register();
		ingotIron = new ItemBase(RockSolidLib.makeRes("ingotIron")).register();
		ingotSteel = new ItemBase(RockSolidLib.makeRes("ingotSteel")).register();
		
		gemCoke = new ItemBase(RockSolidLib.makeRes("gemCoke")).register();
		
		clusterTin = new ItemBase(RockSolidLib.makeRes("clusterTin")).register();
		clusterIron = new ItemBase(RockSolidLib.makeRes("clusterIron")).register();
		
		gritTin = new ItemBase(RockSolidLib.makeRes("gritTin")).register();
		gritIron = new ItemBase(RockSolidLib.makeRes("gritIron")).register();
		
		pickaxeTin = new ItemPickaxe(RockSolidLib.makeRes("pickaxeTin"), 4.5f, 2).register();
		pickaxeCopper = new ItemPickaxe(RockSolidLib.makeRes("pickaxeCopper"), 4.5f, 2).register();
		pickaxeBronze = new ItemPickaxe(RockSolidLib.makeRes("pickaxeBronze"), 6, 3).register();
		pickaxeIron = new ItemPickaxe(RockSolidLib.makeRes("pickaxeIron"), 7, 3).register();
		pickaxeSteel = new ItemPickaxe(RockSolidLib.makeRes("pickaxeSteel"), 8, 4).register();
		
		wrench = new ItemWrench().register();
    }
}
