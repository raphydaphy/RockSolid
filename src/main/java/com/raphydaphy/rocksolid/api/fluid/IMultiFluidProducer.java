package com.raphydaphy.rocksolid.api.fluid;

public interface IMultiFluidProducer extends IMultiFluidTile
{
	// Returns true if the tile can and did remove the specified amount of fluid
	// from the specified tank location
	boolean removeFluid(int amount, int tank);
}
