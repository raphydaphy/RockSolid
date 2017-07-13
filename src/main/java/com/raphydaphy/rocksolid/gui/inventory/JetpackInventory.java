package com.raphydaphy.rocksolid.gui.inventory;

import com.raphydaphy.rocksolid.init.ModItems;

import de.ellpeck.rockbottom.api.inventory.IInvChangeCallback;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class JetpackInventory implements IInventory {

    public JetpackInventory()
    {
        
    }

    public int getSlotAmount() 
    {
        return 4;
    }



	@Override
	public void set(int id, ItemInstance instance) 
	{
		
	}



	@Override
	public ItemInstance add(int id, int amount) 
	{
		return null;
	}



	@Override
	public ItemInstance remove(int id, int amount) 
	{
		return null;
	}



	@Override
	public ItemInstance get(int id) 
	{
		if (id == 0)
		{
			return new ItemInstance(ModItems.jetBooster);
		}
		return null;
	}



	@Override
	public void notifyChange(int slot) 
	{
		
	}



	@Override
	public void addChangeCallback(IInvChangeCallback callback) 
	{
		
	}



	@Override
	public void removeChangeCallback(IInvChangeCallback callback) 
	{
		
	}
}