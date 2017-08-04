package com.raphydaphy.rocksolid.api.fluid;

public interface IFluidProducer extends IFluidTile
{
	// Returns true if the tile can and did remove the specified amount of fluid
	// from its internal buffer
	boolean removeFluid(int amount);
}
