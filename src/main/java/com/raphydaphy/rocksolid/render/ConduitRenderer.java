package com.raphydaphy.rocksolid.render;

import com.raphydaphy.rocksolid.tile.conduit.TileConduit;
import com.raphydaphy.rocksolid.tileentity.TileEntityConduit;
import com.raphydaphy.rocksolid.tileentity.TileEntityConduit.ConduitMode;
import com.raphydaphy.rocksolid.tileentity.TileEntityConduit.ConduitSide;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.texture.ITexture;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.tile.DefaultTileRenderer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class ConduitRenderer<T extends Tile> extends DefaultTileRenderer<T>
{

	public ConduitRenderer(IResourceName texture)
	{
		super(texture);
	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, Tile tile, TileState state,
			int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light)
	{
		if (tile instanceof TileConduit)
		{
			IResourceName center = this.texture.addSuffix(".center");

			TileEntityConduit te = world.getTileEntity(layer, x, y, TileEntityConduit.class);
			Pos2 pos = new Pos2(x, y);
			
			if (te != null)
			{
				if (te.isMaster())
				{
					center = this.texture.addSuffix(".master");
				}

				manager.getTexture(center).getPositionalVariation(x, y).draw(renderX, renderY, scale, scale, light);

				for (ConduitSide side : ConduitSide.values())
				{
					ConduitMode mode = te.getMode(pos, side);
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
		manager.getTexture(this.texture.addSuffix(".center")).draw(x, y, scale, scale, filter);
		manager.getTexture(this.texture.addSuffix(".up")).draw(x, y, scale, scale, filter);
		manager.getTexture(this.texture.addSuffix(".down")).draw(x, y, scale, scale, filter);
	}

	@Override
	public ITexture getParticleTexture(IGameInstance game, IAssetManager manager, IRenderer g, Tile tile,
			TileState state)
	{
		return manager.getTexture(this.texture.addSuffix(".center"));
	}

}
