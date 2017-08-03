package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.item.ItemAxe;
import com.raphydaphy.rocksolid.item.ItemBase;
import com.raphydaphy.rocksolid.item.ItemBucket;
import com.raphydaphy.rocksolid.item.ItemElectricLantern;
import com.raphydaphy.rocksolid.item.ItemJetpack;
import com.raphydaphy.rocksolid.item.ItemLantern;
import com.raphydaphy.rocksolid.item.ItemPickaxe;
import com.raphydaphy.rocksolid.item.ItemShovel;
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

	public static Item pelletUranium;

	public static Item clumpClay;
	public static Item clay;
	public static Item porcelianClay;

	public static Item clusterTin;
	public static Item clusterIron;
	public static Item clusterMagnesium;
	public static Item clusterUranium;

	public static Item gritTin;
	public static Item gritIron;
	public static Item gritMagnesium;
	public static Item gritUranium;

	public static Item pickaxeBronze;
	public static Item pickaxeIron;
	public static Item pickaxeSteel;
	public static Item pickaxeTitanium;

	public static Item axeBronze;
	public static Item axeIron;
	public static Item axeSteel;
	public static Item axeTitanium;

	public static Item shovelIron;

	public static Item wrench;
	public static Item jetpack;
	public static Item electricLantern;
	public static Item lantern;

	public static Item bucket;

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

		pelletUranium = new ItemBase(RockSolidLib.makeRes("pelletUranium")).register();

		clumpClay = new ItemBase(RockSolidLib.makeRes("clumpClay")).register();
		clay = new ItemBase(RockSolidLib.makeRes("itemClay")).register();
		porcelianClay = new ItemBase(RockSolidLib.makeRes("porcelianClay")).register();

		clusterTin = new ItemBase(RockSolidLib.makeRes("clusterTin")).register();
		clusterIron = new ItemBase(RockSolidLib.makeRes("clusterIron")).register();
		clusterMagnesium = new ItemBase(RockSolidLib.makeRes("clusterMagnesium")).register();
		clusterUranium = new ItemBase(RockSolidLib.makeRes("clusterUranium")).register();

		gritTin = new ItemBase(RockSolidLib.makeRes("gritTin")).register();
		gritIron = new ItemBase(RockSolidLib.makeRes("gritIron")).register();
		gritMagnesium = new ItemBase(RockSolidLib.makeRes("gritMagnesium")).register();
		gritUranium = new ItemBase(RockSolidLib.makeRes("gritUranium")).register();

		pickaxeBronze = new ItemPickaxe(RockSolidLib.makeRes("pickaxeBronze"), 6, 3).register();
		pickaxeIron = new ItemPickaxe(RockSolidLib.makeRes("pickaxeIron"), 7, 3).register();
		pickaxeSteel = new ItemPickaxe(RockSolidLib.makeRes("pickaxeSteel"), 8, 4).register();
		pickaxeTitanium = new ItemPickaxe(RockSolidLib.makeRes("pickaxeTitanium"), 10, 5).register();

		axeBronze = new ItemAxe(RockSolidLib.makeRes("axeBronze"), 6f, 1).register();
		axeIron = new ItemAxe(RockSolidLib.makeRes("axeIron"), 7f, 1).register();
		axeSteel = new ItemAxe(RockSolidLib.makeRes("axeSteel"), 8f, 1).register();
		axeTitanium = new ItemAxe(RockSolidLib.makeRes("axeTitanium"), 10f, 1).register();

		shovelIron = new ItemShovel(RockSolidLib.makeRes("shovelIron"), 7f, 1).register();

		wrench = new ItemWrench().register();
		jetpack = new ItemJetpack();
		electricLantern = new ItemElectricLantern();
		lantern = new ItemLantern();

		bucket = new ItemBucket("bucket");
	}
}
