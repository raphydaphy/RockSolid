package com.raphydaphy.rocksolid.energy;

import de.ellpeck.rockbottom.api.tile.TileLiquid;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;

public interface IEnergyTile
{
	boolean add(Pos2 pos, int joules, boolean simulate);

	boolean remove(Pos2 pos, int joules, boolean simulate);

	int getCapacity(IWorld world, Pos2 pos);
}

