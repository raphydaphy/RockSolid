package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.api.gas.Gas;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.tileentity.TileEntityElectrolyzer;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class GuiElectrolyzer extends GuiContainer
{
	public static final int ELECTRICITY_COLOR = 0x9400d3;
	public static final int PROGRESS_COLOR = 0x1a801a;
	public static final int FIRE_COLOR = 0x801a1a;
	private final TileEntityElectrolyzer tile;

	public GuiElectrolyzer(final AbstractEntityPlayer player, final TileEntityElectrolyzer tile)
	{
		super(player, 198, 150);
		this.tile = tile;
	}

	@Override
	public void init(final IGameInstance game)
	{
		super.init(game);
		this.components.add(new ComponentProgressBar(this, this.x + 73, this.y + 15, 35, 8, PROGRESS_COLOR, false,
				this.tile::getSmeltPercentage));

		this.components.add(new ComponentProgressBar(this, this.x + 50, this.y + 6, 10, 30,
				Fluid.getByName(this.tile.getFluidType()).getColor(), true, this.tile::getFluidTankFullness));

		this.components.add(new ComponentProgressBar(this, this.x + 125, this.y + 6, 10, 30,
				Gas.getByName(tile.getGasTanksType()[0]).getColor(), true, this.tile::getGasTank1Fullness));
		this.components.add(new ComponentProgressBar(this, this.x + 145, this.y + 6, 10, 30,
				Gas.getByName(tile.getGasTanksType()[1]).getColor(), true, this.tile::getGasTank2Fullness));

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

		boolean mouseOverFluidBarX = (g.getMouseInGuiX() >= this.x + 50) && (g.getMouseInGuiX() <= (this.x + 50 + 10));
		boolean mouseOverFluidBarY = (g.getMouseInGuiY() >= this.y + 6) && (g.getMouseInGuiY() <= (this.y + 6 + 30));

		if (mouseOverFluidBarX && mouseOverFluidBarY)
		{
			g.drawHoverInfoAtMouse(game, manager, false, 500,
					new String[] {
							"Storing " + this.tile.getCurrentFluid() + "mL of "
									+ Fluid.getByName(this.tile.getFluidType()).getName(),
							"Uses 150mL per operation" });
		}

		boolean mouseOverGasBar1X = (g.getMouseInGuiX() >= this.x + 125) && (g.getMouseInGuiX() <= (this.x + 125 + 10));
		boolean mouseOverGasBar1Y = (g.getMouseInGuiY() >= this.y + 6) && (g.getMouseInGuiY() <= (this.y + 6 + 30));

		if (mouseOverGasBar1X && mouseOverGasBar1Y)
		{
			g.drawHoverInfoAtMouse(game, manager, false, 500,
					new String[] {
							"Storing " + this.tile.getGasTanksStorage()[0] + "cc of " + this.tile.getGasTanksType()[0],
							"Produces up to 100cc per operation" });
		}

		boolean mouseOverGasBar2X = (g.getMouseInGuiX() >= this.x + 145) && (g.getMouseInGuiX() <= (this.x + 145 + 10));
		boolean mouseOverGasBar2Y = (g.getMouseInGuiY() >= this.y + 6) && (g.getMouseInGuiY() <= (this.y + 6 + 30));

		if (mouseOverGasBar2X && mouseOverGasBar2Y)
		{
			g.drawHoverInfoAtMouse(game, manager, false, 500,
					new String[] {
							"Storing " + this.tile.getGasTanksStorage()[1] + "cc of " + this.tile.getGasTanksType()[1],
							"Produces up to 100cc per operation" });
		}
	}

	@Override
	public IResourceName getName()
	{
		return RockSolidAPILib.makeInternalRes("guiElectrolyzer");
	}

}
