package com.raphydaphy.rocksolid.tile.machine;

import com.raphydaphy.rocksolid.container.ContainerElectricSeparator;
import com.raphydaphy.rocksolid.gui.GuiElectricSeparator;
import com.raphydaphy.rocksolid.render.ElectricSeparatorRenderer;
import com.raphydaphy.rocksolid.tileentity.TileEntityElectricSeparator;
import com.raphydaphy.rocksolid.util.ToolInfo;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ToolProperty;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileElectricSeparator extends TileMachineBase<TileEntityElectricSeparator> {
    public TileElectricSeparator() {
        super("electric_separator", TileEntityElectricSeparator.class, 20f, true, new ToolInfo(ToolProperty.PICKAXE, 6));
    }

    @Override
    protected ITileRenderer<TileElectricSeparator> createRenderer(ResourceName name) {
        return new ElectricSeparatorRenderer(name, this);
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
    protected boolean[][] makeStructure() {
        return super.autoStructure(2, 2);
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player) {
        TileEntityElectricSeparator te = getTE(world, world.getState(x, y), x, y);
        player.openGuiContainer(new GuiElectricSeparator(player, te), new ContainerElectricSeparator(player, te));
        return true;
    }

    @Override
    public int getLight(IWorld world, int x, int y, TileLayer layer) {
        TileState state = world.getState(x, y);
        TileEntityElectricSeparator te = getTE(world, state, x, y);
        if (this.getInnerCoord(state).getY() == 0 && te != null && te.isActive()) {
            return 30;
        }
        return 0;
    }

    @Override
    public TileEntity makeTE(IWorld world, int x, int y, TileLayer layer) {
        return new TileEntityElectricSeparator(world, x, y, layer);
    }
}
