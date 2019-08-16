package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.gui.component.ComponentEnergyBar;
import com.raphydaphy.rocksolid.gui.component.ComponentLiquidBar;
import com.raphydaphy.rocksolid.tileentity.TileEntityPump;
import com.raphydaphy.rocksolid.util.ModUtils;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.awt.*;

public class GuiPump extends GuiContainer {
    private final TileEntityPump te;

    public GuiPump(AbstractEntityPlayer player, TileEntityPump te) {
        super(player, 136, 112);
        this.te = te;
    }

    @Override
    public void init(IGameInstance game) {
        super.init(game);

        this.components.add(new ComponentEnergyBar(this, 27, 16, 81, 10, ModUtils.ENERGY, false, this.te::getEnergyFullness, this.te::getEnergyStored, this.te::getMaxTransfer));

        this.components.add(new ComponentLiquidBar(this, 27, 1, 81, 10, Color.blue.getRGB(), false, this.te::getLiquidFullness, this.te::getLiquidVolume, this.te::getLiquidType));
    }

    @Override
    public ResourceName getName() {
        return RockSolid.createRes("gui_pump");
    }

}
