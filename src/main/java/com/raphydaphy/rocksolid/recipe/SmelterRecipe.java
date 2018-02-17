package com.raphydaphy.rocksolid.recipe;

import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.construction.resource.ItemUseInfo;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;

import java.util.ArrayList;
import java.util.List;

public class SmelterRecipe
{
	public static final List<SmelterRecipe> REGISTRY = new ArrayList<>();

	public final IUseInfo in;
	public final ItemInstance out;

	public SmelterRecipe(Item in, Item out)
	{
		this.in = new ItemUseInfo(in);
		this.out = new ItemInstance(out);
	}

	public SmelterRecipe(IUseInfo in, ItemInstance out)
	{
		this.in = in;
		this.out = out;
	}

	public static SmelterRecipe getFromInputs(ItemInstance input)
	{
		for (SmelterRecipe recipe : REGISTRY)
		{
			if (recipe.in.containsItem(input))
			{
				return recipe;
			}
		}
		return null;
	}
}
