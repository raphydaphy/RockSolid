package com.raphydaphy.rocksolid.render;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.init.ModGasses;
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

public class GasTankRenderer extends MultiTileRenderer<TileGasTank> {

	public GasTankRenderer(final IResourceName texture, final TileGasTank tile) {
		super(texture, tile);

	}

	@Override
	public void render(IGameInstance game, IAssetManager manager, Graphics g, IWorld world, TileGasTank tile,
			TileState state, int x, int y, TileLayer layer, float renderX, float renderY, float scale, Color[] light) {
		final Pos2 innerCoord = tile.getInnerCoord(state);
		final Pos2 mainPos = tile.getMainPos(x, y, state);
		TileEntityGasTank tileEntity = world.getTileEntity(mainPos.getX(), mainPos.getY(), TileEntityGasTank.class);

		IResourceName tex = this.texture.addSuffix("." + innerCoord.getX() + "." + innerCoord.getY());

		float fullness = tileEntity.getGasTankFullnesss();
		int stage = Util.floor(fullness * 20);
		if (stage > 0) {
			if (stage > 20) {
				stage = 20;
			}
			if (tileEntity.getGasType().equals(ModGasses.gasOxygen.toString())) {
				tex = this.texture.addSuffix(".gasOxygen." + stage + "." + innerCoord.getX() + "." + innerCoord.getY());
			} else if (tileEntity.getGasType().equals(ModGasses.gasHydrogen.toString())) {
				tex = this.texture
						.addSuffix(".gasHydrogen." + stage + "." + innerCoord.getX() + "." + innerCoord.getY());
			} else if (tileEntity.getGasType().equals(ModGasses.gasSteam.toString())) {
				tex = this.texture.addSuffix(".gasSteam." + stage + "." + innerCoord.getX() + "." + innerCoord.getY());
			}

		}

		manager.getTexture(tex).drawWithLight(renderX, renderY, scale, scale, light);
	}

}
