package com.raphydaphy.rocksolid.render;

import com.raphydaphy.rocksolid.tile.TileCotton;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.StaticTileProps;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.tile.DefaultTileRenderer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class CottonRenderer extends DefaultTileRenderer
{
	private final ResourceName[][] textures = new ResourceName[2][10];
	private final ResourceName[][] dehydratedTextures = new ResourceName[2][5];

	public CottonRenderer(ResourceName name)
	{
		super(name);

		for (int i = 0; i < 10; ++i)
		{
			this.textures[0][i] = this.texture.addSuffix("." + i + ".bottom");
			this.textures[1][i] = this.texture.addSuffix("." + i + ".top");

			if (i < 5)
			{
				this.dehydratedTextures[0][i] = this.texture.addSuffix(".dehydrated." + i + ".bottom");
				this.dehydratedTextures[1][i] = this.texture.addSuffix(".dehydrated." + i + ".top");
			}
		}

	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, Tile tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light)
	{

	}

	@Override
	public void renderInForeground(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, Tile tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light)
	{
		boolean top = state.get(StaticTileProps.TOP_HALF);
		int growth = state.get(TileCotton.COTTON_GROWTH);

		if (!state.get(TileCotton.IRRIGATED))
		{
			manager.getTexture(this.dehydratedTextures[top ? 1 : 0][Math.min(growth, 4)]).getPositionalVariation(x, y).draw(renderX, renderY, scale, scale, light);
		}
		else
		{
			manager.getTexture(this.textures[top ? 1 : 0][growth]).getPositionalVariation(x, y).draw(renderX, renderY, scale, scale, light);
		}

	}
}
