package com.raphydaphy.rocksolid.tile.multi;

import com.raphydaphy.rocksolid.container.ContainerSmelter;
import com.raphydaphy.rocksolid.gui.GuiSmelter;
import com.raphydaphy.rocksolid.render.FueledTERenderer;
import com.raphydaphy.rocksolid.tileentity.TileEntitySmelter;
import com.raphydaphy.rocksolid.util.ToolInfo;
import de.ellpeck.rockbottom.api.RockBottomAPI;
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
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TileSmelter extends MultiTileBase
{

	public TileSmelter()
	{
		super("smelter", 6f, new ToolInfo(ToolType.PICKAXE, 1));
	}

	@Override
	public boolean canPlaceInLayer(TileLayer layer)
	{
		return layer == TileLayer.MAIN;
	}

	@Override
	protected ITileRenderer<MultiTile> createRenderer(IResourceName name)
	{
		return new FueledTERenderer(name, this);
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
		TileEntitySmelter te = getTE(world, world.getState(x, y), x, y);
		player.openGuiContainer(new GuiSmelter(player, te), new ContainerSmelter(player, te));
		return true;
	}

	@Override
	public TileEntity provideTileEntity(IWorld world, int x, int y, TileLayer layer)
	{
		TileState state = world.getState(x, y);
		return layer == TileLayer.MAIN && this.isMainPos(x, y, state) ? new TileEntitySmelter(world, x, y, layer) : null;
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

		TileEntitySmelter te = this.getTE(world, world.getState(x, y), x, y);

		if (te != null)
		{
			drops.add(te.getInventory().get(0));
			drops.add(te.getInventory().get(1));
		}

		return drops;
	}

	@Override
	public void updateRandomlyForRendering(IWorld world, int x, int y, TileLayer layer, AbstractEntityPlayer player)
	{
		TileState state = world.getState(x, y);
		Pos2 innerCoord = this.getInnerCoord(state);

		if (innerCoord.getY() == 1)
		{
			TileEntitySmelter te = this.getTE(world, world.getState(x, y), x, y);
			Random rand = new Random();

			if (te.isActive())
			{
				boolean left = rand.nextBoolean();
				double particleX = x + (left ? .05 : .74) + (rand.nextFloat() / 40);
				double particleY = y + (left ? .9 : .7);
				RockBottomAPI.getGame().getParticleManager().addSmokeParticle(world, particleX, particleY, 0, 0.02, 0.2f + (rand.nextFloat() / 20));
			}
		}
	}

	public TileEntitySmelter getTE(IWorld world, TileState state, int x, int y)
	{
		Pos2 main = this.getMainPos(x, y, state);
		return world.getTileEntity(main.getX(), main.getY(), TileEntitySmelter.class);
	}

	@Override
	public int getLight(IWorld world, int x, int y, TileLayer layer)
	{
		TileState state = world.getState(x, y);
		TileEntitySmelter te = getTE(world, state, x, y);
		if (this.getInnerCoord(state).getY() == 0 && te != null && te.isActive())
		{
			return 30;
		}
		return 0;
	}

}
