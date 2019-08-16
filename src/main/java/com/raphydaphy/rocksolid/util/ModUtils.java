package com.raphydaphy.rocksolid.util;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.init.ModItems;
import com.raphydaphy.rocksolid.init.ModMisc;
import com.raphydaphy.rocksolid.init.ModTiles;
import com.raphydaphy.rocksolid.tileentity.base.TileEntityFueledBase;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.construction.resource.ResUseInfo;
import de.ellpeck.rockbottom.api.construction.smelting.SmeltingRecipe;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.particle.IParticleManager;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class ModUtils {
    public static final int COAL = new Color(0.5F, 0.1F, 0.1F).getRGB();
    public static final int PROGRESS = new Color(0.1F, 0.5F, 0.1F).getRGB();
    public static final int ENERGY = new Color(148, 0, 211).getRGB();

    public static final ResourceName ASSEMBLY_CAPACITY_KEY = RockSolid.createRes("assembly_capacity");
    public static final ResourceName ASSEMBLY_EFFICIENCY_KEY = RockSolid.createRes("assembly_efficiency");
    public static final ResourceName ASSEMBLY_SPEED_KEY = RockSolid.createRes("assembly_speed");
    public static final ResourceName ASSEMBLY_BONUS_KEY = RockSolid.createRes("assembly_bonus");
    public static final ResourceName ASSEMBLY_THROUGHPUT_KEY = RockSolid.createRes("assembly_throughput");
    private static ResUseInfo stone = new ResUseInfo(GameContent.RES_STONE);
    private static ResUseInfo sandstone = new ResUseInfo(ModMisc.RES_SANDSTONE);
    private static ResUseInfo soil = new ResUseInfo(GameContent.RES_SOIL);
    private static ResUseInfo log = new ResUseInfo(GameContent.RES_WOOD_RAW);
    private static ResUseInfo moon_stone = new ResUseInfo(ModTiles.RES_MOON_STONE);
    private static ResUseInfo moon_turf = new ResUseInfo(ModTiles.RES_MOON_TURF);
    private static ResUseInfo copper = new ResUseInfo(GameContent.RES_COPPER_PROCESSED);
    private static ResUseInfo tin = new ResUseInfo(ModItems.RES_TIN_PROCESSED);
    private static ResUseInfo bronze = new ResUseInfo(ModItems.RES_BRONZE_PROCESSED);
    private static ResUseInfo iron = new ResUseInfo(ModItems.RES_IRON_PROCESSED);
    private static ResUseInfo nickel = new ResUseInfo(ModItems.RES_NICKEL_PROCESSED);
    private static ResUseInfo steel = new ResUseInfo(ModItems.RES_STEEL_PROCESSED);
    private static ResUseInfo magnesium = new ResUseInfo(ModItems.RES_MAGNESIUM_PROCESSED);
    private static ResUseInfo impure_titanium = new ResUseInfo(ModItems.RES_TITANIUM_IMPURE);
    private static ResUseInfo titanium = new ResUseInfo(ModItems.RES_TITANIUM_PROCESSED);
    private static ResUseInfo tungsten = new ResUseInfo(ModItems.RES_TUNGSTEN_PROCESSED);
    private static ResUseInfo nickel_tungsten = new ResUseInfo(ModItems.RES_NICKEL_TUNGSTEN_PROCESSED);
    private static ResUseInfo nickel_tungsten_carbide = new ResUseInfo(ModItems.RES_NICKEL_TUNGSTEN_CARBIDE_PROCESSED);
    private static ResUseInfo coal = new ResUseInfo(GameContent.RES_COAL);
    private static ResUseInfo coke = new ResUseInfo(ModItems.RES_COAL_PROCESSED);

    public static void smokeParticle(IWorld world, int x, int y, IParticleManager manager, TileEntityFueledBase te) {
        Random rand = new Random();

        if (te.isActive()) {
            boolean left = rand.nextBoolean();
            double particleX = x + (left ? .05 : .74) + (rand.nextFloat() / 40);
            double particleY = y + (left ? .9 : .7);
            manager.addSmokeParticle(world, particleX, particleY, 0, 0.02, 0.2f + (rand.nextFloat() / 20));
        }
    }

    public static SmeltingRecipe getSmeltingRecipeSafe(ItemInstance input) {
        if (input != null) {
            for (SmeltingRecipe recipe : Registries.SMELTING_REGISTRY.values()) {
                if (recipe.getInput().containsItem(input)) {
                    return recipe;
                }
            }
        }
        return null;
    }

    public static Pos2 innerCoord(IWorld world, Pos2 pos) {
        if (world != null && pos != null) {
            TileState state = world.getState(pos.getX(), pos.getY());

            if (state.getTile() instanceof MultiTile) {
                return ((MultiTile) state.getTile()).getInnerCoord(state);
            }
        }
        return null;
    }

    public static float getAssemblyCapacity(List<ItemInstance> items) {
        float capacity = 0.5f;

        for (ItemInstance item : items) {
            if (item != null) {
                if (sandstone.containsItem(item)) {
                    capacity *= 1.2f;
                } else if (stone.containsItem(item)) {
                    capacity *= 1.1f;
                } else if (soil.containsItem(item)) {
                    capacity *= 0.8f;
                } else if (moon_stone.containsItem(item)) {
                    capacity *= 1.3f;
                } else if (copper.containsItem(item)) {
                    capacity *= 1.5f;
                } else if (iron.containsItem(item)) {
                    capacity *= 0.95f;
                } else if (bronze.containsItem(item)) {
                    capacity *= 1.25f;
                } else if (impure_titanium.containsItem(item)) {
                    capacity *= 0.65f;
                } else if (nickel_tungsten_carbide.containsItem(item)) {
                    capacity *= 1.3f;
                } else if (coke.containsItem(item)) {
                    capacity *= 1.1f;
                }
            }
        }

        return Math.min(Math.max(0, capacity), 1);
    }

    public static float getAssemblyEfficiency(List<ItemInstance> items) {
        float efficiency = 0.5f;

        for (ItemInstance item : items) {
            if (item != null) {
                if (stone.containsItem(item)) {
                    efficiency *= 1.1f;
                } else if (log.containsItem(item)) {
                    efficiency *= 1.15f;
                } else if (soil.containsItem(item)) {
                    efficiency *= 0.6f;
                } else if (sandstone.containsItem(item)) {
                    efficiency *= 0.9f;
                } else if (moon_turf.containsItem(item)) {
                    efficiency *= 1.2f;
                } else if (steel.containsItem(item)) {
                    efficiency *= 1.45f;
                } else if (iron.containsItem(item)) {
                    efficiency *= 0.9f;
                } else if (bronze.containsItem(item)) {
                    efficiency *= 1.25f;
                } else if (tin.containsItem(item)) {
                    efficiency *= 0.8f;
                } else if (copper.containsItem(item)) {
                    efficiency *= 0.85f;
                } else if (impure_titanium.containsItem(item)) {
                    efficiency *= 0.8f;
                } else if (titanium.containsItem(item)) {
                    efficiency *= 1.6f;
                } else if (magnesium.containsItem(item)) {
                    efficiency *= 0.9f;
                } else if (nickel.containsItem(item)) {
                    efficiency *= 1.15f;
                } else if (tungsten.containsItem(item)) {
                    efficiency *= 1.25f;
                } else if (coal.containsItem(item)) {
                    efficiency *= 0.9f;
                } else if (coke.containsItem(item)) {
                    efficiency *= 1.1f;
                }
            }
        }

        return Math.min(Math.max(0, efficiency), 1);
    }

    public static float getAssemblySpeed(List<ItemInstance> items) {
        float speed = 0.5f;

        for (ItemInstance item : items) {
            if (item != null) {
                if (stone.containsItem(item)) {
                    speed *= 0.9f;
                } else if (log.containsItem(item)) {
                    speed *= 0.8f;
                } else if (soil.containsItem(item)) {
                    speed *= 1.35f;
                } else if (moon_turf.containsItem(item)) {
                    speed *= 1.3f;
                } else if (steel.containsItem(item)) {
                    speed *= 1.4f;
                } else if (iron.containsItem(item)) {
                    speed *= 1.1f;
                } else if (bronze.containsItem(item)) {
                    speed *= 1.25f;
                } else if (copper.containsItem(item)) {
                    speed *= 0.9f;
                } else if (impure_titanium.containsItem(item)) {
                    speed *= 1.3f;
                } else if (titanium.containsItem(item)) {
                    speed *= 0.9f;
                } else if (magnesium.containsItem(item)) {
                    speed *= 1.1f;
                } else if (nickel.containsItem(item)) {
                    speed *= 0.7f;
                } else if (nickel_tungsten_carbide.containsItem(item)) {
                    speed *= 1.5f;
                } else if (coal.containsItem(item)) {
                    speed *= 0.8f;
                } else if (coke.containsItem(item)) {
                    speed *= 1.2f;
                }
            }
        }

        return Math.min(Math.max(0, speed), 1);
    }

    public static float getAssemblyBonusYield(List<ItemInstance> items) {
        float bonus = 0.5f;

        for (ItemInstance item : items) {
            if (item != null) {
                if (log.containsItem(item)) {
                    bonus *= 1.15f;
                } else if (soil.containsItem(item)) {
                    bonus *= 0.8f;
                } else if (sandstone.containsItem(item)) {
                    bonus *= 1.05f;
                } else if (moon_stone.containsItem(item)) {
                    bonus *= 1.5f;
                } else if (steel.containsItem(item)) {
                    bonus *= 0.95f;
                } else if (iron.containsItem(item)) {
                    bonus *= 1.2f;
                } else if (bronze.containsItem(item)) {
                    bonus *= 0.9f;
                } else if (tin.containsItem(item)) {
                    bonus *= 1.05f;
                } else if (copper.containsItem(item)) {
                    bonus *= 1.1f;
                } else if (magnesium.containsItem(item)) {
                    bonus *= 1.3f;
                } else if (titanium.containsItem(item)) {
                    bonus *= 1.2f;
                } else if (nickel.containsItem(item)) {
                    bonus *= 1.8f;
                } else if (coal.containsItem(item)) {
                    bonus *= 1.2f;
                } else if (coke.containsItem(item)) {
                    bonus *= 0.85f;
                }
            }
        }

        return Math.min(Math.max(0, bonus), 1);
    }

    public static float getAssemblyThroughput(List<ItemInstance> items) {
        float throughput = 0.5f;

        for (ItemInstance item : items) {
            if (item != null) {
                if (stone.containsItem(item)) {
                    throughput *= 1.1f;
                } else if (log.containsItem(item)) {
                    throughput *= 1.05f;
                } else if (soil.containsItem(item)) {
                    throughput *= 1.3f;
                } else if (moon_turf.containsItem(item)) {
                    throughput *= 1.4f;
                } else if (steel.containsItem(item)) {
                    throughput *= 0.95f;
                } else if (iron.containsItem(item)) {
                    throughput *= 1.1f;
                } else if (bronze.containsItem(item)) {
                    throughput *= 1.25f;
                } else if (tin.containsItem(item)) {
                    throughput *= 1.3f;
                } else if (copper.containsItem(item)) {
                    throughput *= 1.35f;
                } else if (titanium.containsItem(item)) {
                    throughput *= 1.2f;
                } else if (nickel_tungsten.containsItem(item)) {
                    throughput *= 1.4f;
                } else if (coal.containsItem(item)) {
                    throughput *= 0.9f;
                } else if (coke.containsItem(item)) {
                    throughput *= 1.2f;
                }
            }
        }

        return Math.min(Math.max(0, throughput), 1);
    }

    public static boolean placeInCustomLayer(IWorld world, int x, int y, AbstractEntityPlayer player, ItemInstance instance, Tile tile, TileLayer layer) {
        Tile currentTile;
        TileState currentState;
        ResourceName soundName;

        if ((currentTile = world.getState(layer, x, y).getTile()) != tile && currentTile.canReplace(world, x, y, layer) && tile.canPlace(world, x, y, layer, player)) {
            if (!world.isClient()) {
                tile.doPlace(world, x, y, layer, instance, player);
                player.getInv().remove(player.getSelectedSlot(), 1);

                if ((currentState = world.getState(layer, x, y)).getTile() == tile && (soundName = tile.getPlaceSound(player.world, x, y, layer, player, currentState)) != null) {
                    world.playSound(soundName, (double) x + 0.5D, (double) y + 0.5D, (double) layer.index(), 1.0F, 1.0F);
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
