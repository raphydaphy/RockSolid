package com.raphydaphy.rocksolid.util;

import com.raphydaphy.rocksolid.tileentity.base.TileEntityFueledBase;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.construction.smelting.SmeltingRecipe;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.particle.IParticleManager;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;

import java.awt.*;
import java.util.Random;

public class ModUtils
{
    public static final int COAL = new Color(0.5F, 0.1F, 0.1F).getRGB();
    public static final int PROGRESS = new Color(0.1F, 0.5F, 0.1F).getRGB();
    public static final int ENERGY = new Color(148, 0, 211).getRGB();

    public static void smokeParticle(IWorld world, int x, int y, IParticleManager manager, TileEntityFueledBase te)
    {
        Random rand = new Random();

        if (te.isActive())
        {
            boolean left = rand.nextBoolean();
            double particleX = x + (left ? .05 : .74) + (rand.nextFloat() / 40);
            double particleY = y + (left ? .9 : .7);
            manager.addSmokeParticle(world, particleX, particleY, 0, 0.02, 0.2f + (rand.nextFloat() / 20));
        }
    }

    public static SmeltingRecipe getSmeltingRecipeSafe(ItemInstance input)
    {
        if (input != null)
        {
            for (SmeltingRecipe recipe : Registries.SMELTING_REGISTRY.values())
            {
                if (recipe.getInput().containsItem(input))
                {
                    return recipe;
                }
            }
        }
        return null;
    }

    public static Pos2 innerCoord(IWorld world, Pos2 pos)
    {
        if (world != null && pos != null)
        {
            TileState state = world.getState(pos.getX(), pos.getY());

            if (state.getTile() instanceof MultiTile)
            {
                return ((MultiTile) state.getTile()).getInnerCoord(state);
            }
        }
        return null;
    }
}
