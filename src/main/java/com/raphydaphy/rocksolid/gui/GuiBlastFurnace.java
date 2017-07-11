package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.tileentity.TileEntityBlastFurnace;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;

public class GuiBlastFurnace extends GuiContainer
{

    private final TileEntityBlastFurnace tile;
    
    public GuiBlastFurnace(final AbstractEntityPlayer player, final TileEntityBlastFurnace tile) {
        super(player, 198, 150);
        this.tile = tile;
    }
    
    @Override
    public void initGui(final IGameInstance game) {
        super.initGui(game);
        this.components.add(new ComponentProgressBar(this, this.guiLeft + 80, this.guiTop + 15, 40, 8, GuiBlastFurnace.PROGRESS_COLOR, false, this.tile::getSmeltPercentage));
    }

}
