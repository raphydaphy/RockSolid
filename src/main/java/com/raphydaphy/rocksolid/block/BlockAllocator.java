package com.raphydaphy.rocksolid.block;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Input;

import com.raphydaphy.rocksolid.gui.GuiAllocator;
import com.raphydaphy.rocksolid.gui.GuiConduitConfig;
import com.raphydaphy.rocksolid.gui.container.ContainerAllocator;
import com.raphydaphy.rocksolid.item.ItemWrench;
import com.raphydaphy.rocksolid.render.ConduitRenderer;
import com.raphydaphy.rocksolid.tileentity.TileEntityAllocator;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ToolType;
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
		this.setHardness((float)20);
        this.addEffectiveTool(ToolType.PICKAXE, 1);
	}
	
	@Override
	public TileEntity provideTileEntity(IWorld world, int x, int y)
	{
        return new TileEntityAllocator(world, x, y);
    }
	
	protected ITileRenderer<Tile> createRenderer(IResourceName name)
	{
		return new ConduitRenderer<Tile>(name);
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
			Input input = RockBottomAPI.getGame().getInput();
            if (player.getInvContainer().getSlot(player.getSelectedSlot()).get() != null) 
            {
            	
            	if (player.getInvContainer().getSlot(player.getSelectedSlot()).get().getItem() instanceof ItemWrench)
            	{
            		if (input.isKeyDown(Keyboard.KEY_LSHIFT))
            		{
            			world.destroyTile(x, y, TileLayer.MAIN, player, true);
            			return true;
            		}
            		else
            		{
            			player.openGuiContainer(new GuiConduitConfig(player, tile), new ContainerAllocator(player, tile));
            			return true;
            		}
            	}
            }
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
	public void onChangeAround(IWorld world, int x, int y, TileLayer layer, int changedX, int changedY, TileLayer changedLayer)
	{
		super.onChangeAround(world, x, y, layer, changedX, changedY, changedLayer);
		
		TileEntityAllocator tile = (TileEntityAllocator) world.getTileEntity(x, y);
		
		if (tile != null)
		{
			tile.onChangedAround(world, x, y, layer, changedX, changedY, changedLayer);
		}
	}

}
