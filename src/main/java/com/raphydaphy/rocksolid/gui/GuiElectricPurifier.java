package com.raphydaphy.rocksolid.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.tileentity.TileEntityElectricPurifier;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class GuiElectricPurifier extends GuiContainer
{

	private final TileEntityElectricPurifier tile;

	public GuiElectricPurifier(final AbstractEntityPlayer player, final TileEntityElectricPurifier tile)
	{
		super(player, 198, 150);
		this.tile = tile;
	}

	@Override
	public void initGui(final IGameInstance game)
	{
		super.initGui(game);
		this.components.add(new ComponentProgressBar(this, this.guiLeft + 80, this.guiTop + 15, 35, 8,
				GuiElectricPurifier.PROGRESS_COLOR, false, this.tile::getSmeltPercentage));
		this.components.add(new ComponentProgressBar(this, this.guiLeft + 60, this.guiTop + 30, 80, 10,
				RockSolidLib.getFluidColor(tile.getFluidType()), false, this.tile::getFluidTankFullness));
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
				&& (game.getMouseInGuiX() <= (this.guiLeft + 60 + 80));
		boolean mouseOverFluidBarY = (game.getMouseInGuiY() >= this.guiTop + 30)
				&& (game.getMouseInGuiY() <= (this.guiTop + 30 + 10));

		if (mouseOverFluidBarX && mouseOverFluidBarY)
		{
			RockBottomAPI.getApiHandler().drawHoverInfoAtMouse(game, manager, g, false, 500, new String[] {
					"Storing " + this.tile.getCurrentFluid() + "mL of Fluid", "Uses 100mL per operation" });
		}
	}

	@Override
	public IResourceName getName()
	{
		return RockSolidLib.makeRes("guiElectricPurifier");
	}

}
