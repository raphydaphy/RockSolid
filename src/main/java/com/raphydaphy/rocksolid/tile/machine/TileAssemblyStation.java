package com.raphydaphy.rocksolid.tile.machine;

import com.raphydaphy.rocksolid.container.ContainerAssemblyStation;
import com.raphydaphy.rocksolid.gui.GuiAssemblyStation;
import com.raphydaphy.rocksolid.tileentity.TileEntityAssemblyStation;
import com.raphydaphy.rocksolid.util.ToolInfo;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.tile.entity.IFilteredInventory;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileAssemblyStation extends TileMachineBase<TileEntityAssemblyStation>
{
	public TileAssemblyStation()
	{
		super("assembly_station", TileEntityAssemblyStation.class,18, false,new ToolInfo(ToolType.PICKAXE, 6));
	}

	@Override
	protected boolean[][] makeStructure()
	{
		return super.autoStructure(2, 1);
	}

	@Override
	public int getWidth()
	{
		return 2;
	}

	@Override
	public int getHeight()
	{
		return 1;
	}

	@Override
	public TileEntity makeTE(IWorld world, int x, int y, TileLayer layer)
	{
		return new TileEntityAssemblyStation(world, x, y, layer);
	}

	@Override
	public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player)
	{
		TileEntityAssemblyStation te = getTE(world, world.getState(x, y), x, y);
		player.openGuiContainer(new GuiAssemblyStation(player, te), new ContainerAssemblyStation(player, te));
		return true;
	}

	@Override
	public void onRemoved(IWorld world, int x, int y, TileLayer layer)
	{
		if (!world.isClient())
		{
			TileEntityAssemblyStation tile = world.getTileEntity(x, y, TileEntityAssemblyStation.class);
			if (tile != null)
			{
				IFilteredInventory inv = tile.getInvHidden();
				if (inv != null)
				{
					tile.dropInventory(inv);
				}
			}
		}
	}
}

