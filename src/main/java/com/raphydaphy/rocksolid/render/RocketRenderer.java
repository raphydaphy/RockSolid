package com.raphydaphy.rocksolid.render;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.entity.EntityRocket;

import de.ellpeck.rockbottom.api.IGameInstance;
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
	public void render(IGameInstance game, IAssetManager manager, Graphics g, IWorld world, EntityRocket entity,
			float x, float y, Color light)
	{
		if (entity.shouldRender())
		{
			manager.getTexture(texture).drawWithLight(x, y - 4, 1, 48 / 12f,
					new Color[] { light, light, light, light });
		}
	}
}