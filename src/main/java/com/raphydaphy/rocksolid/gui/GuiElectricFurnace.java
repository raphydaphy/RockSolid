package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.gui.component.ComponentEnergyBar;
import com.raphydaphy.rocksolid.tileentity.base.TileEntityElectric;
import com.raphydaphy.rocksolid.util.ModUtils;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class GuiElectricFurnace extends GuiContainer {
    private final TileEntityElectric te;

    public GuiElectricFurnace(AbstractEntityPlayer player, TileEntityElectric te) {
        super(player, 136, 113);
        this.te = te;

        int playerSlots = player.getInv().getSlotAmount();

        ShiftClickBehavior input = new ShiftClickBehavior(0, playerSlots - 1, playerSlots, playerSlots);
        shiftClickBehaviors.add(input);
        shiftClickBehaviors.add(input.reversed());

        ShiftClickBehavior output = new ShiftClickBehavior(0, playerSlots - 1, playerSlots + 1, playerSlots + 1);
        this.shiftClickBehaviors.add(output.reversed());
    }

    @Override
    public void init(IGameInstance game) {
        super.init(game);

        this.components.add(new ComponentProgressBar(this, 49, 4, 37, 8, ModUtils.PROGRESS, false, this.te::getSmeltPercentage));

        this.components.add(new ComponentEnergyBar(this, 27, 20, 81, 10, ModUtils.ENERGY, false, this.te::getEnergyFullness, this.te::getEnergyStored, this.te::getMaxTransfer));
    }

    @Override
    public ResourceName getName() {
        return RockSolid.createRes("gui_electric_furnace");
    }

}