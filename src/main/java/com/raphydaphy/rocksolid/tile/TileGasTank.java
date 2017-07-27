package com.raphydaphy.rocksolid.tile;

import java.util.List;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.api.gui.ContainerEmpty;
import com.raphydaphy.rocksolid.gui.GuiGasTank;
import com.raphydaphy.rocksolid.init.ModItems;
import com.raphydaphy.rocksolid.render.GasTankRenderer;
import com.raphydaphy.rocksolid.tileentity.TileEntityGasTank;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
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

public class TileGasTank extends MultiTile
{
	private static final String name = "gasTank";
	private final IResourceName desc = RockBottomAPI.createRes(RockSolid.INSTANCE,"details." + name);
	
	public TileGasTank() 
	{
		super(RockSolidLib.makeRes(name));
		this.setHardness((float)20);
        this.addEffectiveTool(ToolType.PICKAXE, 1);
        this.register();
	}
	
	@Override
	public TileEntity provideTileEntity(IWorld world, int x, int y)
	{
        return new TileEntityGasTank(world, x, y);
    }
	
	@Override
	protected ITileRenderer<TileGasTank> createRenderer(final IResourceName name) 
	{
		return new GasTankRenderer(name, this);
    }

	@Override
    public boolean canProvideTileEntity(){
        return true;
    }
	
	@Override
	public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player)
	{
		ItemInstance selected = player.getInv().get(player.getSelectedSlot());
		if (selected != null)
		{
			if (selected.getItem().equals(ModItems.bucket))
			{
				return false;
			}
		}
		Pos2 main = this.getMainPos(x, y, world.getState(x,  y));
		TileEntityGasTank tile = world.getTileEntity(main.getX(), main.getY(), TileEntityGasTank.class);
		if (tile != null)
		{
			player.openGuiContainer(new GuiGasTank(player, tile), new ContainerEmpty(player));
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
	protected boolean[][] makeStructure() 
	{
		return new boolean[][] { { true}, { true} };
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
	
	public void describeItem(IAssetManager manager, ItemInstance instance, List<String> desc, boolean isAdvanced) {
        super.describeItem(manager, instance, desc, isAdvanced);
        desc.addAll(manager.getFont().splitTextToLength(500,1f,true, manager.localize(this.desc)));
    }

}