package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.recipe.SeparatorRecipe;
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
import java.util.Arrays;

public class TileEntityElectricSeparator extends TileEntityElectric
{
	private static final String KEY_OUTPUT = "output";
	private static final String KEY_BIPRODUCT = "biproduct";

	private final TileInventory inventory = new TileInventory(this, 3, (input) ->
	{
		ArrayList<Integer> avalableSlots = new ArrayList<>(1);
		if (SeparatorRecipe.forInput(input) != null)
		{
			avalableSlots.add(0);
		}
		return avalableSlots;
	}, Arrays.asList(1, 2));

	public ItemInstance output;
	public ItemInstance biproduct;

	public TileEntityElectricSeparator(IWorld world, int x, int y, TileLayer layer)
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
		inventory.load(set);
		if (!forSync)
		{
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
		if ((item = this.inventory.get(0)) != null && (recipe = SeparatorRecipe.forInput(item)) != null)
		{
			IUseInfo input = recipe.in;
			if (item.getAmount() >= input.getAmount())
			{
				item = recipe.out;
				ItemInstance biproduct = recipe.biproduct;
				ItemInstance var4;
				boolean doBiproduct = Util.RANDOM.nextInt((int)(recipe.biproductChance / getBonusYieldModifier())) == 0; // Bonus Yield Modifier
				if (((var4 = this.inventory.get(1)) == null || var4.isEffectivelyEqual(item) && var4.fitsAmount(item.getAmount())) && (!doBiproduct || ((var4 = this.inventory.get(2)) == null || var4.isEffectivelyEqual(biproduct) && var4.fitsAmount(biproduct.getAmount()))))
				{
					this.output = item.copy();

					double chance = Math.pow(2, 5 * (getBonusYieldModifier() / 2f)); // Bonus Yield Modifier
					if ((Util.RANDOM.nextDouble() * 100) < chance && this.inventory.get(1).fitsAmount(this.inventory.get(1).getAmount() + 1))
					{
						this.output.addAmount(1);
					}

					this.maxSmeltTime.set((int)((recipe.time / 2.5f) / getSpeedModifier())); // speed multiplier
					this.biproduct = null;
					if (doBiproduct)
					{
						this.biproduct = biproduct.copy();
					}
					this.inventory.remove(0, input.getAmount());
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

		if ((outSlot = this.inventory.get(1)) != null && outSlot.isEffectivelyEqual(this.output))
		{
			this.inventory.add(1, this.output.getAmount());
		} else
		{
			this.inventory.set(1, this.output);
		}

		if (biproduct != null)
		{
			if ((outSlot = this.inventory.get(2)) != null && outSlot.isEffectivelyEqual(this.biproduct))
			{
				this.inventory.add(2, this.biproduct.getAmount());
			} else
			{
				this.inventory.set(2, this.biproduct);
			}
		}

		this.output = null;
		this.biproduct = null;
	}

	@Override
	public int getEnergyCapacity(IWorld world, Pos2 pos)
	{
		Pos2 innerCoord = ModUtils.innerCoord(world, pos);
		if (innerCoord != null && innerCoord.getY() == 1)
		{
			return 0;
		}
		return this.maxEnergyStored.get();
	}
}
