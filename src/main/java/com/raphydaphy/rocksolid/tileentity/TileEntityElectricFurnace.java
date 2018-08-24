package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.tileentity.base.TileEntityElectric;
import com.raphydaphy.rocksolid.util.ModUtils;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.construction.smelting.SmeltingRecipe;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.TileInventory;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.ArrayList;
import java.util.Collections;

public class TileEntityElectricFurnace extends TileEntityElectric
{
	private static final String KEY_OUTPUT = "output";

	private final TileInventory inventory = new TileInventory(this, 2, (input) ->
	{
		ArrayList<Integer> avalableSlots = new ArrayList<>(1);
		if (ModUtils.getSmeltingRecipeSafe(input) != null)
		{
			avalableSlots.add(0);
		}
		return avalableSlots;
	}, Collections.singletonList(1));

	public ItemInstance output;

	public TileEntityElectricFurnace(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
		this.maxEnergyStored.set(2500);
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
		if (!forSync)
		{
			if (this.output != null)
			{
				DataSet tmpSet = new DataSet();
				this.output.save(tmpSet);
				set.addDataSet(KEY_OUTPUT, tmpSet);
			}
		}
	}

	@Override
	public void load(DataSet set, boolean forSync)
	{
		super.load(set, forSync);
		inventory.load(set);
		if (!forSync)
		{
			if (set.hasKey(KEY_OUTPUT))
			{
				DataSet tepSet = set.getDataSet(KEY_OUTPUT);
				this.output = ItemInstance.load(tepSet);
			}
		}
	}

	@Override
	protected void getRecipeAndStart()
	{
		ItemInstance item;
		SmeltingRecipe recipe;
		if ((item = this.inventory.get(0)) != null && (recipe = SmeltingRecipe.forInput(item)) != null)
		{
			IUseInfo input = recipe.getInput();
			if (item.getAmount() >= input.getAmount())
			{
				item = recipe.getOutput();
				ItemInstance var4;
				if (((var4 = this.inventory.get(1)) == null || var4.isEffectivelyEqual(item) && var4.fitsAmount(item.getAmount())))
				{
					this.maxSmeltTime.set(recipe.getTime() / 5); // speed multiplier
					this.output = item.copy();
					this.inventory.remove(0, input.getAmount());
				}
			}
		}

		if (this.maxSmeltTime.get() <= 0)
		{
			this.output = null;
		}
	}

	@Override
	protected void putOutputItems()
	{
		ItemInstance outSlot;

		if ((outSlot = this.inventory.get(1)) != null && outSlot.isEffectivelyEqual(this.output))
		{
			this.inventory.add(1, this.output.getAmount());
		} else
		{
			this.inventory.set(1, this.output);
		}

		this.output = null;
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
		return maxEnergyStored.get();
	}
}
