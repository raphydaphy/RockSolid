package com.raphydaphy.rocksolid.util;

import org.newdawn.slick.Color;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.init.ModFluids;

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
	
	public static Fluid bucketMetaToFluid(int meta)
	{
		switch(meta)
		{
		case 1:
			return ModFluids.fluidWater;
		case 2:
			return ModFluids.fluidLava;
		}
		return ModFluids.fluidEmpty;
	}
	public static Color getFluidColor(String fluid)
	{
		if (fluid.equals(ModFluids.fluidLava.toString()))
		{
			return Color.orange;
		}
		else if (fluid.equals(ModFluids.fluidWater.toString()))
		{
			return Color.blue;
		}
		return Color.lightGray;
	}
	
	public static TileEntity getTileFromConduitSide(Pos2 center, int side, IWorld world)
	{
		Pos2 tilePos = conduitSideToPos(center, side);
		
		return getTileFromPos(tilePos.getX(), tilePos.getY(), world);
	}
	public static TileEntity getTileFromPos(int x, int y, IWorld world)
	{
		Tile realTileDown = world.getState(x, y).getTile();
		if (!realTileDown.isAir())
		{
		    if (realTileDown instanceof MultiTile)
		    {
		 	   Pos2 main = ((MultiTile)realTileDown).getMainPos(x, y, world.getState(x,  y));
		 	   return world.getTileEntity(main.getX(), main.getY(), TileEntity.class);
		    }
		    else
		    {
		 	   return world.getTileEntity(x, y);
		    }
		}
		return null;
	}
	
	public static Pos2 conduitSideToPos(Pos2 center, int side)
	{
		switch(side)
		{
		case 0:
			//up
			return center.add(0,1);
		case 1:
			//down
			return center.add(0,-1);
		case 2:
			//left
			return center.add(-1, 0);
		case 3:
			//right
			return center.add(1, 0);
		}
		return null;
	}
	
	public static int posAndOffsetToConduitSide(Pos2 center, Pos2 side)
	{
		Pos2 difference = center.add(-side.getX(), -side.getY());
		if (difference.getY() == -1)
		{
			return 0;
		}
		else if (difference.getY() == 1)
		{
			return 1;
		}
		else if (difference.getX() == 1)
		{
			return 2;
		}
		else if (difference.getX() == -1)
		{
			return 3;
		}
		
		
		return 0;
	}
	
	
}
