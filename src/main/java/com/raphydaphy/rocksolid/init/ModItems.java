package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.item.*;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.resource.IResourceRegistry;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemTool;
import de.ellpeck.rockbottom.api.item.ToolProperty;

@SuppressWarnings("WeakerAccess")
public class ModItems
{
	public static Item BUCKET;
	public static Item WRENCH;

	public static Item TIN_CLUSTER;
	public static Item IRON_CLUSTER;
	public static Item MAGNESIUM_CLUSTER;
	public static Item URANIUM_CLUSTER;
	public static Item TUNGSTEN_CLUSTER;
	public static Item LUNAR_NICKEL_CLUSTER;
	public static Item LUNAR_ALUMINUM_CLUSTER;
	public static Item LUNAR_COBALT_CLUSTER;

	public static Item COPPER_GRIT;
	public static Item TIN_GRIT;
	public static Item IRON_GRIT;
	public static Item NICKEL_GRIT;
	public static Item BRONZE_GRIT;
	public static Item MAGNESIUM_GRIT;
	public static Item URANIUM_GRIT;
	public static Item TUNGSTEN_GRIT;

	public static Item TIN_INGOT;
	public static Item IRON_INGOT;
	public static Item NICKEL_INGOT;
	public static Item BRONZE_INGOT;
	public static Item STEEL_INGOT;
	public static Item MAGNESIUM_INGOT;
	public static Item IMPURE_TITANIUM_INGOT;
	public static Item TITANIUM_INGOT;
	public static Item URANIUM_INGOT;
	public static Item TUNGSTEN_INGOT;
	public static Item NICKEL_TUNGSTEN_INGOT;
	public static Item NICKEL_TUNGSTEN_CARBIDE_INGOT;

	public static Item BRONZE_PICKAXE;
	public static Item STEEL_PICKAXE;
	public static Item TITANIUM_PICKAXE;

	public static Item BRONZE_AXE;
	public static Item STEEL_AXE;
	public static Item TITANIUM_AXE;

	public static Item BRONZE_SHOVEL;
	public static Item STEEL_SHOVEL;
	public static Item TITANIUM_SHOVEL;

	public static Item COKE;
	public static Item SLAG;
	public static Item URANIUM_PELLET;
	public static Item URANIUM_ROD;

	public static Item JETPACK;
	public static Item ROCKET;

	public static String RES_TIN_RAW;
	public static String RES_IRON_RAW;
	public static String RES_MAGNESIUM_RAW;
	public static String RES_URANIUM_RAW;
	public static String RES_NICKEL_RAW;
	public static String RES_ALUMINUM_RAW;
	public static String RES_COBALT_RAW;
	public static String RES_TUNGSTEN_RAW;

	public static String RES_COPPER_CRUSHED;
	public static String RES_TIN_CRUSHED;
	public static String RES_BRONZE_CRUSHED;
	public static String RES_IRON_CRUSHED;
	public static String RES_NICKEL_CRUSHED;
	public static String RES_MAGNESIUM_CRUSHED;
	public static String RES_URANIUM_CRUSHED;
	public static String RES_TUNGSTEN_CRUSHED;

	public static String RES_TIN_PROCESSED;
	public static String RES_BRONZE_PROCESSED;
	public static String RES_IRON_PROCESSED;
	public static String RES_NICKEL_PROCESSED;
	public static String RES_STEEL_PROCESSED;
	public static String RES_MAGNESIUM_PROCESSED;
	public static String RES_TITANIUM_PROCESSED;
	public static String RES_URANIUM_PROCESSED;
	public static String RES_TUNGSTEN_PROCESSED;
	public static String RES_NICKEL_TUNGSTEN_PROCESSED;
	public static String RES_NICKEL_TUNGSTEN_CARBIDE_PROCESSED;

	public static String RES_COAL_PROCESSED;
	public static String RES_TITANIUM_IMPURE;
	public static String RES_URANIUM_COMPRESSED;

	private static IResourceRegistry res()
	{
		return RockBottomAPI.getResourceRegistry();
	}

