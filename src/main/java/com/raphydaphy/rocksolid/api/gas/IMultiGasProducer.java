package com.raphydaphy.rocksolid.api.gas;

public interface IMultiGasProducer extends IMultiGasTile
{
	// Returns true if the tile can and did remove the specified amount of gas
	// from the specified tank location
	boolean removeGas(int amount, int tank);
}
