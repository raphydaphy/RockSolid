package com.raphydaphy.rocksolid.tile;

import com.raphydaphy.rocksolid.render.LampRenderer;

import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class TileLamp extends Tile
{
	private ITileRenderer<Tile> renderer;
	public TileLamp(IResourceName name) 
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
		return new LampRenderer<Tile>(name);
    }
	
	@Override
    public BoundBox getBoundBox(final IWorld world, final int x, final int y) 
	{
        return null;
    }
	
	@Override
    public ITileRenderer<Tile> getRenderer(){
        return this.renderer;
    }
}

