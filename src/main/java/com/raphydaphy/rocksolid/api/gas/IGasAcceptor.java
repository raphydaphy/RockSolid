package com.raphydaphy.rocksolid.api.gas;

public interface IGasAcceptor extends IGasTile
{
	// Returns true if the tile can and did add the specified amount of gas to its storage
	boolean addGas(int amount, String type);
	
	// Returns true if the tile did and can set the internal gas type to the specified string
	boolean setGasType(String type);
}
