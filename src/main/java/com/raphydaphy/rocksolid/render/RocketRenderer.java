package com.raphydaphy.rocksolid.render;

import com.raphydaphy.rocksolid.entity.EntityRocket;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAnimation;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.render.IPlayerDesign;
import de.ellpeck.rockbottom.api.render.entity.IEntityRenderer;
import de.ellpeck.rockbottom.api.util.Colors;
import de.ellpeck.rockbottom.api.util.Direction;
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

			manager.getTexture(texture.addSuffix(".back")).draw(x - 0.5f, y - 2, 1, 4, light);
			if (entity.passenger != null)
			{
				AbstractEntityPlayer passanger = world.getPlayer(entity.passenger);
				int row =2;
				if (passanger.facing == Direction.LEFT)
				{
					row = 3;
				}
				drawHead(manager, passanger.getDesign(), x - 0.26f, y - 0.746f, 0.54F, row, light);
			}

			manager.getTexture(texture).draw(x - 0.5f, y - 2, 1, 4, light);
		}
	}

	private final ResourceName a = ResourceName.intern("player.base.s");

	private void drawHead(IAssetManager manager, IPlayerDesign design, float x, float y, float scale, int row, int light)
	{
		int baseDesign = design.getBase();
		IAnimation base = manager.getAnimation((baseDesign == -1 ? a : IPlayerDesign.BASE.get(baseDesign)).addSuffix("." + (design.isFemale() ? "female" : "male")));

		base.drawRow(row, x, y, x + scale, y + (scale / 12) * 11, 0, 0, 12, 11, null, light);
		manager.getAnimation(IPlayerDesign.EYES).drawRow(row, x, y, scale, scale * 2.0F, Colors.multiply(light, design.getEyeColor()));

		ResourceName tex;

		if ((tex = IPlayerDesign.EYEBROWS.get(design.getEyebrows())) != null)
		{
			manager.getAnimation(tex).drawRow(row, x, y, scale, scale * 2.0F, Colors.multiply(light, design.getEyebrowsColor()));
		}

		if ((tex = IPlayerDesign.MOUTH.get(design.getMouth())) != null)
		{
			manager.getAnimation(tex).drawRow(row, x, y, scale, scale * 2.0F, light);
		}

		if ((tex = IPlayerDesign.BEARD.get(design.getBeard())) != null)
		{
			manager.getAnimation(tex).drawRow(row, x, y, scale, scale * 2.0F, Colors.multiply(light, design.getBeardColor()));
		}
		ResourceName hair;

		if ((hair = IPlayerDesign.HAIR.get(design.getHair())) != null)
		{
			manager.getAnimation(hair).drawRow(row, x, y, scale, scale * 2.0F, Colors.multiply(light, design.getHairColor()));
		}

		ResourceName accessories;
		if ((accessories = IPlayerDesign.ACCESSORIES.get(design.getAccessory())) != null)
		{
			manager.getAnimation(accessories).drawRow(row, x, y, scale, scale * 2.0F, light);
		}
	}
}
