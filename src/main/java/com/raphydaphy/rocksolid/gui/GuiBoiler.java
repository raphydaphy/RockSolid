package com.raphydaphy.rocksolid.gui;

import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.tileentity.TileEntityBoiler;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class GuiBoiler extends GuiContainer
{

	private final TileEntityBoiler tile;

	public GuiBoiler(final AbstractEntityPlayer player, final TileEntityBoiler tile)
	{
		super(player, 198, 150);
		this.tile = tile;
	}

	@Override
	public void initGui(final IGameInstance game)
	{
		super.initGui(game);
		this.components.add(new ComponentProgressBar(this, this.guiLeft + 60, this.guiTop + 15, 80, 10,
				RockSolidAPILib.getGasColor(tile.getGasType()), false, this.tile::getGeneratorFullness));

		this.components.add(new ComponentProgressBar(this, this.guiLeft + 60, this.guiTop + 0, 80, 10,
				Fluid.getByName(this.tile.getFluidType()).getColor(), false, this.tile::getFluidTankFullness));

		this.components.add(new ComponentProgressBar(this, this.guiLeft + 74, this.guiTop + 30, 8, 18,
				GuiBoiler.FIRE_COLOR, true, this.tile::getFuelPercentage));
	}

	@Override
	public void renderOverlay(IGameInstance game, IAssetManager manager, Graphics g)
	{
		super.renderOverlay(game, manager, g);
		boolean mouseOverBarX = (game.getMouseInGuiX() >= this.guiLeft + 60)
				&& (game.getMouseInGuiX() <= (this.guiLeft + 60 + 80));
		boolean mouseOverBarY = (game.getMouseInGuiY() >= this.guiTop + 15)
				&& (game.getMouseInGuiY() <= (this.guiTop + 15 + 10));

		if (mouseOverBarX && mouseOverBarY)
		{
			RockBottomAPI.getApiHandler().drawHoverInfoAtMouse(game, manager, g, false, 500,
					new String[] {
							"Storing " + this.tile.getCurrentGas() + "cc of "
									+ RockSolidAPILib.getGasLocName(this.tile.getGasType()),
							"Produces " + TileEntityBoiler.productionPerTick + "cc per tick" });
		}

		boolean mouseOverFluidBarX = (game.getMouseInGuiX() >= this.guiLeft + 60)
				&& (game.getMouseInGuiX() <= (this.guiLeft + 60 + 80));
		boolean mouseOverFluidBarY = (game.getMouseInGuiY() >= this.guiTop + 0)
				&& (game.getMouseInGuiY() <= (this.guiTop + 0 + 10));

		if (mouseOverFluidBarX && mouseOverFluidBarY)
		{
			RockBottomAPI.getApiHandler().drawHoverInfoAtMouse(game, manager, g, false, 500,
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
