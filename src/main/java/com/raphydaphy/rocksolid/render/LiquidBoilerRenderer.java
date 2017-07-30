package com.raphydaphy.rocksolid.render;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.tile.TileLiquidBoiler;
import com.raphydaphy.rocksolid.tileentity.TileEntityLiquidBoiler;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.tile.MultiTileRenderer;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class LiquidBoilerRenderer extends MultiTileRenderer<TileLiquidBoiler>
{
	protected final Map<Pos2, IResourceName> texturesActive;

	public LiquidBoilerRenderer(final IResourceName texture, final MultiTile tile)
	{
		super(texture, tile);
		this.texturesActive = new HashMap<Pos2, IResourceName>();
		for (int x = 0; x < tile.getWidth(); ++x)
		{
			for (int y = 0; y < tile.getHeight(); ++y)
			{
				if (tile.isStructurePart(x, y))
				{
					this.texturesActive.put(new Pos2(x, y), this.texture.addSuffix(".active." + x + "." + y));
				}
			}
		}
	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, Graphics g, IWorld world, TileLiquidBoiler tile,
			TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, Color[] light)
	{
		final Pos2 innerCoord = tile.getInnerCoord(state);
		final Pos2 mainPos = tile.getMainPos(x, y, state);
		final TileEntityLiquidBoiler tileEntity = world.getTileEntity(mainPos.getX(), mainPos.getY(),
				TileEntityLiquidBoiler.class);
		IResourceName tex;
		if (tileEntity != null && tileEntity.isActive())
		{
			tex = this.texturesActive.get(innerCoord);
		} else
		{
			tex = this.textures.get(innerCoord);
		}
		manager.getTexture(tex).drawWithLight(renderX, renderY, scale, scale, light);
	}
}
