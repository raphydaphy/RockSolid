package com.raphydaphy.rocksolid.gui.slot;

import java.util.function.Predicate;

import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ContainerSlot;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class PlayerInvSlot extends ContainerSlot 
{
	AbstractEntityPlayer player;
	String slotName;
	boolean isUnlocked;
	private final Predicate<ItemInstance> allowedInput;
	
	public PlayerInvSlot(AbstractEntityPlayer player, String slotName, final Predicate<ItemInstance> allowedInput, int x, int y)
	{
		super(player.getInv(), 0, x, y);
		this.player = player;
		this.slotName = slotName;
		if (allowedInput == null)
		{
			isUnlocked = true;
		}
		this.allowedInput = allowedInput;
	}

    @Override
    public boolean canPlace(ItemInstance instance)
    {
    	if (isUnlocked)
    	{
    		return true;
    	}
        return this.allowedInput.test(instance);
    }
    @Override
    public boolean canRemove()
    {
        return true;
    }
    @Override
    public void set(ItemInstance instance)
    {
    	
    	DataSet data;
		if (player != null)
		{
	        if (player.getAdditionalData() != null) 
	        {
	        	data = player.getAdditionalData();
	        	if (instance != null)
	        	{
		        	
		        	DataSet jetpackData = new DataSet();
		        	instance.save(jetpackData);
		        	data.addDataSet(slotName, jetpackData);
		        	player.setAdditionalData(data);
		        }
	        	else
	        	{
	        		data.addDataSet(slotName, new DataSet());
	        		player.setAdditionalData(data);
	        	}
			}
    	}
        
    }
    @Override
    public ItemInstance get()
    {
    	DataSet data;
		if (player != null)
		{
	        if (player.getAdditionalData() != null) 
	        {
	        	data = player.getAdditionalData();
	        	if (data.getDataSet(slotName) != null)
	        	{
	        		if (data.getDataSet(slotName).getString("item_name") != null)
	        		{
	            		if (data.getDataSet(slotName).getString("item_name").equals("") == false)
	            		{
	            			return ItemInstance.load(data.getDataSet(slotName));
	            		}
	        		}
	        	}
	        }
		}
        return null;
    }
    
}