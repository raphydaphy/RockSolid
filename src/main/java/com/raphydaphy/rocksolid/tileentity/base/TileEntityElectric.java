package com.raphydaphy.rocksolid.tileentity.base;

import com.raphydaphy.rocksolid.energy.IEnergyTile;
import com.raphydaphy.rocksolid.recipe.SeparatorRecipe;
import com.raphydaphy.rocksolid.util.FilteredTileInventory;
import com.raphydaphy.rocksolid.util.ModUtils;
import com.raphydaphy.rocksolid.util.SlotInfo;
import com.raphydaphy.rocksolid.util.SlotInfo.SimpleSlotInfo;
import com.raphydaphy.rocksolid.util.SlotInfo.SlotType;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.Random;

public abstract class TileEntityElectric extends TileEntity implements IEnergyTile
{
	private static final String KEY_SMELT_PROGRESS = "smelt_progress";
	private static final String KEY_ENERGY_STORED = "energy_stored";
	public int smeltProgress = 0;
	public int energyStored = 0;
	private int lastSmeltProgress = 0;
	private int lastEnergyStored = 0;

	public TileEntityElectric(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
	}

	@Override
	public boolean doesTick()
	{
		return true;
	}

	@Override
	public int getEnergyStored()
	{
		return this.energyStored;
	}

	@Override
	public void save(DataSet set, boolean forSync)
	{
		super.save(set, forSync);
		set.addInt(KEY_SMELT_PROGRESS, smeltProgress);
		set.addInt(KEY_ENERGY_STORED, energyStored);
	}

	@Override
	public void load(DataSet set, boolean forSync)
	{
		super.load(set, forSync);
		smeltProgress = set.getInt(KEY_SMELT_PROGRESS);
		energyStored = set.getInt(KEY_ENERGY_STORED);

		System.out.println(energyStored);
	}

	public abstract float getSmeltTime();

	public float getSmeltPercent()
	{
		return this.smeltProgress / getSmeltTime();
	}

	public abstract boolean hasValidRecipe();

	public abstract boolean processSmelt();

	public int getEnergyPerTick()
	{
		return 1;
	}

	@Override
	public int getMaxTransfer()
	{
		return getEnergyPerTick() * 2;
	}

	@Override
	public void update(IGameInstance game)
	{
		if (!world.isClient())
		{
			boolean lastActive = isActive();
			if (hasValidRecipe())
			{
				if (this.energyStored >= getEnergyPerTick())
				{
					if (getSmeltPercent() >= 1)
					{
						if (processSmelt())
						{
							this.smeltProgress = 0;
						}
					} else
					{
						this.smeltProgress++;
						this.energyStored -= getEnergyPerTick();


					}
				} else if (smeltProgress > 0)
				{
					smeltProgress--;
				}
			} else if (smeltProgress > 0)
			{
				smeltProgress = 0;
			}

			if (isActive() != lastActive)
			{
				onActiveChange(isActive());
			}
		}
	}

	@Override
	protected boolean needsSync()
	{
		return this.smeltProgress != this.lastSmeltProgress || this.energyStored != this.lastEnergyStored || super.needsSync();
	}

	@Override
	public void onSync()
	{
		super.onSync();
		this.lastSmeltProgress = this.smeltProgress;
		this.lastEnergyStored = this.energyStored;
	}

	public void onActiveChange(boolean active)
	{
		world.causeLightUpdate(this.x, this.y);
	}

	public boolean isActive()
	{
		return this.smeltProgress > 0;
	}

	@Override
	public boolean addEnergy(Pos2 pos, int joules, boolean simulate)
	{
		if (this.energyStored + joules < getEnergyCapacity(world, pos))
		{
			if (!simulate && !world.isClient())
			{
				this.energyStored += joules;
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean removeEnergy(Pos2 pos, int joules, boolean simulate)
	{
		return false;
	}

	public float getEnergyFullness()
	{
		return (float) this.energyStored / getEnergyCapacity(world, null);
	}

	@Override
	public int getEnergyCapacity(IWorld world, Pos2 pos)
	{
		return 2500;
	}
}
