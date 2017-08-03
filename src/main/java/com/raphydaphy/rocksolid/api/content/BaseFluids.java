package com.raphydaphy.rocksolid.api.content;

import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.fluid.FluidEmpty;
import com.raphydaphy.rocksolid.fluid.FluidLava;
import com.raphydaphy.rocksolid.fluid.FluidOil;
import com.raphydaphy.rocksolid.fluid.FluidWater;

public class BaseFluids
{
	public static Fluid fluidEmpty;
	public static Fluid fluidWater;
	public static Fluid fluidLava;
	public static Fluid fluidOil;

	public static void init()
	{
		fluidEmpty = new FluidEmpty();
		fluidWater = new FluidWater();
		fluidLava = new FluidLava();
		fluidOil = new FluidOil();
	}
}