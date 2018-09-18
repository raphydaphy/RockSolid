package com.raphydaphy.rocksolid.tile;

import com.raphydaphy.rocksolid.render.CottonRenderer;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.StaticTileProps;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.state.BoolProp;
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
	public static final BoolProp ALIVE = new BoolProp("alive", false);

	public TileCotton()
	{
		super("cotton", 2, true, false);
		this.addProps(StaticTileProps.TOP_HALF, COTTON_GROWTH, ALIVE);
		register();
	}

	@Override
	protected final ITileRenderer createRenderer(ResourceName name)
	{
		return new CottonRenderer(name);
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
		TileState state = world.getState(layer, x, y);
		if (!state.get(StaticTileProps.TOP_HALF))
		{
			int growth = state.get(COTTON_GROWTH);

			boolean left = true;
			TileState liquid = world.getState(TileLayer.LIQUIDS, x - 1, y - 1);
			if (liquid.getTile() != GameContent.TILE_WATER)
			{
				left = false;
				liquid = world.getState(TileLayer.LIQUIDS, x + 1, y - 1);
			}

			boolean wasAlive = state.get(ALIVE);
			System.out.println("light: " + world.getCombinedLight(x, y));
			boolean alive = liquid.getTile() == GameContent.TILE_WATER && world.getCombinedLight(x, y) > 50;
			if (wasAlive != alive)
			{
				if (alive)
				{
					world.setState(layer, x, y, state.prop(ALIVE, true));
					if (growth >= 4)
					{
						TileState up = world.getState(layer, x, y + 1);
						if (up.getTile() == this)
						{
							world.setState(layer, x, y + 1, up.prop(ALIVE, true));
						}
					}
				} else
				{
					world.setState(layer, x, y, state.prop(ALIVE, false));
					if (growth >= 4)
					{
						TileState up = world.getState(layer, x, y + 1);
						if (up.getTile() == this)
						{
							world.setState(layer, x, y + 1, up.prop(ALIVE, false));
						}
					}
				}
			}

			if (Util.RANDOM.nextFloat() >= 0.95F && growth < 9)
			{
				if (alive)
				{
					if (growth >= 3)
					{
						TileState topHalf;
						if ((topHalf = world.getState(layer, x, y + 1)).getTile() == this)
						{
							world.setState(layer, x, y + 1, topHalf.prop(COTTON_GROWTH, growth + 1).prop(ALIVE, true));
						} else
						{
							if (!world.getState(layer, x, y + 1).getTile().canReplace(world, x, y + 1, layer))
							{
								return;
							}

							world.setState(layer, x, y + 1, this.getDefState().prop(StaticTileProps.TOP_HALF, Boolean.TRUE).prop(COTTON_GROWTH, growth + 1).prop(ALIVE, true));
						}
					}
					if (Util.RANDOM.nextFloat() >= 0.3f)
					{
						System.out.println("cotton decreasing water level");
						int level = liquid.get(GameContent.TILE_WATER.level);
						if (level > 0)
						{
							world.setState(TileLayer.LIQUIDS, x + (left ? -1 : 1), y - 1, liquid.prop(GameContent.TILE_WATER.level, level - 1));
						} else
						{
							world.setState(TileLayer.LIQUIDS, x + (left ? -1 : 1), y - 1, GameContent.TILE_AIR.getDefState());
						}
					}
					world.setState(layer, x, y, state.prop(COTTON_GROWTH, growth + 1).prop(ALIVE, true));
				} else
				{
					world.setState(layer, x, y, state.prop(ALIVE, false));
					if (growth >= 3)
					{
						world.setState(layer, x, y + 1, this.getDefState().prop(StaticTileProps.TOP_HALF, Boolean.TRUE).prop(COTTON_GROWTH, growth).prop(ALIVE, false));
					}
				}
			}
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
