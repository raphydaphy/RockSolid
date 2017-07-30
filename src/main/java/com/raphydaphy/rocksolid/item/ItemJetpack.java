package com.raphydaphy.rocksolid.item;

import java.util.List;

import com.raphydaphy.rocksolid.api.energy.IItemWithPower;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class ItemJetpack extends ItemBase implements IItemWithPower
{
	private static final String name = "jetpack";

	public ItemJetpack()
	{
		super(RockSolidLib.makeRes(name));
		this.maxAmount = 1;
		this.register();
	}

	public void describeItem(IAssetManager manager, ItemInstance instance, List<String> desc, boolean isAdvanced)
	{
		super.describeItem(manager, instance, desc, isAdvanced);

		DataSet itemData;
		int itemPowerStored = 0;
		int itemMaxPower = this.getMaxPower();

		if (instance.getAdditionalData() != null)
		{
			itemData = instance.getAdditionalData();
			itemPowerStored = itemData.getInt("itemPowerStored");
		} else
		{
			itemData = new DataSet();
			itemData.addInt("itemPowerStored", 0);
			instance.setAdditionalData(itemData);
		}

		desc.addAll(manager.getFont().splitTextToLength(500, 1f, true,
				"Storing " + itemPowerStored + " / " + itemMaxPower + " Energy"));
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
