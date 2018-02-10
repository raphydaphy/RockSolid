package com.raphydaphy.rocksolid.fluid;

import java.util.List;

import javax.annotation.Nullable;

import de.ellpeck.rockbottom.api.tile.TileLiquid;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Pos2;

public interface IFluidTile<T extends TileEntity>
{
	public boolean add(Pos2 pos, TileLiquid liquid, int ml, boolean simulate);
	
	public boolean remove(Pos2 pos, TileLiquid liquid, int ml, boolean simulate);
	
	public int getCapacity(Pos2 pos, TileLiquid liquid);
	
	@Nullable
	public List<TileLiquid> getLiquidAt(Pos2 pos);
}
