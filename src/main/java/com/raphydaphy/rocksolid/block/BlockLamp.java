package com.raphydaphy.rocksolid.block;

import com.raphydaphy.rocksolid.api.IConduit;
import com.raphydaphy.rocksolid.render.ConduitRenderer;

import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class BlockLamp extends Tile implements IConduit
{
	private ITileRenderer<Tile> renderer;
	public BlockLamp(IResourceName name) 
	{
		super(name);
		
		this.renderer = this.createRenderer(name);
		this.setHardness((float)10);
        this.addEffectiveTool(ToolType.PICKAXE, 1);
        
        this.register();
	}
	
	@Override
	public int getLight(IWorld world, int x, int y, TileLayer layer)
	{
		return 60;
	}
	
	@Override
	public boolean isFullTile()
	{
		return false;
	}
	
	protected ITileRenderer<Tile> createRenderer(IResourceName name)
	{
		return new ConduitRenderer<Tile>(name);
    }
	
	@Override
    public BoundBox getBoundBox(final IWorld world, final int x, final int y) 
	{
        return null;
    }

	@Override
	public int getSideMode(int side) 
	{
		return 0;
	}

	@Override
	public void setSideMode(int side, int mode) 
	{
		// its not actually a conduit
	}

	@Override
	public boolean canConnectTo(Class<?> adjacentBlock) {
		return true;
	}

	@Override
	public void setSync() {
		// dont hurt me
	}
	
	 @Override
    public ITileRenderer<Tile> getRenderer(){
        return this.renderer;
    }
}

