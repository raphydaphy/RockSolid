package com.raphydaphy.rocksolid.gui;

import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.entity.EntityRocket;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class GuiEntityRocket extends GuiContainer
{

	private final EntityRocket rocket;

	public GuiEntityRocket(final AbstractEntityPlayer player, final EntityRocket entity)
	{
		super(player, 198, 150);
		this.rocket = entity;
	}

	@Override
	public void initGui(final IGameInstance game)
	{
		super.initGui(game);
		this.components.add(new ComponentProgressBar(this, this.guiLeft + 60, this.guiTop + 10, 80, 10,
				Fluid.FUEL.getColor(), false, this.rocket::getTankFullness));
	}

	@Override
	public void renderOverlay(IGameInstance game, IAssetManager manager, Graphics g)
	{
		super.renderOverlay(game, manager, g);

		boolean mouseOverFluidBarX = (game.getMouseInGuiX() >= this.guiLeft + 60)
				&& (game.getMouseInGuiX() <= (this.guiLeft + 60 + 80));
		boolean mouseOverFluidBarY = (game.getMouseInGuiY() >= this.guiTop + 10)
				&& (game.getMouseInGuiY() <= (this.guiTop + 10 + 10));

		if (mouseOverFluidBarX && mouseOverFluidBarY)
		{
			RockBottomAPI.getApiHandler().drawHoverInfoAtMouse(game, manager, g, false, 100, "Storing "
					+ this.rocket.getFuel() + "mL of " + Fluid.FUEL.getName());
		}

		if (this.rocket.getFuel() == 1)
		{
			game.getGuiManager().closeGui();
		}
	}

	@Override
	public IResourceName getName()
	{
		return RockSolidAPILib.makeInternalRes("guiRocketEntity");
	}

}