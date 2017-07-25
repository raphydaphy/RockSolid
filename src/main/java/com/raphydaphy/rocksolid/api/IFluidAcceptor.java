package com.raphydaphy.rocksolid.api;

public interface IFluidAcceptor extends IFluidTile
{
	// Returns true if the tile can and did add the specified amount of fluid to its storage
	boolean addFluid(int amount);
}
