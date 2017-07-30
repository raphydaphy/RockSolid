package com.raphydaphy.rocksolid.render;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.item.ItemBucket;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.item.IItemRenderer;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class BucketRenderer<T extends ItemBucket> implements IItemRenderer<T>
{
	protected final IResourceName texture;

	public BucketRenderer(IResourceName texture)
	{
		this.texture = texture.addPrefix("items.");
	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, Graphics g, ItemBucket item, ItemInstance instance,
			float x, float y, float scale, Color filter)
	{
		IResourceName curTex = this.texture;
		switch (instance.getMeta())
		{
		case 1:
			curTex = curTex.addSuffix(".water");
			break;
		case 2:
			curTex = curTex.addSuffix(".lava");
			break;
		case 3:
			curTex = curTex.addSuffix(".oil");
			break;
		}

		manager.getTexture(curTex).draw(x, y, 1F * scale, 1F * scale, filter);
	}

	@Override
	public void renderOnMouseCursor(IGameInstance game, IAssetManager manager, Graphics g, ItemBucket item,
			ItemInstance instance, float x, float y, float scale, Color filter)
	{
		this.render(game, manager, g, item, instance, x, y, scale, filter);
	}

}
