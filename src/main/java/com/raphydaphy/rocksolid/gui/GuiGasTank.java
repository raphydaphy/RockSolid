package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.tileentity.TileEntityGasTank;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;

public class GuiGasTank extends GuiContainer
{

    private final TileEntityGasTank tile;
    
	public GuiGasTank(final AbstractEntityPlayer player, final TileEntityGasTank tile) {
	    super(player, 198, 150);
	    this.tile = tile;
	}
	
	@Override
	public void initGui(final IGameInstance game) {
	    super.initGui(game);
	    this.components.add(new ComponentProgressBar(this, this.guiLeft + 60, this.guiTop + 10, 80, 10, RockSolidLib.getGasColor(this.tile.getGasType()), false, this.tile::getTankFullnesss));
	}

}
