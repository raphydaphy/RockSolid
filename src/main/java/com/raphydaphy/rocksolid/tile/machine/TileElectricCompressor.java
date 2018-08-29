package com.raphydaphy.rocksolid.tile.machine;

import com.raphydaphy.rocksolid.container.ContainerElectricFurnace;
import com.raphydaphy.rocksolid.gui.GuiElectricFurnace;
import com.raphydaphy.rocksolid.render.ActivatableRenderer;
import com.raphydaphy.rocksolid.tileentity.TileEntityElectricCompressor;
import com.raphydaphy.rocksolid.util.ToolInfo;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ToolProperty;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileElectricCompressor extends TileMachineBase<TileEntityElectricCompressor>
{
	public TileElectricCompressor()
	{
		super("electric_compressor", TileEntityElectricCompressor.class, 25, false, new ToolInfo(ToolProperty.PICKAXE, 6));
	}

	@Override
	protected boolean[][] makeStructure()
	{
		return super.autoStructure(3, 2);
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
	protected ITileRenderer createRenderer(ResourceName name)
	{
		return new ActivatableRenderer(name, this);
	}

	@Override
	public TileEntity makeTE(IWorld world, int x, int y, TileLayer layer)
	{
		return new TileEntityElectricCompressor(world, x, y, layer);
	}

	@Override
	public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player)
	{
		TileEntityElectricCompressor te = getTE(world, world.getState(x, y), x, y);
		System.out.println(te);
		player.openGuiContainer(new GuiElectricFurnace(player, te), new ContainerElectricFurnace(player, te));
		return true;
	}
}
