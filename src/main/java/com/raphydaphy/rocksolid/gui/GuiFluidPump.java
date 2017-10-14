package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.tileentity.TileEntityFluidPump;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class GuiFluidPump extends GuiContainer
{
	public static final int ELECTRICITY_COLOR = 0x9400d3;
	public static final int PROGRESS_COLOR = 0x1a801a;
	public static final int FIRE_COLOR = 0x801a1a;
	private final TileEntityFluidPump tile;

	public GuiFluidPump(final AbstractEntityPlayer player, final TileEntityFluidPump tile)
	{
		super(player, 198, 150);
		this.tile = tile;
	}

	@Override
	public void init(final IGameInstance game)
	{
		super.init(game);
		this.components.add(new ComponentProgressBar(this, this.x + 60, this.y + 25, 80, 10, ELECTRICITY_COLOR, false,
				this.tile::getEnergyFullness));
		this.components.add(new ComponentProgressBar(this, this.x + 60, this.y + 40, 80, 10,
				Fluid.getByName(this.tile.getFluidType()).getColor(), false, this.tile::getFluidTankFullness));
	}

	@Override
	public void renderOverlay(IGameInstance game, IAssetManager manager, IGraphics g)
	{
		super.renderOverlay(game, manager, g);
		boolean mouseOverPowerBarX = (g.getMouseInGuiX() >= this.x + 60) && (g.getMouseInGuiX() <= (this.x + 60 + 80));
		boolean mouseOverPowerBarY = (g.getMouseInGuiY() >= this.y + 25) && (g.getMouseInGuiY() <= (this.y + 25 + 10));

		if (mouseOverPowerBarX && mouseOverPowerBarY)
		{
			g.drawHoverInfoAtMouse(game, manager, false, 500,
					new String[] { "Storing " + this.tile.getCurrentEnergy() + "kWh of Energy",
							"Uses " + tile.getPowerPerOperation() + "kWh per tick" });
		}

		boolean mouseOverFluidBarX = (g.getMouseInGuiX() >= this.x + 60) && (g.getMouseInGuiX() <= (this.x + 60 + 80));
		boolean mouseOverFluidBarY = (g.getMouseInGuiY() >= this.y + 40) && (g.getMouseInGuiY() <= (this.y + 40 + 10));

		if (mouseOverFluidBarX && mouseOverFluidBarY)
		{
			g.drawHoverInfoAtMouse(game, manager, false, 500,
					new String[] {
							"Storing " + this.tile.getCurrentFluid() + "mL of "
									+ Fluid.getByName(this.tile.getFluidType()).getName(),
							"Produces 1000mL per operation" });
		}
	}

	@Override
	public IResourceName getName()
	{
		return RockSolidAPILib.makeInternalRes("guiFluidPump");
	}

}
