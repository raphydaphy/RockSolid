package com.raphydaphy.rocksolid.item;

import java.util.List;

import com.raphydaphy.rocksolid.api.energy.IItemWithPower;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;

import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class ItemAsteroidDataChip extends ItemBase implements IItemWithPower
{
	private static final String name = "asteroidDataChip";

	public ItemAsteroidDataChip()
	{
		super(RockSolidAPILib.makeInternalRes(name));
		this.maxAmount = 1;
		this.register();
	}

	public void describeItem(IAssetManager manager, ItemInstance instance, List<String> desc, boolean isAdvanced)
	{
		super.describeItem(manager, instance, desc, isAdvanced);

		DataSet itemData;
		String info = "Not identified yet!";

		if (instance.getAdditionalData() != null)
		{
			itemData = instance.getAdditionalData();
			info = "Bound to Asteroid #" + itemData.getInt("asteroidID");
		}

		desc.addAll(manager.getFont().splitTextToLength(500, 1f, true, info));
	}

	@Override
	public int getMaxPower()
	{
		return 100000;
	}

	@Override
	public int getMaxTransfer()
	{
		return 10000;
	}

}
