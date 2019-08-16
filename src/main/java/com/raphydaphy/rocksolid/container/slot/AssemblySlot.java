package com.raphydaphy.rocksolid.container.slot;

import com.raphydaphy.rocksolid.gui.component.ComponentAssemblySlot;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentSlot;
import de.ellpeck.rockbottom.api.gui.container.RestrictedInputSlot;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.IFilteredInventory;

public class AssemblySlot extends RestrictedInputSlot {
    private IUseInfo metal;

    public AssemblySlot(IFilteredInventory inventory, int slot, int x, int y) {
        super(inventory, slot, x, y);
    }

    @Override
    public ComponentSlot getGraphicalSlot(GuiContainer gui, int index, int xOffset, int yOffset) {
        return new ComponentAssemblySlot(gui, this, index, xOffset + this.x, yOffset + this.y);
    }

    public void setMetal(IUseInfo metal) {
        this.metal = metal;
    }

    @Override
    public boolean canPlace(ItemInstance instance) {
        if (this.slot == 1 && metal != null) {
            return metal.containsItem(instance);
        }
        return super.canPlace(instance);
    }
}
