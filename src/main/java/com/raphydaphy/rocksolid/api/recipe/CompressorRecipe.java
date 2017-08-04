package com.raphydaphy.rocksolid.api.recipe;

import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class CompressorRecipe
{
	private final ResUseInfo input;
	private final ItemInstance output;
	private final int time;

	public CompressorRecipe(ItemInstance output, ResUseInfo input, int time)
	{
		this.input = input;
		this.output = output;
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

	public int getTime()
	{
		return this.time;
	}
}
