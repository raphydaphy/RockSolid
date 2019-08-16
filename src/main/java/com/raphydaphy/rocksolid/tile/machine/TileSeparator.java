package com.raphydaphy.rocksolid.tile.machine;

import com.raphydaphy.rocksolid.container.ContainerSeparator;
import com.raphydaphy.rocksolid.gui.GuiSeparator;
import com.raphydaphy.rocksolid.render.ActivatableRenderer;
import com.raphydaphy.rocksolid.tileentity.TileEntitySeparator;
import com.raphydaphy.rocksolid.util.ModUtils;
import com.raphydaphy.rocksolid.util.ToolInfo;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ToolProperty;
import de.ellpeck.rockbottom.api.particle.IParticleManager;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileSeparator extends TileMachineBase<TileEntitySeparator> {

    public TileSeparator() {
        super("separator", TileEntitySeparator.class, 17f, false, new ToolInfo(ToolProperty.PICKAXE, 2));
    }

    @Override
    protected ITileRenderer<MultiTile> createRenderer(ResourceName name) {
        return new ActivatableRenderer(name, this);
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

    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player) {
        TileEntitySeparator te = getTE(world, world.getState(x, y), x, y);
        player.openGuiContainer(new GuiSeparator(player, te), new ContainerSeparator(player, te));
        return true;
    }

    @Override
    public TileEntity makeTE(IWorld world, int x, int y, TileLayer layer) {
        return new TileEntitySeparator(world, x, y, layer);
    }

    @Override
    public void updateRandomlyInPlayerView(IWorld world, int x, int y, TileLayer layer, TileState state, IParticleManager manager) {
        Pos2 innerCoord = this.getInnerCoord(state);

        if (innerCoord.getY() == 1 && innerCoord.getX() == 1) {
            ModUtils.smokeParticle(world, x, y, manager, getTE(world, world.getState(x, y), x, y));
        }
    }

    @Override
    public int getLight(IWorld world, int x, int y, TileLayer layer) {
        TileState state = world.getState(x, y);
        TileEntitySeparator te = getTE(world, state, x, y);
        if (this.getInnerCoord(state).getY() == 0 && te != null && te.isActive()) {
            return 30;
        }
        return 0;
    }

}
