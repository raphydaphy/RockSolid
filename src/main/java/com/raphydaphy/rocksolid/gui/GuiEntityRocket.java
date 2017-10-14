package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.entity.EntityRocket;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IGraphics;
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
	public void init(final IGameInstance game)
	{
		super.init(game);
		this.components.add(new ComponentProgressBar(this, this.x + 60, this.y + 10, 80, 10, Fluid.FUEL.getColor(),
				false, this.rocket::getTankFullness));
	}

	@Override
	public void renderOverlay(IGameInstance game, IAssetManager manager, IGraphics g)
	{
		super.renderOverlay(game, manager, g);

		boolean mouseOverFluidBarX = (g.getMouseInGuiX() >= this.x + 60) && (g.getMouseInGuiX() <= (this.x + 60 + 80));
		boolean mouseOverFluidBarY = (g.getMouseInGuiY() >= this.y + 10) && (g.getMouseInGuiY() <= (this.y + 10 + 10));

		if (mouseOverFluidBarX && mouseOverFluidBarY)
		{
			g.drawHoverInfoAtMouse(game, manager, false, 100,
					"Storing " + this.rocket.getFuel() + "mL of " + Fluid.FUEL.getName());
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
