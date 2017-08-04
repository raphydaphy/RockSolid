package com.raphydaphy.rocksolid.api.gas;

import com.raphydaphy.rocksolid.api.RockSolidAPI;
import com.raphydaphy.rocksolid.api.render.GasRenderer;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.TileBasic;
import de.ellpeck.rockbottom.api.tile.state.IntProp;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public abstract class Gas extends TileBasic
{
	public static final int MAX_VOLUME = 12;
	public static final int CANISTER_VOLUME = 5;
	public static final IntProp gasLevel = new IntProp("volume", 1, MAX_VOLUME + 1);
	public float weight = 1;

	public Gas(String name)
	{
		this(RockSolidAPILib.makeInternalRes(name));
		this.addProps(gasLevel);
	}

	public Gas(IResourceName name)
	{
		super(name);
	}

	@Override
	public Gas register()
	{
		RockSolidAPI.GAS_REGISTRY.register(this.getName(), this);
		return (Gas) super.register();
	}

	protected ITileRenderer<?> createRenderer(IResourceName name)
	{
		return new GasRenderer<Gas>(name);
	}

	@Override
	public void onAdded(IWorld world, int x, int y, TileLayer layer)
	{
		world.scheduleUpdate(x, y, TileLayer.MAIN, 8);
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
	public void onScheduledUpdate(IWorld world, int x, int y, TileLayer layer)
	{
		TileState thisState = world.getState(x, y);
		TileState upState = world.getState(x, y + 1);
		// TileState leftTile = world.getState(x - 1, y);
		// TileState rightTile = world.getState(x + 1, y);

		if (thisState.getTile() instanceof Gas)
		{
			world.scheduleUpdate(x, y, layer, 8);
		}

		// if the tile below is the same fluid as this
		if (upState.getTile() == thisState.getTile() && upState.get(gasLevel) < MAX_VOLUME)
		{
			// if we can safely move all the fluid into the lower block
			if (upState.get(gasLevel) + thisState.get(gasLevel) <= MAX_VOLUME)
			{
				// move the fluid into the lower block
				world.setState(x, y + 1, upState.prop(gasLevel, upState.get(gasLevel) + thisState.get(gasLevel)));
				world.scheduleUpdate(x, y + 1, layer, 8);
				world.setState(x, y, GameContent.TILE_AIR.getDefState());
				return;
			}
			// if we need to keep some fluid in the top block
			else
			{

			}
		}
		// if the tile below is air
		else if (upState.getTile() == GameContent.TILE_AIR)
		{
			// move the fluid down a block
			world.setState(x, y + 1, thisState);
			world.setState(x, y, GameContent.TILE_AIR.getDefState());
			return;
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
