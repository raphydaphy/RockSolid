package com.raphydaphy.rocksolid.api.recipe;

import com.raphydaphy.rocksolid.api.fluid.Fluid;

public class RefineryRecipe
{
	private final Fluid input;
	private final Fluid output;
	private final int inputVolume;
	private final int outputVolume;
	private final int time;

	public RefineryRecipe(Fluid input, Fluid output, int inputVolume, int outputVolume, int time)
	{
		this.input = input;
		this.output = output;
		this.inputVolume = inputVolume;
		this.outputVolume = outputVolume;

		this.time = time;
	}

	public Fluid getInput()
	{
		return this.input;
	}

	public Fluid getOutput()
	{
		return this.output;
	}

	public int getInputVolume()
	{
		return this.inputVolume;
	}

	public int getOutputVolume()
	{
		return this.outputVolume;
	}

	public int getTime()
	{
		return this.time;
	}
}
