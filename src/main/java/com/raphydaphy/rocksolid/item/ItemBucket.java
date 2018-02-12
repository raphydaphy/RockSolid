package com.raphydaphy.rocksolid.item;

import com.raphydaphy.rocksolid.fluid.FluidWater;
import com.raphydaphy.rocksolid.init.ModTiles;
import com.raphydaphy.rocksolid.render.BucketRenderer;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.item.IItemRenderer;
import de.ellpeck.rockbottom.api.tile.TileLiquid;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class ItemBucket extends ItemBase
{

	public ItemBucket()
	{
		super("bucket");
		this.maxAmount = 1;
	}

	@Override
	protected IItemRenderer<ItemBucket> createRenderer(IResourceName name)
	{
		return new BucketRenderer(name);
	}

	@Override
	public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY,
			AbstractEntityPlayer player, ItemInstance instance)
	{
		TileState state = world.getState(TileLayer.LIQUIDS, x, y);

		if (instance.getMeta() == BucketType.EMPTY.meta)
		{
			if (state.getTile().equals(ModTiles.WATER))
			{
				int curLevel = state.get(((FluidWater) state.getTile()).level);
				if (curLevel > 0)
				{
					world.setState(TileLayer.LIQUIDS, x, y,
							state.prop(((FluidWater) state.getTile()).level, curLevel - 1));
				} else
				{
					world.setState(TileLayer.LIQUIDS, x, y, GameContent.TILE_AIR.getDefState());
				}
				instance.setMeta(BucketType.WATER.meta);
			}
		} else if (instance.getMeta() == BucketType.WATER.meta)
		{
			if (state.getTile().equals(ModTiles.WATER))
			{
				int curLevel = state.get(((FluidWater) state.getTile()).level);
				if (curLevel < 11)
				{
					world.setState(TileLayer.LIQUIDS, x, y,
							state.prop(((TileLiquid) state.getTile()).level, curLevel + 1));
				} else
				{
					return false;
				}
			} else if (state.getTile().isAir())
			{
				world.setState(TileLayer.LIQUIDS, x, y, ModTiles.WATER.getDefState());
			}
			instance.setMeta(BucketType.EMPTY.meta);
		}
		return true;

	}

	@Override
	public int getHighestPossibleMeta()
	{
		return BucketType.values().length;
	}

	@Override
	public IResourceName getUnlocalizedName(ItemInstance instance)
	{
		return this.unlocName.addSuffix("." + BucketType.getFromMeta(instance.getMeta()).toString());
	}

	public static enum BucketType
	{
		EMPTY(0), WATER(1);

		public final int meta;

		private BucketType(int meta)
		{
			this.meta = meta;
		}

		public static BucketType getFromMeta(int meta)
		{
			for (BucketType type : BucketType.values())
			{
				if (type.meta == meta)
				{
					return type;
				}
			}
			return EMPTY;
		}

		@Override
		public String toString()
		{
			return super.toString().toLowerCase();
		}
	}

}
