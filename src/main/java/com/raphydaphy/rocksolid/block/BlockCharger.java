package com.raphydaphy.rocksolid.block;

import java.util.List;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.gui.GuiCharger;
import com.raphydaphy.rocksolid.gui.container.ContainerCharger;
import com.raphydaphy.rocksolid.render.ChargerRenderer;
import com.raphydaphy.rocksolid.tileentity.TileEntityCharger;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.Entity;
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

public class BlockCharger extends MultiTile
{
	private static final String name = "charger";
	private final IResourceName desc = RockBottomAPI.createRes(RockSolid.INSTANCE,"details." + name);
	
	public BlockCharger() 
	{
		super(RockSolidLib.makeRes(name));
		this.setHardness((float)20);
        this.addEffectiveTool(ToolType.PICKAXE, 1);
        this.register();
	}
	
	@Override
    public int getLight(final IWorld world, final int x, final int y, final TileLayer layer) 
	{
		TileEntity mainTile = RockSolidLib.getTileFromPos(x, y, world);
        if (mainTile != null && ((TileEntityCharger)mainTile).isActive()) 
        {
            return 50;
        }
        return 0;
    }
	
	@Override
	public TileEntity provideTileEntity(IWorld world, int x, int y)
	{
        return new TileEntityCharger(world, x, y);
    }
	
	protected ITileRenderer<BlockCharger> createRenderer(final IResourceName name) 
	{
		return new ChargerRenderer(name, this);
    }

	@Override
    public boolean canProvideTileEntity(){
        return true;
    }
	
	@Override
	public boolean onInteractWith(IWorld world, int x, int y, double mouseX, double mouseY, AbstractEntityPlayer player)
	{
		Pos2 main = this.getMainPos(x, y, world.getMeta(x,  y));
		TileEntityCharger tile = world.getTileEntity(main.getX(), main.getY(), TileEntityCharger.class);
		
		if (tile != null)
		{
			player.openGuiContainer(new GuiCharger(player, tile), new ContainerCharger(player, tile));
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
            final TileEntityCharger tile = world.getTileEntity(main.getX(), main.getY(), TileEntityCharger.class);
            if (tile != null) 
            {
                tile.dropInventory(tile.inventory);
            }
        }
    }
	
	@Override
	public boolean canPlace(IWorld world, int x, int y, TileLayer layer)
	{
		super.canPlace(world, x, y, layer);
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
	protected boolean[][] makeStructure() 
	{
		return new boolean[][] { { true, true, true }, { true, true, true } };
	}

	@Override
	public int getWidth() 
	{
		return 3;
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
	
	public void describeItem(IAssetManager manager, ItemInstance instance, List<String> desc, boolean isAdvanced) {
        super.describeItem(manager, instance, desc, isAdvanced);
        desc.addAll(manager.getFont().splitTextToLength(500,1f,true, manager.localize(this.desc)));
    }

}
