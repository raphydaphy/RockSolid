package com.raphydaphy.rocksolid.tile.machine;

import com.raphydaphy.rocksolid.container.ContainerEmpty;
import com.raphydaphy.rocksolid.entity.EntityRocket;
import com.raphydaphy.rocksolid.gui.GuiLaunchPad;
import com.raphydaphy.rocksolid.init.ModItems;
import com.raphydaphy.rocksolid.tileentity.TileEntityLaunchPad;
import com.raphydaphy.rocksolid.util.ToolInfo;
import de.ellpeck.rockbottom.api.entity.AbstractEntityItem;
import de.ellpeck.rockbottom.api.entity.MovableWorldObject;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ToolProperty;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.Collections;
import java.util.List;

public class TileLaunchPad extends TileMachineBase<TileEntityLaunchPad> {
    private final BoundBox BOUNDS = new BoundBox(0, 0, 1, 0.25f);

    public TileLaunchPad() {
        super("launch_pad", TileEntityLaunchPad.class, 30, true, new ToolInfo(ToolProperty.PICKAXE, 11));
    }

    @Override
    public TileEntity makeTE(IWorld world, int x, int y, TileLayer layer) {
        return new TileEntityLaunchPad(world, x, y, layer);
    }

    @Override
    protected boolean[][] makeStructure() {
        return autoStructure(3, 1);
    }

    @Override
    public int getWidth() {
        return 3;
    }

    @Override
    public int getHeight() {
        return 1;
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player) {
        TileState state = world.getState(x, y);
        Pos2 inner = getInnerCoord(state);
        TileEntityLaunchPad te = getTE(world, state, x, y);
        if (inner.getX() == 0) {
            player.openGuiContainer(new GuiLaunchPad(player, te), new ContainerEmpty(player, 0, 41));
            return true;
        } else {
            ItemInstance held = player.getInv().get(player.getSelectedSlot());
            if (held != null && held.getItem() == ModItems.ROCKET) {
                if (te.setRocket(held)) {
                    if (!world.isClient()) {
                        player.getInv().remove(player.getSelectedSlot(), 1);
                    }
                    world.playSound(ResourceName.intern("tiles.generic_tile"), x, y, layer.index(), 1, 1);
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    @Override
    public void onRemoved(IWorld world, int x, int y, TileLayer layer) {
        if (!world.isClient()) {
            TileEntityLaunchPad tile = world.getTileEntity(x, y, TileEntityLaunchPad.class);
            if (tile != null && tile.onRemoved()) {
                AbstractEntityItem.spawn(world, new ItemInstance(ModItems.ROCKET), x + 0.5, y + 0.5, Util.RANDOM.nextGaussian() * 0.1, Util.RANDOM.nextGaussian() * 0.1);
            }
        }
    }

    @Override
    public List<BoundBox> getBoundBoxes(IWorld world, TileState state, int x, int y, TileLayer layer, MovableWorldObject object, BoundBox objectBox, BoundBox objectBoxMotion) {
        if (object instanceof EntityRocket) {
            return Collections.singletonList(BOUNDS.copy().add(x, y));
        } else {
            return Collections.emptyList();
        }
    }
}
