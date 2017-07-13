package com.raphydaphy.rocksolid.gui.inventory;

import com.raphydaphy.rocksolid.init.ModItems;

import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ContainerSlot;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class JetpackSlot extends ContainerSlot 
{
	AbstractEntityPlayer player;
	
	public JetpackSlot(AbstractEntityPlayer player, int x, int y)
	{
		super(null, 0, x, y);
	}

    @Override
    public boolean canPlace(ItemInstance instance)
    {
        return instance.getItem().equals(ModItems.jetpack);
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
		
        if (player.getAdditionalData() != null) 
        {
        	data = player.getAdditionalData();
        	DataSet jetpackData = new DataSet();
        	instance.save(jetpackData);
        	data.addDataSet("jetpackData", jetpackData);
        	player.setAdditionalData(data);
        }
        
    }
    @Override
    public ItemInstance get()
    {
    	DataSet data;
		
        if (player.getAdditionalData() != null) 
        {
        	data = player.getAdditionalData();
        	return ItemInstance.load(data.getDataSet("jetpackData"));
        }
        return null;
    }
    
}