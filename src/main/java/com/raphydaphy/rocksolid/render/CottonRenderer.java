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

	public CottonRenderer(ResourceName name)
	{
		super(name);

		for (int var2 = 0; var2 < 10; ++var2)
		{
			this.textures[0][var2] = this.texture.addSuffix("." + var2 + ".bottom");
			this.textures[1][var2] = this.texture.addSuffix("." + var2 + ".top");
		}

	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, Tile tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light)
	{
		boolean top = state.get(StaticTileProps.TOP_HALF);
		int growth = state.get(TileCotton.COTTON_GROWTH);
		manager.getTexture(this.textures[top ? 1 : 0][growth]).getPositionalVariation(x, y).draw(renderX, renderY, scale, scale, light);
	}
}
