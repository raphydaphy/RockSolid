package com.raphydaphy.rocksolid.tileentity.conduit;

import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileEntityItemConduit extends TileEntityConduit
{
	public TileEntityItemConduit(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer, TileEntityItemConduit.class, 50);
	}

	@Override
	public boolean transfer(IWorld world, int x1, int y1, ConduitSide side1, TileEntity tile2, int x2, int y2, ConduitSide side2, TileEntity tile1, boolean simulate)
	{
		if (tile1 != null && tile1.getTileInventory() != null && tile2 != null && tile2.getTileInventory() != null)
		{
			// tile1 = INPUT
			// tile2 = OUTPUT

			ItemInstance toExtract = null;
			int extractSlot = -1;
			int insertSlot = -1;
			boolean add = false;

			boolean stop = false;
			for (int outSlot : tile2.getTileInventory().getOutputSlots(side2.direction))
			{
				if (stop)
				{
					break;
				}
				ItemInstance outI = tile2.getTileInventory().get(outSlot);
				if (outI != null && outI.getAmount() > 0)
				{
					ItemInstance toExtractTemp = outI.copy().setAmount(1);


					for (int inSlot : tile1.getTileInventory().getInputSlots(toExtractTemp, side1.direction))
					{
						ItemInstance inI = tile1.getTileInventory().get(inSlot);
						if (inI == null || inI.getAmount() == 0)
						{
							insertSlot = inSlot;
							stop = true;

							toExtract = outI.copy().setAmount(1);
							extractSlot = outSlot;
							break;
						} else if (inI.getItem().equals(toExtractTemp.getItem()) && inI.getAmount() + 1 <= inI.getMaxAmount())
						{
							add = true;
							insertSlot = inSlot;
							stop = true;

							toExtract = outI.copy().setAmount(1);
							extractSlot = outSlot;
							break;
						}
					}
				}
			}


			if (insertSlot != -1)
			{
				if (!simulate)
				{
					tile2.getTileInventory().remove(extractSlot, 1);

					if (add)
					{
						tile1.getTileInventory().add(insertSlot, 1);
					} else
					{
						tile1.getTileInventory().set(insertSlot, toExtract);
					}
				}
				return true;
			}
		}
		return false;
	}

}
