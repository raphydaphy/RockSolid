package com.raphydaphy.rocksolid.item;

import com.raphydaphy.rocksolid.fluid.IFluidTile;
import com.raphydaphy.rocksolid.render.BucketRenderer;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.item.IItemRenderer;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.TileLiquid;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.ArrayList;
import java.util.List;

public class ItemBucket extends ItemBase {

    public ItemBucket() {
        super("bucket");
        this.maxAmount = 1;
    }

    @Override
    protected IItemRenderer<ItemBucket> createRenderer(ResourceName name) {
        return new BucketRenderer(name);
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY,
                                  AbstractEntityPlayer player, ItemInstance instance) {
        TileState liquidState = world.getState(TileLayer.LIQUIDS, x, y);
        TileState main = world.getState(TileLayer.MAIN, x, y);
        TileEntity te;
        if (main.getTile() instanceof MultiTile) {
            Pos2 mainPos = ((MultiTile) main.getTile()).getMainPos(x, y, main);
            te = world.getTileEntity(mainPos.getX(), mainPos.getY());
        } else {
            te = world.getTileEntity(x, y);
        }

        if (instance.getMeta() == BucketType.EMPTY.meta) {
            if (liquidState.getTile() instanceof TileLiquid) {
                TileLiquid liquid = (TileLiquid) liquidState.getTile();
                BucketType possibleType = BucketType.getFromLiquid(liquid);
                if (possibleType != null) {
                    int curLevel = liquidState.get(liquid.level);
                    if (curLevel > 0) {
                        world.setState(TileLayer.LIQUIDS, x, y, liquidState.prop(liquid.level, curLevel - 1));
                    } else {
                        world.setState(TileLayer.LIQUIDS, x, y, GameContent.TILE_AIR.getDefState());
                    }
                    instance.setMeta(possibleType.meta);
                }
            } else if (te instanceof IFluidTile<?>) {
                IFluidTile<?> fluidTE = (IFluidTile<?>) te;
                for (BucketType type : BucketType.TYPES) {
                    if (type.liquid != null) {
                        if (fluidTE.removeFluid(new Pos2(x, y), type.liquid, 25, false)) {
                            instance.setMeta(type.meta);
                            return true;
                        }
                    }
                }
                return false;
            } else {
                return false;
            }
        } else {
            BucketType type = BucketType.getFromMeta(instance.getMeta());
            if (type != null) {
                if (liquidState.getTile().equals(type.liquid)) {
                    int curLevel = liquidState.get(type.liquid.level);
                    if (curLevel < 11) {
                        world.setState(TileLayer.LIQUIDS, x, y,
                                liquidState.prop(((TileLiquid) liquidState.getTile()).level, curLevel + 1));
                    } else {
                        return false;
                    }
                } else if (te instanceof IFluidTile<?>) {
                    IFluidTile<?> fluidTE = (IFluidTile<?>) te;
                    if (!fluidTE.addFluid(new Pos2(x, y), type.liquid, 25, false)) {
                        return false;
                    }
                } else if (liquidState.getTile().isAir()) {
                    world.setState(TileLayer.LIQUIDS, x, y, type.liquid.getDefState());
                } else {
                    return false;
                }
                instance.setMeta(BucketType.EMPTY.meta);
            } else {
                return false;
            }
        }
        return true;

    }

    @Override
    public int getHighestPossibleMeta() {
        return BucketType.amount();
    }

    @Override
    public ResourceName getUnlocalizedName(ItemInstance instance) {
        return this.unlocName.addSuffix("." + BucketType.getFromMeta(instance.getMeta()).toString());
    }

    public static class BucketType {
        private final static List<BucketType> TYPES = new ArrayList<>();
        public static final BucketType EMPTY = new BucketType("empty", null);
        public final int meta;
        public final TileLiquid liquid;
        private final String name;

        public BucketType(String name, TileLiquid liquid) {
            this.meta = TYPES.size();
            this.liquid = liquid;
            this.name = name;

            TYPES.add(this);
        }

        public static BucketType getFromLiquid(TileLiquid liquid) {
            for (BucketType type : TYPES) {
                if (type.liquid == liquid) {
                    return type;
                }
            }
            return null;
        }

        public static BucketType getFromMeta(int meta) {
            return meta >= TYPES.size() ? TYPES.get(0) : TYPES.get(meta);
        }

        public static int amount() {
            return TYPES.size() - 1;
        }

        @Override
        public String toString() {
            return name;
        }
    }

}
