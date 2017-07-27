package com.raphydaphy.rocksolid.gui;

import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.tileentity.TileEntityTank;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;

public class GuiTank extends GuiContainer
{

    private final TileEntityTank tile;
    
	public GuiTank(final AbstractEntityPlayer player, final TileEntityTank tile) {
	    super(player, 198, 150);
	    this.tile = tile;
	}
	
	@Override
	public void initGui(final IGameInstance game) {
	    super.initGui(game);
	    this.components.add(new ComponentProgressBar(this, this.guiLeft + 60, this.guiTop + 10, 80, 10, RockSolidLib.getFluidColor(this.tile.getFluidType()), false, this.tile::getFluidTankFullnesss));
	}
	
	@Override
	public void renderOverlay(IGameInstance game, IAssetManager manager, Graphics g)
	{
    	super.renderOverlay(game, manager, g);
		
		boolean mouseOverFluidBarX = (game.getMouseInGuiX() >= this.guiLeft + 60) && (game.getMouseInGuiX() <= (this.guiLeft + 60 + 80));
		boolean mouseOverFluidBarY = (game.getMouseInGuiY() >= this.guiTop + 10) && (game.getMouseInGuiY() <= (this.guiTop + 10 + 10));
		
		if (mouseOverFluidBarX && mouseOverFluidBarY)
		{
			RockBottomAPI.getApiHandler().drawHoverInfoAtMouse(game, manager, g, false, 100, "Storing " + this.tile.getCurrentFluid() + "mL of Fluid");
		}
	}

}
