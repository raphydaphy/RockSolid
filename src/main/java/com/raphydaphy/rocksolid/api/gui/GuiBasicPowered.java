package com.raphydaphy.rocksolid.api.gui;

import org.newdawn.slick.Color;

import com.raphydaphy.rocksolid.api.energy.TileEntityPowered;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.Pos2;

public class GuiBasicPowered extends GuiContainer
{
    private final TileEntityPowered tile;
    private final Pos2 progressBarOffset;
    
    public GuiBasicPowered(final AbstractEntityPlayer player, final TileEntityPowered tile) 
    {
       this(player, tile, new Pos2(80, 15));
    }
    
    public GuiBasicPowered(final AbstractEntityPlayer player, final TileEntityPowered tile, final Pos2 progressBarOffset)
    {
    	 super(player, 198, 150);
    	 this.progressBarOffset = progressBarOffset;
         this.tile = tile;
    }
    
    @Override
    public void initGui(final IGameInstance game) {
        super.initGui(game);
        this.components.add(new ComponentProgressBar(this, this.guiLeft + this.progressBarOffset.getX(), this.guiTop + this.progressBarOffset.getY(), 40, 8, GuiBasicPowered.PROGRESS_COLOR, false, this.tile::getSmeltPercentage));
        this.components.add(new ComponentProgressBar(this, this.guiLeft + 60, this.guiTop + 38, 80, 10, new Color(148,0,211), false, this.tile::getEnergyFullness));
    }

}
