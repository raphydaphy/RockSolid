package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.entity.EntityRocket;
import com.raphydaphy.rocksolid.network.PacketLaunchRocket;
import com.raphydaphy.rocksolid.network.PacketLeaveRocket;
import com.raphydaphy.rocksolid.recipe.AlloyingRecipe;
import com.raphydaphy.rocksolid.recipe.BlastingRecipe;
import com.raphydaphy.rocksolid.recipe.CompressingRecipe;
import com.raphydaphy.rocksolid.recipe.SeparatingRecipe;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.compendium.construction.ConstructionRecipe;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.event.EventResult;
import de.ellpeck.rockbottom.api.event.IEventHandler;
import de.ellpeck.rockbottom.api.event.impl.EntityTickEvent;
import de.ellpeck.rockbottom.api.event.impl.KeyEvent;
import de.ellpeck.rockbottom.api.event.impl.PlayerJoinWorldEvent;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import org.lwjgl.glfw.GLFW;

public class ModEvents
{
	public static void init(IEventHandler handler)
	{
		handler.registerListener(KeyEvent.class, (result, event) ->
		{
			AbstractEntityPlayer player = RockBottomAPI.getGame().getPlayer();

			if (player != null)
			{
				if (player.hasAdditionalData() && player.getAdditionalData().getBoolean(EntityRocket.IN_ROCKET))
				{

					if (event.key == GLFW.GLFW_KEY_LEFT_SHIFT && event.action == 1)
					{
						PacketLeaveRocket packet = new PacketLeaveRocket(player.getUniqueId());
						if (player.world.isClient())
						{
							RockBottomAPI.getNet().sendToServer(packet);
						} else
						{
							packet.handle(RockBottomAPI.getGame(), null);
						}
					} else if (event.key == Settings.KEY_JUMP.getKey() && event.action == 1)
					{
						PacketLaunchRocket packet = new PacketLaunchRocket(player.getUniqueId());
						if (player.world.isClient())
						{
							RockBottomAPI.getNet().sendToServer(packet);
						} else
						{
							packet.handle(RockBottomAPI.getGame(), null);
						}
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
			System.out.println("join world");
			if (!event.player.world.isClient())
			{
				System.out.println("make listen");
				event.player.getInv().addChangeCallback((inv, slot) ->
				{
					ItemInstance changed = inv.get(slot);

					System.out.println("was a change in " + slot);

					if (changed != null)
					{
						if (changed.getItem() == GameContent.TILE_COPPER.getItem())
						{
							SeparatingRecipe grit = SeparatingRecipe.REGISTRY.get(RockSolid.createRes("copper_separating"));
							if (!grit.isKnown(event.player))
							{
								grit.teach(event.player, true);
							}
						} else if (changed.getItem() == ModItems.TIN_CLUSTER)
						{
							SeparatingRecipe grit = SeparatingRecipe.REGISTRY.get(RockSolid.createRes("tin_separating"));
							if (!grit.isKnown(event.player))
							{
								grit.teach(event.player, true);
							}
						} else if (changed.getItem() == ModItems.TIN_INGOT)
						{
							ConstructionRecipe alloySmelter = ConstructionRecipe.forName(RockSolid.createRes("alloy_smelter"));
							if (!event.player.getKnowledge().knowsRecipe(alloySmelter))
							{
								event.player.getKnowledge().teachRecipe(alloySmelter);
							}
						} else if (changed.getItem() == ModTiles.ALLOY_SMELTER.getItem())
						{
							AlloyingRecipe bronze = AlloyingRecipe.REGISTRY.get(RockSolid.createRes("bronze_ingot_alloying"));
							if (!bronze.isKnown(event.player))
							{
								bronze.teach(event.player, true);
								AlloyingRecipe.REGISTRY.get(RockSolid.createRes("bronze_grit_alloying")).teach(event.player, true);
							}
						} else if (changed.getItem() == ModItems.IRON_CLUSTER)
						{
							SeparatingRecipe grit = SeparatingRecipe.REGISTRY.get(RockSolid.createRes("iron_separating"));
							if (!grit.isKnown(event.player))
							{
								grit.teach(event.player, true);
							}
						}
						else if (changed.getItem() == ModItems.BRONZE_INGOT)
						{
							ConstructionRecipe recipe = ConstructionRecipe.forName((RockSolid.createRes("bronze_pickaxe")));
							if (!event.player.getKnowledge().knowsRecipe(recipe))
							{
								event.player.getKnowledge().teachRecipe(recipe);
								event.player.getKnowledge().teachRecipe(ConstructionRecipe.forName((RockSolid.createRes("bronze_axe"))));
								event.player.getKnowledge().teachRecipe(ConstructionRecipe.forName((RockSolid.createRes("bronze_shovel"))));
							}
						} else if (changed.getItem() == ModItems.IRON_INGOT)
						{
							ConstructionRecipe ironLamp = ConstructionRecipe.forName(RockSolid.createRes("lamp_iron"));
							if (!event.player.getKnowledge().knowsRecipe(ironLamp))
							{
								event.player.getKnowledge().teachRecipe(ironLamp);
								event.player.getKnowledge().teachRecipe(ConstructionRecipe.forName(RockSolid.createRes("bucket")));
								event.player.getKnowledge().teachRecipe(ConstructionRecipe.forName(RockSolid.createRes("blast_furnace")));
								event.player.getKnowledge().teachRecipe(ConstructionRecipe.forName(RockSolid.createRes("separator")));
							}
						} else if (changed.getItem() == ModTiles.BLAST_FURNACE.getItem())
						{
							BlastingRecipe coke = BlastingRecipe.REGISTRY.get(RockSolid.createRes("coal_blasting"));
							if (!coke.isKnown(event.player))
							{
								coke.teach(event.player, true);
							}
						} else if (changed.getItem() == ModItems.COKE)
						{
							AlloyingRecipe steel = AlloyingRecipe.REGISTRY.get(RockSolid.createRes("steel_alloying"));
							if (!steel.isKnown(event.player))
							{
								steel.teach(event.player, true);
							}
						} else if (changed.getItem() == ModItems.STEEL_INGOT)
						{
							ConstructionRecipe recipe = ConstructionRecipe.forName((RockSolid.createRes("steel_pickaxe")));
							if (!event.player.getKnowledge().knowsRecipe(recipe))
							{
								event.player.getKnowledge().teachRecipe(recipe);
								event.player.getKnowledge().teachRecipe(ConstructionRecipe.forName((RockSolid.createRes("steel_axe"))));
								event.player.getKnowledge().teachRecipe(ConstructionRecipe.forName((RockSolid.createRes("steel_shovel"))));
								event.player.getKnowledge().teachRecipe(ConstructionRecipe.forName(RockSolid.createRes("wrench")));
								event.player.getKnowledge().teachRecipe(ConstructionRecipe.forName(RockSolid.createRes("assembly_station")));
							}
						} else if (changed.getItem() == ModItems.WRENCH)
						{
							ConstructionRecipe recipe = ConstructionRecipe.forName(RockSolid.createRes("energy_conduit"));
							if (!event.player.getKnowledge().knowsRecipe(recipe))
							{
								event.player.getKnowledge().teachRecipe(recipe);
								event.player.getKnowledge().teachRecipe(ConstructionRecipe.forName((RockSolid.createRes("item_conduit"))));
								event.player.getKnowledge().teachRecipe(ConstructionRecipe.forName((RockSolid.createRes("gas_conduit"))));
								event.player.getKnowledge().teachRecipe(ConstructionRecipe.forName((RockSolid.createRes("fluid_conduit"))));
							}
						} else if (changed.getItem() == ModTiles.RUTILE_ORE.getItem())
						{
							BlastingRecipe rutile = BlastingRecipe.REGISTRY.get(RockSolid.createRes("rutile_blasting"));
							if (!rutile.isKnown(event.player))
							{
								rutile.teach(event.player, true);
							}
						} else if (changed.getItem() == ModItems.MAGNESIUM_CLUSTER)
						{
							SeparatingRecipe grit = SeparatingRecipe.REGISTRY.get(RockSolid.createRes("magnesium_separating"));
							if (!grit.isKnown(event.player))
							{
								grit.teach(event.player, true);
							}
						} else if (changed.getItem() == ModItems.IMPURE_TITANIUM_INGOT)
						{
							AlloyingRecipe titanium = AlloyingRecipe.REGISTRY.get(RockSolid.createRes("titanium_alloying"));
							if (!titanium.isKnown(event.player))
							{
								titanium.teach(event.player, true);
							}
						} else if (changed.getItem() == ModItems.TITANIUM_INGOT)
						{
							ConstructionRecipe recipe = ConstructionRecipe.forName((RockSolid.createRes("titanium_pickaxe")));
							if (!event.player.getKnowledge().knowsRecipe(recipe))
							{
								event.player.getKnowledge().teachRecipe(recipe);
								event.player.getKnowledge().teachRecipe(ConstructionRecipe.forName((RockSolid.createRes("titanium_axe"))));
								event.player.getKnowledge().teachRecipe(ConstructionRecipe.forName((RockSolid.createRes("titanium_shovel"))));
							}
						} else if (changed.getItem() == ModItems.URANIUM_CLUSTER)
						{
							SeparatingRecipe grit = SeparatingRecipe.REGISTRY.get(RockSolid.createRes("uranium_separating"));
							if (!grit.isKnown(event.player))
							{
								grit.teach(event.player, true);
							}
						} else if (changed.getItem() == ModItems.URANIUM_INGOT)
						{
							CompressingRecipe pellet = CompressingRecipe.REGISTRY.get(RockSolid.createRes("uranium_compressing"));
							if (!pellet.isKnown(event.player))
							{
								pellet.teach(event.player, true);
							}
						} else if (changed.getItem() == ModItems.TUNGSTEN_CLUSTER)
						{
							SeparatingRecipe grit = SeparatingRecipe.REGISTRY.get(RockSolid.createRes("tungsten_separating"));
							if (!grit.isKnown(event.player))
							{
								grit.teach(event.player, true);
							}
						} else if (changed.getItem() == ModItems.TUNGSTEN_INGOT)
						{
							AlloyingRecipe alloy = AlloyingRecipe.REGISTRY.get(RockSolid.createRes("nickel_tungsten_alloying"));
							if (!alloy.isKnown(event.player))
							{
								alloy.teach(event.player, true);
							}
						} else if (changed.getItem() == ModItems.NICKEL_TUNGSTEN_INGOT)
						{
							AlloyingRecipe alloy = AlloyingRecipe.REGISTRY.get(RockSolid.createRes("nickel_tungsten_carbide_alloying"));
							if (!alloy.isKnown(event.player))
							{
								alloy.teach(event.player, true);
							}
						} else if (changed.getItem() == ModItems.LUNAR_NICKEL_CLUSTER)
						{
							SeparatingRecipe grit = SeparatingRecipe.REGISTRY.get(RockSolid.createRes("nickel_separating"));
							if (!grit.isKnown(event.player))
							{
								grit.teach(event.player, true);
							}
						}
					}
				});
			}
			return EventResult.DEFAULT;
		});
	}
}
