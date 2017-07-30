package com.raphydaphy.rocksolid.item;

import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.render.item.DefaultItemRenderer;
import de.ellpeck.rockbottom.api.render.item.IItemRenderer;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

@SuppressWarnings("rawtypes")
public class ItemBase extends Item
{

	private final IItemRenderer renderer;

	public ItemBase(IResourceName name)
	{
		super(name);
		this.renderer = this.createRenderer(name);
	}

	protected IItemRenderer createRenderer(IResourceName name)
	{
		return new DefaultItemRenderer(name);
	}

	@Override
	public IItemRenderer getRenderer()
	{
		return this.renderer;
	}
}
