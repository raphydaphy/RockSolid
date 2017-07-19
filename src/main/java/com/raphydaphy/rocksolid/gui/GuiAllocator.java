package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.tileentity.TileEntityAllocator;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;

public class GuiAllocator extends GuiContainer
{

    
    public GuiAllocator(final AbstractEntityPlayer player, final TileEntityAllocator tile) {
        super(player, 198, 150);
    }
    
    @Override
    public void initGui(final IGameInstance game) {
        super.initGui(game);
        
    }

}
