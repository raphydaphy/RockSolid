package com.raphydaphy.rocksolid.tile.machine;

import com.raphydaphy.rocksolid.container.ContainerEmpty;
import com.raphydaphy.rocksolid.gui.GuiRefinery;
import com.raphydaphy.rocksolid.render.RefineryRenderer;
import com.raphydaphy.rocksolid.tileentity.TileEntityRefinery;
import com.raphydaphy.rocksolid.util.ToolInfo;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ToolProperty;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileRefinery extends TileMachineBase<TileEntityRefinery> {
    public TileRefinery() {
        super("refinery", TileEntityRefinery.class, 25, false, new ToolInfo(ToolProperty.PICKAXE, 6));
    }

    @Override
    protected boolean[][] makeStructure() {
        return super.autoStructure(3, 2);
    }

    @Override
    public int getWidth() {
        return 3;
    }

    @Override
    public int getHeight() {
        return 2;
    }

    @Override
    protected ITileRenderer createRenderer(ResourceName name) {
        return new RefineryRenderer(name, this);
    }

    @Override
    public TileEntity makeTE(IWorld world, int x, int y, TileLayer layer) {
        return new TileEntityRefinery(world, x, y, layer);
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player) {
        TileEntityRefinery te = getTE(world, world.getState(x, y), x, y);
        player.openGuiContainer(new GuiRefinery(player, te), new ContainerEmpty(player));
        return true;
    }
}
