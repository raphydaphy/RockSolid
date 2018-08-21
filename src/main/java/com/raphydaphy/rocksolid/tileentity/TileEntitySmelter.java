package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.recipe.SmelterRecipe;
import com.raphydaphy.rocksolid.util.FilteredTileInventory;
import com.raphydaphy.rocksolid.util.SlotInfo;
import com.raphydaphy.rocksolid.util.SlotInfo.SimpleSlotInfo;
import com.raphydaphy.rocksolid.util.SlotInfo.SlotType;
import de.ellpeck.rockbottom.api.construction.smelting.FuelInput;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileEntitySmelter extends TileEntityFueledBase
{
	private static final String KEY_SMELT_PROGRESS = "smelt_progress";
	private final FilteredTileInventory inventory = new FilteredTileInventory(this, SlotInfo.makeList(new SimpleSlotInfo(SlotType.INPUT, instance -> FuelInput.getFuelTime(instance) > 0), new SimpleSlotInfo(SlotType.INPUT, instance -> SmelterRecipe.getFromInputs(instance) != null), new SimpleSlotInfo(SlotType.OUTPUT)));
	private int smeltProgress = 0;
	private int lastSmeltProgress = 0;

	public TileEntitySmelter(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
	}

	@Override
	public FilteredTileInventory getTileInventory()
	{
		return this.inventory;
	}

	@Override
	public void save(DataSet set, boolean forSync)
	{
		super.save(set, forSync);
		set.addInt(KEY_SMELT_PROGRESS, smeltProgress);
		inventory.save(set);
	}

	@Override
	public void load(DataSet set, boolean forSync)
	{
		super.load(set, forSync);
		smeltProgress = set.getInt(KEY_SMELT_PROGRESS);
		inventory.load(set);
	}

	public float getSmeltPercent()
	{
		return this.smeltProgress / 300f;
	}

	@Override
	protected boolean tryTickAction()
	{
		SmelterRecipe r = SmelterRecipe.getFromInputs(this.inventory.get(1));
		if (r != null)
		{
			if (this.coalTime > 0)
			{
				if (getSmeltPercent() >= 1)
				{
					boolean removed = false;
					if (this.inventory.get(2) == null)
					{
						this.inventory.set(2, r.out.copy());
						removed = true;
					} else if (this.inventory.get(2).getItem().equals(r.out.getItem()) && this.inventory.get(2).getAmount() + r.out.getAmount() <= r.out.getMaxAmount())
					{
						this.inventory.add(2, r.out.getAmount());
						removed = true;
					}

					if (removed)
					{
						this.inventory.remove(1, r.in.getAmount());
						this.smeltProgress = 0;
					}

				} else
				{
					this.smeltProgress++;
				}
			} else if (smeltProgress > 0)
			{
				smeltProgress--;
			}
			return true;
		} else if (smeltProgress > 0)
		{
			smeltProgress = 0;
		}
		return false;
	}

	@Override
	protected float getFuelModifier()
	{
		return 1.5f;
	}

	@Override
	protected boolean needsSync()
	{
		return this.smeltProgress != this.lastSmeltProgress || super.needsSync();
	}

	@Override
	public void onSync()
	{
		super.onSync();
		this.lastSmeltProgress = this.smeltProgress;
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
