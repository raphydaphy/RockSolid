package com.raphydaphy.rocksolid.gui.container;

import com.raphydaphy.rocksolid.tileentity.TileEntityChest;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class ContainerChest extends ItemContainer
{
	private final TileEntityChest tile;
	
	public ContainerChest(AbstractEntityPlayer player, TileEntityChest tile)
	{
	  super(player, new IInventory[] { player.getInv(), tile.inventory });
	  this.tile = tile;
	  
	  addSlotGrid(tile.inventory, 0, tile.inventory.getSlotAmount(), 0, 0, 10);
	  addPlayerInventory(player, 20, 60);
	}
	
	public void onOpened()
	{
	  this.tile.openCount += 1;
	}
	
	public void onClosed()
	{
	  this.tile.openCount -= 1;
	}
	
	@Override
	public IResourceName getName() 
	{
		return RockSolidLib.makeRes("containerChest");
	}
}