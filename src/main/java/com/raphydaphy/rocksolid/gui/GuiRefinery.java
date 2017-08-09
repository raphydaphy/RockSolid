package com.raphydaphy.rocksolid.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.tileentity.TileEntityRefinery;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class GuiRefinery extends GuiContainer
{

	private final TileEntityRefinery tile;

	public GuiRefinery(final AbstractEntityPlayer player, final TileEntityRefinery tile)
	{
		super(player, 198, 150);
		this.tile = tile;
	}

	@Override
	public void initGui(final IGameInstance game)
	{
		super.initGui(game);
		this.components.add(new ComponentProgressBar(this, this.guiLeft + 80, this.guiTop + 15, 40, 8,
				GuiContainer.PROGRESS_COLOR, false, this.tile::getSmeltPercentage));

		this.components.add(new ComponentProgressBar(this, this.guiLeft + 60, this.guiTop + 6, 10, 30,
				Fluid.getByName(this.tile.getFluidTanksType()[0]).getColor(), true,
				this.tile::getInputFluidTankFullness));

		this.components.add(new ComponentProgressBar(this, this.guiLeft + 130, this.guiTop + 6, 10, 30,
				Fluid.getByName(tile.getFluidTanksType()[1]).getColor(), true, this.tile::getOutputFluidTankFullness));

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

		boolean mouseOverFluidBarX = (game.getMouseInGuiX() >= this.guiLeft + 60)
				&& (game.getMouseInGuiX() <= (this.guiLeft + 60 + 10));
		boolean mouseOverFluidBarY = (game.getMouseInGuiY() >= this.guiTop + 6)
				&& (game.getMouseInGuiY() <= (this.guiTop + 6 + 30));

		if (mouseOverFluidBarX && mouseOverFluidBarY)
		{
			RockBottomAPI.getApiHandler().drawHoverInfoAtMouse(game, manager, g, false, 500,
					new String[] {
							"Storing " + this.tile.getFluidTanksStorage()[0] + "mL of "
									+ Fluid.getByName(this.tile.getFluidTanksType()[0]).getName(),
							"Uses up to 50mL per operation" });
		}

		boolean mouseOverOutputBarX = (game.getMouseInGuiX() >= this.guiLeft + 130)
				&& (game.getMouseInGuiX() <= (this.guiLeft + 130 + 10));
		boolean mouseOverOutputBarY = (game.getMouseInGuiY() >= this.guiTop + 6)
				&& (game.getMouseInGuiY() <= (this.guiTop + 6 + 30));

		if (mouseOverOutputBarX && mouseOverOutputBarY)
		{
			RockBottomAPI.getApiHandler().drawHoverInfoAtMouse(game, manager, g, false, 500,
					new String[] {
							"Storing " + this.tile.getFluidTanksStorage()[1] + "mL of "
									+ Fluid.getByName(this.tile.getFluidTanksType()[1]).getName(),
							"Produces up to 25mL per operation" });
		}
	}

	@Override
	public IResourceName getName()
	{
		return RockSolidAPILib.makeInternalRes("guiElectrolyzer");
	}

}
