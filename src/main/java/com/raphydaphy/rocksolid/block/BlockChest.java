package com.raphydaphy.rocksolid.block;

import com.raphydaphy.rocksolid.gui.GuiChest;
import com.raphydaphy.rocksolid.gui.container.ContainerChest;
import com.raphydaphy.rocksolid.tileentity.TileEntityChest;

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

public class BlockChest extends Tile
{
	protected final ITileRenderer<Tile> renderer;
	public BlockChest(IResourceName name) {
		super(name);
		this.renderer = this.createRenderer(name);
		this.setHardness((float)20);
        this.addEffectiveTool(ToolType.PICKAXE, 1);
	}
	
	@Override
	public TileEntity provideTileEntity(IWorld world, int x, int y)
	{
        return new TileEntityChest(world, x, y);
    }
	
	protected ITileRenderer<Tile> createRenderer(IResourceName name)
	{
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
		TileEntityChest tile = world.getTileEntity(x, y, TileEntityChest.class);
		
		if (tile != null)
		{
			player.openGuiContainer(new GuiChest(player), new ContainerChest(player, tile));
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
            final TileEntityChest tile = world.getTileEntity(x,y, TileEntityChest.class);
            if (tile != null) 
            {
                tile.dropInventory(tile.inventory);
            }
        }
    }
	
	@Override
	public boolean canPlace(IWorld world, int x, int y, TileLayer layer)
	{
        if(!this.canPlaceInLayer(layer))
        {
            return false;
        }
        
        return true;
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

