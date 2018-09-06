package com.raphydaphy.rocksolid.tile.machine;

import com.raphydaphy.rocksolid.container.ContainerAssemblyStation;
import com.raphydaphy.rocksolid.container.ContainerPrecisionAssembler;
import com.raphydaphy.rocksolid.gui.GuiAssemblyStation;
import com.raphydaphy.rocksolid.gui.GuiPrecisionAssembler;
import com.raphydaphy.rocksolid.tileentity.TileEntityAssemblyStation;
import com.raphydaphy.rocksolid.tileentity.TileEntityPrecisionAssembler;
import com.raphydaphy.rocksolid.util.ToolInfo;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ToolProperty;
import de.ellpeck.rockbottom.api.tile.entity.IFilteredInventory;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TilePrecisionAssembler extends TileMachineBase<TileEntityPrecisionAssembler>
{
	public TilePrecisionAssembler()
	{
		super("precision_assembler", TileEntityPrecisionAssembler.class,23, false, new ToolInfo(ToolProperty.PICKAXE, 11));
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
		return new TileEntityPrecisionAssembler(world, x, y, layer);
	}

	@Override
	public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player)
	{
		TileEntityPrecisionAssembler te = getTE(world, world.getState(x, y), x, y);
		player.openGuiContainer(new GuiPrecisionAssembler(player, te), new ContainerPrecisionAssembler(player, te));
		return true;
	}

	@Override
	public void onRemoved(IWorld world, int x, int y, TileLayer layer)
	{
		if (!world.isClient())
		{
			TileEntityPrecisionAssembler tile = world.getTileEntity(x, y, TileEntityPrecisionAssembler.class);
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

