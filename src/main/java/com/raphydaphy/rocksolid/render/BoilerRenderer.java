package com.raphydaphy.rocksolid.render;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.tile.TileBoiler;
import com.raphydaphy.rocksolid.tileentity.TileEntityBoiler;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.tile.MultiTileRenderer;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class BoilerRenderer extends MultiTileRenderer<TileBoiler>
{
	protected final Map<Pos2, IResourceName> texturesActive;

	public BoilerRenderer(final IResourceName texture, final MultiTile tile)
	{
		super(texture, tile);
		this.texturesActive = new HashMap<Pos2, IResourceName>();

		for (int x = 0; x < tile.getWidth(); x++)
		{
			for (int y = 0; y < tile.getHeight(); ++y)
			{
				if (tile.isStructurePart(x, y))
				{
					if (x == 0)
					{
						this.texturesActive.put(new Pos2(x, y), this.texture.addSuffix(".active." + x + "." + y));
					} else
					{
						this.texturesActive.put(new Pos2(x, y), this.texture.addSuffix("." + x + "." + y));
					}
				}
			}
		}
	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, Graphics g, IWorld world, TileBoiler tile,
			TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, Color[] light)
	{
		final Pos2 innerCoord = tile.getInnerCoord(state);
		final Pos2 mainPos = tile.getMainPos(x, y, state);
		final TileEntityBoiler tileEntity = world.getTileEntity(mainPos.getX(), mainPos.getY(), TileEntityBoiler.class);
		IResourceName tex;
		if (innerCoord.getX() == 0)
		{
			if (tileEntity != null && tileEntity.isActive())
			{
				tex = this.texturesActive.get(innerCoord);
			} else
			{
				tex = this.textures.get(innerCoord);
			}
		} else if (innerCoord.getX() == 2)
		{
			if (tileEntity.getGeneratorFullness() > 0.98)
			{
				tex = stageToTex(6, innerCoord);
			} else if (tileEntity.getGeneratorFullness() > 0.81)
			{
				tex = stageToTex(5, innerCoord);
			} else if (tileEntity.getGeneratorFullness() > 0.64)
			{
				tex = stageToTex(4, innerCoord);
			} else if (tileEntity.getGeneratorFullness() > 0.48)
			{
				tex = stageToTex(3, innerCoord);
			} else if (tileEntity.getGeneratorFullness() > 0.31)
			{
				tex = stageToTex(2, innerCoord);
			} else if (tileEntity.getGeneratorFullness() > 0.14)
			{
				tex = stageToTex(1, innerCoord);
			} else
			{
				tex = this.textures.get(innerCoord);
			}
		} else
		{
			tex = this.textures.get(innerCoord);
		}

		manager.getTexture(tex).drawWithLight(renderX, renderY, scale, scale, light);
	}

	private IResourceName stageToTex(int stage, Pos2 innerCoord)
	{
		if (stage < 4)
		{
			if (innerCoord.getY() == 0)
			{
				return this.texture.addSuffix("." + stage + "." + innerCoord.getX() + "." + innerCoord.getY());
			}
		} else if (stage > 3)
		{
			if (innerCoord.getY() == 1)
			{
				return this.texture.addSuffix("." + stage + "." + innerCoord.getX() + "." + innerCoord.getY());
			}
			return this.texture.addSuffix(".4.2.0");
		}
		return this.textures.get(innerCoord);
	}

}