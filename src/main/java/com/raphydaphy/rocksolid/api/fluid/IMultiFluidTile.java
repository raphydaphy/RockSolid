package com.raphydaphy.rocksolid.api.fluid;

import de.ellpeck.rockbottom.api.util.Pos2;

public interface IMultiFluidTile
{
	// Returns the currently stored amount of fluid in each tank of the tile
	int[] getFluidTanksStorage();

	// Returns the max gas that the tile can store
	int getMaxFluid();

	// Returns the unlocalied string of the current fluid type stored in each
	// tank of the tile. Can be vacuum.
	String[] getFluidTanksType();

	// Returns if the specified tile part is able to input, output, or do
	// nothing
	int getSideMode(int posX, int posY);

	// Returns the tank number of the given coordinate to use for other methods.
	// Use -1 for no tank
	int getTankNumber(Pos2 tankLocation);
}
