package com.raphydaphy.rocksolid.api.recipe;

import com.raphydaphy.rocksolid.api.gas.Gas;

public class ElectrolyzerRecipe
{
	private final Gas output1;
	private final Gas output2;
	private final String fluid;
	private final int fluidVolume;
	private final int output1Volume;
	private final int output2Volume;
	private final int time;

	public ElectrolyzerRecipe(Gas output1, Gas output2, String fluid, int fluidVolume, int output1Volume,
			int output2Volume, int time)
	{
		this.output1 = output1;
		this.output2 = output2;

		this.output1Volume = output1Volume;
		this.output2Volume = output2Volume;

		this.fluid = fluid;
		this.fluidVolume = fluidVolume;

		this.time = time;
	}

	public Gas getOutput1()
	{
		return this.output1;
	}

	public Gas getOutput2()
	{
		return this.output2;
	}

	public String getFluid()
	{
		return this.fluid;
	}

	public int getFluidVolume()
	{
		return this.fluidVolume;
	}

	public int getOutput1Volume()
	{
		return this.output1Volume;
	}

	public int getOutput2Volume()
	{
		return this.output2Volume;
	}

	public int getTime()
	{
		return this.time;
	}
}
