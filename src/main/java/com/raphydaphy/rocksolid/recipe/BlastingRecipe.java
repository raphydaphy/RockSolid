package com.raphydaphy.rocksolid.recipe;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.init.ModItems;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.content.IContent;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.NameRegistry;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class BlastingRecipe implements IContent
{
	public static final NameRegistry<BlastingRecipe> REGISTRY = new NameRegistry<>(RockSolid.createRes("blasting_registry"), true).register();

	private final ResourceName name;
	public final IUseInfo in;
	public final ItemInstance out;
	public final int time;

	public BlastingRecipe(String inRes, Item out, int time)
	{
		this(RockSolid.createRes(inRes + "_blasting"), inRes, out, time);
	}

	public BlastingRecipe(ResourceName name, String inRes, Item out, int time)
	{
		this(name, new ResUseInfo(inRes), new ItemInstance(out), time);
	}

	public BlastingRecipe(ResourceName name, IUseInfo in, ItemInstance out, int time)
	{
		this.name = name;
		this.in = in;
		this.out = out;
		this.time = time;
	}

	public static BlastingRecipe forInput(ItemInstance input)
	{
		if (input != null)
		{
			for (BlastingRecipe recipe : REGISTRY.values())
			{
				if (recipe.in.containsItem(input))
				{
					return recipe;
				}
			}
		}
		return null;
	}

	public BlastingRecipe register()
	{
		REGISTRY.register(this.name, this);
		return this;
	}
}
