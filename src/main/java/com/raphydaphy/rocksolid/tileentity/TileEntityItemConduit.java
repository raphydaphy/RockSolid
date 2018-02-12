package com.raphydaphy.rocksolid.tileentity;

import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileEntityItemConduit extends TileEntityConduit
{
	public TileEntityItemConduit(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
	}

	@Override
	public void transfer(IWorld world, int x1, int y1, ConduitSide side1, TileState state1, int x2, int y2,
			ConduitSide side2, TileState state2)
	{
		TileEntity tile1 = null;
		if (state1.getTile() instanceof MultiTile)
		{
			Pos2 main = ((MultiTile) state1.getTile()).getMainPos(x1, y1, state1);
			tile1 = world.getTileEntity(main.getX(), main.getY());
		} else
		{
			tile1 = world.getTileEntity(x1, y1);
		}
		if (tile1 != null && tile1.getInventory() != null)
		{
			TileEntity tile2 = null;
			if (state2.getTile() instanceof MultiTile)
			{
				Pos2 main = ((MultiTile) state2.getTile()).getMainPos(x2, y2, state2);
				tile2 = world.getTileEntity(main.getX(), main.getY());

			} else
			{
				tile2 = world.getTileEntity(x2, y2);
			}

			if (tile2 != null && tile2.getInventory() != null)
			{
				// tile1 = INPUT
				// tile2 = OUTPUT

				ItemInstance toExtract = null;
				int extractSlot = -1;
				for (int outSlot : tile2.getInventory().getOutputSlots(side2.getDirection()))
				{
					ItemInstance i = tile2.getInventory().get(outSlot);
					if (i != null && i.getAmount() > 0)
					{
						toExtract = i.copy().setAmount(1);
						extractSlot = outSlot;
						break;
					}
				}

				if (extractSlot != -1)
				{
					int insertSlot = -1;
					boolean add = false;
					for (int inSlot : tile1.getInventory().getInputSlots(toExtract, side1.getDirection()))
					{
						ItemInstance i = tile1.getInventory().get(inSlot);
						if (i == null || i.getAmount() == 0)
						{
							insertSlot = inSlot;
							break;
						} else if (i != null && i.getItem().equals(toExtract.getItem()) && i.getAmount() + 1 <= i.getMaxAmount())
						{
							add = true;
							insertSlot = inSlot;
							break;
						}
					}
					if (insertSlot != -1)
					{
						tile2.getInventory().remove(extractSlot, 1);

						if (add)
						{
							tile1.getInventory().add(insertSlot, 1);
						} else
						{
							tile1.getInventory().set(insertSlot, toExtract);
						}

					}
				}
			}
		}
	}

}
