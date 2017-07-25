package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.tileentity.TileEntityTank;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.IGameInstance;
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
	    this.components.add(new ComponentProgressBar(this, this.guiLeft + 60, this.guiTop + 10, 80, 10, RockSolidLib.getFluidColor(this.tile.getFluidType()), false, this.tile::getTankFullnesss));
	}

}
