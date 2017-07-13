package com.raphydaphy.rocksolid.gui.container;

import com.raphydaphy.rocksolid.gui.slot.InputSlot;
import com.raphydaphy.rocksolid.tileentity.TileEntityCoalGenerator;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.IInventory;

public class ContainerCoalGenerator extends ItemContainer
{
	public ContainerCoalGenerator(final AbstractEntityPlayer player, final TileEntityCoalGenerator tile) 
	{
        super(player, new IInventory[] { player.getInv(), tile.inventory });
        this.addSlot(new InputSlot(tile.inventory, 0, 90, 30, instance -> RockBottomAPI.getFuelValue(instance) > 0));
        this.addPlayerInventory(player, 20, 60);
    }
}
