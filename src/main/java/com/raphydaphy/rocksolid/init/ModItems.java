package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.item.ItemAxe;
import com.raphydaphy.rocksolid.item.ItemBase;
import com.raphydaphy.rocksolid.item.ItemJetBooster;
import com.raphydaphy.rocksolid.item.ItemPickaxe;
import com.raphydaphy.rocksolid.item.ItemWrench;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.item.Item;

public class ModItems 
{
	public static Item ingotTin;
	public static Item ingotBronze;
	public static Item ingotIron;
	public static Item ingotSteel;
	public static Item ingotMagnesium;
	public static Item ingotImpureTitanium;
	public static Item ingotTitanium;
	public static Item ingotUranium;
	
	public static Item gemCoke;
	
	public static Item clusterTin;
	public static Item clusterIron;
	public static Item clusterMagnesium;
	public static Item clusterUranium;
	
	public static Item gritTin;
	public static Item gritIron;
	public static Item gritMagnesium;
	public static Item gritUranium;
	
	public static Item pickaxeBronze;
	public static Item pickaxeCopper;
	public static Item pickaxeIron;
	public static Item pickaxeSteel;
	public static Item pickaxeTitanium;
	
	public static Item axeWood;
	public static Item axeRock;
	public static Item axeCopper;
	public static Item axeBronze;
	public static Item axeIron;
	public static Item axeSteel;
	public static Item axeTitanium;
	
	public static Item wrench;
	public static Item jetBooster;
	
	public static void init() 
	{
		ingotTin = new ItemBase(RockSolidLib.makeRes("ingotTin")).register();
		ingotBronze = new ItemBase(RockSolidLib.makeRes("ingotBronze")).register();
		ingotIron = new ItemBase(RockSolidLib.makeRes("ingotIron")).register();
		ingotSteel = new ItemBase(RockSolidLib.makeRes("ingotSteel")).register();
		ingotMagnesium = new ItemBase(RockSolidLib.makeRes("ingotMagnesium")).register();
		ingotImpureTitanium = new ItemBase(RockSolidLib.makeRes("ingotImpureTitanium")).register();
		ingotTitanium = new ItemBase(RockSolidLib.makeRes("ingotTitanium")).register();
		ingotUranium = new ItemBase(RockSolidLib.makeRes("ingotUranium")).register();
		
		gemCoke = new ItemBase(RockSolidLib.makeRes("gemCoke")).register();
		
		clusterTin = new ItemBase(RockSolidLib.makeRes("clusterTin")).register();
		clusterIron = new ItemBase(RockSolidLib.makeRes("clusterIron")).register();
		clusterMagnesium = new ItemBase(RockSolidLib.makeRes("clusterMagnesium")).register();
		clusterUranium = new ItemBase(RockSolidLib.makeRes("clusterUranium")).register();
		
		gritTin = new ItemBase(RockSolidLib.makeRes("gritTin")).register();
		gritIron = new ItemBase(RockSolidLib.makeRes("gritIron")).register();
		gritMagnesium = new ItemBase(RockSolidLib.makeRes("gritMagnesium")).register();
		gritUranium = new ItemBase(RockSolidLib.makeRes("gritUranium")).register();
		
		pickaxeCopper = new ItemPickaxe(RockSolidLib.makeRes("pickaxeCopper"), 4.5f, 2).register();
		pickaxeBronze = new ItemPickaxe(RockSolidLib.makeRes("pickaxeBronze"), 6, 3).register();
		pickaxeIron = new ItemPickaxe(RockSolidLib.makeRes("pickaxeIron"), 7, 3).register();
		pickaxeSteel = new ItemPickaxe(RockSolidLib.makeRes("pickaxeSteel"), 8, 4).register();
		pickaxeTitanium = new ItemPickaxe(RockSolidLib.makeRes("pickaxeTitanium"), 10, 5).register();
		
		axeWood = new ItemAxe(RockSolidLib.makeRes("axeWood"), 2f, 1).register();
		axeRock = new ItemAxe(RockSolidLib.makeRes("axeRock"), 3f, 1).register();
		axeCopper = new ItemAxe(RockSolidLib.makeRes("axeCopper"), 4.5f, 1).register();
		axeBronze = new ItemAxe(RockSolidLib.makeRes("axeBronze"), 6f, 1).register();
		axeIron = new ItemAxe(RockSolidLib.makeRes("axeIron"), 7f, 1).register();
		axeSteel = new ItemAxe(RockSolidLib.makeRes("axeSteel"), 8f, 1).register();
		axeTitanium = new ItemAxe(RockSolidLib.makeRes("axeTitanium"), 10f, 1).register();
		
		wrench = new ItemWrench().register();
		jetBooster = new ItemJetBooster();
    }
}
