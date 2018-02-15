package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.energy.IEnergyTile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileEntityCreativeEnergySource extends TileEntity implements IEnergyTile
{
	public TileEntityCreativeEnergySource(IWorld world, int x, int y, TileLayer layer) {
		super(world, x, y, layer);
	}

	@Override
	public boolean addEnergy(Pos2 pos, int joules, boolean simulate) {
		return false;
	}

	@Override
	public boolean removeEnergy(Pos2 pos, int joules, boolean simulate) {
		return true;
	}

	@Override
	public int getEnergyCapacity(IWorld world, Pos2 pos)
	{
		return 10000000;
	}
}
