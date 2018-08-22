package com.raphydaphy.rocksolid.recipe;

import com.raphydaphy.rocksolid.RockSolid;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.NameRegistry;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class AlloySmelterRecipe
{
	public static final NameRegistry<AlloySmelterRecipe> REGISTRY = new NameRegistry<>(RockSolid.createRes("alloy_smelting_registry"), true).register();

	private final ResourceName name;
	public final IUseInfo in1;
	public final IUseInfo in2;
	public final ItemInstance out;

	public AlloySmelterRecipe(String in1, int quantity1, String in2, int quantity2, Item out, int quantityOut)
	{
		this(new ResUseInfo(in1, quantity1), new ResUseInfo(in2, quantity2), new ItemInstance(out, quantityOut));
	}

	public AlloySmelterRecipe(IUseInfo in1, IUseInfo in2, ItemInstance out)
	{
		this(out.getItem().getName(), in1, in2, out);
	}

	public AlloySmelterRecipe(ResourceName name, IUseInfo in1, IUseInfo in2, ItemInstance out)
	{
		this.name = name;
		this.in1 = in1;
		this.in2 = in2;
		this.out = out;
	}

	public static AlloySmelterRecipe getFromInputs(ItemInstance input1, ItemInstance input2, boolean ignoreNull)
	{
		for (AlloySmelterRecipe recipe : REGISTRY.values())
		{
			if (input1 != null && input2 != null)
			{
				if ((recipe.in1.containsItem(input1) && recipe.in2.containsItem(input2)))
				{
					return recipe;
				} else if ((recipe.in2.containsItem(input1) && recipe.in1.containsItem(input2)))
				{
					return new AlloySmelterRecipe(recipe.in2, recipe.in1, recipe.out);
				}
			}
			if (input1 != null && ignoreNull && input2 == null && (recipe.in1.containsItem(input1) || recipe.in2.containsItem(input1)))
			{
				return recipe;
			}
		}
		return null;
	}

	public AlloySmelterRecipe register()
	{
		REGISTRY.register(this.name, this);
		return this;
	}
}
