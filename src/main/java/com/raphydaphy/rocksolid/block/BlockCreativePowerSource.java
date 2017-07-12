package com.raphydaphy.rocksolid.block;

import java.util.List;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.gui.GuiCreativePowerSource;
import com.raphydaphy.rocksolid.gui.container.ContainerEmpty;
import com.raphydaphy.rocksolid.tileentity.TileEntityCreativePowerSource;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.tile.TileBasic;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class BlockCreativePowerSource extends TileBasic
{
	private static final String name = "creativePowerSource";
	private final IResourceName desc = RockBottomAPI.createRes(RockSolid.INSTANCE,"details." + name);
	
	public BlockCreativePowerSource() 
	{
		super(RockSolidLib.makeRes(name));
		this.setHardness((float)20);
        this.addEffectiveTool(ToolType.PICKAXE, 1);
        this.register();
	}
	
	@Override
	public TileEntity provideTileEntity(IWorld world, int x, int y)
	{
        return new TileEntityCreativePowerSource(world, x, y);
    }
	
	@Override
    public int getLight(final IWorld world, final int x, final int y, final TileLayer layer) {
        return 20;
    }
	

	@Override
    public boolean canProvideTileEntity(){
        return true;
    }
	
	@Override
	public boolean onInteractWith(IWorld world, int x, int y, AbstractEntityPlayer player)
	{
		TileEntityCreativePowerSource tile = world.getTileEntity(x, y, TileEntityCreativePowerSource.class);
		
		if (tile != null)
		{
			player.openGuiContainer(new GuiCreativePowerSource(player, tile), new ContainerEmpty(player));
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

	
	public void describeItem(IAssetManager manager, ItemInstance instance, List<String> desc, boolean isAdvanced) {
        super.describeItem(manager, instance, desc, isAdvanced);
        desc.addAll(manager.getFont().splitTextToLength(500,1f,true, manager.localize(this.desc)));
    }

}
