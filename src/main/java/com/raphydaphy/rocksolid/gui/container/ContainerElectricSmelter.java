package com.raphydaphy.rocksolid.gui.container;

import com.raphydaphy.rocksolid.gui.slot.InputSlot;
import com.raphydaphy.rocksolid.gui.slot.OutputSlot;
import com.raphydaphy.rocksolid.tileentity.TileEntityElectricSmelter;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.IInventory;

public class ContainerElectricSmelter extends ItemContainer
{
    public ContainerElectricSmelter(final AbstractEntityPlayer player, final TileEntityElectricSmelter tile) {
        super(player, new IInventory[] { player.getInv(), tile.inventory });
        this.addSlot(new InputSlot(tile.inventory, 0, 50, 10, instance -> RockBottomAPI.getSmelterRecipe(instance) != null));
        this.addSlot(new OutputSlot(tile.inventory, 1, 130, 10));
        this.addPlayerInventory(player, 20, 60);
    }
}