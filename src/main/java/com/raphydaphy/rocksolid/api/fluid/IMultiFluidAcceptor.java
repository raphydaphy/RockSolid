package com.raphydaphy.rocksolid.api.fluid;

public interface IMultiFluidAcceptor extends IMultiFluidTile
{
	// Returns true if the tile can and did add the specified amount of fluid to
	// the specified tank
	boolean addFluid(int amount, String type, int tank);

	// Returns true if the tile did and can set the internal fluid type of the
	// specified tank to the specified string
	boolean setFluidType(String type, int tank);
}
