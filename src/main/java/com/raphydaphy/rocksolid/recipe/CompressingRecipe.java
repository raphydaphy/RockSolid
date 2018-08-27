package com.raphydaphy.rocksolid.recipe;

import com.raphydaphy.rocksolid.RockSolid;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.NameRegistry;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class CompressingRecipe
{
	public static final NameRegistry<CompressingRecipe> REGISTRY = new NameRegistry<>(RockSolid.createRes("compressing_registry"), true).register();

	private final ResourceName name;
	public final IUseInfo in;
	public final ItemInstance out;
	public final int time;

	public CompressingRecipe(String inRes, Item out, int time)
	{
		this(RockSolid.createRes(inRes + "_compressing"), inRes, out, time);
	}

	public CompressingRecipe(ResourceName name, String inRes, Item out, int time)
	{
		this(name, new ResUseInfo(inRes), new ItemInstance(out), time);
	}

	public CompressingRecipe(ResourceName name, IUseInfo in, ItemInstance out, int time)
	{
		this.name = name;
		this.in = in;
		this.out = out;
		this.time = time;
	}

	public static CompressingRecipe forInput(ItemInstance input)
	{
		if (input != null)
		{
			for (CompressingRecipe recipe : REGISTRY.values())
			{
				if (recipe.in.containsItem(input))
				{
					return recipe;
				}
			}
		}
		return null;
	}

	public CompressingRecipe register()
	{
		REGISTRY.register(this.name, this);
		return this;
	}
}
