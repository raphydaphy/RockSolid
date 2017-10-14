package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.network.PacketRocketLaunch;
import com.raphydaphy.rocksolid.tileentity.TileEntityRocket;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IGraphics;
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
	public void init(final IGameInstance game)
	{
		super.init(game);
		this.components.add(new ComponentProgressBar(this, this.x + 60, this.y, 80, 10,
				Fluid.getByName(this.tile.getFluidType()).getColor(), false, this.tile::getFluidTankFullnesss));
		if (this.tile.displayLaunchBtn())
		{
			this.components.add(new ComponentButton(this, this.x + 108, this.y + 18, 60, 18, () ->
			{
				return this.onButtonActivated(game, 0);
			}, "Launch", "Make sure you have plenty of fuel before launching!"));
		}
	}

	public boolean onButtonActivated(IGameInstance game, int button)
	{
		if (button == 0)
		{
			if (tile.displayLaunchBtn())
			{
				tile.launch();
				RockBottomAPI.getNet().sendToServer(new PacketRocketLaunch(tile.x, tile.y));
				return true;
			}
		}
		return false;
	}

	@Override
	public void renderOverlay(IGameInstance game, IAssetManager manager, IGraphics g)
	{
		super.renderOverlay(game, manager, g);

		boolean mouseOverFluidBarX = (g.getMouseInGuiX() >= this.x + 60) && (g.getMouseInGuiX() <= (this.x + 60 + 80));
		boolean mouseOverFluidBarY = (g.getMouseInGuiY() >= this.y) && (g.getMouseInGuiY() <= (this.y + 10));

		if (mouseOverFluidBarX && mouseOverFluidBarY)
		{
			g.drawHoverInfoAtMouse(game, manager, false, 100, "Storing " + this.tile.getCurrentFluid() + "mL of "
					+ Fluid.getByName(this.tile.getFluidType()).getName());
		}

		if (this.tile.getCurrentFluid() == 1)
		{
			game.getGuiManager().closeGui();
			System.out.println("test");
		}
	}

	@Override
	public IResourceName getName()
	{
		return RockSolidAPILib.makeInternalRes("guiRocket");
	}

}
