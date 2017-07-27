package com.raphydaphy.rocksolid.api.gas;

public interface IGasProducer extends IGasTile
{
	// Returns true if the tile can and did remove the specified amount of gas from its internal buffer
	boolean removeGas(int amount);
}
