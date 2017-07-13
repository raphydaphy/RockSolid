package com.raphydaphy.rocksolid.gui.inventory;

import com.raphydaphy.rocksolid.init.ModItems;

import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.inventory.IInvChangeCallback;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class JetpackInventory implements IInventory {
	AbstractEntityPlayer player;
    public JetpackInventory(AbstractEntityPlayer player)
    {
        this.player = player;
    }

    public int getSlotAmount() 
    {
        return 2;
    }



	@Override
	public void set(int id, ItemInstance instance) 
	{
		
		if (id == 0)
		{
			DataSet data;
			
            if (player.getAdditionalData() != null) 
            {
            	data = player.getAdditionalData();
            	
            	if (!data.getBoolean("hasJetpack"))
            	{
            		if (instance != null)
            		{
	            		if (instance.getItem().equals(ModItems.jetpack))
	                	{
	            			data.addBoolean("hasJetpack", true);
	                	}
            		}
            		
            	}
            	else
            	{
            		if (instance == null)
            		{
            			data.addBoolean("hasJetpack", false);
            		}
            	}
            	
            	player.setAdditionalData(data);
            	
            }
		}
		return;
	}



	@Override
	public ItemInstance add(int id, int amount) 
	{
		return null;
	}



	@Override
	public ItemInstance remove(int id, int amount) 
	{
		if (id == 0)
		{
			DataSet data;
			
            if (player.getAdditionalData() != null) 
            {
            	data = player.getAdditionalData();
            	
            	if (data.getBoolean("hasJetpack"))
            	{
            		data.addBoolean("hasJetpack", false);
            	}
            	player.setAdditionalData(data);
            }
		}
		return null;
	}



	@Override
	public ItemInstance get(int id) 
	{
		if (id == 0)
		{
			DataSet data;
			
            if (player.getAdditionalData() != null) 
            {
            	data = player.getAdditionalData();
            	
            	if (data.getBoolean("hasJetpack"))
            	{
            		return new ItemInstance(ModItems.jetpack);
            	}
            }
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