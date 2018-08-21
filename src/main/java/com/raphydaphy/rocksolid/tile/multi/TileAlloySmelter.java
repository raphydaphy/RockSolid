package com.raphydaphy.rocksolid.tile.multi;

import com.raphydaphy.rocksolid.container.ContainerAlloySmelter;
import com.raphydaphy.rocksolid.gui.GuiAlloySmelter;
import com.raphydaphy.rocksolid.render.ActivatableRenderer;
import com.raphydaphy.rocksolid.tileentity.TileEntityAlloySmelter;
import com.raphydaphy.rocksolid.util.ModUtils;
import com.raphydaphy.rocksolid.util.ToolInfo;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.particle.IParticleManager;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.ArrayList;
import java.util.List;

public class TileAlloySmelter extends MultiTileBase
{

	public TileAlloySmelter()
	{
		super("alloy_smelter", 6f, new ToolInfo(ToolType.PICKAXE, 1));
	}

	@Override
	public boolean canPlaceInLayer(TileLayer layer)
	{
		return layer == TileLayer.MAIN;
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

	@Override
	public BoundBox getBoundBox(IWorld world, int x, int y, TileLayer layer)
	{
		return null;
	}

	@Override
	public boolean isFullTile()
	{
		return false;
	}

	public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player)
	{
		TileEntityAlloySmelter te = getTE(world, world.getState(x, y), x, y);
		player.openGuiContainer(new GuiAlloySmelter(player, te), new ContainerAlloySmelter(player, te));
		return true;
	}

	@Override
	public TileEntity provideTileEntity(IWorld world, int x, int y, TileLayer layer)
	{
		TileState state = world.getState(x, y);
		return layer == TileLayer.MAIN && this.isMainPos(x, y, state) ? new TileEntityAlloySmelter(world, x, y, layer) : null;
	}

	@Override
	public boolean canProvideTileEntity()
	{
		return true;
	}

	@Override
	public List<ItemInstance> getDrops(IWorld world, int x, int y, TileLayer layer, Entity destroyer)
	{
		List<ItemInstance> drops = new ArrayList<>();

		Item item = this.getItem();

		if (item != null)
		{
			drops.add(new ItemInstance(item));
		}

		TileEntityAlloySmelter te = this.getTE(world, world.getState(x, y), x, y);

		if (te != null)
		{
			drops.add(te.getTileInventory().get(0));
			drops.add(te.getTileInventory().get(1));
		}

		return drops;
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

	public TileEntityAlloySmelter getTE(IWorld world, TileState state, int x, int y)
	{
		Pos2 main = this.getMainPos(x, y, state);
		return world.getTileEntity(main.getX(), main.getY(), TileEntityAlloySmelter.class);
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
