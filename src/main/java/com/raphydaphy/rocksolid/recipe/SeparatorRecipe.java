package com.raphydaphy.rocksolid.recipe;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.init.ModItems;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.construction.resource.ItemUseInfo;
import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.NameRegistry;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.ArrayList;
import java.util.List;

public class SeparatorRecipe
{
	public static final NameRegistry<SeparatorRecipe> REGISTRY = new NameRegistry<>(RockSolid.createRes("separator_registry"), true).register();

	private final ResourceName name;
	public final IUseInfo in;
	public final ItemInstance out;
	public final ItemInstance biproduct;
	public final int biproductChance;

	public SeparatorRecipe(String inRes, Item out)
	{
		this(RockSolid.createRes(inRes + "_separation"), inRes, out);
	}

	public SeparatorRecipe(ResourceName name, String inRes, Item out)
	{
		this(name, new ResUseInfo(inRes), new ItemInstance(out), new ItemInstance(ModItems.SLAG), 3);
	}

	public SeparatorRecipe(ResourceName name, IUseInfo in, ItemInstance out, ItemInstance biproduct, int biproductChance)
	{
		this.name = name;
		this.in = in;
		this.out = out;
		this.biproduct = biproduct;
		this.biproductChance = biproductChance;
	}

	public static SeparatorRecipe getFromInputs(ItemInstance input)
	{
		if (input != null)
		{
			for (SeparatorRecipe recipe : REGISTRY.values())
			{
				if (recipe.in.containsItem(input))
				{
					return recipe;
				}
			}
		}
		return null;
	}

	public SeparatorRecipe register()
	{
		REGISTRY.register(this.name, this);
		return this;
	}
}
