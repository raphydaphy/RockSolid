package com.raphydaphy.rocksolid.render;

import java.util.HashMap;
import java.util.Map;

import com.raphydaphy.rocksolid.init.ModTiles;
import com.raphydaphy.rocksolid.tile.TilePump;
import com.raphydaphy.rocksolid.tileentity.TileEntityPump;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.tile.MultiTileRenderer;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class PumpRenderer extends MultiTileRenderer<TilePump>
{

	protected final Map<Pos2, IResourceName> waterTextures = new HashMap<>();

	public PumpRenderer(IResourceName texture, TilePump tile)
	{
		super(texture, tile);

		for (int x = 0; x < tile.getWidth(); x++)
		{
			for (int y = 0; y < tile.getHeight(); y++)
			{
				if (tile.isStructurePart(x, y))
				{
					this.waterTextures.put(new Pos2(x, y), this.texture.addSuffix(".water." + x + "." + y));
				}
			}
		}
	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, TilePump tile,
			TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light)
	{
		Pos2 innerCoord = tile.getInnerCoord(state);

		TileEntityPump te = tile.getTE(world, x, y);
		if (te != null)
		{
			manager.getTexture(this.textures.get(innerCoord)).getPositionalVariation(x, y).draw(renderX, renderY, scale,
					scale, light);

			if (te.getLiquidType() != null)
			{
				if (te.getLiquidType().equals(ModTiles.WATER))
				{
					manager.getTexture(this.waterTextures.get(innerCoord)).getPositionalVariation(x, y).draw(renderX,
							renderY, scale, scale, light);
				}
			}
		}
	}

}
