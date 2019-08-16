package com.raphydaphy.rocksolid.tileentity.conduit;

import com.raphydaphy.rocksolid.gas.Gas;
import com.raphydaphy.rocksolid.gas.IGasTile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileEntityGasConduit extends TileEntityConduit {
    public TileEntityGasConduit(IWorld world, int x, int y, TileLayer layer) {
        super(world, x, y, layer, TileEntityGasConduit.class, 10);
    }

    @Override
    public boolean transfer(IWorld world, int x1, int y1, ConduitSide side1, TileEntity tile1, int x2, int y2, ConduitSide side2, TileEntity tile2, boolean simulate) {
        if (tile1 != null && tile1 instanceof IGasTile && tile2 != null && tile2 instanceof IGasTile && world.isPosLoaded(x1, y1) && world.isPosLoaded(x2, y2)) {
            IGasTile<?> f1 = (IGasTile<?>) tile1;
            IGasTile<?> f2 = (IGasTile<?>) tile2;

            Gas toTransfer = null;
            Pos2 pos1 = new Pos2(x1, y1);
            for (Gas gas : f1.getGasAt(world, new Pos2(x1, y1))) {
                if (gas != null && f1.removeGas(pos1, gas, 2, true)) {
                    toTransfer = gas;
                    break;
                }
            }

            if (toTransfer != null) {
                Pos2 pos2 = new Pos2(x2, y2);
                if (f2.addGas(pos2, toTransfer, 2, true)) {
                    if (!simulate) {
                        f2.addGas(pos2, toTransfer, 2, false);
                        f1.removeGas(pos1, toTransfer, 2, false);
                    }
                    return true;
                }

            }
        }
        return false;
    }

}
