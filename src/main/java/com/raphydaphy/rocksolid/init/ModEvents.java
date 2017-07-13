package com.raphydaphy.rocksolid.init;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Input;

import com.raphydaphy.rocksolid.gui.GuiInventorySpecial;
import com.raphydaphy.rocksolid.gui.container.ContainerInventorySpecial;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.font.Font;
import de.ellpeck.rockbottom.api.assets.font.FormattingCode;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.event.EventResult;
import de.ellpeck.rockbottom.api.event.IEventHandler;
import de.ellpeck.rockbottom.api.event.impl.ContainerOpenEvent;
import de.ellpeck.rockbottom.api.event.impl.EntityTickEvent;
import de.ellpeck.rockbottom.api.event.impl.WorldRenderEvent;

public class ModEvents {
	public static void init(IEventHandler e)
	{
		e.registerListener(EntityTickEvent.class, (result, event) -> {
			if (event.entity instanceof AbstractEntityPlayer)
			{
				boolean engineActive = false;
				boolean hoverActive = false;
				int jetpackEnergy = 0;
				
				AbstractEntityPlayer player = (AbstractEntityPlayer)event.entity;
				Input input = RockBottomAPI.getGame().getInput();
				DataSet data;
				
                if (player.getAdditionalData() != null) 	
                {
                	data = player.getAdditionalData();
                	engineActive = data.getBoolean("engineActive");
                	hoverActive = data.getBoolean("hoverActive");
                	jetpackEnergy = data.getInt("jetpackEnergy");
                }
                else
                {
                	data = new DataSet();
                	player.setAdditionalData(data);
                }
				
				if (input.isKeyPressed(Keyboard.KEY_F))
                {
					if (data.getBoolean("jetpackInventoryOpen") == false)
					{
						player.openGuiContainer(new GuiInventorySpecial(player), new ContainerInventorySpecial(player));
						data.addBoolean("jetpackInventoryOpen", true);
					}
                
                }
				
				if (input.isKeyPressed(Keyboard.KEY_E) || input.isKeyPressed(Keyboard.KEY_ESCAPE))
                {
					data.addBoolean("jetpackInventoryOpen", false);
                	
                }
				
				if (data.getBoolean("hasJetpack"))
				{
					if (input.isKeyDown(Keyboard.KEY_SPACE))
					{
						if (engineActive && jetpackEnergy > 3)
						{
							data.addInt("jetpackEnergy", jetpackEnergy - 3);
							player.motionY += 0.05;
							player.fallAmount = 0;
						}
					}
					
					if (hoverActive && engineActive && jetpackEnergy > 4)
					{
						if (!input.isKeyDown(Keyboard.KEY_SPACE))
						{
							if (player.motionY < 0)
							{
								player.motionY = -0.0005f;
								data.addInt("jetpackEnergy", jetpackEnergy - 4);
							}
						}
						player.fallAmount = 0;
					}
					
					
					if (engineActive)
					{
		                if (input.isKeyPressed(Keyboard.KEY_R))
		                {
		                	data.addBoolean("hoverActive", !hoverActive);
		                }
					}
	                if (input.isKeyPressed(Keyboard.KEY_Q))
	                {
	                	data.addBoolean("engineActive", !engineActive);
	                }
					player.setAdditionalData(data);
				}
				
			}
			return EventResult.DEFAULT;
		});
		
		e.registerListener(WorldRenderEvent.class, (result, event) -> {
            if (event.world != null && RockBottomAPI.getNet().isThePlayer(event.player)) {
                DataSet data = event.player.getAdditionalData();
                if (data != null && data.getBoolean("hasJetpack") && !data.getBoolean("is_creative")) 
                {
                	int jetpackEnergy = data.getInt("jetpackEnergy");
					if (jetpackEnergy > 0)
					{
						jetpackEnergy = jetpackEnergy / 10000;
					}
                	String jetpackEnergyString = FormattingCode.RED.toString() + "Depleted";
                	if (jetpackEnergy >= 70)
                	{
                		jetpackEnergyString = FormattingCode.GREEN.toString() +  jetpackEnergy + " Percent";
                	}
                	else if (jetpackEnergy >=20)
                	{
                		jetpackEnergyString = FormattingCode.YELLOW.toString() +  jetpackEnergy + " Percent";
                	}
                	else if (jetpackEnergy > 0)
                	{
                		jetpackEnergyString = FormattingCode.RED.toString() +  jetpackEnergy + " Percent";
                	}
                    Font font = event.assetManager.getFont();
                    
                    font.drawString(0.2F, 0.2F, FormattingCode.WHITE.toString() + "Jetpack fuel: " +  jetpackEnergyString, 0.0175F);
                    
                    String engine = FormattingCode.RED.toString() + "Engine";
                    String hover = FormattingCode.RED.toString() + "Hover";
                    
                    
                    if (data.getBoolean("engineActive"))
                    {
                    	engine = FormattingCode.GREEN.toString() + "Engine";
                    	
                    	if (data.getBoolean("hoverActive"))	
                    	{
                        	hover = FormattingCode.GREEN.toString() + "Hover";
                    	}
                    }
                    
                    	
            		font.drawString(0.2F, 0.7F, String.format(engine + FormattingCode.WHITE.toString() + " - " + hover), 0.0175F);
                }
            }
            return EventResult.DEFAULT;
        });
		
		e.registerListener(ContainerOpenEvent.class, (result, event) -> {
            AbstractEntityPlayer player = RockBottomAPI.getGame().getPlayer();
            if (player != null)
            {
				DataSet data = player.getAdditionalData();
	            if (data != null)
	            {
					data.addBoolean("jetpackInventoryOpen", true);
	            	//data.addLong("worldTimeLastOpen", RockBottomAPI.getGame().getWorld().getWorldInfo().totalTimeInWorld);
	            	player.setAdditionalData(data);
	            }
			}
			 
            return EventResult.DEFAULT;
		});
       	 
	}
}
