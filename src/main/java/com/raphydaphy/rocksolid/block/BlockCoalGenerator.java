package com.raphydaphy.rocksolid.block;

import com.raphydaphy.rocksolid.gui.GuiCoalGenerator;
import com.raphydaphy.rocksolid.gui.container.ContainerCoalGenerator;
import com.raphydaphy.rocksolid.render.CoalGeneratorRenderer;
import com.raphydaphy.rocksolid.tileentity.TileEntityCoalGenerator;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class BlockCoalGenerator extends MultiTile
{
	protected final ITileRenderer<Tile> renderer;
	
	public BlockCoalGenerator(IResourceName name) 
	{
		super(name);
		this.renderer = this.createRenderer(name);
		this.setHardness((float)20);
        this.addEffectiveTool(ToolType.PICKAXE, 1);
	}
	
	@Override
	public TileEntity provideTileEntity(IWorld world, int x, int y)
	{
        return new TileEntityCoalGenerator(world, x, y);
    }
	
	protected ITileRenderer createRenderer(final IResourceName name) 
	{
		return new CoalGeneratorRenderer(name, this);
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
		TileEntityCoalGenerator tile = world.getTileEntity(main.getX(), main.getY(), TileEntityCoalGenerator.class);
		
		if (tile != null)
		{
			player.openGuiContainer(new GuiCoalGenerator(player, tile), new ContainerCoalGenerator(player, tile));
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
            final TileEntityCoalGenerator tile = world.getTileEntity(main.getX(), main.getY(), TileEntityCoalGenerator.class);
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

	@Override
	protected boolean[][] makeStructure() {
		return new boolean[][] { { true, true, true }, { true, true, true } };
	}

	@Override
	public int getWidth() {
		return 3;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 2;
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
