package com.raphydaphy.rocksolid.api;

import java.util.List;

import de.ellpeck.rockbottom.api.inventory.Inventory;

public interface IHasInventory 
{
	Inventory getInventory();
	List<Integer> getInputs();
	List<Integer> getOutputs();
}
