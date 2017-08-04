package com.raphydaphy.rocksolid.api.content;

import com.raphydaphy.rocksolid.api.RockSolidAPI;
import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.api.gas.Gas;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.util.reg.NameRegistry;

public class RockSolidContent
{
	public static final Tile alloySmelter = getTile("alloySmelter");
	public static final Tile blastFurnace = getTile("blastFurnace");

	public static final Tile itemConduit = getTile("itemConduit");
	public static final Tile energyConduit = getTile("energyConduit");
	public static final Tile fluidConduit = getTile("fluidConduit");
	public static final Tile gasConduit = getTile("gasConduit");
	
	public static final Tile quarry = getTile("quarry");
	public static final Tile creativePowerSource = getTile("creativePowerSource");
	public static final Tile charger = getTile("charger");

	public static final Tile rockLight = getTile("rockLight");
	public static final Tile limestone = getTile("limestone");
	public static final Tile clay = getTile("clay");

	public static final Tile tank = getTile("tank");
	public static final Tile fluidPump = getTile("fluidPump");
	public static final Tile gasTank = getTile("gasTank");
	public static final Tile electrolyzer = getTile("electrolyzer");

	public static final Tile boiler = getTile("boiler");
	public static final Tile liquidBoiler = getTile("liquidBoiler");
	public static final Tile turbine = getTile("turbine");

	public static final Tile combustionEngine = getTile("combustionEngine");
	public static final Tile nuclearReactor = getTile("nuclearReactor");
	public static final Tile battery = getTile("battery");

	public static final Tile electricPurifier = getTile("electricPurifier");
	public static final Tile electricBlastFurnace = getTile("electricBlastFurnace");
	public static final Tile electricAlloySmelter = getTile("electricAlloySmelter");
	public static final Tile electricSeparator = getTile("electricSeparator");
	public static final Tile electricSmelter = getTile("electricSmelter");
	public static final Tile compressor = getTile("compressor");

	public static final Tile oreTin = getTile("oreTin");
	public static final Tile oreIron = getTile("oreIron");
	public static final Tile oreMagnesium = getTile("oreMagnesium");
	public static final Tile oreRutile = getTile("oreRutile");
	public static final Tile oreUranium = getTile("oreUranium");

	public static final Tile constructionBlockSteel = getTile("constructionBlockSteel");
	public static final Tile constructionBlockTitanium = getTile("constructionBlockTitanium");
	
	public static final Item ingotTin = getItem("ingotTin");
	public static final Item ingotBronze = getItem("ingotBronze");
	public static final Item ingotIron = getItem("ingotIron");
	public static final Item ingotSteel = getItem("ingotSteel");
	public static final Item ingotMagnesium = getItem("ingotMagnesium");
	public static final Item ingotImpureTitanium = getItem("ingotImpureTitanium");
	public static final Item ingotTitanium = getItem("ingotTitanium");
	public static final Item ingotUranium = getItem("ingotUranium");

	public static final Item gemCoke = getItem("gemCoke");

	public static final Item pelletUranium = getItem("pelletUranium");

	public static final Item clumpClay = getItem("clumpClay");
	public static final Item itemClay = getItem("itemClay");
	public static final Item porcelianClay = getItem("porcelianClay");

	public static final Item clusterTin = getItem("clusterTin");
	public static final Item clusterIron = getItem("clusterIron");
	public static final Item clusterMagnesium = getItem("clusterMagnesium");
	public static final Item clusterUranium = getItem("clusterUranium");

	public static final Item gritTin = getItem("gritTin");
	public static final Item gritIron = getItem("gritIron");
	public static final Item gritMagnesium = getItem("gritMagnesium");
	public static final Item gritUranium = getItem("gritUranium");

	public static final Item pickaxeBronze = getItem("pickaxeBronze");
	public static final Item pickaxeIron = getItem("pickaxeIron");
	public static final Item pickaxeSteel = getItem("pickaxeSteel");
	public static final Item pickaxeTitanium = getItem("pickaxeTitanium");

	public static final Item axeBronze = getItem("axeBronze");
	public static final Item axeIron = getItem("axeIron");
	public static final Item axeSteel = getItem("axeSteel");
	public static final Item axeTitanium = getItem("axeTitanium");

	public static final Item shovelIron = getItem("shovelIron");

	public static final Item wrench = getItem("wrench");
	public static final Item jetpack = getItem("jetpack");
	public static final Item electricLantern = getItem("electricLantern");
	public static final Item lantern = getItem("lantern");

	public static Item bucket = getItem("bucket");
	
	public static final Gas gasVacuum = getGas("gasVacuum");
	public static final Gas gasOxygen = getGas("gasOxygen");
	public static final Gas gasHydrogen = getGas("gasHydrogen");
	public static final Gas gasSteam = getGas("gasSteam");
	
	public static final Fluid fluidEmpty = getFluid("fluidEmpty");
	public static final Fluid fluidWater = getFluid("fluidWater");
	public static final Fluid fluidLava = getFluid("fluidLava");
	public static final Fluid fluidOil = getFluid("fluidOil");

	private static Tile getTile(String name)
	{
		return get(name, RockBottomAPI.TILE_REGISTRY);
	}

	private static Item getItem(String name)
	{
		return get(name, RockBottomAPI.ITEM_REGISTRY);
	}

	private static Fluid getFluid(String name)
	{
		return get(name, RockSolidAPI.FLUID_REGISTRY);
	}

	private static Gas getGas(String name)
	{
		return get(name, RockSolidAPI.GAS_REGISTRY);
	}

	private static <T> T get(String name, NameRegistry<T> registry)
	{
		IResourceName res = RockBottomAPI.createRes(RockSolidAPI.RockSolid, name);
		T thing = registry.get(res);

		if (thing == null)
		{
			throw new IllegalStateException("Object with name " + res + " was not found in registry " + registry
					+ "! This is probably due to RockSolidContent being accessed before the mod has initialized!");
		} else
		{
			return thing;
		}
	}
}