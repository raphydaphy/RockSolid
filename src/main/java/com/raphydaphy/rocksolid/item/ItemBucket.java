package com.raphydaphy.rocksolid.item;

import com.raphydaphy.rocksolid.render.BucketRenderer;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.item.IItemRenderer;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class ItemBucket extends ItemBase {

	public ItemBucket(String name) 
	{
		super(RockSolidLib.makeRes(name));
	}
	
	@Override
	protected IItemRenderer<ItemBucket> createRenderer(IResourceName name){
        return new BucketRenderer<ItemBucket>(name);
    }
	
	@Override
	public int getHighestPossibleMeta(){
        return 2;
    }
	
	@Override
	public IResourceName getUnlocalizedName(ItemInstance instance)
	{
		String bucketType = "";
		switch(instance.getMeta())
		{
		case 1:
			bucketType = bucketType + ".lava";
		}
        return RockSolidLib.makeRes("item.bucket" + bucketType);
    }

}
