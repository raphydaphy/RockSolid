package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.container.slot.PlayerInvSlot;
import com.raphydaphy.rocksolid.entity.EntityRocket;
import com.raphydaphy.rocksolid.network.PacketJetpackMovement;
import com.raphydaphy.rocksolid.network.PacketJoinServer;
import com.raphydaphy.rocksolid.network.PacketLaunchRocket;
import com.raphydaphy.rocksolid.network.PacketLeaveRocket;
import com.raphydaphy.rocksolid.recipe.AlloyingRecipe;
import com.raphydaphy.rocksolid.recipe.BlastingRecipe;
import com.raphydaphy.rocksolid.recipe.CompressingRecipe;
import com.raphydaphy.rocksolid.recipe.SeparatingRecipe;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.compendium.construction.ConstructionRecipe;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.data.set.ModBasedDataSet;
import de.ellpeck.rockbottom.api.data.settings.Settings;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.event.EventResult;
import de.ellpeck.rockbottom.api.event.IEventHandler;
import de.ellpeck.rockbottom.api.event.impl.EntityTickEvent;
import de.ellpeck.rockbottom.api.event.impl.KeyEvent;
import de.ellpeck.rockbottom.api.event.impl.PlayerJoinWorldEvent;
import de.ellpeck.rockbottom.api.event.impl.ResetMovedPlayerEvent;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import org.lwjgl.glfw.GLFW;

public class ModEvents {
    private static final ResourceName PREV_X = RockSolid.createRes("prev_x");
    private static final ResourceName PREV_Y = RockSolid.createRes("prev_y");

