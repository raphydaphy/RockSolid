package com.raphydaphy.rocksolid.render;

import com.raphydaphy.rocksolid.api.gas.Gas;
import com.raphydaphy.rocksolid.tile.TileGasTank;
import com.raphydaphy.rocksolid.tileentity.TileEntityGasTank;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.tile.MultiTileRenderer;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class GasTankRenderer extends MultiTileRenderer<TileGasTank>
{

	public GasTankRenderer(final IResourceName texture, final TileGasTank tile)
	{
		super(texture, tile);

	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, IGraphics g, IWorld world, TileGasTank tile,
			TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, int[] light)
	{
		final Pos2 innerCoord = tile.getInnerCoord(state);
		final Pos2 mainPos = tile.getMainPos(x, y, state);
		TileEntityGasTank tileEntity = world.getTileEntity(mainPos.getX(), mainPos.getY(), TileEntityGasTank.class);

		IResourceName tex = this.texture.addSuffix("." + innerCoord.getX() + "." + innerCoord.getY());
		manager.getTexture(tex).draw(renderX, renderY, scale, scale, light);
		float fullness = tileEntity.getGasTankFullnesss();
		int stage = Util.floor(fullness * 20);
		if (stage > 0)
		{
			if (stage > 20)
			{
				stage = 20;
			}

			if (innerCoord.getY() == 1)
			{
				if (stage < 10)
				{
					return;
				} else
				{
					stage -= 10;
				}
			} else if (innerCoord.getY() == 0)
			{
				if (stage > 10)
				{
					stage = 10;
				}
			}

			tex = this.texture.addSuffix(
					"." + Gas.getByName(tileEntity.getGasType()) + "." + innerCoord.getX() + "." + innerCoord.getY());

			float scaleY = (float) (scale / 12);

			float startY = (scale) - (scaleY * stage);

			if (innerCoord.getY() == 0)
			{
				startY -= (2 * scaleY);
			}
			manager.getTexture(tex).draw(renderX, renderY + startY, renderX + scale,
					renderY + startY + (scaleY * stage), 0, 0, 12, stage, light);

		}

	}

}
