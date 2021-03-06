package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tileentity.TileEntitySeparator;
import com.raphydaphy.rocksolid.util.ModUtils;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class GuiSeparator extends GuiContainer {
    private final TileEntitySeparator te;

    public GuiSeparator(AbstractEntityPlayer player, TileEntitySeparator te) {
        super(player, 136, 120);
        this.te = te;

        int playerSlots = player.getInv().getSlotAmount();

        ShiftClickBehavior input = new ShiftClickBehavior(0, playerSlots - 1, playerSlots, playerSlots + 1);
        shiftClickBehaviors.add(input);
        shiftClickBehaviors.add(input.reversed());

        ShiftClickBehavior output = new ShiftClickBehavior(0, playerSlots - 1, playerSlots + 2, playerSlots + 3);
        this.shiftClickBehaviors.add(output.reversed());
    }

    @Override
    public void init(IGameInstance game) {
        super.init(game);

        this.components.add(new ComponentProgressBar(this, 40, 4, 37, 8, ModUtils.PROGRESS, false, GuiSeparator.this.te::getSmeltPercentage));

        this.components.add(new ComponentProgressBar(this, 39, 20, 8, 16, ModUtils.COAL, true, GuiSeparator.this.te::getFuelPercentage));
    }

    @Override
    public ResourceName getName() {
        return RockSolid.createRes("gui_separator");
    }

}