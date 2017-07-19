package com.raphydaphy.rocksolid.api;

// Should be implemented on any block that uses the ConduitRenderer system to connect the textures
public interface IConduit 
{
	// Sides: 0: Up, 1: Down, 2: Left, 3: Right
	// yes it should be an enum ill do that one day
	
	// Modes: 0: Output, 1: Input, 2: Disabled
	
	// Returns the side mode of the specified side
	int getSideMode(int side);
	// Should set the side mode for the specified side
	void setSideMode(int side, int mode);
	// Returns true if the block can connect to the specified adjacent block type
	boolean canConnectTo(Class<?> adjacentBlock);
	
	void setSync();
}
