package com.raphydaphy.rocksolid.api.gui;

import org.newdawn.slick.Color;

import com.raphydaphy.rocksolid.api.energy.TileEntityPowered;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;

public class GuiBasicPowered extends GuiContainer
{
    private final TileEntityPowered tile;
    
    public GuiBasicPowered(final AbstractEntityPlayer player, final TileEntityPowered tile) 
    {
        super(player, 198, 150);
        this.tile = tile;
    }
    
    @Override
    public void initGui(final IGameInstance game) {
        super.initGui(game);
        this.components.add(new ComponentProgressBar(this, this.guiLeft + 80, this.guiTop + 15, 40, 8, GuiBasicPowered.PROGRESS_COLOR, false, this.tile::getSmeltPercentage));
        this.components.add(new ComponentProgressBar(this, this.guiLeft + 60, this.guiTop + 38, 80, 10, new Color(148,0,211), false, this.tile::getEnergyFullness));
    }

}
