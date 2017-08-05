package com.raphydaphy.rocksolid.item;

import com.raphydaphy.rocksolid.api.content.RockSolidContent;
import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.api.fluid.IFluidAcceptor;
import com.raphydaphy.rocksolid.api.fluid.IFluidProducer;
import com.raphydaphy.rocksolid.render.BucketRenderer;
import com.raphydaphy.rocksolid.util.RockSolidLib;

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
		super(RockSolidLib.makeRes(name));
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
		TileEntity atPos = RockSolidLib.getTileFromPos(x, y, world);
		TileState atState = world.getState(x, y);

		Fluid fluid = RockSolidLib.bucketMetaToFluid(instance.getMeta());

		// if the bucket is empty
		if (instance.getMeta() == 0)
		{
			if (atPos instanceof IFluidProducer)
			{
				IFluidProducer tank = (IFluidProducer) atPos;
				if (tank.getCurrentFluid() >= 1000)
				{
					if (!(tank.getFluidType().equals(RockSolidContent.fluidEmpty.toString())))
					{
						if (tank.getFluidType().equals(RockSolidContent.fluidWater.toString()))
						{
							if (tank.removeFluid(1000))
							{
								instance.setMeta(1);
								return true;
							}
						} else if (tank.getFluidType().equals(RockSolidContent.fluidLava.toString()))
						{
							if (tank.removeFluid(1000))
							{
								instance.setMeta(2);
								return true;
							}
						}
					}
				}

			} else if (atState.getTile() instanceof Fluid)
			{
				int volume = atState.get(Fluid.fluidLevel);
				if (volume >= Fluid.BUCKET_VOLUME)
				{
					if (atState.getTile() == RockSolidContent.fluidWater)
					{
						instance.setMeta(1);
					} else if (atState.getTile() == RockSolidContent.fluidLava)
					{
						instance.setMeta(2);
					} else if (atState.getTile() == RockSolidContent.fluidOil)
					{
						instance.setMeta(3);
					}
					if (volume - Fluid.BUCKET_VOLUME == 0)
					{
						world.setState(x, y, GameContent.TILE_AIR.getDefState());
					} else
					{
						world.setState(x, y, atState.prop(Fluid.fluidLevel, volume - Fluid.BUCKET_VOLUME));
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
				String fluidString = fluid.toString();
				if (tank.addFluid(1000, fluidString))
				{
					instance.setMeta(0);
					return true;
				}

			} else if (atState.getTile() == GameContent.TILE_AIR || atState.getTile() == fluid)
			{
				System.out.println(fluid.toString());
				fluid.doPlace(world, x, y, layer, instance, null);
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
		switch (instance.getMeta())
		{
		case 1:
			bucketType = bucketType + ".water";
			break;
		case 2:
			bucketType = bucketType + ".lava";
			break;
		case 3:
			bucketType = bucketType + ".oil";
			break;

		}
		return RockSolidLib.makeRes("item.bucket" + bucketType);
	}

}
