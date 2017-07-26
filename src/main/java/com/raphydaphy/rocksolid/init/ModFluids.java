package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.fluid.FluidEmpty;
import com.raphydaphy.rocksolid.fluid.FluidLava;
import com.raphydaphy.rocksolid.fluid.FluidWater;

public class ModFluids 
{
	public static Fluid fluidEmpty;
	public static Fluid fluidWater;
	public static Fluid fluidLava;
	
	public static void init() 
	{
		fluidEmpty = new FluidEmpty();
		fluidWater = new FluidWater();
		fluidLava = new FluidLava();
    }
}