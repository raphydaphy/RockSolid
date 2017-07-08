package com.raphydaphy.rocksolid.gui.inventory;

import java.util.List;

public interface IHasInventory 
{
	ContainerInventory getInventory();
	List<Integer> getInputs();
	List<Integer> getOutputs();
}
