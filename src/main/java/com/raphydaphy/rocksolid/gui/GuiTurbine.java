package com.raphydaphy.rocksolid.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.tileentity.TileEntityTurbine;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class GuiTurbine extends GuiContainer
{

	private final TileEntityTurbine tile;

	public GuiTurbine(final AbstractEntityPlayer player, final TileEntityTurbine tile)
	{
		super(player, 198, 150);
		this.tile = tile;
	}

	@Override
	public void initGui(final IGameInstance game)
	{
		super.initGui(game);
		this.components.add(new ComponentProgressBar(this, this.guiLeft + 60, this.guiTop + 40, 80, 10,
				new Color(148, 0, 211), false, this.tile::getGeneratorFullness));

		this.components.add(new ComponentProgressBar(this, this.guiLeft + 60, this.guiTop + 25, 80, 10,
				RockSolidAPILib.getGasColor(tile.getGasType()), false, this.tile::getGasTankFullness));
	}

	@Override
	public void renderOverlay(IGameInstance game, IAssetManager manager, Graphics g)
	{
		super.renderOverlay(game, manager, g);
		boolean mouseOverBarX = (game.getMouseInGuiX() >= this.guiLeft + 60)
				&& (game.getMouseInGuiX() <= (this.guiLeft + 60 + 80));
		boolean mouseOverBarY = (game.getMouseInGuiY() >= this.guiTop + 40)
				&& (game.getMouseInGuiY() <= (this.guiTop + 40 + 10));

		if (mouseOverBarX && mouseOverBarY)
		{
			RockBottomAPI.getApiHandler().drawHoverInfoAtMouse(game, manager, g, false, 500,
					new String[] { "Storing " + this.tile.getCurrentEnergy() + "kWh of Energy",
							"Produces " + TileEntityTurbine.productionPerTick + "kWh per tick" });
		}

		boolean mouseOverGasBarX = (game.getMouseInGuiX() >= this.guiLeft + 60)
				&& (game.getMouseInGuiX() <= (this.guiLeft + 60 + 80));
		boolean mouseOverGasBarY = (game.getMouseInGuiY() >= this.guiTop + 25)
				&& (game.getMouseInGuiY() <= (this.guiTop + 25 + 10));

		if (mouseOverGasBarX && mouseOverGasBarY)
		{
			RockBottomAPI.getApiHandler().drawHoverInfoAtMouse(game, manager, g, false, 500,
					new String[] {
							"Storing " + this.tile.getCurrentGas() + "cc of "
									+ RockSolidAPILib.getGasLocName(this.tile.getGasType()),
							"Consumes " + TileEntityTurbine.gasConsumptionPerTick + "cc per tick" });
		}
	}

	@Override
	public IResourceName getName()
	{
		return RockSolidAPILib.makeInternalRes("guiCoalGenerator");
	}

}
