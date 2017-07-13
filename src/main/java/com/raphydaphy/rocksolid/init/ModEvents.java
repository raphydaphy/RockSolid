package com.raphydaphy.rocksolid.init;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Input;

import com.raphydaphy.rocksolid.gui.GuiInventorySpecial;
import com.raphydaphy.rocksolid.gui.container.ContainerInventorySpecial;
import com.raphydaphy.rocksolid.item.ItemJetBooster;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.font.Font;
import de.ellpeck.rockbottom.api.assets.font.FormattingCode;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.event.EventResult;
import de.ellpeck.rockbottom.api.event.IEventHandler;
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
				
				AbstractEntityPlayer player = (AbstractEntityPlayer)event.entity;
				Input input = RockBottomAPI.getGame().getInput();
				DataSet data;
				
                if (player.getAdditionalData() != null) 
                {
                	data = player.getAdditionalData();
                	engineActive = data.getBoolean("engineActive");
                	hoverActive = data.getBoolean("hoverActive");
                }
                else
                {
                	data = new DataSet();
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
				
				if (player.getInv().get(player.getSelectedSlot()) != null)
				{
					
					if (player.getInv().get(player.getSelectedSlot()).getItem() instanceof ItemJetBooster)
					{
		                
						if (input.isKeyDown(Keyboard.KEY_SPACE))
						{
							if (engineActive)
							{
								player.motionY += 0.05;
								player.fallAmount = 0;
							}
						}
						
						if (hoverActive)
						{
							if (!input.isKeyDown(Keyboard.KEY_SPACE))
							{
								player.motionY = -0.0005f;
							}
							player.fallAmount = 0;
						}
						
						data.addBoolean("wearingJetpack", true);
		                if (input.isKeyPressed(Keyboard.KEY_Q))
		                {
		                	data.addBoolean("engineActive", !engineActive);
		                }
		                if (input.isKeyPressed(Keyboard.KEY_R))
		                {
		                	data.addBoolean("hoverActive", !hoverActive);
		                }
						player.setAdditionalData(data);
					}
				}
				
			}
			return EventResult.DEFAULT;
		});
		
		e.registerListener(WorldRenderEvent.class, (result, event) -> {
            if (event.world != null && RockBottomAPI.getNet().isThePlayer(event.player)) {
                DataSet data = event.player.getAdditionalData();
                if (data != null && data.getBoolean("wearingJetpack") && !data.getBoolean("is_creative")) {
                    Font font = event.assetManager.getFont();
                    font.drawString(0.2F, 0.2F, FormattingCode.WHITE.toString() + "Jetpack fuel:" + FormattingCode.GREEN.toString() +  " 100%", 0.0175F);
                    
                    String engine = FormattingCode.RED.toString() + "Engine";
                    String hover = FormattingCode.RED.toString() + "Hover";
                    
                    
                    if (data.getBoolean("engineActive"))
                    {
                    	engine = FormattingCode.GREEN.toString() + "Engine";
                    }
                    if (data.getBoolean("hoverActive"))	
                	{
                    	hover = FormattingCode.GREEN.toString() + "Hover";
                	}
                    	
            		font.drawString(0.2F, 0.7F, String.format(engine + FormattingCode.WHITE.toString() + " - " + hover), 0.0175F);
                }
            }
            return EventResult.DEFAULT;
        });
       	 
	}
}
