package com.raphydaphy.rocksolid.util;

import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.item.ItemInstance;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ModUtils
{
	public static final int COAL = new Color(0.5F, 0.1F, 0.1F).getRGB();
	public static final int PROGRESS = new Color(0.1F, 0.5F, 0.1F).getRGB();
	public static final int ENERGY = new Color(148, 0, 211).getRGB();

	public static final Map<IUseInfo, Integer> FUEL_REGISTRY = new HashMap<>();

	public static int getFuelValue(ItemInstance instance)
	{
		for (Map.Entry<IUseInfo, Integer> entry : FUEL_REGISTRY.entrySet())
		{
			if (entry.getKey().containsItem(instance))
			{
				return entry.getValue();
			}
		}
		return 0;
	}
}
