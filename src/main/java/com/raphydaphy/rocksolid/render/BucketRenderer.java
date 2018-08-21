package com.raphydaphy.rocksolid.render;

import com.raphydaphy.rocksolid.item.ItemBucket;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.item.DefaultItemRenderer;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class BucketRenderer extends DefaultItemRenderer<ItemBucket>
{

	public BucketRenderer(ResourceName texture)
	{
		super(texture);
	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, IRenderer g, ItemBucket item, ItemInstance instance,
			float x, float y, float scale, int filter)
	{
		manager.getTexture(this.texture.addSuffix("." + ItemBucket.BucketType.getFromMeta(instance.getMeta()))).draw(x,
				y, 1F * scale, 1F * scale, filter);
	}

}
