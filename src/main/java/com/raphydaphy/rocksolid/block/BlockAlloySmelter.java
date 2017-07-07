package com.raphydaphy.rocksolid.block;

import com.raphydaphy.rocksolid.gui.GuiAlloySmelter;
import com.raphydaphy.rocksolid.gui.container.ContainerAlloySmelter;
import com.raphydaphy.rocksolid.render.AlloySmelterRenderer;
import com.raphydaphy.rocksolid.tileentity.TileEntityAlloySmelter;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class BlockAlloySmelter extends MultiTile
{

	public BlockAlloySmelter(IResourceName name) 
	{
		super(name);
		this.setHardness(4);
        this.addEffectiveTool(ToolType.PICKAXE, 1);
	}
	
	@Override
    protected ITileRenderer createRenderer(final IResourceName name) {
        return new AlloySmelterRenderer(name, this);
    }
	
	@Override
    public int getLight(final IWorld world, final int x, final int y, final TileLayer layer) {
        if (this.isMainPos(x, y, world.getMeta(x, y))) {
            final TileEntityAlloySmelter tile = world.getTileEntity(x, y, TileEntityAlloySmelter.class);
            if (tile != null && tile.isActive()) {
                return 20;
            }
        }
        return 0;
    }
	
	@Override
	public TileEntity provideTileEntity(IWorld world, int x, int y){
        return new TileEntityAlloySmelter(world, x, y);
    }

	@Override
    public boolean canProvideTileEntity(){
        return true;
    }
	
	@Override
	public boolean onInteractWith(IWorld world, int x, int y, AbstractEntityPlayer player)
	{
		TileEntityAlloySmelter tile = world.getTileEntity(x, y, TileEntityAlloySmelter.class);
		
		if (tile != null)
		{
			player.openGuiContainer(new GuiAlloySmelter(player, tile), new ContainerAlloySmelter(player, tile));
			return true;
		}
		else
		{
			return false;
		}
    }
	
	@Override
    public void onDestroyed(final IWorld world, final int x, final int y, final Entity destroyer, final TileLayer layer, final boolean forceDrop)
    {
        super.onDestroyed(world, x, y, destroyer, layer, forceDrop);
        if (!RockBottomAPI.getNet().isClient()) 
        {
            final Pos2 main = this.getMainPos(x, y, world.getMeta(x, y));
            final TileEntityAlloySmelter tile = world.getTileEntity(main.getX(), main.getY(), TileEntityAlloySmelter.class);
            if (tile != null) 
            {
                tile.dropInventory(tile.inventory);
            }
        }
    }
	
	@Override
	protected boolean[][] makeStructure() 
	{
		return new boolean[][] { { true }, { true } };
	}

	@Override
	public int getWidth() 
	{
		return 1;
	}

	@Override
	public int getHeight() 
	{
		return 2;
	}

	@Override
	public int getMainX() 
	{
		return 0;
	}

	@Override
	public int getMainY() 
	{
		return 0;
	}
	
	@Override
    public BoundBox getBoundBox(final IWorld world, final int x, final int y) 
	{
        return null;
    }
	
	@Override
    public boolean isFullTile() 
	{
        return false;
    }
}
