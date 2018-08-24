package com.raphydaphy.rocksolid.tile.machine;

import com.raphydaphy.rocksolid.container.ContainerCokeOven;
import com.raphydaphy.rocksolid.gui.GuiCokeOven;
import com.raphydaphy.rocksolid.render.ActivatableRenderer;
import com.raphydaphy.rocksolid.tileentity.TileEntityCokeOven;
import com.raphydaphy.rocksolid.util.ToolInfo;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileCokeOven extends TileMachineBase<TileEntityCokeOven>
{

	public TileCokeOven()
	{
		super("coke_oven", TileEntityCokeOven.class, 17,false, new ToolInfo(ToolType.PICKAXE, 2));
	}

	@Override
	protected boolean[][] makeStructure()
	{
		return super.autoStructure(2, 2);
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
	protected ITileRenderer<MultiTile> createRenderer(ResourceName name)
	{
		return new ActivatableRenderer(name, this);
	}

	@Override
	public TileEntity makeTE(IWorld world, int x, int y, TileLayer layer)
	{
		return new TileEntityCokeOven(world, x, y, layer);
	}

	@Override
	public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player)
	{
		TileEntityCokeOven te = getTE(world, world.getState(x, y), x, y);
		player.openGuiContainer(new GuiCokeOven(player, te), new ContainerCokeOven(player, te));
		return true;
	}

	@Override
	public int getLight(IWorld world, int x, int y, TileLayer layer)
	{
		TileState state = world.getState(x, y);
		TileEntityCokeOven te = getTE(world, state, x, y);
		if (this.getInnerCoord(state).getY() == 0 && te != null && te.isActive())
		{
			return 30;
		}
		return 0;
	}

}
