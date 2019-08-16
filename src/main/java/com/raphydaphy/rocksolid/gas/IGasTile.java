package com.raphydaphy.rocksolid.gas;


import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;

import javax.annotation.Nullable;
import java.util.List;

// TODO: perhaps make a TileGas that floats up like the original rocksolid had
public interface IGasTile<T extends TileEntity> {
    boolean addGas(Pos2 pos, Gas liquid, int cc, boolean simulate);

    boolean removeGas(Pos2 pos, Gas liquid, int cc, boolean simulate);

    int getGasCapacity(IWorld world, Pos2 pos, Gas gas);

    @Nullable
    List<Gas> getGasAt(IWorld world, Pos2 pos);
}

