package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class GuiInventorySpecial extends GuiContainer
{

    
	public GuiInventorySpecial(final AbstractEntityPlayer player) {
	    super(player, 198, 150);
	}
	
	@Override
	public void initGui(final IGameInstance game) {
	    super.initGui(game);
	}
	
	@Override
    public void onClosed(IGameInstance game){
        super.onClosed(game);
        
        AbstractEntityPlayer player = game.getPlayer();
		DataSet data;
		
		if (player != null)
		{
	        if (player.getAdditionalData() != null) 	
	        {
	        	data = player.getAdditionalData();
	        }
	        else
	        {
	        	data = new DataSet();
	        	player.setAdditionalData(data);
	        }
	        data.addBoolean("jetpackInventoryOpen", false);
	        
	        player.setAdditionalData(data);
		}
    }
	
	@Override
    public void onOpened(IGameInstance game){
		super.onOpened(game);
		
		AbstractEntityPlayer player = game.getPlayer();
		DataSet data;
		
		if (player != null)
		{
	        if (player.getAdditionalData() != null) 	
	        {
	        	data = player.getAdditionalData();
	        }
	        else
	        {
	        	data = new DataSet();
	        	player.setAdditionalData(data);
	        }
	        data.addBoolean("jetpackInventoryOpen", true);
	        
	        player.setAdditionalData(data);
		}
	}
	
	@Override
	public IResourceName getName() 
	{
		return RockSolidLib.makeRes("guiInventorySpecial");
	}
	
}
