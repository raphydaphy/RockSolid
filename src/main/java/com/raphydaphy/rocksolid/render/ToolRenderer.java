package com.raphydaphy.rocksolid.render;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.item.DefaultItemRenderer;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class ToolRenderer<T extends Item> extends DefaultItemRenderer<T>
{
	public ToolRenderer(final IResourceName texture)
	{
		super(texture);
	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, IGraphics g, T item, ItemInstance instance, float x,
			float y, float scale, int filter)
	{
		this.render(game, manager, g, item, instance, x, y, scale, filter);
	}

}
