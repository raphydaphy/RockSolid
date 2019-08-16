package com.raphydaphy.rocksolid.tile.machine;

import com.raphydaphy.rocksolid.container.ContainerBlastFurnace;
import com.raphydaphy.rocksolid.gui.GuiBlastFurnace;
import com.raphydaphy.rocksolid.render.ActivatableRenderer;
import com.raphydaphy.rocksolid.tileentity.TileEntityBlastFurnace;
import com.raphydaphy.rocksolid.util.ToolInfo;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ToolProperty;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileBlastFurnace extends TileMachineBase<TileEntityBlastFurnace> {

    public TileBlastFurnace() {
        super("blast_furnace", TileEntityBlastFurnace.class, 17, false, new ToolInfo(ToolProperty.PICKAXE, 2));
    }

    @Override
    protected boolean[][] makeStructure() {
        return super.autoStructure(2, 2);
    }

    @Override
    public int getWidth() {
        return 2;
    }

    @Override
    public int getHeight() {
        return 2;
    }

    @Override
    protected ITileRenderer<MultiTile> createRenderer(ResourceName name) {
        return new ActivatableRenderer(name, this);
    }

    @Override
    public TileEntity makeTE(IWorld world, int x, int y, TileLayer layer) {
        return new TileEntityBlastFurnace(world, x, y, layer);
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player) {
        TileEntityBlastFurnace te = getTE(world, world.getState(x, y), x, y);
        player.openGuiContainer(new GuiBlastFurnace(player, te), new ContainerBlastFurnace(player, te));
        return true;
    }

    @Override
    public int getLight(IWorld world, int x, int y, TileLayer layer) {
        TileState state = world.getState(x, y);
        TileEntityBlastFurnace te = getTE(world, state, x, y);
        if (this.getInnerCoord(state).getY() == 0 && te != null && te.isActive()) {
            return 30;
        }
        return 0;
    }

}
