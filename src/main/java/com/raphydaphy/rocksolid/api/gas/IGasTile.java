package com.raphydaphy.rocksolid.api.gas;

public interface IGasTile
{
	// Returns the currently stored amount of gas in the tile
	int getCurrentGas();

	// Returns the max gas that the tile can store
	int getMaxGas();

	// Returns the unlocalied string of the current gas type stored in the tile.
	// Can be vacuum.
	String getGasType();
}
