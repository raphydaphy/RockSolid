package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.recipe.SeparatorRecipe;
import com.raphydaphy.rocksolid.tileentity.base.TileEntityFueledBase;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.construction.smelting.FuelInput;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.TileInventory;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class TileEntitySeparator extends TileEntityFueledBase
{
	private static final String KEY_OUTPUT = "output";
	private static final String KEY_BIPRODUCT = "biproduct";

	private final TileInventory inventory = new TileInventory(this, 4, (input) ->
	{
		ArrayList<Integer> avalableSlots = new ArrayList<>(2);
		if (FuelInput.getFuelTime(input) > 0)
		{
			avalableSlots.add(0);
		}
		if (SeparatorRecipe.forInput(input) != null)
		{
			avalableSlots.add(1);
		}
		return avalableSlots;
	}, Arrays.asList(2, 3));

	public ItemInstance output;
	public ItemInstance biproduct;

	public TileEntitySeparator(IWorld world, int x, int y, TileLayer layer)
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
		if (!forSync)
		{
			if (this.output != null)
			{
				DataSet tmpSet = new DataSet();
				this.output.save(tmpSet);
				set.addDataSet(KEY_OUTPUT, tmpSet);
			}
			if (this.biproduct != null)
			{
				DataSet tmpSet = new DataSet();
				this.biproduct.save(tmpSet);
				set.addDataSet(KEY_BIPRODUCT, tmpSet);
			}
		}
	}

	@Override
	public void load(DataSet set, boolean forSync)
	{
		super.load(set, forSync);
		if (!forSync)
		{
			inventory.load(set);
			if (set.hasKey(KEY_OUTPUT))
			{
				DataSet tepSet = set.getDataSet(KEY_OUTPUT);
				this.output = ItemInstance.load(tepSet);
			}
			if (set.hasKey(KEY_BIPRODUCT))
			{
				DataSet tepSet = set.getDataSet(KEY_BIPRODUCT);
				this.biproduct = ItemInstance.load(tepSet);
			}
		}
	}

	@Override
	protected void getRecipeAndStart()
	{
		ItemInstance item;
		SeparatorRecipe recipe;
		if ((item = this.inventory.get(1)) != null && (recipe = SeparatorRecipe.forInput(item)) != null)
		{
			IUseInfo input = recipe.in;
			if (item.getAmount() >= input.getAmount())
			{
				item = recipe.out;
				ItemInstance biproduct = recipe.biproduct;
				ItemInstance var4;
				boolean doBiproduct = Util.RANDOM.nextInt(recipe.biproductChance) == 0;
				if (((var4 = this.inventory.get(2)) == null || var4.isEffectivelyEqual(item) && var4.fitsAmount(item.getAmount()))
						&& (!doBiproduct || ((var4 = this.inventory.get(3)) == null || var4.isEffectivelyEqual(biproduct) && var4.fitsAmount(biproduct.getAmount()))))
				{
					this.maxSmeltTime.set(recipe.time);
					this.output = item.copy();
					this.biproduct = null;
					if (doBiproduct)
					{
						this.biproduct = biproduct.copy();
					}
					this.inventory.remove(1, input.getAmount());
				}
			}
		}

		if (this.maxSmeltTime.get() <= 0)
		{
			this.output = null;
			this.biproduct = null;
		}
	}

	@Override
	protected void putOutputItems()
	{
		ItemInstance outSlot;

		if ((outSlot = this.inventory.get(2)) != null && outSlot.isEffectivelyEqual(this.output))
		{
			this.inventory.add(2, this.output.getAmount());
		} else
		{
			this.inventory.set(2, this.output);
		}

		if (biproduct != null)
		{
			if ((outSlot = this.inventory.get(3)) != null && outSlot.isEffectivelyEqual(this.biproduct))
			{
				this.inventory.add(3, this.biproduct.getAmount());
			} else
			{
				this.inventory.set(3, this.biproduct);
			}
		}

		this.output = null;
		this.biproduct = null;
	}

	@Override
	protected ItemInstance getFuel()
	{
		return this.getTileInventory().get(0);
	}

	@Override
	protected void removeFuel()
	{
		this.getTileInventory().remove(0, 1);
	}

	@Override
	protected void onActiveChange(boolean active)
	{
		world.causeLightUpdate(this.x, this.y);
	}

}
