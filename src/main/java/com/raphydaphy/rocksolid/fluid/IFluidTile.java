package com.raphydaphy.rocksolid.fluid;

import java.util.List;

import javax.annotation.Nullable;

import de.ellpeck.rockbottom.api.tile.TileLiquid;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;

public interface IFluidTile<T extends TileEntity>
{
	boolean add(Pos2 pos, TileLiquid liquid, int ml, boolean simulate);
	
	boolean remove(Pos2 pos, TileLiquid liquid, int ml, boolean simulate);
	
	int getCapacity(IWorld world, Pos2 pos, TileLiquid liquid);
	
	@Nullable
	List<TileLiquid> getLiquidsAt(IWorld world, Pos2 pos);
}
