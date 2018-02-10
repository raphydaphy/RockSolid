package com.raphydaphy.rocksolid.render;

import java.util.HashMap;
import java.util.Map;

import com.raphydaphy.rocksolid.tile.TileBoiler;
import com.raphydaphy.rocksolid.tileentity.TileEntityBoiler;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.assets.texture.ITexture;
import de.ellpeck.rockbottom.api.render.tile.MultiTileRenderer;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class BoilerRenderer extends MultiTileRenderer<TileBoiler>
{
	protected final Map<Pos2, IResourceName> baseTextures = new HashMap<>();

	public BoilerRenderer(IResourceName texture, TileBoiler tile)
	{
		super(texture, tile);

		for (int x = 0; x < tile.getWidth(); x++)
		{
			for (int y = 0; y < tile.getHeight(); y++)
			{
				if (tile.isStructurePart(x, y))
				{
					this.baseTextures.put(new Pos2(x, y), this.texture.addSuffix(".base." + x + "." + y));
				}
			}
		}

	}

	@Override
	public ITexture getParticleTexture(IGameInstance game, IAssetManager manager, IRenderer g, TileBoiler tile,
			TileState state)
	{
		Pos2 innerCoord = tile.getInnerCoord(state);
		return manager.getTexture(this.baseTextures.get(innerCoord));
	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, IRenderer g, IWorld world, TileBoiler tile,
			TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light)
	{
		Pos2 innerCoord = tile.getInnerCoord(state);
		TileEntityBoiler te = tile.getTE(world, x, y);
		if (te != null)
		{
			manager.getTexture(getTextureFor(innerCoord, te.getSteam(), te.isActive())).getPositionalVariation(x, y)
					.draw(renderX, renderY, scale, scale, light);

			IResourceName steam = this.texture.addSuffix(".full." + innerCoord.getX() + "." + innerCoord.getY());
			if (te.getSteam() > 0 && innerCoord.getY() != 0 && innerCoord.getY() != 4)
			{
				int STEAM = (int) Math.min(te.getSteam(), 26);

				boolean render = false;

				float pixel = ((float) scale / 12f);

				float x1 = renderX;
				float x2 = renderX + scale;

				float y1 = renderY;
				float y2 = renderY + scale;

				float srcX = 0f;
				float srcX2 = 12f;

				float srcY = 0f;
				float srcY2 = 12f;

				if (innerCoord.getX() == 0)
				{
					x1 = renderX + pixel * 11f;
					srcX = 11f;
				} else if (innerCoord.getX() == 1)
				{
					x2 = renderX + pixel * 5;
					srcX2 = 5f;
				}

				int localSteam = STEAM;
				float yMax = 12;
				if (innerCoord.getY() == 1)
				{
					localSteam = (int) Math.min(7, STEAM);
					yMax = 7;
					render = true;
				} else if (innerCoord.getY() == 2 && STEAM > 7)
				{
					localSteam = (int) Math.min(19, STEAM);
					localSteam -= 7;
					render = true;
				} else if (innerCoord.getY() == 3 && STEAM > 19)
				{
					localSteam = STEAM - 19;
					render = true;
				}

				if (render)
				{
					y1 = renderY + pixel * (yMax - localSteam);
					y2 = y1 + pixel * localSteam;

					srcY = yMax - localSteam;
					srcY2 = srcY + localSteam;

					manager.getTexture(steam).getPositionalVariation(x, y).draw(x1, y1, x2, y2, srcX, srcY, srcX2,
							srcY2, light);
				}
			}
		}
	}

	private IResourceName getTextureFor(Pos2 coord, int stage, boolean active)
	{
		IResourceName tex = baseTextures.get(coord);
		stage = Math.min(stage, 7);

		if (active && coord.getY() == 0)
		{
			tex = this.texture.addSuffix(".active." + coord.getX() + "." + coord.getY());
		}

		return tex;
	}

}
