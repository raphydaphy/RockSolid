package com.raphydaphy.rocksolid.render;

import com.raphydaphy.rocksolid.tile.TileAlloySmelter;
import com.raphydaphy.rocksolid.tileentity.TileEntityAlloySmelter;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.tile.MultiTileRenderer;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class AlloySmelterRenderer<T extends TileAlloySmelter> extends MultiTileRenderer<T>
{
	private final IResourceName texActive;

	public AlloySmelterRenderer(final IResourceName texture, final MultiTile tile)
	{
		super(texture, tile);
		this.texActive = this.texture.addSuffix(".active");
	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, IGraphics g, IWorld world, T tile, TileState state,
			int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light)
	{
		if (tile.isMainPos(x, y, state))
		{
			final TileEntityAlloySmelter tileEntity = world.getTileEntity(x, y, TileEntityAlloySmelter.class);
			if (tileEntity != null && tileEntity.isActive())
			{
				manager.getTexture(this.texActive).draw(renderX, renderY, scale, scale, light);
				return;
			}
		}
		super.render(game, manager, g, world, tile, state, x, y, layer, renderX, renderY, scale, light);
	}
}
