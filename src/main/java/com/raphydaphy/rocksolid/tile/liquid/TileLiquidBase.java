package com.raphydaphy.rocksolid.tile.liquid;

import com.raphydaphy.rocksolid.RockSolid;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.tile.TileLiquid;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Direction;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.Iterator;
import java.util.List;

public class TileLiquidBase extends TileLiquid {
    private final BoundBox[] levelBounds = new BoundBox[this.getLevels()];
    private final double movementSpeed;
    private final float evaporationChance;
    private final boolean canSwim;

    public TileLiquidBase(String name, double movementSpeed, float evaporationChance, boolean canSwim) {
        super(RockSolid.createRes(name));

        for (int level = 0; level < this.levelBounds.length; ++level) {
            this.levelBounds[level] = new BoundBox(0.0D, 0.0D, 1.0D, (double) (level + 1) / (double) this.getLevels());
        }

        this.movementSpeed = movementSpeed;
        this.evaporationChance = evaporationChance;
        this.canSwim = canSwim;
        register();
    }

    public BoundBox getBoundBox(IWorld world, TileState state, int x, int y, TileLayer layer) {
        return this.levelBounds[world.getState(layer, x, y).get(this.level)];
    }

    public int getLevels() {
        return 12;
    }

    public boolean doesFlow() {
        return true;
    }

    public int getFlowSpeed() {
        return 5;
    }

    public void onIntersectWithEntity(IWorld world, int x, int y, TileLayer layer, TileState state, BoundBox entityBox, BoundBox entityBoxMotion, List<BoundBox> tileBoxes, Entity entity) {
        super.onIntersectWithEntity(world, x, y, layer, state, entityBox, entityBoxMotion, tileBoxes, entity);

        if (entity instanceof AbstractEntityPlayer) {
            ((AbstractEntityPlayer) entity).jumping = true;
        }

        Iterator tileBoxIterator = tileBoxes.iterator();

        do {
            if (!tileBoxIterator.hasNext()) {
                return;
            }
        } while (!((BoundBox) tileBoxIterator.next()).contains(entity.getX(), entity.getOriginY()));

        entity.motionX *= movementSpeed;
        if (entity.motionY < 0.0D) {
            entity.motionY *= movementSpeed;
        }

        entity.fallStartY = entity.getY();
    }

    public void updateRandomly(IWorld world, int x, int y, TileLayer layer) {
        TileState state;
        int level;
        if (Util.RANDOM.nextFloat() >= evaporationChance && (level = (state = world.getState(layer, x, y)).get(this.level)) <= 3) {
            Direction[] directions = Direction.ADJACENT;
            int var8 = Direction.ADJACENT.length;

            for (int adjacents = 0; adjacents < var8; ++adjacents) {
                Direction dir = directions[adjacents];
                if (!world.isPosLoaded((double) (x + dir.x), (double) (y + dir.y))) {
                    return;
                }

                TileState adjState;
                if ((adjState = world.getState(layer, x + dir.x, y + dir.y)).getTile() == this && adjState.get(this.level) > 3) {
                    return;
                }
            }

            if (level <= 0) {
                world.setState(layer, x, y, GameContent.TILE_AIR.getDefState());
                return;
            }

            world.setState(layer, x, y, state.prop(this.level, level - 1));
        }

    }

    @Override
    public boolean canSwim(IWorld world, int x, int y, TileLayer layer, Entity entity) {
        return canSwim;
    }
}