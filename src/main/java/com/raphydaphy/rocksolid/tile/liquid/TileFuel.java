package com.raphydaphy.rocksolid.tile.liquid;

public class TileFuel extends TileLiquidBase
{
	public TileFuel() {
		super("fuel", 0.55D, 1F, false);
	}

	@Override
	public final int getFlowSpeed() {
		return 8;
	}
}