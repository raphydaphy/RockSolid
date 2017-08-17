package com.raphydaphy.rocksolid.api.gas;

import com.raphydaphy.rocksolid.api.render.GasRenderer;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.TileBasic;
import de.ellpeck.rockbottom.api.tile.state.EnumProp;
import de.ellpeck.rockbottom.api.tile.state.IntProp;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class GasTile extends TileBasic
{
	// the maximum meta value for a gas block. meta starts at 1.
	public static final int MAX_VOLUME = 12;
	public static final int CANISTER_VOLUME = 5;
	public static final IntProp gasLevel = new IntProp("volume", 1, MAX_VOLUME + 1);
	public static final EnumProp<Gas> gasType = new EnumProp<Gas>(Gas.TYPE_KEY, Gas.HYDROGEN, Gas.class);

	public GasTile(String name)
	{
		this(RockSolidAPILib.makeInternalRes(name));

	}

	public GasTile(IResourceName name)
	{
		super(name);
		this.addProps(gasLevel, gasType);
		this.register();
	}

	protected ITileRenderer<?> createRenderer(IResourceName name)
	{
		return new GasRenderer<GasTile>(name);
	}

	@Override
	public TileState getPlacementState(IWorld world, int x, int y, TileLayer layer, ItemInstance instance,
			AbstractEntityPlayer placer)
	{
		System.out.println("set the placement state");
		TileState existingBlock = world.getState(x, y);
		if (existingBlock.getTile() == GameContent.TILE_AIR)
		{
			return this.getDefState().prop(gasLevel, CANISTER_VOLUME);
		} else
		{
			if (existingBlock.get(gasLevel) + CANISTER_VOLUME <= MAX_VOLUME)
			{
				return existingBlock.prop(gasLevel, existingBlock.get(gasLevel) + CANISTER_VOLUME);
			} else
			{
				return existingBlock.prop(gasLevel, MAX_VOLUME);
			}
		}
	}

	@Override
	public void onAdded(IWorld world, int x, int y, TileLayer layer)
	{
		if (!world.isClient())
		{
			world.scheduleUpdate(x, y, TileLayer.MAIN, 8);
		}
	}

	// what should happen if there is about to be a collision with an enemy
	// gas
	public void onEnemyCollision(Tile enemyTile, Pos2 enemyPos, Pos2 thisPos, IWorld world)
	{
		world.setState(thisPos.getX(), thisPos.getY(), GameContent.TILE_STONE.getDefState());
	}

	@Override
	public void onCollideWithEntity(IWorld world, int x, int y, TileLayer layer, Entity entity)
	{
		// make them swim!
	}

	@Override
	public void onScheduledUpdate(IWorld world, int x, int y, TileLayer layer)
	{
		TileState thisState = world.getState(x, y);
		TileState upState = world.getState(x, y + 1);
		TileState leftState = world.getState(x - 1, y);
		TileState rightState = world.getState(x + 1, y);

		if (thisState.getTile() instanceof GasTile)
		{
			if (!world.isClient())
			{
				world.scheduleUpdate(x, y, layer, 8);
			}
		}

		// if the tile below is the same gas as this
		if (upState.getTile() == thisState.getTile() && upState.get(gasType) == thisState.get(gasType))
		{
			// if the tile below is not full of gas
			if (upState.get(gasLevel) < MAX_VOLUME)
			{
				// if we can safely move all the gas into the lower block
				if (upState.get(gasLevel) + thisState.get(gasLevel) <= MAX_VOLUME)
				{
					// move the gas into the lower block
					world.setState(x, y + 1,
							upState.prop(gasLevel, upState.get(gasLevel) + thisState.get(gasLevel)));
					world.setState(x, y, GameContent.TILE_AIR.getDefState());
					return;
				}
				// if we need to keep some gas in the top block
				else
				{
					// move the most gas we can to the bottom block and keep
					// the remains in the top
					world.setState(x, y + 1, upState.prop(gasLevel, MAX_VOLUME));
					world.setState(x, y,
							thisState.prop(gasLevel, (upState.get(gasLevel) + thisState.get(gasLevel) - 12)));
					return;
				}
			}
		}
		// if the tile below is air
		else if (upState.getTile() == GameContent.TILE_AIR)
		{
			// move the gas down a block
			world.setState(x, y + 1, thisState);
			world.setState(x, y, GameContent.TILE_AIR.getDefState());
			return;
		}

		int furthestLeft = 0;
		int furthestRight = 0;

		boolean finishedLeft = false;
		boolean finishedRight = false;

		for (int curX = 0; curX < MAX_VOLUME + 1; curX++)
		{
			TileState curRight = world.getState(x + curX, y);
			TileState curLeft = world.getState(x - curX, y);

			if (!finishedRight)
			{
				// if the tile to the right is
				if (curRight.getTile() == thisState.getTile() || curRight.getTile() == GameContent.TILE_AIR)
				{
					if (curRight.getTile() == thisState.getTile()
							&& curRight.get(gasType) == thisState.get(gasType))
					{
						if (curRight.get(gasLevel) < MAX_VOLUME)
						{
							furthestRight = curX;
						} else
						{
							finishedRight = true;
						}
					} else
					{
						furthestRight = curX;
					}
				} else
				{
					finishedRight = true;
				}
			}

			if (!finishedLeft)
			{
				if (curLeft.getTile() == thisState.getTile() || curLeft.getTile() == GameContent.TILE_AIR)
				{
					if (curLeft.getTile() == thisState.getTile() && curLeft.get(gasType) == thisState.get(gasType))
					{
						if (curLeft.get(gasLevel) < MAX_VOLUME)
						{
							furthestLeft = curX;
						} else
						{
							finishedLeft = true;
						}
					} else
					{
						finishedLeft = true;
					}
				} else
				{
					finishedLeft = true;
				}
			}

			if (finishedRight && finishedLeft)
			{
				break;
			}
		}

		if (furthestLeft == 0 && furthestRight == 0)
		{
			return;
		}

		if (thisState.get(gasLevel) > 1)
		{
			// if we should flow gas to the left
			if (furthestLeft >= furthestRight)
			{
				// if the tile to the left is not full of gas
				if (leftState.getTile() == thisState.getTile() && leftState.get(gasType) == thisState.get(gasType))
				{
					// if the gas to the left is the same as this
					if (leftState.get(gasLevel) < MAX_VOLUME)
					{
						// if there is less gas in the left tile than this
						if (leftState.get(gasLevel) < thisState.get(gasLevel))
						{
							world.setState(x - 1, y, leftState.prop(gasLevel, leftState.get(gasLevel) + 1));
							world.setState(x, y, thisState.prop(gasLevel, thisState.get(gasLevel) - 1));
							return;
						}
					}
				}
				// if there is air to the left
				else if (leftState.getTile() == GameContent.TILE_AIR)
				{
					world.setState(x - 1, y, thisState.prop(gasLevel, 1));
					world.setState(x, y, thisState.prop(gasLevel, thisState.get(gasLevel) - 1));
					return;
				}
			}
			// if we should flow gas to the right
			else
			{
				// if the gas to the left is the same as this
				if (rightState.getTile() == thisState.getTile()
						&& rightState.get(gasType) == thisState.get(gasType))
				{
					// if the tile to the left is not full of gas
					if (rightState.get(gasLevel) < MAX_VOLUME)
					{
						// if there is less gas in the left tile than this
						if (rightState.get(gasLevel) < rightState.get(gasLevel))
						{
							world.setState(x + 1, y, rightState.prop(gasLevel, rightState.get(gasLevel) + 1));
							world.setState(x, y, thisState.prop(gasLevel, thisState.get(gasLevel) - 1));
							return;
						}
					}
				}
				// if there is air to the left
				else if (rightState.getTile() == GameContent.TILE_AIR)
				{
					world.setState(x + 1, y, thisState.prop(gasLevel, 1));
					world.setState(x, y, thisState.prop(gasLevel, thisState.get(gasLevel) - 1));
					return;
				}
			}
		}

	}

	@Override
	public boolean isFullTile()
	{
		return false;
	}

	@Override
	public boolean canBreak(IWorld world, int x, int y, TileLayer layer)
	{
		return false;
	}

	@Override
	public BoundBox getBoundBox(IWorld world, int x, int y)
	{
		return null;
	}

	@Override
	public boolean canReplace(IWorld world, int x, int y, TileLayer layer, Tile replacementTile)
	{
		return true;
	}

	@Override
	public boolean canPlaceInLayer(TileLayer layer)
	{
		return layer != TileLayer.BACKGROUND || !this.canProvideTileEntity();
	}
}
