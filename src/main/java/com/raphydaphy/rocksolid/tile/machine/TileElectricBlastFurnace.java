package com.raphydaphy.rocksolid.tile.machine;

import com.raphydaphy.rocksolid.container.ContainerElectricFurnace;
import com.raphydaphy.rocksolid.gui.GuiElectricFurnace;
import com.raphydaphy.rocksolid.render.ElectricBlastFurnaceRenderer;
import com.raphydaphy.rocksolid.tileentity.TileEntityElectricBlastFurnace;
import com.raphydaphy.rocksolid.util.ToolInfo;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileElectricBlastFurnace extends TileMachineBase<TileEntityElectricBlastFurnace>
{
	public TileElectricBlastFurnace()
	{
		super("electric_blast_furnace", TileEntityElectricBlastFurnace.class, 25,false, new ToolInfo(ToolType.PICKAXE, 6));
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
	protected ITileRenderer<TileElectricBlastFurnace> createRenderer(ResourceName name)
	{
		return new ElectricBlastFurnaceRenderer(name, this);
	}

	@Override
	public TileEntity makeTE(IWorld world, int x, int y, TileLayer layer)
	{
		return new TileEntityElectricBlastFurnace(world, x, y, layer);
	}

	@Override
	public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player)
	{
		TileEntityElectricBlastFurnace te = getTE(world, world.getState(x, y), x, y);
		player.openGuiContainer(new GuiElectricFurnace(player, te), new ContainerElectricFurnace(player, te));
		return true;
	}

	@Override
	public int getLight(IWorld world, int x, int y, TileLayer layer)
	{
		TileState state = world.getState(x, y);
		TileEntityElectricBlastFurnace te = getTE(world, state, x, y);
		if (this.getInnerCoord(state).getY() == 0 && te != null && te.isActive())
		{
			return 30;
		}
		return 0;
	}

}
