package com.raphydaphy.rocksolid.tile;

import java.util.List;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.api.gui.ContainerEmpty;
import com.raphydaphy.rocksolid.gui.GuiFluidPump;
import com.raphydaphy.rocksolid.render.PumpRenderer;
import com.raphydaphy.rocksolid.tileentity.TileEntityFluidPump;
import com.raphydaphy.rocksolid.tileentity.TileEntityQuarry;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class TileFluidPump extends MultiTile
{
	private static final String name = "fluidPump";
	private final IResourceName desc = RockBottomAPI.createRes(RockSolid.INSTANCE,"details." + name);
    public TileFluidPump() {
        super(RockSolidLib.makeRes(name));
        this.setHardness(25);
        this.addEffectiveTool(ToolType.PICKAXE, 1);
        this.register();
    }
   
    @Override
    protected ITileRenderer<TileFluidPump> createRenderer(final IResourceName name) {
        return new PumpRenderer(name, this);
    }
   
    @Override
    public boolean canProvideTileEntity() {
        return true;
    }
   
    @Override
    public TileEntity provideTileEntity(final IWorld world, final int x, final int y) {
        return this.isMainPos(x, y, world.getState(x, y)) ? new TileEntityFluidPump(world, x, y) : null;
    }
   
    @Override
    public int getLight(final IWorld world, final int x, final int y, final TileLayer layer) {
        if (this.isMainPos(x, y, world.getState(x, y))) {
            final TileEntityQuarry tile = world.getTileEntity(x, y, TileEntityQuarry.class);
            if (tile != null && tile.isActive()) {	
                return 50;
            }
        }
        return 0;
    }
   
    @Override
    public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player)
    {
        final Pos2 main = this.getMainPos(x, y, world.getState(x, y));
        final TileEntityFluidPump tile = world.getTileEntity(main.getX(), main.getY(), TileEntityFluidPump.class);
        if (tile != null) {
            player.openGuiContainer(new GuiFluidPump(player, tile), new ContainerEmpty(player));
            return true;
        }
        return false;
    }
    @Override
    public BoundBox getBoundBox(final IWorld world, final int x, final int y) {
        return null;
    }
   
    @Override
    public boolean isFullTile() {
        return false;
    }
   
    @Override
    protected boolean[][] makeStructure() {
    	return new boolean[][] { { true, true }, { true, true } };
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
    public int getMainX() {
        return 0;
    }
   
    @Override
    public int getMainY() {
        return 0;
    }
    
    @Override
    public void describeItem(IAssetManager manager, ItemInstance instance, List<String> desc, boolean isAdvanced) {
        super.describeItem(manager, instance, desc, isAdvanced);
        desc.addAll(manager.getFont().splitTextToLength(500,1f,true, manager.localize(this.desc)));
    }
}