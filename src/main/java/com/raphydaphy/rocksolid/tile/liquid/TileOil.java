package com.raphydaphy.rocksolid.tile.liquid;

public class TileOil extends TileLiquidBase
{
	public TileOil() {
		super("oil", 0.44D, 0.95F, false);
	}

	@Override
	public final int getFlowSpeed() {
		return 10;
	}
}