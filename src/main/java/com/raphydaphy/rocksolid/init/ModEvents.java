package com.raphydaphy.rocksolid.init;

import java.util.Random;

import com.raphydaphy.rocksolid.gui.slot.PlayerInvSlot;
import com.raphydaphy.rocksolid.item.ItemElectricLantern;
import com.raphydaphy.rocksolid.item.ItemLantern;
import com.raphydaphy.rocksolid.network.PacketItemUpdate;
import com.raphydaphy.rocksolid.network.PacketMovement;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.font.Font;
import de.ellpeck.rockbottom.api.assets.font.FormattingCode;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.event.EventResult;
import de.ellpeck.rockbottom.api.event.IEventHandler;
import de.ellpeck.rockbottom.api.event.impl.ContainerOpenEvent;
import de.ellpeck.rockbottom.api.event.impl.EntityTickEvent;
import de.ellpeck.rockbottom.api.event.impl.WorldRenderEvent;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.Util;

public class ModEvents {
	public static void init(IEventHandler e)
	{
		e.registerListener(EntityTickEvent.class, (result, event) -> {
			
			
			if (event.entity instanceof AbstractEntityPlayer)
			{
				boolean engineActive = false;
				boolean hoverActive = false;
				int jetpackEnergy = 0;
				DataSet data;
				DataSet jetpackData = null;
				ItemInstance jetpack = null;
				
				int lanternEnergy = 0;
				DataSet lanternData = null;
				ItemInstance lantern = null;
				
				
				
				
				AbstractEntityPlayer player = (AbstractEntityPlayer)event.entity;
				
				if (RockBottomAPI.getNet().isServer() && RockBottomAPI.getGame().isDedicatedServer())
				{
					return EventResult.DEFAULT;
				}
				
				Pos2 pos = new Pos2(Util.floor(player.x), Util.floor(player.y));
				Pos2 prevPos = null;
				
                if (player.getAdditionalData() != null) 	
                {
                	data = player.getAdditionalData();
                	prevPos = new Pos2(data.getInt("prevX"), data.getInt("prevY"));
                	data.addInt("prevX",Util.floor(pos.getX()));
                	data.addInt("prevY",Util.floor(pos.getY()));
                	engineActive = data.getBoolean("engineActive");
                	hoverActive = data.getBoolean("hoverActive");
                	if (data.getDataSet("jetpackData") != null)
                	{
                		if (data.getDataSet("jetpackData").isEmpty() == false)
                		{
		                	jetpack = ItemInstance.load(data.getDataSet("jetpackData"));
		                	if (jetpack != null)
		                	{
		                		jetpackData = jetpack.getAdditionalData();
			                	if (jetpackData != null)
			                	{
			                		jetpackEnergy = jetpackData.getInt("itemPowerStored");
			                	}
		                	}
                		}
                	}
                	if (data.getDataSet("lanternData") != null)
                	{
                		
                		if (data.getDataSet("lanternData").getString("item_name") != null)
                		{
	                		if (data.getDataSet("lanternData").getString("item_name").equals("") == false)
	                		{
			                	lantern = ItemInstance.load(data.getDataSet("lanternData"));
			                	
			                	if (lantern != null)
			                	{
			                		lanternData = lantern.getAdditionalData();
				                	if (lanternData != null)
				                	{
				                		if (lantern.getItem() instanceof ItemLantern)
				                		{
				                			if (lanternData.getInt("itemFuel") == 0)
				                			{
					                			if (data.getDataSet("accessory3") != null)
					                        	{
					                        		if (data.getDataSet("accessory3").getString("item_name") != null)
					                        		{
					        	                		if (data.getDataSet("accessory3").getString("item_name").equals("") == false)
					        	                		{
					        	                			ItemInstance accessory3 = ItemInstance.load(data.getDataSet("accessory3"));
															if (accessory3 != null)
															{
																if (accessory3.getAmount() > 0)
																{
																	accessory3.setAmount(accessory3.getAmount() - 1);
																	lanternData.addInt("itemFuel", 1000);
																	lanternEnergy = 1000;
																	if (accessory3.getAmount() == 0)
																	{
																		data.addDataSet("accessory3", new DataSet());
																		
																		RockBottomAPI.getNet().sendToServer(new PacketItemUpdate(player.getUniqueId(), "accessory3", new DataSet()));
																	}
																	else
																	{
																		DataSet accessory3Data = new DataSet();
																		accessory3.save(accessory3Data);
																		
																		data.addDataSet("accessory3", accessory3Data);
																		
																		RockBottomAPI.getNet().sendToServer(new PacketItemUpdate(player.getUniqueId(), "accessory3", accessory3Data));
																	}
																}
															}
					        	                		}
					                        		}
					                        	}
				                			}
				                			else
				                			{
				                				lanternEnergy = lanternData.getInt("itemFuel");
				                			}
				                		}
				                		else if (lantern.getItem() instanceof ItemElectricLantern)
				                		{
				                			lanternEnergy = lanternData.getInt("itemPowerStored");
				                		}
				                	}
			                	}
	                		}
                		}
                	}
                }
                else
                {
                	data = new DataSet();
                	data.addDataSet("jetpackData", new DataSet());
                	data.addDataSet("lanternData", new DataSet());
                	data.addDataSet("accessory3Data", new DataSet());
                	player.setAdditionalData(data);
                }
				
				if (jetpack != null)
				{
					if (Settings.KEY_JUMP.isDown() && RockBottomAPI.getGame().getGuiManager().getGui() == null)
					{
						if (engineActive && jetpackEnergy > 3)
						{
							Random random = new Random();
							for (int i = 0; i < (int)(random.nextFloat() * 10); i++)
							{
								RockBottomAPI.getGame().getParticleManager().addSmokeParticle(RockBottomAPI.getGame().getWorld(), player.x- 0.5 + random.nextFloat(), player.y - 0.3,0, -0, RockBottomAPI.getGame().getWorldScale() * 0.0005f);
							}
							
							jetpackData.addInt("itemPowerStored", jetpackEnergy - 3);
							player.motionY += 0.05;
							player.fallAmount = 0;
							
							RockBottomAPI.getNet().sendToServer(new PacketMovement(player.getUniqueId(), player.motionY, player.fallAmount, player.x, player.y));
						}
					}
					
					if (hoverActive && engineActive && jetpackEnergy > 4)
					{
						if (!Settings.KEY_JUMP.isDown() || RockBottomAPI.getGame().getGuiManager().getGui() != null)
						{
							if (player.motionY < 0)
							{
								player.motionY = -0.0005f;
								Random random = new Random();
								RockBottomAPI.getGame().getParticleManager().addSmokeParticle(RockBottomAPI.getGame().getWorld(), player.x- 0.5 + random.nextFloat(), player.y - 0.3, 0, 0, RockBottomAPI.getGame().getWorldScale() * 0.0005f);
								jetpackData.addInt("itemPowerStored", jetpackEnergy - 4);
							}
						}
						player.fallAmount = 0;
						
						RockBottomAPI.getNet().sendToServer(new PacketMovement(player.getUniqueId(), player.motionY, player.fallAmount, player.x, player.y));
					}
					
					
					if (engineActive)
					{
						
		                if (ModKeybinds.keyJetpackHover.isPressed() && RockBottomAPI.getGame().getGuiManager().getGui() == null)
		                {
		                	data.addBoolean("hoverActive", !hoverActive);
		                }
					}
	                if (ModKeybinds.keyJetpackEngine.isPressed() && RockBottomAPI.getGame().getGuiManager().getGui() == null)
	                {
	                	data.addBoolean("engineActive", !engineActive);
	                }
	                
	                if (jetpack != null)
	                {
		                jetpack.setAdditionalData(jetpackData);
		                DataSet jetpackSlotData = new DataSet();
		                jetpack.save(jetpackSlotData);
		                data.addDataSet("jetpackData", jetpackSlotData);
		                
		                RockBottomAPI.getNet().sendToServer(new PacketItemUpdate(player.getUniqueId(), "jetpackData", jetpackSlotData));
	                }
	                
					player.setAdditionalData(data);
					
				}
				
				if (lantern != null)
				{
					if (!prevPos.equals(pos))
			        {
			        	RockBottomAPI.getGame().getWorld().causeLightUpdate(Util.floor(player.x), Util.floor(player.y) );
			        }
					if (lanternEnergy > 0)
					{
				        for (int x = -5; x < 6; x++)
				        {
				        	for (int y = -5; y < 6; y++)
				        	{
				        		if (y == -5 || y == 5)
				        		{
				        			if (x > 2 || x < -2)
				        			{
				        				continue;
				        			}
				        		}
				        		
				        		if (y == -4 || y == 4)
				        		{
				        			if (x > 3 || x < -3)
				        			{
				        				continue;
				        			}
				        		}
				        		
				        		if (y == -3 || y == 3)
				        		{
				        			if (x > 4 || x < -4)
				        			{
				        				continue;
				        			}
				        		}
				        		RockBottomAPI.getGame().getWorld().setArtificialLight(Util.floor(player.x) + x, Util.floor(player.y) + y, (byte)30);
				        	}
				        }
				        
				        if (lantern.getItem() instanceof ItemLantern)
				        {
				        	lanternData.addInt("itemFuel", lanternEnergy - 1);
				        }
				        else if (lantern.getItem() instanceof ItemElectricLantern)
				        {
				        	lanternData.addInt("itemPowerStored", lanternEnergy - 1);
				        }
					}
					
					
	                lantern.setAdditionalData(lanternData);
	                DataSet lanternSlotData = new DataSet();
	                lantern.save(lanternSlotData);
	                data.addDataSet("lanternData", lanternSlotData);
	                
	                RockBottomAPI.getNet().sendToServer(new PacketItemUpdate(player.getUniqueId(), "lanternData", lanternSlotData));
				}
				
			}
			return EventResult.DEFAULT;
		});
		
		e.registerListener(WorldRenderEvent.class, (result, event) -> {
            if (event.world != null && RockBottomAPI.getNet().isThePlayer(event.player)) {
                DataSet data = event.player.getAdditionalData();
                ItemInstance jetpack = null;
                if (data != null && !data.getBoolean("is_creative")) 
                {
                	int jetpackEnergy = 0;
                	if (data.getDataSet("jetpackData") != null)
                	{
                		if (data.getDataSet("jetpackData").getString("item_name") != null)
                		{
	                		if (data.getDataSet("jetpackData").getString("item_name").equals("") == false)
	                		{
			                	jetpack = ItemInstance.load(data.getDataSet("jetpackData"));
			                	if (jetpack != null)
			                	{
			                		DataSet jetpackData = jetpack.getAdditionalData();
				                	if (jetpackData != null)
				                	{
				                		jetpackEnergy = jetpackData.getInt("itemPowerStored");
				                	}
			                	}
	                		}
                		}
                	}
                	if (jetpack != null)
                	{
						if (jetpackEnergy > 0)
						{
							jetpackEnergy = jetpackEnergy / 1000;
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
            }
            return EventResult.DEFAULT;
        });
		
		e.registerListener(ContainerOpenEvent.class, (result, event) -> {
            
            System.out.println("opened a container!");
            if (event.container != null)
            {
            	System.out.println(event.container.getClass().getName());
            }
        	if (event.container != null && event.container.getClass().getName().equals("de.ellpeck.rockbottom.z"))
        	{
        		AbstractEntityPlayer player = event.player;
        		if (player != null)
                {
            		if (player.getAdditionalData() != null)
            		{
            			if (!player.getAdditionalData().getBoolean("is_creative"))
    					{
         	   				event.container.addSlot(new PlayerInvSlot(player, "jetpackData",instance -> instance.getItem().equals(ModItems.jetpack), 165, 25));
            				event.container.addSlot(new PlayerInvSlot(player, "lanternData", instance -> (instance.getItem().equals(ModItems.electricLantern) || instance.getItem().equals(ModItems.lantern)), 165, 45));
            				event.container.addSlot(new PlayerInvSlot(player, "accessory3", instance -> instance.getItem().equals(GameContent.ITEM_GLOW_CLUSTER), 165, 65));
            				return EventResult.MODIFIED;
    					}
            		}
            		else
            		{
            			DataSet data = new DataSet();
                    	data.addDataSet("jetpackData", new DataSet());
                    	data.addDataSet("lanternData", new DataSet());
                    	data.addDataSet("accessory3Data", new DataSet());
                    	player.setAdditionalData(data);
            		}
            	}
			}
			 
            return EventResult.DEFAULT;
		});
       	 
		
            
	}
}
