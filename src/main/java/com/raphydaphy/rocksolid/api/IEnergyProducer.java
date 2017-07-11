package com.raphydaphy.rocksolid.api;

public interface IEnergyProducer extends IEnergyBlock
{
	boolean removeEnergy(int amount);
}
