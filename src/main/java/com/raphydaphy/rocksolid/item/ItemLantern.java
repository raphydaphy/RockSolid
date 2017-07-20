package com.raphydaphy.rocksolid.item;

import java.util.List;

import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class ItemLantern extends ItemBase
{
	private static final String name = "lantern";
	
	public ItemLantern() {
		super(RockSolidLib.makeRes(name));
		this.maxAmount = 1;
		this.register();	
	}
	
	
	
	public void describeItem(IAssetManager manager, ItemInstance instance, List<String> desc, boolean isAdvanced) {
        super.describeItem(manager, instance, desc, isAdvanced);
        
        DataSet itemData;
		int itemFuel = 0;
		
		if (instance.getAdditionalData() != null)
		{
			itemData = instance.getAdditionalData();
			itemFuel = itemData.getInt("itemFuel");
		}
		else
		{
			itemData = new DataSet();
			itemData.addInt("itemFuel", 0);
			instance.setAdditionalData(itemData);
		}
		
        desc.addAll(manager.getFont().splitTextToLength(500,1f,true, "Fuel " + itemFuel + " / 1000"));
    }

}
