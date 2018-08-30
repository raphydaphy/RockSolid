package com.raphydaphy.rocksolid.item;

import com.raphydaphy.rocksolid.fluid.IFluidTile;
import com.raphydaphy.rocksolid.init.ModTiles;
import com.raphydaphy.rocksolid.render.BucketRenderer;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.item.IItemRenderer;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.TileLiquid;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
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
	protected IItemRenderer<ItemBucket> createRenderer(ResourceName name)
	{
		return new BucketRenderer(name);
	}

	@Override
	public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY,
			AbstractEntityPlayer player, ItemInstance instance)
	{
		TileState liquidState = world.getState(TileLayer.LIQUIDS, x, y);
		TileState main = world.getState(TileLayer.MAIN, x, y);
		TileEntity te;
		if (main.getTile() instanceof MultiTile)
		{
			Pos2 mainPos = ((MultiTile) main.getTile()).getMainPos(x, y, main);
			te =  world.getTileEntity(mainPos.getX(), mainPos.getY());
		} else
		{
			te =  world.getTileEntity(x, y);
		}

		if (instance.getMeta() == BucketType.EMPTY.meta)
		{
			if (liquidState.getTile().equals(GameContent.TILE_WATER))
			{
				int curLevel = liquidState.get(GameContent.TILE_WATER.level);
				if (curLevel > 0)
				{
					world.setState(TileLayer.LIQUIDS, x, y, liquidState.prop(GameContent.TILE_WATER.level, curLevel - 1));
				} else
				{
					world.setState(TileLayer.LIQUIDS, x, y, GameContent.TILE_AIR.getDefState());
				}
				instance.setMeta(BucketType.WATER.meta);
			}
			else if (liquidState.getTile().equals(ModTiles.OIL))
			{
				int curLevel = liquidState.get(ModTiles.OIL.level);
				if (curLevel > 0)
				{
					world.setState(TileLayer.LIQUIDS, x, y, liquidState.prop(ModTiles.OIL.level, curLevel - 1));
				} else
				{
					world.setState(TileLayer.LIQUIDS, x, y, GameContent.TILE_AIR.getDefState());
				}
				instance.setMeta(BucketType.OIL.meta);
			} else if (te instanceof IFluidTile<?>)
			{
				IFluidTile<?> fluidTE = (IFluidTile<?>) te;
				if (fluidTE.removeFluid(new Pos2(x, y), GameContent.TILE_WATER, 25, false))
				{
					instance.setMeta(BucketType.WATER.meta);
				}
				else if (fluidTE.removeFluid(new Pos2(x, y), ModTiles.OIL, 25, false))
				{
					instance.setMeta(BucketType.OIL.meta);
				}
				else
				{
					return false;
				}
			}
			else
			{
				return false;
			}
		} else if (instance.getMeta() == BucketType.WATER.meta)
		{
			if (liquidState.getTile().equals(GameContent.TILE_WATER))
			{
				int curLevel = liquidState.get(GameContent.TILE_WATER.level);
				if (curLevel < 11)
				{
					world.setState(TileLayer.LIQUIDS, x, y,
							liquidState.prop(((TileLiquid) liquidState.getTile()).level, curLevel + 1));
				} else
				{
					return false;
				}
			} else if (te instanceof IFluidTile<?>)
			{
				IFluidTile<?> fluidTE = (IFluidTile<?>) te;
				if (!fluidTE.addFluid(new Pos2(x, y), GameContent.TILE_WATER, 25, false))
				{
					return false;
				}
			} else if (liquidState.getTile().isAir())
			{
				world.setState(TileLayer.LIQUIDS, x, y, GameContent.TILE_WATER.getDefState());
			} else
			{
				return false;
			}
			instance.setMeta(BucketType.EMPTY.meta);
		} else if (instance.getMeta() == BucketType.OIL.meta)
		{
			if (liquidState.getTile().equals(ModTiles.OIL))
			{
				int curLevel = liquidState.get(ModTiles.OIL.level);
				if (curLevel < 11)
				{
					world.setState(TileLayer.LIQUIDS, x, y,
							liquidState.prop(((TileLiquid) liquidState.getTile()).level, curLevel + 1));
				} else
				{
					return false;
				}
			} else if (te instanceof IFluidTile<?>)
			{
				IFluidTile<?> fluidTE = (IFluidTile<?>) te;
				if (!fluidTE.addFluid(new Pos2(x, y), ModTiles.OIL, 25, false))
				{
					return false;
				}
			} else if (liquidState.getTile().isAir())
			{
				world.setState(TileLayer.LIQUIDS, x, y, ModTiles.OIL.getDefState());
			} else
			{
				return false;
			}
			instance.setMeta(BucketType.EMPTY.meta);
		}
		return true;

	}

	@Override
	public int getHighestPossibleMeta()
	{
		return BucketType.values().length - 1;
	}

	@Override
	public ResourceName getUnlocalizedName(ItemInstance instance)
	{
		return this.unlocName.addSuffix("." + BucketType.getFromMeta(instance.getMeta()).toString());
	}

	public enum BucketType
	{
		EMPTY(0), WATER(1), OIL(2);

		public final int meta;

		BucketType(int meta)
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
