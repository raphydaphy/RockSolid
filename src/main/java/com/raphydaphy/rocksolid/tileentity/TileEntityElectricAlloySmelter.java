package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.recipe.AlloyingRecipe;
import com.raphydaphy.rocksolid.tileentity.base.TileEntityElectric;
import com.raphydaphy.rocksolid.util.ModUtils;
import de.ellpeck.rockbottom.api.construction.resource.IUseInfo;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.TileInventory;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.ArrayList;
import java.util.Collections;

public class TileEntityElectricAlloySmelter extends TileEntityElectric
{
	private static final String KEY_OUTPUT = "output";

	private final TileInventory inventory = new TileInventory(this, 3, (input) ->
	{
		ArrayList<Integer> avalableSlots = new ArrayList<>(2);
		if (AlloyingRecipe.forInput(input, this.inventory.get(1), true) != null)
		{
			avalableSlots.add(0);
		}
		if (AlloyingRecipe.forInput(input, this.inventory.get(0), true) != null)
		{
			avalableSlots.add(1);
		}
		return avalableSlots;
	}, Collections.singletonList(2));

	private ItemInstance output;

	public TileEntityElectricAlloySmelter(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
		this.maxEnergyStored.set(2500);
	}

	@Override
	protected void getRecipeAndStart()
	{
		ItemInstance item1 = this.inventory.get(0);
		ItemInstance item2 = this.inventory.get(1);
		AlloyingRecipe recipe;
		if (item1 != null && item2 != null && (recipe = AlloyingRecipe.forInput(item1, item2, false)) != null)
		{
			IUseInfo input1 = recipe.in1;
			IUseInfo input2 = recipe.in2;
			if (item1.getAmount() >= input1.getAmount() && item2.getAmount() >= input2.getAmount())
			{
				ItemInstance rOut = recipe.out;
				ItemInstance var4;
				if (((var4 = this.inventory.get(2)) == null || var4.isEffectivelyEqual(rOut) && var4.fitsAmount(rOut.getAmount())))
				{
					this.output = rOut.copy();

					double chance = Math.pow(2, 5 * (getBonusYieldModifier() / 2f)); // Bonus Yield Modifier
					if ((Util.RANDOM.nextDouble() * 100) < chance && (var4 == null || var4.fitsAmount( output.getAmount() + 1)))
					{
						this.output.addAmount(1);
					}

					this.maxSmeltTime.set((int)((recipe.time / 2.5f) / getSpeedModifier())); // speed multiplier
					this.inventory.remove(0, input1.getAmount());
					this.inventory.remove(1, input2.getAmount());
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

		if ((outSlot = this.inventory.get(2)) != null && outSlot.isEffectivelyEqual(this.output))
		{
			this.inventory.add(2, this.output.getAmount());
		} else
		{
			this.inventory.set(2, this.output);
		}

		this.output = null;
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
