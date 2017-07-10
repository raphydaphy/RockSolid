package com.raphydaphy.rocksolid.gui;

import org.newdawn.slick.Color;

import com.raphydaphy.rocksolid.tileentity.TileEntityElectricAlloySmelter;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;

public class GuiElectricAlloySmelter extends GuiContainer
{

    private final TileEntityElectricAlloySmelter tile;
    
	public GuiElectricAlloySmelter(final AbstractEntityPlayer player, final TileEntityElectricAlloySmelter tile) {
	    super(player, 198, 150);
	    this.tile = tile;
	}
	
	@Override
	public void initGui(final IGameInstance game) {
	    super.initGui(game);
	    this.components.add(new ComponentProgressBar(this, this.guiLeft + 80, this.guiTop + 15, 40, 8, GuiElectricAlloySmelter.PROGRESS_COLOR, false, this.tile::getSmeltPercentage));
	    this.components.add(new ComponentProgressBar(this, this.guiLeft + 60, this.guiTop + 38, 80, 10, Color.red, false, this.tile::getEnergyFullness));
	}

}
