package com.raphydaphy.rocksolid.block;

import com.raphydaphy.rocksolid.gui.GuiBattery;
import com.raphydaphy.rocksolid.gui.container.ContainerEmpty;
import com.raphydaphy.rocksolid.tileentity.TileEntityBattery;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.render.tile.MultiTileRenderer;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class BlockBattery extends MultiTile
{
	protected final ITileRenderer<Tile> renderer;
	
	public BlockBattery(IResourceName name) 
	{
		super(name);
		this.renderer = this.createRenderer(name);
		this.setHardness((float)20);
        this.addEffectiveTool(ToolType.PICKAXE, 1);
	}
	
	@Override
	public TileEntity provideTileEntity(IWorld world, int x, int y)
	{
        return new TileEntityBattery(world, x, y);
    }
	
	@Override
    public int getLight(final IWorld world, final int x, final int y, final TileLayer layer) {
        return 20;
    }
	
	protected ITileRenderer createRenderer(final IResourceName name) 
	{
		return new MultiTileRenderer(name, this);
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
		Pos2 main = this.getMainPos(x, y, world.getMeta(x,  y));
		TileEntityBattery tile = world.getTileEntity(main.getX(), main.getY(), TileEntityBattery.class);
		
		if (tile != null)
		{
			player.openGuiContainer(new GuiBattery(player, tile), new ContainerEmpty(player));
			return true;
		}
		else
		{
			return false;
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

	@Override
	protected boolean[][] makeStructure() {
		return new boolean[][] { { true, true, true }, { true, true, true }, { true, true, true } };
	}

	@Override
	public int getWidth() {
		return 3;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public int getMainX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMainY() {
		// TODO Auto-generated method stub
		return 0;
	}

}
