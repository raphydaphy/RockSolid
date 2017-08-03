package com.raphydaphy.rocksolid.api.recipe;

import com.raphydaphy.rocksolid.api.content.BaseFluids;

import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class PurifierRecipe
{
	private final ResUseInfo input;
	private final ItemInstance output;
	private final String fluid;
	private final int fluidVolume;
	private final int time;

	public PurifierRecipe(ItemInstance output, ResUseInfo input, String fluid, int fluidVolume, int time)
	{
		this.input = input;
		this.output = output;
		if (fluid == null)
		{
			this.fluid = BaseFluids.fluidEmpty.toString();
		} else
		{
			this.fluid = fluid;
		}
		this.fluidVolume = fluidVolume;

		this.time = time;
	}

	public ResUseInfo getInput()
	{
		return this.input;
	}

	public ItemInstance getOutput()
	{
		return this.output;
	}

	public String getFluid()
	{
		return this.fluid;
	}

	public int getFluidVolume()
	{
		return this.fluidVolume;
	}

	public int getTime()
	{
		return this.time;
	}
}
