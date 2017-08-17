package com.raphydaphy.rocksolid.gui;

import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.api.gas.Gas;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.tileentity.TileEntityGasTank;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class GuiGasTank extends GuiContainer
{

	private final TileEntityGasTank tile;

	public GuiGasTank(final AbstractEntityPlayer player, final TileEntityGasTank tile)
	{
		super(player, 198, 150);
		this.tile = tile;
	}

	@Override
	public void initGui(final IGameInstance game)
	{
		super.initGui(game);
		this.components.add(new ComponentProgressBar(this, this.guiLeft + 60, this.guiTop + 10, 80, 10,
				Gas.getByName(this.tile.getGasType()).getColor(), false, this.tile::getGasTankFullnesss));
	}

	@Override
	public void renderOverlay(IGameInstance game, IAssetManager manager, Graphics g)
	{
		super.renderOverlay(game, manager, g);

		boolean mouseOverGasBarX = (game.getMouseInGuiX() >= this.guiLeft + 60)
				&& (game.getMouseInGuiX() <= (this.guiLeft + 60 + 80));
		boolean mouseOverGasBarY = (game.getMouseInGuiY() >= this.guiTop + 10)
				&& (game.getMouseInGuiY() <= (this.guiTop + 10 + 10));

		if (mouseOverGasBarX && mouseOverGasBarY)
		{
			RockBottomAPI.getApiHandler().drawHoverInfoAtMouse(game, manager, g, false, 500, "Storing "
					+ this.tile.getCurrentGas() + "cc of " + this.tile.getGasType());
		}
	}

	@Override
	public IResourceName getName()
	{
		return RockSolidAPILib.makeInternalRes("guiGasTank");
	}

}
