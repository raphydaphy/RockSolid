package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.tileentity.TileEntityRefinery;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class GuiRefinery extends GuiContainer
{
	public static final int ELECTRICITY_COLOR = 0x9400d3;
	public static final int PROGRESS_COLOR = 0x1a801a;
	public static final int FIRE_COLOR = 0x801a1a;
	private final TileEntityRefinery tile;

	public GuiRefinery(final AbstractEntityPlayer player, final TileEntityRefinery tile)
	{
		super(player, 198, 150);
		this.tile = tile;
	}

	@Override
	public void init(final IGameInstance game)
	{
		super.init(game);
		this.components.add(new ComponentProgressBar(this, this.x + 80, this.y + 15, 40, 8, PROGRESS_COLOR, false,
				this.tile::getSmeltPercentage));

		this.components.add(new ComponentProgressBar(this, this.x + 60, this.y + 6, 10, 30,
				Fluid.getByName(this.tile.getFluidTanksType()[0]).getColor(), true,
				this.tile::getInputFluidTankFullness));

		this.components.add(new ComponentProgressBar(this, this.x + 130, this.y + 6, 10, 30,
				Fluid.getByName(tile.getFluidTanksType()[1]).getColor(), true, this.tile::getOutputFluidTankFullness));

		this.components.add(new ComponentProgressBar(this, this.x + 60, this.y + 45, 80, 10, ELECTRICITY_COLOR, false,
				this.tile::getEnergyFullness));
	}

	@Override
	public void renderOverlay(IGameInstance game, IAssetManager manager, IGraphics g)
	{
		super.renderOverlay(game, manager, g);
		boolean mouseOverPowerBarX = (g.getMouseInGuiX() >= this.x + 60) && (g.getMouseInGuiX() <= (this.x + 60 + 80));
		boolean mouseOverPowerBarY = (g.getMouseInGuiY() >= this.y + 45) && (g.getMouseInGuiY() <= (this.y + 45 + 10));

		if (mouseOverPowerBarX && mouseOverPowerBarY)
		{
			g.drawHoverInfoAtMouse(game, manager, false, 500, new String[] {
					"Storing " + this.tile.getCurrentEnergy() + "kWh of Energy", "Uses 30kWh per tick" });
		}

		boolean mouseOverFluidBarX = (g.getMouseInGuiX() >= this.x + 60) && (g.getMouseInGuiX() <= (this.x + 60 + 10));
		boolean mouseOverFluidBarY = (g.getMouseInGuiY() >= this.y + 6) && (g.getMouseInGuiY() <= (this.y + 6 + 30));

		if (mouseOverFluidBarX && mouseOverFluidBarY)
		{
			g.drawHoverInfoAtMouse(game, manager, false, 500,
					new String[] {
							"Storing " + this.tile.getFluidTanksStorage()[0] + "mL of "
									+ Fluid.getByName(this.tile.getFluidTanksType()[0]).getName(),
							"Uses up to 50mL per operation" });
		}

		boolean mouseOverOutputBarX = (g.getMouseInGuiX() >= this.x + 130)
				&& (g.getMouseInGuiX() <= (this.x + 130 + 10));
		boolean mouseOverOutputBarY = (g.getMouseInGuiY() >= this.y + 6) && (g.getMouseInGuiY() <= (this.y + 6 + 30));

		if (mouseOverOutputBarX && mouseOverOutputBarY)
		{
			g.drawHoverInfoAtMouse(game, manager, false, 500,
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
