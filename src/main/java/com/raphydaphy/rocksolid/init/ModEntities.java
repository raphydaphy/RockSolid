package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.entity.EntityRocket;

import de.ellpeck.rockbottom.api.RockBottomAPI;

public class ModEntities
{

	public static void init()
	{
		RockBottomAPI.ENTITY_REGISTRY.register(RockSolidAPILib.makeInternalRes("rocket"), EntityRocket.class);
	}
}
