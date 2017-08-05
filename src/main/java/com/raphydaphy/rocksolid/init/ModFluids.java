package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.api.fluid.FluidTile;

public class ModFluids
{
	public static FluidTile fluid;

	public static void init()
	{
		fluid = new FluidTile("fluid");
	}
}
