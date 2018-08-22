package com.raphydaphy.rocksolid.tile.multi;

import com.raphydaphy.rocksolid.container.ContainerArcFurnace;
import com.raphydaphy.rocksolid.gui.GuiArcFurnace;
import com.raphydaphy.rocksolid.render.ActivatableRenderer;
import com.raphydaphy.rocksolid.tileentity.TileEntityArcFurnace;
import com.raphydaphy.rocksolid.util.ToolInfo;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
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

import java.util.ArrayList;
import java.util.List;

public class TileArcFurnace extends MultiTileBase
{

	public TileArcFurnace()
	{
		super("arc_furnace", 17, new ToolInfo(ToolType.PICKAXE, 2));
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
	public BoundBox getBoundBox(IWorld world, int x, int y, TileLayer layer)
	{
		return null;
	}

	@Override
	public boolean isFullTile()
	{
		return false;
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
	public TileEntity provideTileEntity(IWorld world, int x, int y, TileLayer layer)
	{
		TileState state = world.getState(x, y);
		return layer == TileLayer.MAIN && this.isMainPos(x, y, state) ? new TileEntityArcFurnace(world, x, y, layer) : null;
	}

	@Override
	public boolean canProvideTileEntity()
	{
		return true;
	}

	public TileEntityArcFurnace getTE(IWorld world, TileState state, int x, int y)
	{
		Pos2 main = this.getMainPos(x, y, state);
		return world.getTileEntity(main.getX(), main.getY(), TileEntityArcFurnace.class);
	}

	@Override
	public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player)
	{
		TileEntityArcFurnace te = getTE(world, world.getState(x, y), x, y);
		player.openGuiContainer(new GuiArcFurnace(player, te), new ContainerArcFurnace(player, te));
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

		TileEntityArcFurnace te = this.getTE(world, world.getState(x, y), x, y);

		if (te != null)
		{
			drops.add(te.getTileInventory().get(0));
			drops.add(te.getTileInventory().get(1));
		}

		return drops;
	}

	@Override
	public int getLight(IWorld world, int x, int y, TileLayer layer)
	{
		TileState state = world.getState(x, y);
		TileEntityArcFurnace te = getTE(world, state, x, y);
		if (this.getInnerCoord(state).getY() == 0 && te != null && te.isActive())
		{
			return 30;
		}
		return 0;
	}

}
