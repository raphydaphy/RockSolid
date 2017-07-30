package com.raphydaphy.rocksolid.api.recipe;

public class ElectrolyzerRecipe
{
	private final String output1;
	private final String output2;
	private final String fluid;
	private final int fluidVolume;
	private final int output1Volume;
	private final int output2Volume;
	private final int time;

	public ElectrolyzerRecipe(String output1, String output2, String fluid, int fluidVolume, int output1Volume,
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

	public String getOutput1()
	{
		return this.output1;
	}

	public String getOutput2()
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
