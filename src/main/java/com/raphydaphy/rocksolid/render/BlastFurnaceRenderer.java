package com.raphydaphy.rocksolid.render;

import java.util.HashMap;
import java.util.Map;

import com.raphydaphy.rocksolid.tile.TileBlastFurnace;
import com.raphydaphy.rocksolid.tileentity.TileEntityBlastFurnace;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.tile.MultiTileRenderer;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class BlastFurnaceRenderer extends MultiTileRenderer<TileBlastFurnace>
{
	protected final Map<Pos2, IResourceName> texturesActive;

	public BlastFurnaceRenderer(final IResourceName texture, final MultiTile tile)
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
	public void render(IGameInstance game, IAssetManager manager, IGraphics g, IWorld world, TileBlastFurnace tile,
			TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light)
	{
		final Pos2 innerCoord = tile.getInnerCoord(state);
		final Pos2 mainPos = tile.getMainPos(x, y, state);
		final TileEntityBlastFurnace tileEntity = world.getTileEntity(mainPos.getX(), mainPos.getY(),
				TileEntityBlastFurnace.class);
		IResourceName tex;
		if (tileEntity != null && tileEntity.isActive())
		{
			tex = this.texturesActive.get(innerCoord);
		} else
		{
			tex = this.textures.get(innerCoord);
		}
		manager.getTexture(tex).draw(renderX, renderY, scale, scale, light);
	}

}
