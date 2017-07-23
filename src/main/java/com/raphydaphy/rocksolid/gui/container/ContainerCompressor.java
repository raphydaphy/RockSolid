package com.raphydaphy.rocksolid.gui.container;

import com.raphydaphy.rocksolid.api.RockSolidAPI;
import com.raphydaphy.rocksolid.gui.slot.InputSlot;
import com.raphydaphy.rocksolid.gui.slot.OutputSlot;
import com.raphydaphy.rocksolid.tileentity.TileEntityCompressor;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.IInventory;

public class ContainerCompressor extends ItemContainer
{
	public ContainerCompressor(final AbstractEntityPlayer player, final TileEntityCompressor tile) 
	{
        super(player, new IInventory[] { player.getInv(), tile.inventory });
        this.addSlot(new InputSlot(tile.inventory, 0, 40, 10, instance -> RockSolidAPI.getCompressorRecipe(instance) != null));
        this.addSlot(new OutputSlot(tile.inventory, 1, 140, 10));
        this.addPlayerInventory(player, 20, 60);
    }
}
