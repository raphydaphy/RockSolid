package com.raphydaphy.rocksolid.render;

import com.raphydaphy.rocksolid.entity.EntityRocket;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.entity.IEntityRenderer;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;

public class RocketRenderer implements IEntityRenderer<EntityRocket>
{
	private final ResourceName texture;

	public RocketRenderer(ResourceName texture)
	{
		this.texture = texture;
	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, EntityRocket entity, float x, float y, int light)
	{
		if (entity.shouldRender())
		{
			manager.getTexture(texture).draw(x - 0.5f, y - 2, 1, 4, light);
		}
	}
}
