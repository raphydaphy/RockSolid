package com.raphydaphy.rocksolid.fluid;

import de.ellpeck.rockbottom.api.tile.TileLiquid;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;

import javax.annotation.Nullable;
import java.util.List;

public interface IFluidTile<T extends TileEntity> {
    boolean addFluid(Pos2 pos, TileLiquid liquid, int ml, boolean simulate);

    boolean removeFluid(Pos2 pos, TileLiquid liquid, int ml, boolean simulate);

    int getFluidCapacity(IWorld world, Pos2 pos, TileLiquid liquid);

    @Nullable
    List<TileLiquid> getLiquidsAt(IWorld world, Pos2 pos);
}
