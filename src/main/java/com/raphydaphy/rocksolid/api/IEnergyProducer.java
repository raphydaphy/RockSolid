package com.raphydaphy.rocksolid.api;

public interface IEnergyProducer extends IEnergyTile
{
	// Returns true if the block can and did remove the specified amount of energy from its internal buffer
	boolean removeEnergy(int amount);
}
