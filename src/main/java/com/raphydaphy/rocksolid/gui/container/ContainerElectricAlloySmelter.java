package com.raphydaphy.rocksolid.gui.container;

import com.raphydaphy.rocksolid.tileentity.TileEntityElectricAlloySmelter;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.IInventory;

public class ContainerElectricAlloySmelter extends ItemContainer
{
	public ContainerElectricAlloySmelter(final AbstractEntityPlayer player, final TileEntityElectricAlloySmelter tile) 
	{
        super(player, new IInventory[] { player.getInv(), tile.inventory });
        this.addSlot(new InputSlot(tile.inventory, 0, 25, 10));
        this.addSlot(new InputSlot(tile.inventory, 1, 55, 10));
        this.addSlot(new OutputSlot(tile.inventory, 3, 140, 10));
        this.addPlayerInventory(player, 20, 60);
        		
        		
    }


}
