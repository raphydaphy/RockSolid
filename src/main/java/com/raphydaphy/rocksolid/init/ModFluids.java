package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.api.Fluid;
import com.raphydaphy.rocksolid.fluid.FluidLava;
import com.raphydaphy.rocksolid.fluid.FluidWater;

public class ModFluids 
{
	public static Fluid fluidWater;
	public static Fluid fluidLava;
	
	public static void init() 
	{
		fluidWater = new FluidWater();
		fluidLava = new FluidLava();
    }
}