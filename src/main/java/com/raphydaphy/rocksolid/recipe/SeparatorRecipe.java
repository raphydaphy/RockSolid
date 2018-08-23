package com.raphydaphy.rocksolid.recipe;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.init.ModItems;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.NameRegistry;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class SeparatorRecipe
{
	public static final NameRegistry<SeparatorRecipe> REGISTRY = new NameRegistry<>(RockSolid.createRes("separator_registry"), true).register();

	private final ResourceName name;
	public final IUseInfo in;
	public final ItemInstance out;
	public final ItemInstance biproduct;
	public final int biproductChance;
	public final int time;

	public SeparatorRecipe(String inRes, Item out, int time)
	{
		this(RockSolid.createRes(inRes + "_separation"), inRes, out, time);
	}

	public SeparatorRecipe(ResourceName name, String inRes, Item out, int time)
	{
		this(name, new ResUseInfo(inRes), new ItemInstance(out), new ItemInstance(ModItems.SLAG), 3, time);
	}

	public SeparatorRecipe(ResourceName name, IUseInfo in, ItemInstance out, ItemInstance biproduct, int biproductChance, int time)
	{
		this.name = name;
		this.in = in;
		this.out = out;
		this.biproduct = biproduct;
		this.biproductChance = biproductChance;
		this.time = time;
	}

	public static SeparatorRecipe forInput(ItemInstance input)
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
