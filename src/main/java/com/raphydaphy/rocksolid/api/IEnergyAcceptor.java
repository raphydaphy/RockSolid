package com.raphydaphy.rocksolid.api;

public interface IEnergyAcceptor extends IEnergyBlock
{
	// Returns true if the block can and did add the specified amount of energy to its storage
	boolean addEnergy(int amount);
}
