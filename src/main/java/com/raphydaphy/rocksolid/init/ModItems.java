package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.item.ItemAxe;
import com.raphydaphy.rocksolid.item.ItemBase;
import com.raphydaphy.rocksolid.item.ItemBucket;
import com.raphydaphy.rocksolid.item.ItemElectricLantern;
import com.raphydaphy.rocksolid.item.ItemJetpack;
import com.raphydaphy.rocksolid.item.ItemLantern;
import com.raphydaphy.rocksolid.item.ItemPickaxe;
import com.raphydaphy.rocksolid.item.ItemShovel;
import com.raphydaphy.rocksolid.item.ItemWrench;

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
	public static Item ingotNickel;

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
	public static Item gritNickel;

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
		ingotTin = new ItemBase(RockSolidAPILib.makeInternalRes("ingotTin")).register();
		ingotBronze = new ItemBase(RockSolidAPILib.makeInternalRes("ingotBronze")).register();
		ingotIron = new ItemBase(RockSolidAPILib.makeInternalRes("ingotIron")).register();
		ingotSteel = new ItemBase(RockSolidAPILib.makeInternalRes("ingotSteel")).register();
		ingotMagnesium = new ItemBase(RockSolidAPILib.makeInternalRes("ingotMagnesium")).register();
		ingotImpureTitanium = new ItemBase(RockSolidAPILib.makeInternalRes("ingotImpureTitanium")).register();
		ingotTitanium = new ItemBase(RockSolidAPILib.makeInternalRes("ingotTitanium")).register();
		ingotUranium = new ItemBase(RockSolidAPILib.makeInternalRes("ingotUranium")).register();
		ingotNickel = new ItemBase(RockSolidAPILib.makeInternalRes("ingotNickel")).register();

		gemCoke = new ItemBase(RockSolidAPILib.makeInternalRes("gemCoke")).register();

		pelletUranium = new ItemBase(RockSolidAPILib.makeInternalRes("pelletUranium")).register();

		clumpClay = new ItemBase(RockSolidAPILib.makeInternalRes("clumpClay")).register();
		clay = new ItemBase(RockSolidAPILib.makeInternalRes("itemClay")).register();
		porcelianClay = new ItemBase(RockSolidAPILib.makeInternalRes("porcelianClay")).register();

		clusterTin = new ItemBase(RockSolidAPILib.makeInternalRes("clusterTin")).register();
		clusterIron = new ItemBase(RockSolidAPILib.makeInternalRes("clusterIron")).register();
		clusterMagnesium = new ItemBase(RockSolidAPILib.makeInternalRes("clusterMagnesium")).register();
		clusterUranium = new ItemBase(RockSolidAPILib.makeInternalRes("clusterUranium")).register();

		gritTin = new ItemBase(RockSolidAPILib.makeInternalRes("gritTin")).register();
		gritIron = new ItemBase(RockSolidAPILib.makeInternalRes("gritIron")).register();
		gritMagnesium = new ItemBase(RockSolidAPILib.makeInternalRes("gritMagnesium")).register();
		gritUranium = new ItemBase(RockSolidAPILib.makeInternalRes("gritUranium")).register();
		gritNickel = new ItemBase(RockSolidAPILib.makeInternalRes("gritNickel")).register();

		pickaxeBronze = new ItemPickaxe(RockSolidAPILib.makeInternalRes("pickaxeBronze"), 10, 6).register();
		pickaxeIron = new ItemPickaxe(RockSolidAPILib.makeInternalRes("pickaxeIron"), 13, 7).register();
		pickaxeSteel = new ItemPickaxe(RockSolidAPILib.makeInternalRes("pickaxeSteel"), 15, 8).register();
		pickaxeTitanium = new ItemPickaxe(RockSolidAPILib.makeInternalRes("pickaxeTitanium"), 18, 10).register();

		axeBronze = new ItemAxe(RockSolidAPILib.makeInternalRes("axeBronze"), 10f, 6).register();
		axeIron = new ItemAxe(RockSolidAPILib.makeInternalRes("axeIron"), 13f, 7).register();
		axeSteel = new ItemAxe(RockSolidAPILib.makeInternalRes("axeSteel"), 15f, 8).register();
		axeTitanium = new ItemAxe(RockSolidAPILib.makeInternalRes("axeTitanium"), 18f, 10).register();

		shovelIron = new ItemShovel(RockSolidAPILib.makeInternalRes("shovelIron"), 6.5f, 7).register();

		wrench = new ItemWrench().register();
		jetpack = new ItemJetpack();
		electricLantern = new ItemElectricLantern();
		lantern = new ItemLantern();

		bucket = new ItemBucket("bucket");
	}
}
