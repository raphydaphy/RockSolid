package com.raphydaphy.rocksolid.api.recipe;

import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class AlloySmelterRecipe
{
	private final ResUseInfo input1;
	private final ResUseInfo input2;
	private final ItemInstance output;
	private final int time;

	public AlloySmelterRecipe(ItemInstance output, ResUseInfo input1, ResUseInfo input2, int time)
	{
		this.input1 = input1;
		this.input2 = input2;
		this.output = output;
		this.time = time;
	}

	public ResUseInfo getInput1()
	{
		return this.input1;
	}

	public ResUseInfo getInput2()
	{
		return this.input2;
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
