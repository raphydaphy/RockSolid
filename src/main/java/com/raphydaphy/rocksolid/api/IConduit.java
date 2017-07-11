package com.raphydaphy.rocksolid.api;

public interface IConduit {
	int getSideMode(int side);
	void setSideMode(int side, int mode);
	boolean canConnectTo(Class<?> adjacentBlock);
}
