package com.raphydaphy.rocksolid.item;

import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.api.fluid.FluidTile;
import com.raphydaphy.rocksolid.api.fluid.IFluidAcceptor;
import com.raphydaphy.rocksolid.api.fluid.IFluidProducer;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.render.BucketRenderer;

import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.item.IItemRenderer;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class ItemBucket extends ItemBase
{

	public ItemBucket(String name)
	{
		super(RockSolidAPILib.makeInternalRes(name));
		this.maxAmount = 1;
		this.register();
	}

	@Override
	protected IItemRenderer<ItemBucket> createRenderer(IResourceName name)
	{
		return new BucketRenderer<ItemBucket>(name);
	}

	@Override
	public int getHighestPossibleMeta()
	{
		return 3;
	}

	public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY,
			AbstractEntityPlayer player, ItemInstance instance)
	{
		TileEntity atPos = RockSolidAPILib.getTileFromPos(x, y, world);
		TileState atState = world.getState(x, y);

		Fluid fluid = RockSolidAPILib.bucketMetaToFluid(instance.getMeta());

		// if the bucket is empty
		if (instance.getMeta() == 0)
		{
			if (atPos instanceof IFluidProducer)
			{
				IFluidProducer tank = (IFluidProducer) atPos;
				if (tank.getCurrentFluid() >= 1000)
				{
					if (!(tank.getFluidType().equals(Fluid.EMPTY.getName())))
					{
						String wasFluidType = tank.getFluidType();
						if (tank.removeFluid(1000))
						{
							instance.setMeta(Fluid.getByName(wasFluidType).getBucketMeta());
							return true;
						}
					}
				}

			} else if (atState.getTile() instanceof FluidTile)
			{
				int volume = atState.get(FluidTile.fluidLevel);
				if (volume >= FluidTile.BUCKET_VOLUME)
				{
					instance.setMeta(atState.get(FluidTile.fluidType).getBucketMeta());
					if (volume - FluidTile.BUCKET_VOLUME == 0)
					{
						world.setState(x, y, GameContent.TILE_AIR.getDefState());
					} else
					{
						world.setState(x, y, atState.prop(FluidTile.fluidLevel, volume - FluidTile.BUCKET_VOLUME));
					}

					return true;
				}
			}
		}
		// if the bucket already has liquid in it
		else
		{
			if (atPos instanceof IFluidAcceptor)
			{
				IFluidAcceptor tank = (IFluidAcceptor) atPos;
				String fluidString = fluid.getName();
				if (tank.addFluid(1000, fluidString))
				{
					instance.setMeta(0);
					return true;
				}

			} else if (atState.getTile() == GameContent.TILE_AIR || atState.get(FluidTile.fluidType).equals(fluid))
			{
				fluid.getTile().getTile().doPlace(world, x, y, layer, instance, null);
				if (atState.getTile() == GameContent.TILE_AIR)
				{
					world.setState(x, y, world.getState(x, y).prop(FluidTile.fluidType, fluid));
				}
				instance.setMeta(0);
				return true;
			}
		}
		return false;
	}

	@Override
	public IResourceName getUnlocalizedName(ItemInstance instance)
	{
		String bucketType = "";
		if (instance.getMeta() > 0)
		{
			bucketType = "." + RockSolidAPILib.bucketMetaToFluid(instance.getMeta()).toString();
		}
		return RockSolidAPILib.makeInternalRes("item.bucket" + bucketType);
	}

}
