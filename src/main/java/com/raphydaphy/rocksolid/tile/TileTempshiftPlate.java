package com.raphydaphy.rocksolid.tile;

import com.raphydaphy.rocksolid.init.ModMisc;
import com.raphydaphy.rocksolid.init.ModTiles;
import com.raphydaphy.rocksolid.render.TempshiftPlateRenderer;
import com.raphydaphy.rocksolid.tile.machine.TileNuclearReactor;
import com.raphydaphy.rocksolid.tileentity.TileEntityNuclearReactor;
import com.raphydaphy.rocksolid.tileentity.TileEntityTempshiftPlate;
import com.raphydaphy.rocksolid.util.ModUtils;
import com.raphydaphy.rocksolid.util.ToolInfo;
import de.ellpeck.rockbottom.api.data.set.ModBasedDataSet;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ItemTile;
import de.ellpeck.rockbottom.api.item.ToolProperty;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.ArrayList;
import java.util.List;

public class TileTempshiftPlate extends TileBase {
    public TileTempshiftPlate() {
        super("tempshift_plate", 25f, new ToolInfo(ToolProperty.PICKAXE, 8));
    }

    @Override
    public boolean canPlace(IWorld world, int x, int y, TileLayer layer, AbstractEntityPlayer player) {
        return layer.equals(ModMisc.TEMPSHIFT_LAYER);
    }

    @Override
    public void onDestroyed(IWorld world, int x, int y, Entity destroyer, TileLayer layer, boolean shouldDrop) {
        if (destroyer == null) {
            shouldDrop = true;
        }
        super.onDestroyed(world, x, y, destroyer, layer, shouldDrop);
    }

    @Override
    public boolean canPlaceInLayer(TileLayer layer) {
        return layer.equals(ModMisc.TEMPSHIFT_LAYER);
    }

    @Override
    public boolean isFullTile() {
        return false;
    }

    @Override
    protected TempshiftPlateRenderer createRenderer(ResourceName name) {
        return new TempshiftPlateRenderer(name);
    }

    @Override
    protected ItemTile createItemTile() {
        return new ItemTile(this.getName()) {
            @Override
            public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player, ItemInstance instance) {
                TileState mainState = world.getState(x, y);
                if (mainState.getTile() != ModTiles.NUCLEAR_REACTOR) {
                    return false;
                }

                Pos2 inner = ((TileNuclearReactor) mainState.getTile()).getInnerCoord(mainState);

                if (inner.getX() == 2 && inner.getY() != 3) {
                    return false;
                }
                return ModUtils.placeInCustomLayer(world, x, y, player, instance, getTile(), ModMisc.TEMPSHIFT_LAYER);
            }
        };
    }

    @Override
    public List<ItemInstance> getDrops(IWorld world, int x, int y, TileLayer layer, Entity destroyer) {
        List<ItemInstance> drops = new ArrayList<>();
        Item item = this.getItem();
        if (item != null) {
            TileEntityTempshiftPlate te = world.getTileEntity(ModMisc.TEMPSHIFT_LAYER, x, y, TileEntityTempshiftPlate.class);
            ItemInstance nbtOut = new ItemInstance(item);
            nbtOut.getOrCreateAdditionalData().addFloat(ModUtils.ASSEMBLY_CAPACITY_KEY, te.getCapacityModifier());
            drops.add(nbtOut);
        }

        return drops;
    }

    @Override
    public void doPlace(IWorld world, int x, int y, TileLayer layer, ItemInstance instance, AbstractEntityPlayer placer) {
        super.doPlace(world, x, y, layer, instance, placer);
        if (!world.isClient()) {
            float capacityModifier = 1;
            TileEntityTempshiftPlate plateTE = world.getTileEntity(ModMisc.TEMPSHIFT_LAYER, x, y, TileEntityTempshiftPlate.class);
            if (plateTE != null) {
                ModBasedDataSet data = instance.getAdditionalData();

                if (data != null) {
                    capacityModifier = data.getFloat(ModUtils.ASSEMBLY_CAPACITY_KEY);
                    plateTE.setCapacityModifier(capacityModifier);
                }
            }
            TileState state = world.getState(x, y);
            if (state.getTile() == ModTiles.NUCLEAR_REACTOR) {
                TileEntityNuclearReactor te = ((TileNuclearReactor) state.getTile()).getTE(world, state, x, y);
                te.addTempshiftPlate(capacityModifier);
            }
        }
    }

    @Override
    public void onRemoved(IWorld world, int x, int y, TileLayer layer) {
        super.onRemoved(world, x, y, layer);
        if (!world.isClient()) {
            TileState state = world.getState(x, y);
            if (state.getTile() == ModTiles.NUCLEAR_REACTOR) {
                TileEntityNuclearReactor te = ((TileNuclearReactor) state.getTile()).getTE(world, state, x, y);
                TileEntityTempshiftPlate tempshiftTE = world.getTileEntity(ModMisc.TEMPSHIFT_LAYER, x, y, TileEntityTempshiftPlate.class);
                float capacityModifier = 1;
                if (tempshiftTE != null) {
                    capacityModifier = tempshiftTE.getCapacityModifier();
                }
                te.removeTempshiftPlate(capacityModifier);
            }
        }
    }

    @Override
    public boolean canStay(IWorld world, int x, int y, TileLayer layer, int changedX, int changedY, TileLayer changedLayer) {
        if (changedLayer.equals(TileLayer.MAIN)) {
            return world.getState(x, y).getTile() == ModTiles.NUCLEAR_REACTOR;
        }
        return true;
    }

    @Override
    public TileEntity provideTileEntity(IWorld world, int x, int y, TileLayer layer) {
        return new TileEntityTempshiftPlate(world, x, y, layer);
    }

    @Override
    public boolean canProvideTileEntity() {
        return true;
    }
}
