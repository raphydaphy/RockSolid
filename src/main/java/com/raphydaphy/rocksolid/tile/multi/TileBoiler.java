package com.raphydaphy.rocksolid.tile.multi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.raphydaphy.rocksolid.container.ContainerBoiler;
import com.raphydaphy.rocksolid.gui.GuiBoiler;
import com.raphydaphy.rocksolid.render.BoilerRenderer;
import com.raphydaphy.rocksolid.tileentity.TileEntityBoiler;
import com.raphydaphy.rocksolid.util.ToolInfo;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileBoiler extends MultiTileBase
{

	public TileBoiler()
	{
		super("boiler", 7f, new ToolInfo(ToolType.PICKAXE, 1));
	}

	@Override
	public boolean canPlaceInLayer(TileLayer layer)
	{
		return layer == TileLayer.MAIN;
	}

	@Override
	protected ITileRenderer<TileBoiler> createRenderer(IResourceName name)
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
		return super.autoStructure(5, 2);
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

	public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY,
			AbstractEntityPlayer player)
	{
		TileEntityBoiler te = getTE(world, world.getState(x, y),x, y);
		player.openGuiContainer(new GuiBoiler(player, te), new ContainerBoiler(player, te));
		return true;
	}

	@Override
	public TileEntity provideTileEntity(IWorld world, int x, int y, TileLayer layer)
	{
		TileState state = world.getState(x, y);
		return layer == TileLayer.MAIN && this.isMainPos(x, y, state) ? new TileEntityBoiler(world, x, y, layer) : null;
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

		TileEntityBoiler te = this.getTE(world,world.getState(x, y), x, y);

		if (te != null)
		{
			drops.add(te.getInventory().get(0));
		}

		return drops;
	}

	@Override
	public void updateRandomlyForRendering(IWorld world, int x, int y, TileLayer layer, AbstractEntityPlayer player)
	{
		TileState state = world.getState(x, y);
		Pos2 innerCoord = this.getInnerCoord(state);

		if (innerCoord.getY() == 4)
		{
			TileEntityBoiler te = this.getTE(world,world.getState(x, y), x, y);
			Random rand = new Random();

			if (rand.nextInt(2) == 1 && te.isActive())
			{
				double particleX = x + (innerCoord.getX() == 0 ? .55 : .24) + (rand.nextFloat() / 40);
				double particleY = y + (innerCoord.getX() == 0 ? .9 : .65);
				RockBottomAPI.getGame().getParticleManager().addSmokeParticle(world, particleX, particleY, 0, 0.02,
						0.2f + (rand.nextFloat() / 20));
			}
		}
	}

	public TileEntityBoiler getTE(IWorld world, TileState state, int x, int y)
	{
		Pos2 main = this.getMainPos(x, y, state);
		return world.getTileEntity(main.getX(), main.getY(), TileEntityBoiler.class);
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
