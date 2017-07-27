package com.raphydaphy.rocksolid.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.tileentity.TileEntityCreativePowerSource;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;

public class GuiCreativePowerSource extends GuiContainer
{

    private final TileEntityCreativePowerSource tile;
    
	public GuiCreativePowerSource(final AbstractEntityPlayer player, final TileEntityCreativePowerSource tile) {
	    super(player, 198, 150);
	    this.tile = tile;
	}
	
	@Override
	public void initGui(final IGameInstance game) {
	    super.initGui(game);
	    this.components.add(new ComponentProgressBar(this, this.guiLeft + 60, this.guiTop + 10, 80, 10, new Color(148,0,211), false, this.tile::getBatteryFullness));
	}
	

	
	@Override
	public void renderOverlay(IGameInstance game, IAssetManager manager, Graphics g)
	{
		super.renderOverlay(game, manager, g);
		
		boolean mouseOverBarX = (game.getMouseInGuiX() >= this.guiLeft + 60) && (game.getMouseInGuiX() <= (this.guiLeft + 60 + 80));
		boolean mouseOverBarY = (game.getMouseInGuiY() >= this.guiTop + 10) && (game.getMouseInGuiY() <= (this.guiTop + 10 + 10));
		
		if (mouseOverBarX && mouseOverBarY)
		{
			RockBottomAPI.getApiHandler().drawHoverInfoAtMouse(game, manager, g, false, 100, "Storing " + this.tile.getCurrentEnergy() + "kAh of Energy");
		}
	}

}
