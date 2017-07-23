package com.raphydaphy.rocksolid.gui;

import org.newdawn.slick.Color;

import com.raphydaphy.rocksolid.tileentity.TileEntityCompressor;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;

public class GuiCompressor extends GuiContainer
{

    private final TileEntityCompressor tile;
    
    public GuiCompressor(final AbstractEntityPlayer player, final TileEntityCompressor tile) {
        super(player, 198, 150);
        this.tile = tile;
    }
    
    @Override
    public void initGui(final IGameInstance game) {
        super.initGui(game);
        this.components.add(new ComponentProgressBar(this, this.guiLeft + 80, this.guiTop + 15, 35, 8, GuiCompressor.PROGRESS_COLOR, false, this.tile::getSmeltPercentage));
        this.components.add(new ComponentProgressBar(this, this.guiLeft + 60, this.guiTop + 38, 80, 10, new Color(148,0,211), false, this.tile::getEnergyFullness));
    }

}
