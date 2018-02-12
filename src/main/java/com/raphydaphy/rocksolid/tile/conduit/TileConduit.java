package com.raphydaphy.rocksolid.tile.conduit;

import javax.annotation.Nullable;

import com.raphydaphy.rocksolid.init.ModMisc;
import com.raphydaphy.rocksolid.render.ConduitRenderer;
import com.raphydaphy.rocksolid.tile.TileBase;
import com.raphydaphy.rocksolid.tileentity.TileEntityConduit;
import com.raphydaphy.rocksolid.util.ToolInfo;

import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ItemTile;
import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public abstract class TileConduit extends TileBase
{

	public TileConduit(String name)
	{
		super(name, 4f, new ToolInfo(ToolType.PICKAXE, 1));
	}

	@Override
	public boolean canPlace(IWorld world, int x, int y, TileLayer layer)
	{
		return layer.equals(ModMisc.CONDUIT_LAYER);
	}

	@Override
	public boolean canPlaceInLayer(TileLayer layer)
	{
		return layer.equals(ModMisc.CONDUIT_LAYER);
	}

	@Override
	public boolean isFullTile()
	{
		return false;
	}

	@Override
	public void onDestroyed(IWorld world, int x, int y, Entity destroyer, TileLayer layer, boolean shouldDrop)
	{
		if (destroyer == null)
		{
			shouldDrop = true;
		}
		super.onDestroyed(world, x, y, destroyer, layer, shouldDrop);
	}

	@Override
	protected ITileRenderer<TileConduit> createRenderer(IResourceName name)
	{
		return new ConduitRenderer<TileConduit>(name);
	}

	@Override
	public void doPlace(IWorld world, int x, int y, TileLayer layer, ItemInstance instance, AbstractEntityPlayer placer)
	{
		if (!world.isClient())
		{
			super.doPlace(world, x, y, layer, instance, placer);
			TileEntityConduit te = world.getTileEntity(layer, x, y, TileEntityConduit.class);
			if (te != null)
			{
				te.doPlace(world, x, y, layer, instance, placer);
			}
		}
	}

	@Override
	public void onChangeAround(IWorld world, int x, int y, TileLayer layer, int changedX, int changedY,
			TileLayer changedLayer)
	{
		super.onChangeAround(world, x, y, layer, changedX, changedY, changedLayer);

		TileEntity te = world.getTileEntity(layer, x, y);
		if (te != null && te instanceof TileEntityConduit)
		{
			((TileEntityConduit) te).onChangedAround(world, x, y, layer, changedX, changedY, changedLayer);
		}
	}

	@Override
	public void onRemoved(IWorld world, int x, int y, TileLayer layer)
	{
		super.onRemoved(world, x, y, layer);

		TileEntity te = world.getTileEntity(layer, x, y);
		if (te != null && te instanceof TileEntityConduit)
		{
			((TileEntityConduit) te).onRemoved(world, x, y, layer);
		}
	}

	@Override
	protected ItemTile createItemTile()
	{
		return new ItemTile(this.getName())
		{
			@Override
			public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY,
					AbstractEntityPlayer player, ItemInstance instance)
			{
				if (world.getState(x, y).getTile().canProvideTileEntity())
				{
					return false;
				}
				layer = ModMisc.CONDUIT_LAYER;

				Tile currentTile;
				TileState currentState;
				IResourceName soundName;
				Tile tile = getTile();

				if ((currentTile = world.getState(layer, x, y).getTile()) != tile
						&& currentTile.canReplace(world, x, y, layer) && tile.canPlace(world, x, y, layer))
				{
					if (!world.isClient())
					{
						tile.doPlace(world, x, y, layer, instance, player);
						player.getInv().remove(player.getSelectedSlot(), 1);

						if ((currentState = world.getState(layer, x, y)).getTile() == tile && (soundName = tile
								.getPlaceSound(player.world, x, y, layer, player, currentState)) != null)
						{
							world.playSound(soundName, (double) x + 0.5D, (double) y + 0.5D, (double) layer.index(),
									1.0F, 1.0F);
						}
					}
					return true;
				} else
				{
					return false;
				}
			}
		};
	}

	@Override
	public boolean canProvideTileEntity()
	{
		return true;
	}

	@Override
	public boolean canStay(IWorld world, int x, int y, TileLayer layer, int changedX, int changedY,
			TileLayer changedLayer)
	{
		if (changedLayer.equals(TileLayer.MAIN))
		{
			return !world.getState(x, y).getTile().canProvideTileEntity();
		}
		return true;
	}

	public boolean canConnect(IWorld world, Pos2 pos, @Nullable TileState conduitLayer, @Nullable TileState mainLayer)
	{
		if (conduitLayer != null && conduitLayer.getTile().equals(this))
			return true;

		if (mainLayer != null)
		{
			Tile tile = mainLayer.getTile();
			if (!tile.isAir())
			{

				TileEntity te = null;
				if (tile instanceof MultiTile)
				{
					Pos2 main = ((MultiTile) tile).getMainPos(pos.getX(), pos.getY(), mainLayer);

					te = world.getTileEntity(main.getX(), main.getY());
				} else
				{
					te = world.getTileEntity(pos.getX(), pos.getY());
				}
				return canConnectAbstract(world, te, pos, mainLayer);
			}
		}
		return false;
	}

	public abstract boolean canConnectAbstract(IWorld world, TileEntity te, Pos2 pos, TileState state);

}
