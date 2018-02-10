package com.raphydaphy.rocksolid.render;

import com.raphydaphy.rocksolid.fluid.FluidWater;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.tile.DefaultTileRenderer;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class FluidRenderer extends DefaultTileRenderer<FluidWater>
{

	public FluidRenderer(IResourceName texture, FluidWater tile)
	{
		super(texture);
	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, FluidWater fluid,
			TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light)
	{
		IResourceName texFull = this.texture.addSuffix(".full");
		IResourceName texTop = this.texture.addSuffix(".top");
		int level = state.get(fluid.level);

		float pixel = ((float) scale / 12f);

		float y1 = renderY + pixel * (12 - level);
		float y2 = y1 + pixel * level;

		float srcY = 12 - level;
		float srcY2 = srcY + level;

		if (level > 0)
		{
			manager.getTexture(texFull).getPositionalVariation(x, y).draw(renderX, y1, renderX + scale, y2, 0, srcY, 12,
					srcY2, light);
		}
		if (!(world.getState(TileLayer.LIQUIDS, x, y + 1).getTile() instanceof FluidWater))
		{
			level += 1;
			
			y1 = renderY + pixel * (12 - level);
			y2 = y1 + pixel;

			srcY = 0;
			srcY2 = 1;

			manager.getTexture(texTop).getPositionalVariation(x, y).draw(renderX, y1, renderX + scale, y2, 0, 0, 12, 1, light);
		}
	}

}