    public static void init(IEventHandler handler) {
        handler.registerListener(KeyEvent.class, (result, event) ->
        {
            AbstractEntityPlayer player = RockBottomAPI.getGame().getPlayer();

            if (player != null) {
                if (player.hasAdditionalData() && player.getAdditionalData().getBoolean(EntityRocket.IN_ROCKET)) {

                    if (event.key == GLFW.GLFW_KEY_LEFT_SHIFT && event.action == 1) {
                        PacketLeaveRocket packet = new PacketLeaveRocket(player.getUniqueId());
                        if (player.world.isClient()) {
                            RockBottomAPI.getNet().sendToServer(packet);
                        } else {
                            packet.handle(RockBottomAPI.getGame(), null);
                        }
                    } else if (event.key == Settings.KEY_JUMP.getKey() && event.action == 1) {
                        PacketLaunchRocket packet = new PacketLaunchRocket(player.getUniqueId());
                        if (player.world.isClient()) {
                            RockBottomAPI.getNet().sendToServer(packet);
                        } else {
                            packet.handle(RockBottomAPI.getGame(), null);
                        }
                    }
                }
            }
            return EventResult.DEFAULT;
        });
        handler.registerListener(ResetMovedPlayerEvent.class, (result, event) ->
        {
            ItemInstance jetpack = PlayerInvSlot.getSlotItem(event.player, PlayerInvSlot.JETPACK);
            if (jetpack != null && jetpack.getItem() == ModItems.JETPACK) {
                return EventResult.CANCELLED;
            } else if (event.player.hasAdditionalData() && event.player.getAdditionalData().getBoolean(EntityRocket.IN_ROCKET)) {
                return EventResult.CANCELLED;
            }
            return EventResult.DEFAULT;
        });
        handler.registerListener(EntityTickEvent.class, (result, event) ->
        {
            if (event.entity instanceof AbstractEntityPlayer) {
                ItemInstance lantern = PlayerInvSlot.getSlotItem((AbstractEntityPlayer) event.entity, PlayerInvSlot.LANTERN);
                if (lantern != null && lantern.getItem() == ModItems.LANTERN) {
                    if (!event.entity.world.isClient()) {
                        boolean fueled = false;
                        boolean refueled = false;

                        // TODO: if lantern has fuel
                        if (lantern.getMeta() < 100) {
                            if (event.entity.world.getTotalTime() % 100 == 0) {
                                lantern.setMeta(lantern.getMeta() + 1);
                                DataSet lanternSet = new DataSet();
                                lantern.save(lanternSet);
                                event.entity.getAdditionalData().addDataSet(PlayerInvSlot.LANTERN, lanternSet);

                            }
                            fueled = true;
                        } else {
                            ItemInstance coal = PlayerInvSlot.getSlotItem((AbstractEntityPlayer) event.entity, PlayerInvSlot.LANTERN_FUEL);
                            if (coal != null && (coal.getItem() == GameContent.TILE_COAL.getItem() || coal.getItem() == ModItems.COKE)) {
                                if (coal.getAmount() == 1) {
                                    event.entity.getAdditionalData().addDataSet(PlayerInvSlot.LANTERN_FUEL, new DataSet());
                                } else {
                                    coal.setAmount(coal.getAmount() - 1);
                                    DataSet coalSet = new DataSet();
                                    coal.save(coalSet);
                                    event.entity.getAdditionalData().addDataSet(PlayerInvSlot.LANTERN_FUEL, coalSet);
                                }

                                if (coal.getItem() == GameContent.TILE_COAL.getItem()) {
                                    lantern.setMeta(50);
                                } else if (coal.getItem() == ModItems.COKE) {
                                    lantern.setMeta(0);
                                }
                                DataSet lanternSet = new DataSet();
                                lantern.save(lanternSet);
                                event.entity.getAdditionalData().addDataSet(PlayerInvSlot.LANTERN, lanternSet);
                                fueled = true;
                                refueled = true;
                            }
                        }

                        ModBasedDataSet data = event.entity.getOrCreateAdditionalData();

                        int prevX = data.getInt(PREV_X);
                        int prevY = data.getInt(PREV_Y);

                        int curX = (int) Math.floor(event.entity.getX());
                        int curY = (int) Math.floor(event.entity.getY());

                        if ((curX != prevX || curY != prevY) || refueled) {
                            event.entity.world.setState(ModMisc.LIGHTING_LAYER, prevX, prevY, GameContent.TILE_AIR.getDefState());
                            event.entity.world.setState(ModMisc.LIGHTING_LAYER, prevX, prevY + 1, GameContent.TILE_AIR.getDefState());

                            if (fueled) {
                                event.entity.world.setState(ModMisc.LIGHTING_LAYER, curX, curY, ModTiles.LIGHT.getDefState());
                                event.entity.world.setState(ModMisc.LIGHTING_LAYER, curX, curY + 1, ModTiles.LIGHT.getDefState());
                            }

                            event.entity.getAdditionalData().addInt(PREV_X, curX);
                            event.entity.getAdditionalData().addInt(PREV_Y, curY);
                        }
                    }
                }
                if (!(RockBottomAPI.getNet().isServer() && RockBottomAPI.getGame().isDedicatedServer())) {
                    AbstractEntityPlayer player = (AbstractEntityPlayer) event.entity;

                    ItemInstance jetpack = PlayerInvSlot.getSlotItem(player, PlayerInvSlot.JETPACK);
                    if (jetpack != null && jetpack.getItem() == ModItems.JETPACK) {
                        int fuel = jetpack.getItem().getHighestPossibleMeta() - jetpack.getMeta();
                        if (fuel > 0) {
                            if (Settings.KEY_JUMP.isDown() && RockBottomAPI.getGame().getGuiManager().getGui() == null) {
                                if (player.motionY < 0.5) {
                                    player.motionY += 0.05;
                                } else {
                                    player.motionY = 0.5;
                                }
                                PacketJetpackMovement packet = new PacketJetpackMovement(player.getUniqueId());

                                if (player.world.isClient()) {
                                    RockBottomAPI.getNet().sendToServer(packet);
                                } else {
                                    packet.handle(RockBottomAPI.getGame(), null);
                                }
                            }
                        }
                    }
                }
            }
            if (event.entity.world.getSubName() != null && event.entity.world.getSubName().equals(ModMisc.MOON_WORLD)) {
                if (event.entity.motionY < 0) {
                    event.entity.motionY /= 1.25;
                }
            }

            if (event.entity.hasAdditionalData() && event.entity.getAdditionalData().getBoolean(EntityRocket.IN_ROCKET)) {
                event.entity.motionX = 0;
            }
            return EventResult.DEFAULT;
        });
        handler.registerListener(PlayerJoinWorldEvent.class, (result, event) ->
        {
            if (!event.player.world.isClient()) {
                if (event.player.getInvContainer() != null) {
                    event.player.getInvContainer().addSlot(new PlayerInvSlot(event.player, 0, PlayerInvSlot.JETPACK, (instance) -> instance != null && instance.getItem() == ModItems.JETPACK, 137, 20));
                    event.player.getInvContainer().addSlot(new PlayerInvSlot(event.player, 1, PlayerInvSlot.LANTERN, (instance) -> instance != null && instance.getItem() == ModItems.LANTERN, 137, 37));
                    event.player.getInvContainer().addSlot(new PlayerInvSlot(event.player, 2, PlayerInvSlot.LANTERN_FUEL, (instance) -> instance != null && (instance.getItem() == GameContent.TILE_COAL.getItem() || instance.getItem() == ModItems.COKE), 137, 54));

                    if (event.player.world.isServer() && event.player.world.isDedicatedServer()) {
                        event.player.sendPacket(new PacketJoinServer());
                    }
                }

                event.player.getInv().addChangeCallback((inv, slot) ->
                {
                    ItemInstance changed = inv.get(slot);

                    if (changed != null) {
                        if (changed.getItem() == GameContent.TILE_COPPER.getItem()) {
                            SeparatingRecipe grit = SeparatingRecipe.REGISTRY.get(RockSolid.createRes("copper_separating"));
                            if (!grit.isKnown(event.player)) {
                                grit.teach(event.player, true);
                            }
                        } else if (changed.getItem() == ModItems.TIN_CLUSTER) {
                            SeparatingRecipe grit = SeparatingRecipe.REGISTRY.get(RockSolid.createRes("tin_separating"));
                            if (!grit.isKnown(event.player)) {
                                grit.teach(event.player, true);
                            }
                        } else if (changed.getItem() == ModTiles.ALLOY_SMELTER.getItem()) {
                            AlloyingRecipe bronze = AlloyingRecipe.REGISTRY.get(RockSolid.createRes("bronze_ingot_alloying"));
                            if (!bronze.isKnown(event.player)) {
                                bronze.teach(event.player, true);
                                AlloyingRecipe.REGISTRY.get(RockSolid.createRes("bronze_grit_alloying")).teach(event.player, true);
                            }
                        } else if (changed.getItem() == ModItems.IRON_CLUSTER) {
                            SeparatingRecipe grit = SeparatingRecipe.REGISTRY.get(RockSolid.createRes("iron_separating"));
                            if (!grit.isKnown(event.player)) {
                                grit.teach(event.player, true);
                            }
                        } else if (changed.getItem() == ModTiles.BLAST_FURNACE.getItem()) {
                            BlastingRecipe coke = BlastingRecipe.REGISTRY.get(RockSolid.createRes("coal_blasting"));
                            if (!coke.isKnown(event.player)) {
                                coke.teach(event.player, true);
                            }
                        } else if (changed.getItem() == ModItems.COKE) {
                            AlloyingRecipe steel = AlloyingRecipe.REGISTRY.get(RockSolid.createRes("steel_alloying"));
                            if (!steel.isKnown(event.player)) {
                                steel.teach(event.player, true);
                            }
                        } else if (changed.getItem() == ModTiles.RUTILE_ORE.getItem()) {
                            BlastingRecipe rutile = BlastingRecipe.REGISTRY.get(RockSolid.createRes("rutile_blasting"));
                            if (!rutile.isKnown(event.player)) {
                                rutile.teach(event.player, true);
                            }
                        } else if (changed.getItem() == ModItems.MAGNESIUM_CLUSTER) {
                            SeparatingRecipe grit = SeparatingRecipe.REGISTRY.get(RockSolid.createRes("magnesium_separating"));
                            if (!grit.isKnown(event.player)) {
                                grit.teach(event.player, true);
                            }
                        } else if (changed.getItem() == ModItems.IMPURE_TITANIUM_INGOT) {
                            AlloyingRecipe titanium = AlloyingRecipe.REGISTRY.get(RockSolid.createRes("titanium_alloying"));
                            if (!titanium.isKnown(event.player)) {
                                titanium.teach(event.player, true);
                            }
                        } else if (changed.getItem() == ModItems.URANIUM_CLUSTER) {
                            SeparatingRecipe grit = SeparatingRecipe.REGISTRY.get(RockSolid.createRes("uranium_separating"));
                            if (!grit.isKnown(event.player)) {
                                grit.teach(event.player, true);
                            }
                        } else if (changed.getItem() == ModItems.URANIUM_INGOT) {
                            CompressingRecipe pellet = CompressingRecipe.REGISTRY.get(RockSolid.createRes("uranium_compressing"));
                            if (!pellet.isKnown(event.player)) {
                                pellet.teach(event.player, true);
                            }
                        } else if (changed.getItem() == ModItems.TUNGSTEN_CLUSTER) {
                            SeparatingRecipe grit = SeparatingRecipe.REGISTRY.get(RockSolid.createRes("tungsten_separating"));
                            if (!grit.isKnown(event.player)) {
                                grit.teach(event.player, true);
                            }
                        } else if (changed.getItem() == ModItems.TUNGSTEN_INGOT) {
                            AlloyingRecipe alloy = AlloyingRecipe.REGISTRY.get(RockSolid.createRes("nickel_tungsten_alloying"));
                            if (!alloy.isKnown(event.player)) {
                                alloy.teach(event.player, true);
                            }
                        } else if (changed.getItem() == ModItems.NICKEL_TUNGSTEN_INGOT) {
                            AlloyingRecipe alloy = AlloyingRecipe.REGISTRY.get(RockSolid.createRes("nickel_tungsten_carbide_alloying"));
                            if (!alloy.isKnown(event.player)) {
                                alloy.teach(event.player, true);
                            }
                        } else if (changed.getItem() == ModItems.LUNAR_NICKEL_CLUSTER) {
                            SeparatingRecipe grit = SeparatingRecipe.REGISTRY.get(RockSolid.createRes("nickel_separating"));
                            if (!grit.isKnown(event.player)) {
                                grit.teach(event.player, true);
                            }
                        }
                    }
                });
                return EventResult.MODIFIED;
            }
            return EventResult.DEFAULT;
        });
    }
}
