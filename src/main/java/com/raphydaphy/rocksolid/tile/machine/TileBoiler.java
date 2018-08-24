package com.raphydaphy.rocksolid.tile.machine;

import com.raphydaphy.rocksolid.container.ContainerBoiler;
import com.raphydaphy.rocksolid.gui.GuiBoiler;
import com.raphydaphy.rocksolid.render.BoilerRenderer;
import com.raphydaphy.rocksolid.tileentity.TileEntityBoiler;
import com.raphydaphy.rocksolid.util.ModUtils;
import com.raphydaphy.rocksolid.util.ToolInfo;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.particle.IParticleManager;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileBoiler extends TileMachineBase<TileEntityBoiler>
{
	public TileBoiler()
	{
		super("boiler", TileEntityBoiler.class,20f,true, new ToolInfo(ToolType.PICKAXE, 6));
	}

	@Override
	protected ITileRenderer<TileBoiler> createRenderer(ResourceName name)
	{
		return new BoilerRenderer(name, this);
	}

	@Override
	public int getWidth()
	{
		return 2;
	}

	@Override
	public int getHeight()
	{
		return 5;
	}

	@Override
	protected boolean[][] makeStructure()
	{
		return super.autoStructure(2,5);
	}

	public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY,
			AbstractEntityPlayer player)
	{
		TileEntityBoiler te = getTE(world, world.getState(x, y),x, y);
		player.openGuiContainer(new GuiBoiler(player, te), new ContainerBoiler(player, te));
		return true;
	}

	@Override
	public TileEntity makeTE(IWorld world, int x, int y, TileLayer layer)
	{
		return new TileEntityBoiler(world, x, y, layer);
	}

	@Override
	public void updateRandomlyInPlayerView(IWorld world, int x, int y, TileLayer layer, TileState state, IParticleManager manager)
	{
		Pos2 innerCoord = this.getInnerCoord(state);

		if (innerCoord.getY() == 4)
		{
			ModUtils.smokeParticle(world, x, y, manager, getTE(world, world.getState(x, y), x, y));
		}
	}

	@Override
	public int getLight(IWorld world, int x, int y, TileLayer layer)
	{
		TileState state = world.getState(x, y);
		TileEntityBoiler te = getTE(world, state, x, y);
		if (this.getInnerCoord(state).getY() == 0 && te != null && te.isActive())
		{
			return 30;
		}
		return 0;
	}

}
