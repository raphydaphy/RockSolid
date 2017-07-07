package com.raphydaphy.rocksolid.util;

import com.raphydaphy.rocksolid.RockSolid;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class RockSolidLib {
	public static IResourceName makeRes(String name){
	   return RockBottomAPI.createRes(RockSolid.INSTANCE, name);
	}
}
