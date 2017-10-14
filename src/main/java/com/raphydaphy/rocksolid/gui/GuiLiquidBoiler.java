package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.api.gas.Gas;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.tileentity.TileEntityLiquidBoiler;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class GuiLiquidBoiler extends GuiContainer
{

	private final TileEntityLiquidBoiler tile;

	public GuiLiquidBoiler(final AbstractEntityPlayer player, final TileEntityLiquidBoiler tile)
	{
		super(player, 198, 150);
		this.tile = tile;
	}

	@Override
	public void init(final IGameInstance game)
	{
		super.init(game);
		this.components.add(new ComponentProgressBar(this, this.x + 60, this.y + 42, 80, 10,
				Gas.getByName(tile.getGasType()).getColor(), false, this.tile::getGeneratorFullness));

		this.components.add(new ComponentProgressBar(this, this.x + 60, this.y + 20, 80, 10,
				Fluid.getByName(tile.getFluidTanksType()[0]).getColor(), false, this.tile::getFluidTank0Fullness));

		this.components.add(new ComponentProgressBar(this, this.x + 60, this.y + 5, 80, 10,
				Fluid.getByName(tile.getFluidTanksType()[1]).getColor(), false, this.tile::getFluidTank1Fullness));
	}

	@Override
	public void renderOverlay(IGameInstance game, IAssetManager manager, IGraphics g)
	{
		super.renderOverlay(game, manager, g);
		boolean mouseOverBarX = (g.getMouseInGuiX() >= this.x + 60) && (g.getMouseInGuiX() <= (this.x + 60 + 80));
		boolean mouseOverBarY = (g.getMouseInGuiY() >= this.y + 42) && (g.getMouseInGuiY() <= (this.y + 42 + 10));

		if (mouseOverBarX && mouseOverBarY)
		{
			g.drawHoverInfoAtMouse(game, manager, false, 500,
					new String[] { "Storing " + this.tile.getCurrentGas() + "cc of " + this.tile.getGasType(),
							"Produces " + TileEntityLiquidBoiler.productionPerTick + "cc per tick" });
		}

		boolean mouseOverFluidBar1X = (g.getMouseInGuiX() >= this.x + 60) && (g.getMouseInGuiX() <= (this.x + 60 + 80));
		boolean mouseOverFluidBar1Y = (g.getMouseInGuiY() >= this.y + 20) && (g.getMouseInGuiY() <= (this.y + 20 + 10));

		if (mouseOverFluidBar1X && mouseOverFluidBar1Y)
		{
			g.drawHoverInfoAtMouse(game, manager, false, 500,
					new String[] {
							"Storing " + this.tile.getFluidTanksStorage()[0] + "mL of "
									+ Fluid.getByName(tile.getFluidTanksType()[0]).getName(),
							"Consumes " + TileEntityLiquidBoiler.fluidConsumptionPerTick[0] + "mL per tick" });
		}

		boolean mouseOverFluidBar2X = (g.getMouseInGuiX() >= this.x + 60) && (g.getMouseInGuiX() <= (this.x + 60 + 80));
		boolean mouseOverFluidBar2Y = (g.getMouseInGuiY() >= this.y + 5) && (g.getMouseInGuiY() <= (this.y + 5 + 10));

		if (mouseOverFluidBar2X && mouseOverFluidBar2Y)
		{
			g.drawHoverInfoAtMouse(game, manager, false, 500,
					new String[] {
							"Storing " + this.tile.getFluidTanksStorage()[1] + "mL of "
									+ Fluid.getByName(tile.getFluidTanksType()[1]).getName(),
							"Consumes " + TileEntityLiquidBoiler.fluidConsumptionPerTick[1] + "mL per tick" });
		}
	}

	@Override
	public IResourceName getName()
	{
		return RockSolidAPILib.makeInternalRes("guiCoalGenerator");
	}

}
