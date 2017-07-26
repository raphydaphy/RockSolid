package com.raphydaphy.rocksolid.tile;

import java.util.List;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.api.gui.ContainerBasicIO;
import com.raphydaphy.rocksolid.gui.GuiElectricPurifier;
import com.raphydaphy.rocksolid.render.ElectricPurifierRenderer;
import com.raphydaphy.rocksolid.tileentity.TileEntityElectricPurifier;
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

public class TileElectricPurifier extends MultiTile
{
	private static final String name = "electricPurifier";
	private final IResourceName desc = RockBottomAPI.createRes(RockSolid.INSTANCE,"details." + name);
	
	public TileElectricPurifier() 
	{
		super(RockSolidLib.makeRes(name));
		this.setHardness(15);
        this.addEffectiveTool(ToolType.PICKAXE, 1);
        this.register();
	}
	@Override
    protected ITileRenderer<TileElectricPurifier> createRenderer(final IResourceName name) {
        return new ElectricPurifierRenderer(name, this);
    }
	
	@Override
    public int getLight(final IWorld world, final int x, final int y, final TileLayer layer) 
	{
		TileEntity mainTile = RockSolidLib.getTileFromPos(x, y, world);
        if (mainTile != null && ((TileEntityElectricPurifier)mainTile).isActive()) 
        {
            return 30;
        }
        return 0;
    }
	
	@Override
	public TileEntity provideTileEntity(IWorld world, int x, int y){
        return this.isMainPos(x,  y,  world.getState(x,  y)) ? new TileEntityElectricPurifier(world, x, y) : null;
    }

	@Override
    public boolean canProvideTileEntity(){
        return true;
    }
	
	@Override
	public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player)
	{
		Pos2 main = this.getMainPos(x, y, world.getState(x,  y));
		TileEntityElectricPurifier tile = world.getTileEntity(main.getX(), main.getY(), TileEntityElectricPurifier.class);
		
		if (tile != null)
		{
			player.openGuiContainer(new GuiElectricPurifier(player, tile), new ContainerBasicIO(player, tile));
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
            final Pos2 main = this.getMainPos(x, y, world.getState(x, y));
            final TileEntityElectricPurifier tile = world.getTileEntity(main.getX(), main.getY(), TileEntityElectricPurifier.class);
            if (tile != null) 
            {
                tile.dropInventory(tile.inventory);
            }
        }
    }
	
	@Override
	protected boolean[][] makeStructure() 
	{
		return new boolean[][] { { true, true }, { true, true } };
	}

	@Override
	public int getWidth() 
	{
		return 2;
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
