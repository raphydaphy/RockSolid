package com.raphydaphy.rocksolid.render;

import com.raphydaphy.rocksolid.tile.conduit.TileConduit;
import com.raphydaphy.rocksolid.tileentity.conduit.TileEntityConduit;
import com.raphydaphy.rocksolid.tileentity.conduit.TileEntityConduit.ConduitMode;
import com.raphydaphy.rocksolid.tileentity.conduit.TileEntityConduit.ConduitSide;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.tile.DefaultTileRenderer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class ConduitRenderer<T extends Tile> extends DefaultTileRenderer<T>
{

	public ConduitRenderer(ResourceName texture)
	{
		super(texture);
	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, Tile tile, TileState state,
			int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light)
	{
		if (tile instanceof TileConduit)
		{
			TileEntityConduit te = world.getTileEntity(layer, x, y, TileEntityConduit.class);
			Pos2 pos = new Pos2(x, y);

			if (te != null)
			{

				manager.getTexture(this.texture).getPositionalVariation(x, y).draw(renderX, renderY, scale, scale, light);

				for (ConduitSide side : ConduitSide.values())
				{
					ConduitMode mode = te.getMode(pos, side, false);
					if (mode != null && mode.shouldRender())
					{

						manager.getTexture(this.texture.addSuffix("." + side.toString().toLowerCase()))
								.getPositionalVariation(x, y).draw(renderX, renderY, scale, scale, light);
					}

				}
			}
		}
	}

	@Override
	public void renderItem(IGameInstance game, IAssetManager manager, IRenderer g, Tile tile, ItemInstance instance,
			float x, float y, float scale, int filter)
	{
		manager.getTexture(this.texture).draw(x, y, scale, scale, filter);
		manager.getTexture(this.texture.addSuffix(".up")).draw(x, y, scale, scale, filter);
		manager.getTexture(this.texture.addSuffix(".down")).draw(x, y, scale, scale, filter);
	}
}
