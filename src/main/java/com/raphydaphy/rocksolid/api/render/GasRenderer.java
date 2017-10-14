package com.raphydaphy.rocksolid.api.render;

import com.raphydaphy.rocksolid.api.gas.Gas;
import com.raphydaphy.rocksolid.api.gas.GasTile;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.ITexture;
import de.ellpeck.rockbottom.api.render.tile.DefaultTileRenderer;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class GasRenderer<T extends GasTile> extends DefaultTileRenderer<GasTile>
{
	public GasRenderer(IResourceName texture)
	{
		super(texture);
	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, IGraphics g, IWorld world, GasTile tile,
			TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light)
	{
		int blockMeta = world.getState(x, y).get(GasTile.gasLevel);
		Gas gasType = state.get(GasTile.gasType);

		if (blockMeta > 0)
		{
			ITexture curTex = manager.getTexture(super.texture.addSuffix("." + gasType));
			curTex.draw(renderX, renderY, scale, (scale / 12) * blockMeta, 0, 0, 12, blockMeta, light);
		}
	}
}
