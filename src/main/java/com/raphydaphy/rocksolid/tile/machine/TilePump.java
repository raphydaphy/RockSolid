package com.raphydaphy.rocksolid.tile.machine;

import com.raphydaphy.rocksolid.container.ContainerEmpty;
import com.raphydaphy.rocksolid.gui.GuiPump;
import com.raphydaphy.rocksolid.render.PumpRenderer;
import com.raphydaphy.rocksolid.tileentity.TileEntityPump;
import com.raphydaphy.rocksolid.util.ToolInfo;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ToolProperty;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TilePump extends TileMachineBase<TileEntityPump> {

    public TilePump() {
        super("pump", TileEntityPump.class, 15, true, new ToolInfo(ToolProperty.PICKAXE, 6));
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
    protected ITileRenderer<TilePump> createRenderer(ResourceName name) {
        return new PumpRenderer(name, this);
    }

    @Override
    public TileEntity makeTE(IWorld world, int x, int y, TileLayer layer) {
        return new TileEntityPump(world, x, y, layer);
    }

    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY,
                                  AbstractEntityPlayer player) {
        TileEntityPump te = getTE(world, world.getState(x, y), x, y);
        player.openGuiContainer(new GuiPump(player, te), new ContainerEmpty(player, 0, 41));
        return true;
    }

}
