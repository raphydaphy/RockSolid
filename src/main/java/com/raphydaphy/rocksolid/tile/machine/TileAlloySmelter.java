package com.raphydaphy.rocksolid.tile.machine;

import com.raphydaphy.rocksolid.container.ContainerAlloySmelter;
import com.raphydaphy.rocksolid.gui.GuiAlloySmelter;
import com.raphydaphy.rocksolid.render.ActivatableRenderer;
import com.raphydaphy.rocksolid.tileentity.TileEntityAlloySmelter;
import com.raphydaphy.rocksolid.util.ModUtils;
import com.raphydaphy.rocksolid.util.ToolInfo;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ToolProperty;
import de.ellpeck.rockbottom.api.particle.IParticleManager;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileAlloySmelter extends TileMachineBase<TileEntityAlloySmelter>
{

	public TileAlloySmelter()
	{
		super("alloy_smelter", TileEntityAlloySmelter.class,15f, false,new ToolInfo(ToolProperty.PICKAXE, 2));
	}

	@Override
	protected ITileRenderer<MultiTile> createRenderer(ResourceName name)
	{
		return new ActivatableRenderer(name, this);
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
	protected boolean[][] makeStructure()
	{
		return super.autoStructure(1, 2);
	}

	public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player)
	{
		TileEntityAlloySmelter te = getTE(world, world.getState(x, y), x, y);
		player.openGuiContainer(new GuiAlloySmelter(player, te), new ContainerAlloySmelter(player, te));
		return true;
	}

	@Override
	public TileEntity makeTE(IWorld world, int x, int y, TileLayer layer)
	{
		return new TileEntityAlloySmelter(world, x, y, layer);
	}

	@Override
	public void updateRandomlyInPlayerView(IWorld world, int x, int y, TileLayer layer, TileState state, IParticleManager manager)
	{
		Pos2 innerCoord = this.getInnerCoord(state);

		if (innerCoord.getY() == 1)
		{
			ModUtils.smokeParticle(world, x, y, manager, getTE(world, world.getState(x, y), x, y));
		}
	}

	@Override
	public int getLight(IWorld world, int x, int y, TileLayer layer)
	{
		TileState state = world.getState(x, y);
		TileEntityAlloySmelter te = getTE(world, state, x, y);
		if (this.getInnerCoord(state).getY() == 0 && te != null && te.isActive())
		{
			return 30;
		}
		return 0;
	}

}
