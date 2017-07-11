package com.raphydaphy.rocksolid.gui;

import org.newdawn.slick.Color;

import com.raphydaphy.rocksolid.tileentity.TileEntityElectricSeparator;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;

public class GuiElectricSeparator extends GuiContainer
{
	private final TileEntityElectricSeparator tile;
   
    public GuiElectricSeparator(final AbstractEntityPlayer player, final TileEntityElectricSeparator tile) {
        super(player, 198, 150);
        this.tile = tile;
    }
   
    @Override
    public void initGui(final IGameInstance game) {
        super.initGui(game);
        this.components.add(new ComponentProgressBar(this, this.guiLeft + 70, this.guiTop + 15, 40, 8, GuiElectricSeparator.PROGRESS_COLOR, false, this.tile::getSmeltPercentage));
        this.components.add(new ComponentProgressBar(this, this.guiLeft + 60, this.guiTop + 38, 80, 10, Color.red, false, this.tile::getEnergyFullness));
    }
}