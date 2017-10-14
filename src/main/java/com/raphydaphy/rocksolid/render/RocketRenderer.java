package com.raphydaphy.rocksolid.render;

import com.raphydaphy.rocksolid.entity.EntityRocket;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.entity.IEntityRenderer;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;

public class RocketRenderer implements IEntityRenderer<EntityRocket>
{
	public final IResourceName texture;

	public RocketRenderer(IResourceName texture)
	{
		this.texture = texture;
	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, IGraphics g, IWorld world, EntityRocket entity,
			float x, float y, int light)
	{
		if (entity.shouldRender())
		{
			manager.getTexture(texture).draw(x, y - 4, 1, 48 / 12f, new int[] { light, light, light, light });
		}
	}
}