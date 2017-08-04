package com.raphydaphy.rocksolid.api.gas;

public interface IMultiGasAcceptor extends IMultiGasTile
{
	// Returns true if the tile can and did add the specified amount of gas to
	// the specified tank
	boolean addGas(int amount, String type, int tank);

	// Returns true if the tile did and can set the internal gas type of the
	// specified tank to the specified string
	boolean setGasType(String type, int tank);
}
