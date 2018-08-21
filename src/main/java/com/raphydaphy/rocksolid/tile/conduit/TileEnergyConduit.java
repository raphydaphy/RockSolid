package com.raphydaphy.rocksolid.tile.conduit;

import com.raphydaphy.rocksolid.energy.IEnergyTile;
import com.raphydaphy.rocksolid.tileentity.conduit.TileEntityEnergyConduit;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileEnergyConduit extends TileConduit
{

	public TileEnergyConduit()
	{
		super("energy_conduit");
	}


	@Override
	public boolean canConnectAbstract(IWorld world, TileEntity te, Pos2 pos, TileState state)
	{
		return (te instanceof IEnergyTile && ((IEnergyTile) te).getEnergyCapacity(world, pos) > 0);
	}

	public TileEntity provideTileEntity(IWorld world, int x, int y, TileLayer layer)
	{
		return new TileEntityEnergyConduit(world, x, y, layer);
	}

}
