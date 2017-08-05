package com.raphydaphy.rocksolid.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.tileentity.TileEntityFluidPump;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class GuiFluidPump extends GuiContainer
{

	private final TileEntityFluidPump tile;

	public GuiFluidPump(final AbstractEntityPlayer player, final TileEntityFluidPump tile)
	{
		super(player, 198, 150);
		this.tile = tile;
	}

	@Override
	public void initGui(final IGameInstance game)
	{
		super.initGui(game);
		this.components.add(new ComponentProgressBar(this, this.guiLeft + 60, this.guiTop + 25, 80, 10,
				new Color(148, 0, 211), false, this.tile::getEnergyFullness));
		this.components.add(new ComponentProgressBar(this, this.guiLeft + 60, this.guiTop + 40, 80, 10,
				Fluid.getByName(this.tile.getFluidType()).getColor(), false, this.tile::getFluidTankFullness));
	}

	@Override
	public void renderOverlay(IGameInstance game, IAssetManager manager, Graphics g)
	{
		super.renderOverlay(game, manager, g);
		boolean mouseOverPowerBarX = (game.getMouseInGuiX() >= this.guiLeft + 60)
				&& (game.getMouseInGuiX() <= (this.guiLeft + 60 + 80));
		boolean mouseOverPowerBarY = (game.getMouseInGuiY() >= this.guiTop + 25)
				&& (game.getMouseInGuiY() <= (this.guiTop + 25 + 10));

		if (mouseOverPowerBarX && mouseOverPowerBarY)
		{
			RockBottomAPI.getApiHandler().drawHoverInfoAtMouse(game, manager, g, false, 500,
					new String[] { "Storing " + this.tile.getCurrentEnergy() + "kWh of Energy",
							"Uses " + tile.getPowerPerOperation() + "kWh per tick" });
		}

		boolean mouseOverFluidBarX = (game.getMouseInGuiX() >= this.guiLeft + 60)
				&& (game.getMouseInGuiX() <= (this.guiLeft + 60 + 80));
		boolean mouseOverFluidBarY = (game.getMouseInGuiY() >= this.guiTop + 40)
				&& (game.getMouseInGuiY() <= (this.guiTop + 40 + 10));

		if (mouseOverFluidBarX && mouseOverFluidBarY)
		{
			RockBottomAPI.getApiHandler().drawHoverInfoAtMouse(game, manager, g, false, 500, new String[] {
					"Storing " + this.tile.getCurrentFluid() + "mL of Fluid", "Produces 1000mL per operation" });
		}
	}

	@Override
	public IResourceName getName()
	{
		return RockSolidAPILib.makeInternalRes("guiFluidPump");
	}

}
