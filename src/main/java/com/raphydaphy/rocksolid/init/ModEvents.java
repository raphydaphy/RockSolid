package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.RockSolid;
import de.ellpeck.rockbottom.api.construction.IRecipe;
import de.ellpeck.rockbottom.api.event.EventResult;
import de.ellpeck.rockbottom.api.event.IEventHandler;
import de.ellpeck.rockbottom.api.event.impl.PlayerJoinWorldEvent;
import de.ellpeck.rockbottom.api.item.ItemInstance;

public class ModEvents
{
	public static void init(IEventHandler handler)
	{
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
								event.player.getKnowledge().teachRecipe(IRecipe.forName(RockSolid.createRes("coke_oven")));
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
					}
				});
			}
			return EventResult.DEFAULT;
		});
	}
}
