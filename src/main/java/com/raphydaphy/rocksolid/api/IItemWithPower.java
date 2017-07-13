package com.raphydaphy.rocksolid.api;

// Should be implemented on any items that can hold power
public interface IItemWithPower {
	// The maximum amount of power the item can hold
	int getMaxPower();
	// The most power the item can recieve in a single tick
	// Anything less than 100 will be ignored
	int getMaxTransfer();
}
