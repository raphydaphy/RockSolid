package com.raphydaphy.rocksolid.render;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.api.content.RockSolidContent;
import com.raphydaphy.rocksolid.tile.TileGasTank;
import com.raphydaphy.rocksolid.tileentity.TileEntityGasTank;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.render.tile.MultiTileRenderer;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class GasTankRenderer extends MultiTileRenderer<TileGasTank>
{

	public GasTankRenderer(final IResourceName texture, final TileGasTank tile)
	{
		super(texture, tile);

	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, Graphics g, IWorld world, TileGasTank tile,
			TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, Color[] light)
	{
		final Pos2 innerCoord = tile.getInnerCoord(state);
		final Pos2 mainPos = tile.getMainPos(x, y, state);
		TileEntityGasTank tileEntity = world.getTileEntity(mainPos.getX(), mainPos.getY(), TileEntityGasTank.class);

		IResourceName tex = this.texture.addSuffix("." + innerCoord.getX() + "." + innerCoord.getY());
		manager.getTexture(tex).drawWithLight(renderX, renderY, scale, scale, light);
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
			if (tileEntity.getGasType().equals(RockSolidContent.gasOxygen.toString()))
			{
				tex = this.texture.addSuffix(".gasOxygen." + innerCoord.getX() + "." + innerCoord.getY());
			} else if (tileEntity.getGasType().equals(RockSolidContent.gasHydrogen.toString()))
			{
				tex = this.texture.addSuffix(".gasHydrogen." + innerCoord.getX() + "." + innerCoord.getY());
			} else if (tileEntity.getGasType().equals(RockSolidContent.gasSteam.toString()))
			{
				tex = this.texture.addSuffix(".gasSteam." + innerCoord.getX() + "." + innerCoord.getY());
			}

			float scaleY = (float) (scale / 12);

			float startY = (scale) - (scaleY * stage);

			if (innerCoord.getY() == 0)
			{
				startY -= (2 * scaleY);
			}
			manager.getTexture(tex).drawWithLight(renderX, renderY + startY, renderX + scale,
					renderY + startY + (scaleY * stage), 0, 0, 12, stage, light, null);

		}

	}

}
