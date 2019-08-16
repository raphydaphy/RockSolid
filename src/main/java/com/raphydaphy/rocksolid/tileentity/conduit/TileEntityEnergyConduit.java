package com.raphydaphy.rocksolid.tileentity.conduit;

import com.raphydaphy.rocksolid.energy.IEnergyTile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileEntityEnergyConduit extends TileEntityConduit {
    public TileEntityEnergyConduit(IWorld world, int x, int y, TileLayer layer) {
        super(world, x, y, layer, TileEntityEnergyConduit.class, 5);
    }

    @Override
    public boolean transfer(IWorld world, int x1, int y1, ConduitSide side1, TileEntity tile1, int x2, int y2, ConduitSide side2, TileEntity tile2, boolean simulate) {
        if (tile1 instanceof IEnergyTile && tile2 instanceof IEnergyTile && world.isPosLoaded(x1, y1) && world.isPosLoaded(x2, y2)) {
            IEnergyTile f1 = (IEnergyTile) tile1;
            IEnergyTile f2 = (IEnergyTile) tile2;

            Pos2 pos1 = new Pos2(x1, y1);

            Pos2 pos2 = new Pos2(x2, y2);

            int transfer = Math.min(f1.getMaxTransfer() * 5, f2.getMaxTransfer() * 5);

            if (transfer > f1.getEnergyStored()) {
                transfer = f1.getEnergyStored();
            }

            int canStoreF2 = f2.getEnergyCapacity(null, null) - f2.getEnergyStored();

            if (transfer > canStoreF2) {
                //transfer = canStoreF2;
                return false;
            }

            if (transfer > 0 && f1.removeEnergy(pos1, transfer, true) && f2.addEnergy(pos2, transfer, true)) {
                if (!simulate) {
                    f2.addEnergy(pos2, transfer, false);
                    f1.removeEnergy(pos1, transfer, false);
                }
                return true;

            }

        }
        return false;
    }

}
