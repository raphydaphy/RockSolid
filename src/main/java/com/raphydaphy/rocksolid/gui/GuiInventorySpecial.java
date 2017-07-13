package com.raphydaphy.rocksolid.gui;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;

public class GuiInventorySpecial extends GuiContainer
{

    
	public GuiInventorySpecial(final AbstractEntityPlayer player) {
	    super(player, 198, 150);
	}
	
	@Override
	public void initGui(final IGameInstance game) {
	    super.initGui(game);
	}

}
