package com.raphydaphy.rocksolid.render;

import com.raphydaphy.rocksolid.init.ModTiles;
import com.raphydaphy.rocksolid.tile.multi.TilePump;
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

	public PumpRenderer(IResourceName texture, TilePump tile)
	{
		super(texture, tile);
	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, TilePump tile,
			TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light)
	{
		Pos2 innerCoord = tile.getInnerCoord(state);

		TileEntityPump te = tile.getTE(world, state, x, y);
		if (te != null)
		{
			manager.getTexture(this.textures.get(innerCoord)).getPositionalVariation(x, y).draw(renderX, renderY, scale,
					scale, light);

			if (innerCoord.getY() == 1 && te.getLiquidType() != null)
			{
				if (te.getLiquidType().equals(ModTiles.WATER))
				{
					float pixel = ((float) scale / 12f);
					//X 8 BEFORE TEXTURE, 13 TEXTURE WIDTH, 2 AFTER TEXTURE
					//Y 8 ABOVE TEXTURE, 2 TEXTURE, 14 AFTER TEXTURE
					float x1 = renderX;
					float x2 = renderX + scale;

					float y1 = renderY + 8 * pixel;
					float y2 = y1 + 2 * pixel;

					float srcX = 0f;
					float srcX2 = 12f;

					float srcY = 0f;
					float srcY2 = 2f;

					int fullness = (int) Math.min((te.getLiquidFullness() * 13), 13);
					boolean doRender = false;
					if (innerCoord.getX() == 1)
					{
						if (fullness > 4)
						{
							doRender = true;
							fullness -= 4;
						}
						x2 = x1 + fullness * pixel;
						srcX = 4;
						srcX2 = srcX + fullness;
					} else
					{
						if (fullness > 4)
						{
							fullness = 4;
							doRender = true;
						} else if (fullness > 0)
						{
							doRender = true;
						}
						x1 = renderX + 8 * pixel;
						x2 = x1 + fullness * pixel;
						srcX2 = fullness;
					}
					
					if (doRender)
					{
						manager.getTexture(this.texture.addSuffix(".water")).getPositionalVariation(x, y).draw(x1, y1,
								x2, y2, srcX, srcY, srcX2, srcY2, light);
					}
				}
			}
		}
	}

}
