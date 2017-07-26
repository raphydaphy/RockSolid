package com.raphydaphy.rocksolid.gui;

import org.newdawn.slick.Color;

import com.raphydaphy.rocksolid.tileentity.TileEntityElectricPurifier;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;

public class GuiElectricPurifier extends GuiContainer
{

    private final TileEntityElectricPurifier tile;
    
    public GuiElectricPurifier(final AbstractEntityPlayer player, final TileEntityElectricPurifier tile) {
        super(player, 198, 150);
        this.tile = tile;
    }
    
    @Override
    public void initGui(final IGameInstance game) 
    {
        super.initGui(game);
        this.components.add(new ComponentProgressBar(this, this.guiLeft + 80, this.guiTop + 15, 35, 8, GuiElectricPurifier.PROGRESS_COLOR, false, this.tile::getSmeltPercentage));
        this.components.add(new ComponentProgressBar(this, this.guiLeft + 60, this.guiTop + 30, 80, 10, RockSolidLib.getFluidColor(tile.getFluidType()), false, this.tile::getTankFullnesss));
        this.components.add(new ComponentProgressBar(this, this.guiLeft + 60, this.guiTop + 45, 80, 10, new Color(148,0,211), false, this.tile::getEnergyFullness));
    }

}
