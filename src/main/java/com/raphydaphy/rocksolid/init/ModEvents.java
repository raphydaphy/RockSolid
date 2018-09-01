package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.entity.EntityRocket;
import com.raphydaphy.rocksolid.network.PacketLeaveRocket;
import com.raphydaphy.rocksolid.util.ModUtils;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.IRecipe;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.event.EventResult;
import de.ellpeck.rockbottom.api.event.IEventHandler;
import de.ellpeck.rockbottom.api.event.impl.*;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;
import org.lwjgl.glfw.GLFW;

import java.util.UUID;

public class ModEvents
{
	public static void init(IEventHandler handler)
	{
		handler.registerListener(KeyEvent.class, (result, event) ->
		{
			if (event.key == GLFW.GLFW_KEY_LEFT_SHIFT && event.action == 1)
			{
				AbstractEntityPlayer player = RockBottomAPI.getGame().getPlayer();
				if (player.hasAdditionalData() && player.getAdditionalData().getBoolean(EntityRocket.IN_ROCKET))
				{
					PacketLeaveRocket packet = new PacketLeaveRocket(player.getUniqueId());
					if (player.world.isClient())
					{
						RockBottomAPI.getNet().sendToServer(packet);
					} else
					{
						packet.handle(RockBottomAPI.getGame(), null);
					}
				}
			}
			return EventResult.DEFAULT;
		});
		handler.registerListener(EntityTickEvent.class, (result, event) ->
		{
			if (event.entity.world.getSubName() != null && event.entity.world.getSubName().equals(ModMisc.MOON_WORLD))
			{
				if (event.entity.motionY < 0)
				{
					event.entity.motionY /= 1.25;
				}
			}

			if (event.entity.hasAdditionalData() && event.entity.getAdditionalData().getBoolean(EntityRocket.IN_ROCKET))
			{
				event.entity.motionX = 0;
			}
			return EventResult.DEFAULT;
		});
		handler.registerListener(PlayerJoinWorldEvent.class, (result, event) ->
		{
			if (!event.isConnected)
			{
				event.player.getInv().addChangeCallback((inv, slot) ->
				{
					ItemInstance changed = inv.get(slot);

					if (changed != null)
					{
						if (changed.getItem() == ModItems.TIN_INGOT)
						{
							IRecipe alloySmelter = IRecipe.forName(RockSolid.createRes("alloy_smelter"));
							if (!event.player.getKnowledge().knowsRecipe(alloySmelter))
							{
								event.player.getKnowledge().teachRecipe(alloySmelter);
							}
						}
						else if (changed.getItem() == ModItems.IRON_INGOT)
						{
							IRecipe ironLamp = IRecipe.forName(RockSolid.createRes("lamp_iron"));
							if (!event.player.getKnowledge().knowsRecipe(ironLamp))
							{
								event.player.getKnowledge().teachRecipe(ironLamp);
								event.player.getKnowledge().teachRecipe(IRecipe.forName(RockSolid.createRes("bucket")));
								event.player.getKnowledge().teachRecipe(IRecipe.forName(RockSolid.createRes("blast_furnace")));
								event.player.getKnowledge().teachRecipe(IRecipe.forName(RockSolid.createRes("separator")));
							}
						}
						else if (changed.getItem() == ModItems.BRONZE_INGOT)
						{
							IRecipe recipe = IRecipe.forName((RockSolid.createRes("bronze_pickaxe")));
							if (!event.player.getKnowledge().knowsRecipe(recipe))
							{
								event.player.getKnowledge().teachRecipe(recipe);
								event.player.getKnowledge().teachRecipe(IRecipe.forName((RockSolid.createRes("bronze_axe"))));
								event.player.getKnowledge().teachRecipe(IRecipe.forName((RockSolid.createRes("bronze_shovel"))));
							}
						}
						else if (changed.getItem() == ModItems.STEEL_INGOT)
						{
							IRecipe recipe = IRecipe.forName((RockSolid.createRes("steel_pickaxe")));
							if (!event.player.getKnowledge().knowsRecipe(recipe))
							{
								event.player.getKnowledge().teachRecipe(recipe);
								event.player.getKnowledge().teachRecipe(IRecipe.forName((RockSolid.createRes("steel_axe"))));
								event.player.getKnowledge().teachRecipe(IRecipe.forName((RockSolid.createRes("steel_shovel"))));
								event.player.getKnowledge().teachRecipe(IRecipe.forName(RockSolid.createRes("wrench")));
								event.player.getKnowledge().teachRecipe(IRecipe.forName(RockSolid.createRes("assembly_station")));
							}
						}
						else if (changed.getItem() == ModItems.WRENCH)
						{
							IRecipe recipe = IRecipe.forName(RockSolid.createRes("energy_conduit"));
							if (!event.player.getKnowledge().knowsRecipe(recipe))
							{
								event.player.getKnowledge().teachRecipe(recipe);
								event.player.getKnowledge().teachRecipe(IRecipe.forName((RockSolid.createRes("item_conduit"))));
								event.player.getKnowledge().teachRecipe(IRecipe.forName((RockSolid.createRes("gas_conduit"))));
								event.player.getKnowledge().teachRecipe(IRecipe.forName((RockSolid.createRes("fluid_conduit"))));
							}
						}
						else if (changed.getItem() == ModItems.TITANIUM_INGOT)
						{
							IRecipe recipe = IRecipe.forName((RockSolid.createRes("titanium_pickaxe")));
							if (!event.player.getKnowledge().knowsRecipe(recipe))
							{
								event.player.getKnowledge().teachRecipe(recipe);
								event.player.getKnowledge().teachRecipe(IRecipe.forName((RockSolid.createRes("titanium_axe"))));
								event.player.getKnowledge().teachRecipe(IRecipe.forName((RockSolid.createRes("titanium_shovel"))));
							}
						}
					}
				});
			}
			return EventResult.DEFAULT;
		});
	}
}
