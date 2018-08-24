package com.raphydaphy.rocksolid.tile.machine;

import com.raphydaphy.rocksolid.container.ContainerEmpty;
import com.raphydaphy.rocksolid.gui.GuiTurbine;
import com.raphydaphy.rocksolid.tileentity.TileEntityTurbine;
import com.raphydaphy.rocksolid.util.ToolInfo;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileTurbine extends TileMachineBase<TileEntityTurbine>
{

	public TileTurbine()
	{
		super("turbine", TileEntityTurbine.class,20, true,new ToolInfo(ToolType.PICKAXE, 6));
	}

	@Override
	protected boolean[][] makeStructure()
	{
		return super.autoStructure(3,2);
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
	public TileEntity makeTE(IWorld world, int x, int y, TileLayer layer)
	{
		return new TileEntityTurbine(world, x, y, layer);
	}

	@Override
	public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player)
	{
		TileEntityTurbine te = getTE(world, world.getState(x, y), x, y);
		player.openGuiContainer(new GuiTurbine(player, te), new ContainerEmpty(player, 0, 41));
		return true;
	}

}