	public static void init()
	{
		BUCKET = new ItemBucket();
		WRENCH = new ItemWrench();

		TIN_CLUSTER = new ItemBase("cluster.tin").setMaxAmount(35);
		IRON_CLUSTER = new ItemBase("cluster.iron").setMaxAmount(35);
		MAGNESIUM_CLUSTER = new ItemBase("cluster.magnesium").setMaxAmount(35);
		URANIUM_CLUSTER = new ItemBase("cluster.uranium").setMaxAmount(35);
		TUNGSTEN_CLUSTER = new ItemBase("cluster.tungsten").setMaxAmount(35);
		LUNAR_NICKEL_CLUSTER = new ItemBase("cluster.lunar.nickel").setMaxAmount(30);
		LUNAR_ALUMINUM_CLUSTER = new ItemBase("cluster.lunar.aluminum").setMaxAmount(30);
		LUNAR_COBALT_CLUSTER = new ItemBase("cluster.lunar.cobalt").setMaxAmount(30);

		COPPER_GRIT = new ItemBase("grit.copper").setMaxAmount(40);
		TIN_GRIT = new ItemBase("grit.tin").setMaxAmount(40);
		IRON_GRIT = new ItemBase("grit.iron").setMaxAmount(40);
		NICKEL_GRIT = new ItemBase("grit.nickel").setMaxAmount(40);
		BRONZE_GRIT = new ItemBase("grit.bronze").setMaxAmount(40);
		MAGNESIUM_GRIT = new ItemBase("grit.magnesium").setMaxAmount(40);
		URANIUM_GRIT = new ItemBase("grit.uranium").setMaxAmount(40);
		TUNGSTEN_GRIT = new ItemBase("grit.tungsten").setMaxAmount(40);

		TIN_INGOT = new ItemBase("ingot.tin").setMaxAmount(50);
		IRON_INGOT = new ItemBase("ingot.iron").setMaxAmount(50);
		NICKEL_INGOT = new ItemBase("ingot.nickel").setMaxAmount(50);
		BRONZE_INGOT = new ItemBase("ingot.bronze").setMaxAmount(50);
		STEEL_INGOT = new ItemBase("ingot.steel").setMaxAmount(50);
		MAGNESIUM_INGOT = new ItemBase("ingot.magnesium").setMaxAmount(50);
		IMPURE_TITANIUM_INGOT = new ItemBase("ingot.impure_titanium").setMaxAmount(50);
		TITANIUM_INGOT = new ItemBase("ingot.titanium").setMaxAmount(50);
		URANIUM_INGOT = new ItemBase("ingot.uranium").setMaxAmount(50);
		TUNGSTEN_INGOT = new ItemBase("ingot.tungsten").setMaxAmount(50);
		NICKEL_TUNGSTEN_INGOT = new ItemBase("ingot.nickel_tungsten").setMaxAmount(50);
		NICKEL_TUNGSTEN_CARBIDE_INGOT = new ItemBase("ingot.nickel_tungsten_carbide").setMaxAmount(50);

		BRONZE_PICKAXE = new ItemTool(RockSolid.createRes("pickaxe.bronze"), 6, 500, ToolProperty.PICKAXE, 15).register();
		STEEL_PICKAXE = new ItemTool(RockSolid.createRes("pickaxe.steel"), 8.5f, 750, ToolProperty.PICKAXE, 20).register();
		TITANIUM_PICKAXE = new ItemTool(RockSolid.createRes("pickaxe.titanium"), 10f, 1000, ToolProperty.PICKAXE, 30).register();

		BRONZE_AXE = new ItemTool(RockSolid.createRes("axe.bronze"), 3, 500, ToolProperty.AXE, 15).register();
		STEEL_AXE = new ItemTool(RockSolid.createRes("axe.steel"), 4.5f, 750, ToolProperty.AXE, 20).register();
		TITANIUM_AXE = new ItemTool(RockSolid.createRes("axe.titanium"), 6f, 1000, ToolProperty.AXE, 30).register();

		BRONZE_SHOVEL = new ItemTool(RockSolid.createRes("shovel.bronze"), 3, 500, ToolProperty.SHOVEL, 15).register();
		STEEL_SHOVEL = new ItemTool(RockSolid.createRes("shovel.steel"), 4.5f, 750, ToolProperty.SHOVEL, 20).register();
		TITANIUM_SHOVEL = new ItemTool(RockSolid.createRes("shovel.titanium"), 6f, 1000, ToolProperty.SHOVEL, 30).register();

		COKE = new ItemBase("coal_coke").setMaxAmount(45);
		SLAG = new ItemBase("slag").setMaxAmount(45);
		URANIUM_PELLET = new ItemBase("uranium_pellet").setMaxAmount(25);
		URANIUM_ROD = new ItemDurability("uranium_rod", 20000);

		JETPACK = new ItemJetpack();
		ROCKET = new ItemBase("rocket").setMaxAmount(1);

		RES_TIN_RAW = res().addResources("tin_raw", TIN_CLUSTER);
		RES_IRON_RAW = res().addResources("iron_raw", IRON_CLUSTER);
		RES_MAGNESIUM_RAW = res().addResources("magnesium_raw", MAGNESIUM_CLUSTER);
		RES_URANIUM_RAW = res().addResources("uranium_raw", URANIUM_CLUSTER);
		RES_TUNGSTEN_RAW = res().addResources("tungsten_raw", TUNGSTEN_CLUSTER);
		RES_NICKEL_RAW = res().addResources("nickel_raw", LUNAR_NICKEL_CLUSTER);
		RES_ALUMINUM_RAW = res().addResources("aluminum_raw", LUNAR_ALUMINUM_CLUSTER);
		RES_COBALT_RAW = res().addResources("cobalt_raw", LUNAR_COBALT_CLUSTER);

		RES_COPPER_CRUSHED = res().addResources("copper_crushed", COPPER_GRIT);
		RES_TIN_CRUSHED = res().addResources("tin_crushed", TIN_GRIT);
		RES_BRONZE_CRUSHED = res().addResources("bronze_crushed", BRONZE_GRIT);
		RES_IRON_CRUSHED = res().addResources("iron_crushed", IRON_GRIT);
		RES_NICKEL_CRUSHED = res().addResources("nickel_crushed", NICKEL_GRIT);
		RES_MAGNESIUM_CRUSHED = res().addResources("magnesium_crushed", MAGNESIUM_GRIT);
		RES_URANIUM_CRUSHED = res().addResources("uranium_crushed", URANIUM_GRIT);
		RES_TUNGSTEN_CRUSHED = res().addResources("tungsten_crushed", TUNGSTEN_GRIT);

		RES_TIN_PROCESSED = res().addResources("tin_processed", TIN_INGOT);
		RES_BRONZE_PROCESSED = res().addResources("bronze_processed", BRONZE_INGOT);
		RES_IRON_PROCESSED = res().addResources("iron_processed", IRON_INGOT);
		RES_NICKEL_PROCESSED = res().addResources("nickel_processed", NICKEL_INGOT);
		RES_STEEL_PROCESSED = res().addResources("steel_processed", STEEL_INGOT);
		RES_MAGNESIUM_PROCESSED = res().addResources("magnesium_processed", MAGNESIUM_INGOT);
		RES_TITANIUM_PROCESSED = res().addResources("titanium_processed", TITANIUM_INGOT);
		RES_URANIUM_PROCESSED = res().addResources("uranium_processed", URANIUM_INGOT);
		RES_TUNGSTEN_PROCESSED = res().addResources("tungsten_processed", TUNGSTEN_INGOT);
		RES_NICKEL_TUNGSTEN_PROCESSED = res().addResources("nickel_tungsten_processed", NICKEL_TUNGSTEN_INGOT);
		RES_NICKEL_TUNGSTEN_CARBIDE_PROCESSED = res().addResources("nickel_tungsten_carbide_processed", NICKEL_TUNGSTEN_CARBIDE_INGOT);

		RES_COAL_PROCESSED = res().addResources("coal_processed", COKE);
		RES_TITANIUM_IMPURE = res().addResources("titanium_impure", IMPURE_TITANIUM_INGOT);
		RES_URANIUM_COMPRESSED = res().addResources("uranium_compressed", URANIUM_PELLET);

	}
}
