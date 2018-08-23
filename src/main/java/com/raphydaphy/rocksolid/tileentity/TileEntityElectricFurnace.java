package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.tileentity.base.TileEntityElectric;
import com.raphydaphy.rocksolid.util.ModUtils;
import com.raphydaphy.rocksolid.util.SlotInfo;
import com.raphydaphy.rocksolid.util.SlotInfo.SimpleSlotInfo;
import com.raphydaphy.rocksolid.util.SlotInfo.SlotType;
import de.ellpeck.rockbottom.api.construction.smelting.SmeltingRecipe;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.entity.TileInventory;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.ArrayList;
import java.util.Collections;

public class TileEntityElectricFurnace extends TileEntityElectric
{
	private final TileInventory inventory = new TileInventory(this, 2, (input) ->
	{
		ArrayList<Integer> avalableSlots = new ArrayList<>(1);
		if (ModUtils.getSmeltingRecipeSafe(input) != null)
		{
			avalableSlots.add(0);
		}
		return avalableSlots;
	}, Collections.singletonList(1));

	public TileEntityElectricFurnace(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
	}

	@Override
	public TileInventory getTileInventory()
	{
		return this.inventory;
	}

	@Override
	public void save(DataSet set, boolean forSync)
	{
		super.save(set, forSync);
		inventory.save(set);
	}

	@Override
	public void load(DataSet set, boolean forSync)
	{
		super.load(set, forSync);
		inventory.load(set);
	}


	public boolean hasValidRecipe()
	{
		return ModUtils.getSmeltingRecipeSafe(this.inventory.get(0)) != null;
	}

	public boolean processSmelt()
	{
		SmeltingRecipe r = ModUtils.getSmeltingRecipeSafe(this.inventory.get(0));
		if (r != null)
		{
			boolean removed = false;

			if (this.inventory.get(1) == null)
			{
				this.inventory.set(1, r.getOutput().copy());
				removed = true;
			} else if (this.inventory.get(1).getItem().equals(r.getOutput().getItem()) && this.inventory.get(1).getAmount() + r.getOutput().getAmount() <= r.getOutput().getMaxAmount())
			{
				this.inventory.add(1, r.getOutput().getAmount());
				removed = true;
			}

			if (removed)
			{
				this.inventory.remove(0, r.getInput().getAmount());

				return true;
			}
		}
		return false;
	}

	@Override
	public int getEnergyCapacity(IWorld world, Pos2 pos)
	{
		Pos2 innerCoord = ModUtils.innerCoord(world, pos);
		if (innerCoord != null && innerCoord.getY() == 1)
		{
			return 0;
		}
		return 2500;
	}

	@Override
	public float getSmeltTime()
	{
		SmeltingRecipe r = ModUtils.getSmeltingRecipeSafe(this.inventory.get(0));
		if (r != null)
		{
			return r.getTime();
		}
		return 100f;
	}
}
