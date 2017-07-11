package com.raphydaphy.rocksolid.item;

import java.util.List;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.api.RockSolidLib;
import com.raphydaphy.rocksolid.render.ToolRenderer;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.item.IItemRenderer;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class ItemWrench extends ItemBase
{
	private static final String name = "wrench";
	private final IResourceName desc = RockBottomAPI.createRes(RockSolid.INSTANCE,"details." + name);
	
	public ItemWrench() {
		super(RockSolidLib.makeRes(name));
		this.maxAmount = 1;
	}
	
	@Override
    protected IItemRenderer createRenderer(final IResourceName name) {
        return new ToolRenderer(name);
    }
	
	public void describeItem(IAssetManager manager, ItemInstance instance, List<String> desc, boolean isAdvanced) {
        super.describeItem(manager, instance, desc, isAdvanced);
        desc.addAll(manager.getFont().splitTextToLength(500,1f,true, manager.localize(this.desc)));
    }

}
