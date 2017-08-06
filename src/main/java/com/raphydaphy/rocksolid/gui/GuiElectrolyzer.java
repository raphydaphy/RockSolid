package com.raphydaphy.rocksolid.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.tileentity.TileEntityElectrolyzer;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class GuiElectrolyzer extends GuiContainer
{

	private final TileEntityElectrolyzer tile;

	public GuiElectrolyzer(final AbstractEntityPlayer player, final TileEntityElectrolyzer tile)
	{
		super(player, 198, 150);
		this.tile = tile;
	}

	@Override
	public void initGui(final IGameInstance game)
	{
		super.initGui(game);
		this.components.add(new ComponentProgressBar(this, this.guiLeft + 73, this.guiTop + 15, 35, 8,
				GuiContainer.PROGRESS_COLOR, false, this.tile::getSmeltPercentage));

		this.components.add(new ComponentProgressBar(this, this.guiLeft + 50, this.guiTop + 6, 10, 30,
				Fluid.getByName(this.tile.getFluidType()).getColor(), true, this.tile::getFluidTankFullness));

		this.components.add(new ComponentProgressBar(this, this.guiLeft + 125, this.guiTop + 6, 10, 30,
				RockSolidAPILib.getGasColor(tile.getGasTanksType()[0]), true, this.tile::getGasTank1Fullness));
		this.components.add(new ComponentProgressBar(this, this.guiLeft + 145, this.guiTop + 6, 10, 30,
				RockSolidAPILib.getGasColor(tile.getGasTanksType()[1]), true, this.tile::getGasTank2Fullness));

		this.components.add(new ComponentProgressBar(this, this.guiLeft + 60, this.guiTop + 45, 80, 10,
				new Color(148, 0, 211), false, this.tile::getEnergyFullness));
	}

	@Override
	public void renderOverlay(IGameInstance game, IAssetManager manager, Graphics g)
	{
		super.renderOverlay(game, manager, g);
		boolean mouseOverPowerBarX = (game.getMouseInGuiX() >= this.guiLeft + 60)
				&& (game.getMouseInGuiX() <= (this.guiLeft + 60 + 80));
		boolean mouseOverPowerBarY = (game.getMouseInGuiY() >= this.guiTop + 45)
				&& (game.getMouseInGuiY() <= (this.guiTop + 45 + 10));

		if (mouseOverPowerBarX && mouseOverPowerBarY)
		{
			RockBottomAPI.getApiHandler().drawHoverInfoAtMouse(game, manager, g, false, 500, new String[] {
					"Storing " + this.tile.getCurrentEnergy() + "kWh of Energy", "Uses 30kWh per tick" });
		}

		boolean mouseOverFluidBarX = (game.getMouseInGuiX() >= this.guiLeft + 50)
				&& (game.getMouseInGuiX() <= (this.guiLeft + 50 + 10));
		boolean mouseOverFluidBarY = (game.getMouseInGuiY() >= this.guiTop + 6)
				&& (game.getMouseInGuiY() <= (this.guiTop + 6 + 30));

		if (mouseOverFluidBarX && mouseOverFluidBarY)
		{
			RockBottomAPI.getApiHandler().drawHoverInfoAtMouse(game, manager, g, false, 500,
					new String[] {
							"Storing " + this.tile.getCurrentFluid() + "mL of "
									+ Fluid.getByName(this.tile.getFluidType()).getName(),
							"Uses 150mL per operation" });
		}

		boolean mouseOverGasBar1X = (game.getMouseInGuiX() >= this.guiLeft + 125)
				&& (game.getMouseInGuiX() <= (this.guiLeft + 125 + 10));
		boolean mouseOverGasBar1Y = (game.getMouseInGuiY() >= this.guiTop + 6)
				&& (game.getMouseInGuiY() <= (this.guiTop + 6 + 30));

		if (mouseOverGasBar1X && mouseOverGasBar1Y)
		{
			RockBottomAPI.getApiHandler().drawHoverInfoAtMouse(game, manager, g, false, 500,
					new String[] {
							"Storing " + this.tile.getGasTanksStorage()[0] + "cc of "
									+ RockSolidAPILib.getGasLocName(this.tile.getGasTanksType()[0]),
							"Produces up to 100cc per operation" });
		}

		boolean mouseOverGasBar2X = (game.getMouseInGuiX() >= this.guiLeft + 145)
				&& (game.getMouseInGuiX() <= (this.guiLeft + 145 + 10));
		boolean mouseOverGasBar2Y = (game.getMouseInGuiY() >= this.guiTop + 6)
				&& (game.getMouseInGuiY() <= (this.guiTop + 6 + 30));

		if (mouseOverGasBar2X && mouseOverGasBar2Y)
		{
			RockBottomAPI.getApiHandler().drawHoverInfoAtMouse(game, manager, g, false, 500,
					new String[] {
							"Storing " + this.tile.getGasTanksStorage()[1] + "cc of "
									+ RockSolidAPILib.getGasLocName(this.tile.getGasTanksType()[1]),
							"Produces up to 100cc per operation" });
		}
	}

	@Override
	public IResourceName getName()
	{
		return RockSolidAPILib.makeInternalRes("guiElectrolyzer");
	}

}
