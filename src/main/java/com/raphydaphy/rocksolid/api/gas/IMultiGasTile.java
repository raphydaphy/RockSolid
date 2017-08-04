package com.raphydaphy.rocksolid.api.gas;

import de.ellpeck.rockbottom.api.util.Pos2;

public interface IMultiGasTile
{
	// Returns the currently stored amount of gas in each tank of the tile
	int[] getGasTanksStorage();

	// Returns the max gas that the tile can store
	int getMaxGas();

	// Returns the unlocalied string of the current gas type stored in each tank
	// of the tile. Can be vacuum.
	String[] getGasTanksType();

	// Returns if the specified tile part is able to input, output, or do
	// nothing
	int getSideMode(int posX, int posY);

	// Returns the tank number of the given coordinate to use for other methods.
	// Use -1 for no tank
	int getTankNumber(Pos2 tankLocation);
}
