package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tileentity.TileEntityBlastFurnace;
import com.raphydaphy.rocksolid.util.ModUtils;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class GuiBlastFurnace extends GuiContainer {
    private final TileEntityBlastFurnace te;

    public GuiBlastFurnace(AbstractEntityPlayer player, TileEntityBlastFurnace te) {
        super(player, 136, 100);
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

        this.components.add(new ComponentProgressBar(this, 49, 4, 37, 8, ModUtils.PROGRESS, false, GuiBlastFurnace.this.te::getBlastPercentage));
    }

    @Override
    public ResourceName getName() {
        return RockSolid.createRes("gui_blast_furnace");
    }

}
