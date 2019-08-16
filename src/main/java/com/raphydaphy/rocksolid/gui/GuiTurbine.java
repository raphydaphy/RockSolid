package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.gas.Gas;
import com.raphydaphy.rocksolid.gui.component.ComponentEnergyBar;
import com.raphydaphy.rocksolid.gui.component.ComponentGasBar;
import com.raphydaphy.rocksolid.tileentity.TileEntityTurbine;
import com.raphydaphy.rocksolid.util.ModUtils;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.awt.*;

public class GuiTurbine extends GuiContainer {
    private final TileEntityTurbine te;

    public GuiTurbine(AbstractEntityPlayer player, TileEntityTurbine te) {
        super(player, 136, 112);
        this.te = te;
    }

    @Override
    public void init(IGameInstance game) {
        super.init(game);

        this.components.add(new ComponentEnergyBar(this, 27, 16, 81, 10, ModUtils.ENERGY, false, this.te::getEnergyFullness, this.te::getEnergyStored, this.te::getMaxTransfer));

        this.components.add(new ComponentGasBar(this, 27, 1, 81, 10, Color.gray.getRGB(), false, this.te::getSteamFullness, this.te::getSteamVolume, () -> Gas.STEAM));
    }

    @Override
    public ResourceName getName() {
        return RockSolid.createRes("gui_turbine");
    }

}
