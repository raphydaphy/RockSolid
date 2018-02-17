package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.recipe.AlloySmelterRecipe;
import com.raphydaphy.rocksolid.util.FilteredTileInventory;
import com.raphydaphy.rocksolid.util.SlotInfo;
import com.raphydaphy.rocksolid.util.SlotInfo.SimpleSlotInfo;
import com.raphydaphy.rocksolid.util.SlotInfo.SlotType;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileEntityAlloySmelter extends TileEntityFueledBase
{
	private static final String KEY_SMELT_PROGRESS = "smelt_progress";
	private final FilteredTileInventory inventory = new FilteredTileInventory(this, SlotInfo.makeList(new SimpleSlotInfo(SlotType.INPUT, instance -> instance.getItem().equals(GameContent.TILE_COAL.getItem())), new SimpleSlotInfo(SlotType.INPUT, instance -> (AlloySmelterRecipe.getFromInputs(instance, this.getInventory().get(2), true) != null)), new SimpleSlotInfo(SlotType.INPUT, instance -> (AlloySmelterRecipe.getFromInputs(instance, this.getInventory().get(1), true) != null)), new SimpleSlotInfo(SlotType.OUTPUT)));
	private int smeltProgress = 0;
	private int lastSmeltProgress = 0;

	public TileEntityAlloySmelter(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
	}

	@Override
	public FilteredTileInventory getInventory()
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
		return this.smeltProgress / 625f;
	}

	@Override
	protected boolean tryTickAction()
	{
		AlloySmelterRecipe r = AlloySmelterRecipe.getFromInputs(this.inventory.get(1), this.inventory.get(2), false);
		if (r != null)
		{
			if (this.coalTime > 0)
			{
				if (getSmeltPercent() >= 1)
				{
					boolean removed = false;

					if (this.inventory.get(3) == null)
					{
						this.inventory.set(3, r.out.copy());
						removed = true;
					} else if (this.inventory.get(3).getItem().equals(r.out.getItem()) && this.inventory.get(3).getAmount() + r.out.getAmount() <= r.out.getMaxAmount())
					{
						this.inventory.add(3, r.out.getAmount());
						removed = true;
					}

					if (removed)
					{
						this.inventory.remove(1, r.in1.getAmount());
						this.inventory.remove(2, r.in2.getAmount());
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
		return 1f;
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
		return this.getInventory().get(0);
	}

	@Override
	protected void removeFuel()
	{
		this.getInventory().remove(0, 1);
	}

	@Override
	protected void onActiveChange(boolean active)
	{
		world.causeLightUpdate(this.x, this.y);
	}

}
