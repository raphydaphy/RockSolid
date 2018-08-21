package com.raphydaphy.rocksolid.init;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.resource.IResourceRegistry;

public class ModResources
{
    public static final String RES_TIN_RAW = res().addResources("tin_raw", ModItems.TIN_CLUSTER);
    public static final String RES_IRON_RAW = res().addResources("iron_raw", ModItems.IRON_CLUSTER);

    public static final String RES_COPPER_CRUSHED = res().addResources("copper_crushed", ModItems.COPPER_GRIT);
    public static final String RES_TIN_CRUSHED = res().addResources("tin_crushed", ModItems.TIN_GRIT);
    public static final String RES_BRONZE_CRUSHED = res().addResources("bronze_crushed", ModItems.BRONZE_GRIT);
    public static final String RES_IRON_CRUSHED = res().addResources("iron_crushed", ModItems.IRON_GRIT);

    public static final String RES_TIN_PROCESSED = res().addResources("tin_processed", ModItems.TIN_INGOT);
    public static final String RES_BRONZE_PROCESSED = res().addResources("bronze_processed", ModItems.BRONZE_INGOT);
    public static final String RES_IRON_PROCESSED = res().addResources("iron_processed", ModItems.IRON_INGOT);
    public static final String RES_STEEL_PROCESSED = res().addResources("steel_processed", ModItems.STEEL_INGOT);

    public static final String RES_COAL_PROCESSED = res().addResources("coal_processed", ModItems.COKE);

    private static IResourceRegistry res()
    {
        return RockBottomAPI.getResourceRegistry();
    }
}
