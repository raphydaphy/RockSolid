package com.raphydaphy.rocksolid.api.energy;

public interface IEnergyAcceptor extends IEnergyTile
{
	// Returns true if the block can and did add the specified amount of energy
	// to its storage
	boolean addEnergy(int amount);
}
