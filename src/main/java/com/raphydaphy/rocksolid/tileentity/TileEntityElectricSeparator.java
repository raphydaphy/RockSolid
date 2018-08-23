package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.recipe.SeparatorRecipe;
import com.raphydaphy.rocksolid.tileentity.base.TileEntityElectric;
import com.raphydaphy.rocksolid.util.ModUtils;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.entity.TileInventory;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TileEntityElectricSeparator extends TileEntityElectric
{
	private final TileInventory inventory = new TileInventory(this, 3, (input) ->
	{
		ArrayList<Integer> avalableSlots = new ArrayList<>(1);
		if (SeparatorRecipe.forInput(input) != null)
		{
			avalableSlots.add(0);
		}
		return avalableSlots;
	}, Arrays.asList(1, 2));

	public TileEntityElectricSeparator(IWorld world, int x, int y, TileLayer layer)
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
		return SeparatorRecipe.forInput(this.inventory.get(0)) != null;
	}

	public boolean processSmelt()
	{
		SeparatorRecipe r = SeparatorRecipe.forInput(this.inventory.get(0));
		if (r != null && (this.inventory.get(2) == null) || (this.inventory.get(2).getItem().equals(r.biproduct.getItem()) && this.inventory.get(2).getAmount() + r.biproduct.getAmount() <= r.biproduct.getMaxAmount()))
		{
			boolean removed = false;

			if (this.inventory.get(1) == null)
			{
				this.inventory.set(1, r.out.copy());
				removed = true;
			} else if (this.inventory.get(1).getItem().equals(r.out.getItem()) && this.inventory.get(1).getAmount() + r.out.getAmount() <= r.out.getMaxAmount())
			{
				this.inventory.add(1, r.out.getAmount());
				removed = true;
			}

			if (removed)
			{
				if (new Random().nextInt(r.biproductChance) == 0)
				{
					if (this.inventory.get(2) == null)
					{
						this.inventory.set(2, r.biproduct.copy());
					} else
					{
						this.inventory.add(2, r.biproduct.getAmount());
					}
				}
				this.inventory.remove(0, r.in.getAmount());

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
		return 100f;
	}
}
