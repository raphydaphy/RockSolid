package com.raphydaphy.rocksolid.tile;

import com.raphydaphy.rocksolid.render.BoilerRenderer;
import com.raphydaphy.rocksolid.util.ToolInfo;

import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileBoiler extends MultiTileBase
{

	public TileBoiler()
	{
		super("boiler", 5f, new ToolInfo(ToolType.PICKAXE, 1));
	}
	
	@Override
    protected ITileRenderer<TileBoiler> createRenderer(IResourceName name){
        return new BoilerRenderer(name, this);
    }

	@Override
	public int getWidth()
	{
		return 2;
	}

	public int getHeight()
	{
		return 5;
	}

	@Override
	protected boolean[][] makeStructure()
	{
		return super.autoStructure(5, 2);
	}

	public BoundBox getBoundBox(IWorld world, int x, int y, TileLayer layer)
	{
		return null;
	}

	public boolean isFullTile()
	{
		return false;
	}

}
