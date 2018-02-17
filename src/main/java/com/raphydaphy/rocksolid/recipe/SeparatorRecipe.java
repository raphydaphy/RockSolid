package com.raphydaphy.rocksolid.recipe;

import com.raphydaphy.rocksolid.init.ModItems;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.construction.resource.ItemUseInfo;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;

import java.util.ArrayList;
import java.util.List;

public class SeparatorRecipe
{
	public static final List<SeparatorRecipe> REGISTRY = new ArrayList<>();

	public final IUseInfo in;
	public final ItemInstance out;
	public final ItemInstance biproduct;
	public final int biproductChance;

	public SeparatorRecipe(Item in, Item mainOut)
	{
		this(in, mainOut, ModItems.SLAG, 3);
	}

	public SeparatorRecipe(Item in, Item out, Item biproduct, int biproductChance)
	{
		this(new ItemUseInfo(in), new ItemInstance(out, 2), new ItemInstance(biproduct), biproductChance);
	}

	public SeparatorRecipe(IUseInfo in, ItemInstance out, ItemInstance biproduct, int biproductChance)
	{
		this.in = in;
		this.out = out;
		this.biproduct = biproduct;
		this.biproductChance = biproductChance;
	}

	public static SeparatorRecipe getFromInputs(ItemInstance input)
	{
		for (SeparatorRecipe recipe : REGISTRY)
		{
			if (recipe.in.containsItem(input))
			{
				return recipe;
			}
		}
		return null;
	}
}
