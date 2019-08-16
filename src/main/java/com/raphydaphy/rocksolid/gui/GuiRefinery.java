package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.gui.component.ComponentEnergyBar;
import com.raphydaphy.rocksolid.gui.component.ComponentLiquidBar;
import com.raphydaphy.rocksolid.init.ModTiles;
import com.raphydaphy.rocksolid.tileentity.TileEntityRefinery;
import com.raphydaphy.rocksolid.util.ModUtils;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.awt.*;

public class GuiRefinery extends GuiContainer {
    private final TileEntityRefinery te;

    public GuiRefinery(AbstractEntityPlayer player, TileEntityRefinery te) {
        super(player, 134, 132);
        this.te = te;
    }

    @Override
    public void init(IGameInstance game) {
        super.init(game);

        this.components.add(new ComponentLiquidBar(this, 27, 1, 81, 10, Color.yellow.getRGB(), false, this.te::getFuelFullness, this.te::getFuelVolume, () -> ModTiles.FUEL));

        this.components.add(new ComponentLiquidBar(this, 27, 16, 81, 10, Color.black.getRGB(), false, this.te::getOilFullness, this.te::getOilVolume, () -> ModTiles.OIL));

        this.components.add(new ComponentEnergyBar(this, 27, 36, 81, 10, ModUtils.ENERGY, false, this.te::getEnergyFullness, this.te::getEnergyStored, this.te::getMaxTransfer));
    }

    @Override
    public ResourceName getName() {
        return RockSolid.createRes("gui_refinery");
    }

}
