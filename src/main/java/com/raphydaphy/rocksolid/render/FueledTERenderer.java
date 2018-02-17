package com.raphydaphy.rocksolid.render;

import com.raphydaphy.rocksolid.tileentity.TileEntityFueledBase;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.tile.MultiTileRenderer;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class FueledTERenderer extends MultiTileRenderer<MultiTile>
{
	public FueledTERenderer(IResourceName texture, MultiTile tile)
	{
		super(texture, tile);
	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, MultiTile tile, TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light)
	{
		Pos2 innerCoord = tile.getInnerCoord(state);

		Pos2 main = tile.getMainPos(x, y, state);
		TileEntityFueledBase te = world.getTileEntity(main.getX(), main.getY(), TileEntityFueledBase.class);

		if (te != null)
		{
			IResourceName tex = this.textures.get(innerCoord);
			if (te.isActive())
			{
				tex = this.texture.addSuffix(".active." + innerCoord.getX() + "." + innerCoord.getY());
			}
			manager.getTexture(tex).getPositionalVariation(x, y).draw(renderX, renderY, scale, scale, light);
		}
	}

}
