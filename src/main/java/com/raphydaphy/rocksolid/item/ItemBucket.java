package com.raphydaphy.rocksolid.item;

import com.raphydaphy.rocksolid.init.ModFluids;
import com.raphydaphy.rocksolid.render.BucketRenderer;
import com.raphydaphy.rocksolid.tileentity.TileEntityTank;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.item.IItemRenderer;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class ItemBucket extends ItemBase {

	public ItemBucket(String name) 
	{
		super(RockSolidLib.makeRes(name));
		this.register();
	}
	
	@Override
	protected IItemRenderer<ItemBucket> createRenderer(IResourceName name){
        return new BucketRenderer<ItemBucket>(name);
    }
	
	@Override
	public int getHighestPossibleMeta(){
        return 3;
    }
	
	public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player, ItemInstance instance)
	{
		TileEntity atPos = RockSolidLib.getTileFromPos(x, y, world);
		
		// if the bucket is empty
		if (instance.getMeta() == 0)
		{
			if (atPos instanceof TileEntityTank)
			{
				TileEntityTank tank = (TileEntityTank)atPos;
				if (tank.getCurrentFluid() > 1000)
				{
					if (!(tank.getFluidType().equals(ModFluids.fluidEmpty.toString()))
					{
						if(tank.getFluidType().equals(ModFluids.fluidWater.toString()))
						{
							if (tank.removeFluid(1000))
							{
								instance.setMeta(1);
								return true;
							}
						}
						else if (tank.getFluidType().equals(ModFluids.fluidLava.toString()))
						{
							if (tank.removeFluid(1000))
							{
								instance.setMeta(2);
								return true;
							}
						}
					}
				}
				
			}
		}
		// if the bucket already has liquid in it
		else
		{
			if (atPos instanceof TileEntityTank)
			{
				TileEntityTank tank = (TileEntityTank)atPos;
				// try to put the fluid back into the tank
				//if (tank)
			}
		}
        return false;
    }
	
	@Override
	public IResourceName getUnlocalizedName(ItemInstance instance)
	{
		String bucketType = "";
		switch(instance.getMeta())
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
