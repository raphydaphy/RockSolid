package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.api.gas.Gas;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.tileentity.TileEntityBoiler;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class GuiBoiler extends GuiContainer
{
	public static final int PROGRESS_COLOR = 0x1a801a;
	public static final int FIRE_COLOR = 0x801a1a;

	private final TileEntityBoiler tile;

	public GuiBoiler(final AbstractEntityPlayer player, final TileEntityBoiler tile)
	{
		super(player, 198, 150);
		this.tile = tile;
	}

	@Override
	public void init(final IGameInstance game)
	{
		super.init(game);
		this.components.add(new ComponentProgressBar(this, this.x + 60, this.y + 15, 80, 10,
				Gas.getByName(tile.getGasType()).getColor(), false, this.tile::getGeneratorFullness));

		this.components.add(new ComponentProgressBar(this, this.x + 60, this.y + 0, 80, 10,
				Fluid.getByName(this.tile.getFluidType()).getColor(), false, this.tile::getFluidTankFullness));

		this.components.add(new ComponentProgressBar(this, this.x + 74, this.y + 30, 8, 18, FIRE_COLOR, true,
				this.tile::getFuelPercentage));
	}

	@Override
	public void renderOverlay(IGameInstance game, IAssetManager manager, IGraphics g)
	{
		super.renderOverlay(game, manager, g);
		boolean mouseOverBarX = (g.getMouseInGuiX() >= this.x + 60) && (g.getMouseInGuiX() <= (this.x + 60 + 80));
		boolean mouseOverBarY = (g.getMouseInGuiY() >= this.y + 15) && (g.getMouseInGuiY() <= (this.y + 15 + 10));

		if (mouseOverBarX && mouseOverBarY)
		{
			g.drawHoverInfoAtMouse(game, manager, false, 500,
					new String[] { "Storing " + this.tile.getCurrentGas() + "cc of " + this.tile.getGasType(),
							"Produces " + TileEntityBoiler.productionPerTick + "cc per tick" });
		}

		boolean mouseOverFluidBarX = (g.getMouseInGuiX() >= this.x + 60) && (g.getMouseInGuiX() <= (this.x + 60 + 80));
		boolean mouseOverFluidBarY = (g.getMouseInGuiY() >= this.y + 0) && (g.getMouseInGuiY() <= (this.y + 0 + 10));

		if (mouseOverFluidBarX && mouseOverFluidBarY)
		{
			g.drawHoverInfoAtMouse(game, manager, false, 500,
					new String[] {
							"Storing " + this.tile.getCurrentFluid() + "mL of "
									+ Fluid.getByName(this.tile.getFluidType()).getName(),
							"Consumes " + TileEntityBoiler.fluidConsumptionPerTick + "mL per tick" });
		}
	}

	@Override
	public IResourceName getName()
	{
		return RockSolidAPILib.makeInternalRes("guiCoalGenerator");
	}

}
