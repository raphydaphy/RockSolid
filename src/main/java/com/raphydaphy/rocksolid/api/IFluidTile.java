package com.raphydaphy.rocksolid.api;

public interface IFluidTile {
	// Returns the currently stored amount of fluid in the tile
	int getCurrentFluid();
	
	// Returns the max fluid that the tile can store
	int getMaxFluid();
	
	// Returns the unlocalied string of the current fluid type stored in the tile. Can be fluidEmpty.
	String getFluidType();
}
