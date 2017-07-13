package com.raphydaphy.rocksolid.api;

public interface IEnergyBlock {
	// Returns the currently stored amount of energy in the block
	int getCurrentEnergy();
	
	// Returns the max energy that the block can store
	int getMaxEnergy();
}
