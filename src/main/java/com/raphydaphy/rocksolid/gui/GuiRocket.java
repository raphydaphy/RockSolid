package com.raphydaphy.rocksolid.gui;

import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.tileentity.TileEntityRocket;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentButton;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class GuiRocket extends GuiContainer
{

	private final TileEntityRocket tile;

	public GuiRocket(final AbstractEntityPlayer player, final TileEntityRocket tile)
	{
		super(player, 198, 150);
		this.tile = tile;
	}

	@Override
	public void initGui(final IGameInstance game)
	{
		super.initGui(game);
		this.components.add(new ComponentProgressBar(this, this.guiLeft + 60, this.guiTop, 80, 10,
				Fluid.getByName(this.tile.getFluidType()).getColor(), false, this.tile::getFluidTankFullnesss));
		if (this.tile.displayLaunchBtn())
		{
			this.components.add(new ComponentButton(this, 0, this.guiLeft + 75, this.guiTop + 15, 50, 18, "Launch"));
		}
	}
	
	@Override
	public boolean onButtonActivated(IGameInstance game, int button)
	{
		if (button == 0)
		{
			if (tile.displayLaunchBtn())
			{
				tile.launch();
			}
		}
		return false;
	}

	@Override
	public void renderOverlay(IGameInstance game, IAssetManager manager, Graphics g)
	{
		super.renderOverlay(game, manager, g);

		boolean mouseOverFluidBarX = (game.getMouseInGuiX() >= this.guiLeft + 60)
				&& (game.getMouseInGuiX() <= (this.guiLeft + 60 + 80));
		boolean mouseOverFluidBarY = (game.getMouseInGuiY() >= this.guiTop)
				&& (game.getMouseInGuiY() <= (this.guiTop + 10));

		if (mouseOverFluidBarX && mouseOverFluidBarY)
		{
			RockBottomAPI.getApiHandler().drawHoverInfoAtMouse(game, manager, g, false, 100, "Storing "
					+ this.tile.getCurrentFluid() + "mL of " + Fluid.getByName(this.tile.getFluidType()).getName());
		}
		
		if (this.tile.getCurrentFluid() == 1)
		{
			game.getGuiManager().closeGui();
		}
	}

	@Override
	public IResourceName getName()
	{
		return RockSolidAPILib.makeInternalRes("guiRocket");
	}

}
