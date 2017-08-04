package com.raphydaphy.rocksolid.api.fluid;

public interface IFluidAcceptor extends IFluidTile
{
	// Returns true if the tile can and did add the specified amount of fluid to
	// its storage
	boolean addFluid(int amount, String type);

	// Returns true if the tile did and can set the internal fluid type to the
	// specified string
	boolean setFluidType(String type);
}
