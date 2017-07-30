package com.raphydaphy.rocksolid.api.gas;

public interface IMultiGasTile
{
	// Returns the currently stored amount of gas in each tank of the tile
	int[] getGasTanksStorage();

	// Returns the max gas that the tile can store
	int getMaxGas();

	// Returns the unlocalied string of the current gas type stored in each tank
	// of the tile. Can be vacuum.
	String[] getGasTanksType();
}
