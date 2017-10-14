package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.api.gas.Gas;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.tileentity.TileEntityTurbine;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class GuiTurbine extends GuiContainer
{
	public static final int ELECTRICITY_COLOR = 0x9400d3;
	public static final int PROGRESS_COLOR = 0x1a801a;
	public static final int FIRE_COLOR = 0x801a1a;

	private final TileEntityTurbine tile;

	public GuiTurbine(final AbstractEntityPlayer player, final TileEntityTurbine tile)
	{
		super(player, 198, 150);
		this.tile = tile;
	}

	@Override
	public void init(final IGameInstance game)
	{
		super.init(game);
		this.components.add(new ComponentProgressBar(this, this.x + 60, this.y + 40, 80, 10, ELECTRICITY_COLOR, false,
				this.tile::getGeneratorFullness));

		this.components.add(new ComponentProgressBar(this, this.x + 60, this.y + 25, 80, 10,
				Gas.getByName(tile.getGasType()).getColor(), false, this.tile::getGasTankFullness));
	}

	@Override
	public void renderOverlay(IGameInstance game, IAssetManager manager, IGraphics g)
	{
		super.renderOverlay(game, manager, g);
		boolean mouseOverBarX = (g.getMouseInGuiX() >= this.x + 60) && (g.getMouseInGuiX() <= (this.x + 60 + 80));
		boolean mouseOverBarY = (g.getMouseInGuiY() >= this.y + 40) && (g.getMouseInGuiY() <= (this.y + 40 + 10));

		if (mouseOverBarX && mouseOverBarY)
		{
			g.drawHoverInfoAtMouse(game, manager, false, 500,
					new String[] { "Storing " + this.tile.getCurrentEnergy() + "kWh of Energy",
							"Produces " + TileEntityTurbine.productionPerTick + "kWh per tick" });
		}

		boolean mouseOverGasBarX = (g.getMouseInGuiX() >= this.x + 60) && (g.getMouseInGuiX() <= (this.x + 60 + 80));
		boolean mouseOverGasBarY = (g.getMouseInGuiY() >= this.y + 25) && (g.getMouseInGuiY() <= (this.y + 25 + 10));

		if (mouseOverGasBarX && mouseOverGasBarY)
		{
			g.drawHoverInfoAtMouse(game, manager, false, 500,
					new String[] { "Storing " + this.tile.getCurrentGas() + "cc of " + this.tile.getGasType(),
							"Consumes " + TileEntityTurbine.gasConsumptionPerTick + "cc per tick" });
		}
	}

	@Override
	public IResourceName getName()
	{
		return RockSolidAPILib.makeInternalRes("guiCoalGenerator");
	}

}
