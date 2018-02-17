package com.raphydaphy.rocksolid.recipe;

import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.construction.resource.ItemUseInfo;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;

import java.util.ArrayList;
import java.util.List;

public class AlloySmelterRecipe
{
	public static final List<AlloySmelterRecipe> REGISTRY = new ArrayList<>();

	public final IUseInfo in1;
	public final IUseInfo in2;
	public final ItemInstance out;

	public AlloySmelterRecipe(Item in1, int quantity1, Item in2, int quantity2, Item out, int quantityOut)
	{
		this(new ItemUseInfo(in1, quantity1), new ItemUseInfo(in2, quantity2), new ItemInstance(out, quantityOut));
	}

	public AlloySmelterRecipe(IUseInfo in1, IUseInfo in2, ItemInstance out)
	{
		this.in1 = in1;
		this.in2 = in2;
		this.out = out;
	}

	public static AlloySmelterRecipe getFromInputs(ItemInstance input1, ItemInstance input2, boolean ignoreNull)
	{
		for (AlloySmelterRecipe recipe : REGISTRY)
		{
			if ((recipe.in1.containsItem(input1) && recipe.in2.containsItem(input2)))
			{
				return recipe;
			} else if ((recipe.in2.containsItem(input1) && recipe.in1.containsItem(input2)))
			{
				return new AlloySmelterRecipe(recipe.in2, recipe.in1, recipe.out);
			}

			if (ignoreNull && input2 == null && (recipe.in1.containsItem(input1) || recipe.in2.containsItem(input1)))
			{
				return recipe;
			}
		}
		return null;
	}
}
