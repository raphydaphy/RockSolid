package com.raphydaphy.rocksolid.item;

import com.raphydaphy.rocksolid.render.ToolRenderer;

import de.ellpeck.rockbottom.api.render.item.IItemRenderer;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class ItemWrench extends ItemBase
{
	public ItemWrench(IResourceName name) {
		super(name);
		this.maxAmount = 1;
	}
	
	@Override
    protected IItemRenderer createRenderer(final IResourceName name) {
        return new ToolRenderer(name);
    }

}
