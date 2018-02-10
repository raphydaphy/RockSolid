package com.raphydaphy.rocksolid.tile;

import com.raphydaphy.rocksolid.fluid.IFluidTile;
import com.raphydaphy.rocksolid.init.ModMisc;
import com.raphydaphy.rocksolid.render.ConduitRenderer;
import com.raphydaphy.rocksolid.util.IConduit;
import com.raphydaphy.rocksolid.util.ToolInfo;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ItemTile;
import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileFluidConduit extends TileBase implements IConduit<TileFluidConduit>
{

	public TileFluidConduit()
	{
		super("fluid_conduit", 4f, new ToolInfo(ToolType.PICKAXE, 1));
	}

	@Override
	public boolean canPlaceInLayer(TileLayer layer)
	{
		return layer.equals(ModMisc.CONDUIT_LAYER);
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
	protected ITileRenderer<TileFluidConduit> createRenderer(IResourceName name)
	{
		return new ConduitRenderer<TileFluidConduit>(name);
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
				return RockBottomAPI.getInternalHooks().placeTile(x, y, ModMisc.CONDUIT_LAYER, player, instance,
						this.getTile(), true, world.isClient());
			}
		};
	}

	@Override
	public boolean canConnect(IWorld world, Pos2 pos, TileState state)
	{
		TileEntity te = world.getTileEntity(pos.getX(), pos.getY());
		return state.getTile().equals(this) || (te != null && te instanceof IFluidTile);
	}

}
