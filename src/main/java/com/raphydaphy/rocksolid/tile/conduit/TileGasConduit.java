package com.raphydaphy.rocksolid.tile.conduit;

import com.raphydaphy.rocksolid.gas.Gas;
import com.raphydaphy.rocksolid.gas.IGasTile;
import com.raphydaphy.rocksolid.tileentity.conduit.TileEntityGasConduit;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.List;

public class TileGasConduit extends TileConduit {

    public TileGasConduit() {
        super("gas_conduit");
    }


    @Override
    public boolean canConnectAbstract(IWorld world, TileEntity te, Pos2 pos, TileState state) {
        if (te != null && te instanceof IGasTile) {
            List<Gas> gasses = ((IGasTile<?>) te).getGasAt(world, pos);

            return gasses != null && gasses.size() > 0;
        }
        return false;
    }

    @Override
    public TileEntity provideTileEntity(IWorld world, int x, int y, TileLayer layer) {
        return new TileEntityGasConduit(world, x, y, layer);
    }

}
