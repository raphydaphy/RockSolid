package com.raphydaphy.rocksolid.util;

import com.raphydaphy.rocksolid.RockSolid;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;

public class RockSolidLib {
	public static IResourceName makeRes(String name){
	   return RockBottomAPI.createRes(RockSolid.INSTANCE, name);
	}
	
	public static TileEntity getTileFromPos(int x, int y, IWorld world)
	{
		Tile realTileDown = world.getTile(x, y);
		if (!realTileDown.isAir())
		{
		    if (realTileDown instanceof MultiTile)
		    {
		 	   Pos2 main = ((MultiTile)realTileDown).getMainPos(x, y, world.getMeta(x,  y));
		 	   return world.getTileEntity(main.getX(), main.getY(), TileEntity.class);
		    }
		    else
		    {
		 	   return world.getTileEntity(x, y - 1);
		    }
		}
		return null;
	}
}
