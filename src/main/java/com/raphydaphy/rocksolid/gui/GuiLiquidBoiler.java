package com.raphydaphy.rocksolid.gui;

import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.tileentity.TileEntityLiquidBoiler;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
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
	public void initGui(final IGameInstance game)
	{
		super.initGui(game);
		this.components.add(new ComponentProgressBar(this, this.guiLeft + 60, this.guiTop + 42, 80, 10,
				RockSolidLib.getGasColor(tile.getGasType()), false, this.tile::getGeneratorFullness));
		
		this.components.add(new ComponentProgressBar(this, this.guiLeft + 60, this.guiTop + 20, 80, 10,
				RockSolidLib.getFluidColor(tile.getFluidTanksType()[0]), false, this.tile::getFluidTank0Fullness));
		
		this.components.add(new ComponentProgressBar(this, this.guiLeft + 60, this.guiTop + 5, 80, 10,
				RockSolidLib.getFluidColor(tile.getFluidTanksType()[1]), false, this.tile::getFluidTank1Fullness));
	}

	@Override
	public void renderOverlay(IGameInstance game, IAssetManager manager, Graphics g)
	{
		super.renderOverlay(game, manager, g);
		boolean mouseOverBarX = (game.getMouseInGuiX() >= this.guiLeft + 60)
				&& (game.getMouseInGuiX() <= (this.guiLeft + 60 + 80));
		boolean mouseOverBarY = (game.getMouseInGuiY() >= this.guiTop + 42)
				&& (game.getMouseInGuiY() <= (this.guiTop + 42 + 10));

		if (mouseOverBarX && mouseOverBarY)
		{
			RockBottomAPI.getApiHandler().drawHoverInfoAtMouse(game, manager, g, false, 500, new String[] {
					"Storing " + this.tile.getCurrentGas() + "cc of " + RockSolidLib.getGasLocName(this.tile.getGasType()), "Produces " + TileEntityLiquidBoiler.productionPerTick + "cc per tick" });
		}
		
		boolean mouseOverFluidBar1X = (game.getMouseInGuiX() >= this.guiLeft + 60)
				&& (game.getMouseInGuiX() <= (this.guiLeft + 60 + 80));
		boolean mouseOverFluidBar1Y = (game.getMouseInGuiY() >= this.guiTop + 20)
				&& (game.getMouseInGuiY() <= (this.guiTop + 20 + 10));

		if (mouseOverFluidBar1X && mouseOverFluidBar1Y)
		{
			RockBottomAPI.getApiHandler().drawHoverInfoAtMouse(game, manager, g, false, 500, new String[] {
					"Storing " + this.tile.getFluidTanksStorage()[0] + "mL of " + RockSolidLib.getFluidLocName(this.tile.getFluidTanksType()[0]), "Consumes " + TileEntityLiquidBoiler.fluidConsumptionPerTick[0] + "mL per tick" });
		}
		
		boolean mouseOverFluidBar2X = (game.getMouseInGuiX() >= this.guiLeft + 60)
				&& (game.getMouseInGuiX() <= (this.guiLeft + 60 + 80));
		boolean mouseOverFluidBar2Y = (game.getMouseInGuiY() >= this.guiTop + 5)
				&& (game.getMouseInGuiY() <= (this.guiTop + 5 + 10));

		if (mouseOverFluidBar2X && mouseOverFluidBar2Y)
		{
			RockBottomAPI.getApiHandler().drawHoverInfoAtMouse(game, manager, g, false, 500, new String[] {
					"Storing " + this.tile.getFluidTanksStorage()[1] + "mL of " + RockSolidLib.getFluidLocName(this.tile.getFluidTanksType()[1]), "Consumes " + TileEntityLiquidBoiler.fluidConsumptionPerTick[1] + "mL per tick" });
		}
	}

	@Override
	public IResourceName getName()
	{
		return RockSolidLib.makeRes("guiCoalGenerator");
	}

}
