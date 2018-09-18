package com.raphydaphy.rocksolid.tile;

import com.raphydaphy.rocksolid.render.CottonRenderer;
import de.ellpeck.rockbottom.api.StaticTileProps;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.state.IntProp;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.Collections;
import java.util.List;

public class TileCotton extends TileBase
{
	public static final IntProp COTTON_GROWTH = new IntProp("growth", 0, 10);

	public TileCotton()
	{
		super("cotton", 2, true, false);
		this.addProps(StaticTileProps.TOP_HALF, COTTON_GROWTH);
		register();
	}

	@Override
	protected final ITileRenderer createRenderer(ResourceName var1)
	{
		return new CottonRenderer(var1);
	}

	@Override
	public final boolean canStay(IWorld world, int x, int y, TileLayer layer, int changedX, int changedY, TileLayer changedLayer)
	{
		return world.getState(layer, x, y).get(StaticTileProps.TOP_HALF) || isValidPos(world, x, y, layer);
	}

	@Override
	public final boolean canPlace(IWorld world, int x, int y, TileLayer layer, AbstractEntityPlayer player)
	{
		return world.isPosLoaded((double) x, (double) (y - 1), false) && isValidPos(world, x, y, layer);
	}

	private static boolean isValidPos(IWorld world, int x, int y, TileLayer layer)
	{
		return world.getState(layer, x, y - 1).getTile().canKeepFarmablePlants(world, x, y, layer) && world.getState(TileLayer.LIQUIDS, x, y).getTile().isAir();
	}

	@Override
	public final boolean isFullTile()
	{
		return false;
	}

	@Override
	public final BoundBox getBoundBox(IWorld world, int x, int y, TileLayer layer)
	{
		return null;
	}

	@Override
	public final void updateRandomly(IWorld world, int x, int y, TileLayer layer)
	{
		TileState bottomHalf;
		int growth;
		if (Util.RANDOM.nextFloat() >= 0.95F && !(bottomHalf = world.getState(layer, x, y)).get(StaticTileProps.TOP_HALF) && (growth = bottomHalf.get(COTTON_GROWTH)) < 9)
		{
			if (growth >= 3)
			{
				TileState topHalf;
				if ((topHalf = world.getState(layer, x, y + 1)).getTile() == this)
				{
					world.setState(layer, x, y + 1, topHalf.prop(COTTON_GROWTH, growth + 1));
				} else
				{
					if (!world.getState(layer, x, y + 1).getTile().canReplace(world, x, y + 1, layer))
					{
						return;
					}

					world.setState(layer, x, y + 1, this.getDefState().prop(StaticTileProps.TOP_HALF, Boolean.TRUE).prop(COTTON_GROWTH, growth + 1));
				}
			}

			world.setState(layer, x, y, bottomHalf.prop(COTTON_GROWTH, growth + 1));
		}

	}

	@Override
	public final boolean onInteractWithBreakKey(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player)
	{
		TileState state;
		if ((state = world.getState(layer, x, y)).get(StaticTileProps.TOP_HALF) && state.get(COTTON_GROWTH) >= 9)
		{
			this.onDestroyed(world, x, y, null, layer, true);
			if (!world.isClient())
			{
				world.setState(layer, x, y, state.prop(COTTON_GROWTH, 5));
				world.setState(layer, x, y - 1, world.getState(layer, x, y - 1).prop(COTTON_GROWTH, 5));
			}
			return true;
		} else
		{
			return false;
		}
	}

	@Override
	public final List<ItemInstance> getDrops(IWorld world, int x, int y, TileLayer layer, Entity destroyer)
	{
		TileState state;
		return (state = world.getState(layer, x, y)).get(StaticTileProps.TOP_HALF) && state.get(COTTON_GROWTH) >= 9 ? Collections.singletonList(new ItemInstance(this, Util.RANDOM.nextInt(3) + 1)) : Collections.emptyList();
	}

	@Override
	public final void doBreak(IWorld world, int x, int y, TileLayer layer, AbstractEntityPlayer breaker, boolean isRightTool, boolean allowDrop)
	{
		if (!world.isClient())
		{
			isRightTool = allowDrop && (this.forceDrop || isRightTool);
			if ((allowDrop = world.getState(layer, x, y).get(StaticTileProps.TOP_HALF)) || world.getState(layer, x, y + 1).getTile() == this)
			{
				world.destroyTile(x, y + (allowDrop ? -1 : 1), layer, breaker, isRightTool);
			}

			world.destroyTile(x, y, layer, breaker, isRightTool);
		}

	}
}
