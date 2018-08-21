package com.raphydaphy.rocksolid.render;

import com.raphydaphy.rocksolid.tile.multi.TileArcFurnace;
import com.raphydaphy.rocksolid.tileentity.TileEntityArcFurnace;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.tile.MultiTileRenderer;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class ArcFurnaceRenderer extends MultiTileRenderer<TileArcFurnace>
{

	public ArcFurnaceRenderer(ResourceName texture, TileArcFurnace tile)
	{
		super(texture, tile);
	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, TileArcFurnace tile,
			TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light)
	{
		Pos2 innerCoord = tile.getInnerCoord(state);

		TileEntityArcFurnace te = tile.getTE(world, state, x, y);
		if (te != null)
		{
			ResourceName tex = this.textures.get(innerCoord);
			if (te.isActive())
			{
				tex = this.texture.addSuffix(".active." + innerCoord.getX() + "." + innerCoord.getY());
			}
			manager.getTexture(tex).getPositionalVariation(x, y).draw(renderX, renderY, scale,
					scale, light);
		}
	}

}
