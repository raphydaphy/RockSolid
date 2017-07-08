package com.raphydaphy.rocksolid.block;

import com.raphydaphy.rocksolid.gui.GuiAllocator;
import com.raphydaphy.rocksolid.gui.container.ContainerAllocator;
import com.raphydaphy.rocksolid.tileentity.TileEntityAllocator;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.render.tile.DefaultTileRenderer;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class BlockAllocator extends Tile
{
	protected final ITileRenderer<Tile> renderer;
	public BlockAllocator(IResourceName name) {
		super(name);
		this.renderer = this.createRenderer(name);
		this.setHardness((float)5);
        this.addEffectiveTool(ToolType.PICKAXE, 1);
	}
	
	@Override
	public TileEntity provideTileEntity(IWorld world, int x, int y){
        return new TileEntityAllocator(world, x, y);
    }
	
	 protected ITileRenderer<Tile> createRenderer(IResourceName name){
	        return new DefaultTileRenderer<Tile>(name);
	    }

	    @Override
	    public ITileRenderer<Tile> getRenderer(){
	        return this.renderer;
	    }

	@Override
    public boolean canProvideTileEntity(){
        return true;
    }
	
	@Override
	public boolean onInteractWith(IWorld world, int x, int y, AbstractEntityPlayer player)
	{
		TileEntityAllocator tile = world.getTileEntity(x, y, TileEntityAllocator.class);
		
		if (tile != null)
		{
			player.openGuiContainer(new GuiAllocator(player, tile), new ContainerAllocator(player, tile));
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
            final TileEntityAllocator tile = world.getTileEntity(x,y, TileEntityAllocator.class);
            if (tile != null) 
            {
                tile.dropInventory(tile.inventory);
            }
        }
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
