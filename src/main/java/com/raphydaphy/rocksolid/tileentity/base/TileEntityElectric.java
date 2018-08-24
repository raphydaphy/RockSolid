package com.raphydaphy.rocksolid.tileentity.base;

import com.raphydaphy.rocksolid.energy.IEnergyTile;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.entity.SyncedInt;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public abstract class TileEntityElectric extends TileEntityAssemblyConfigurable implements IEnergyTile
{
	protected final SyncedInt smeltTime = new SyncedInt("smelt_time");
	protected final SyncedInt maxSmeltTime = new SyncedInt("max_smelt_time");
	protected final SyncedInt energyStored = new SyncedInt("energy_stored");
	protected final SyncedInt maxEnergyStored = new SyncedInt("max_energy_stored");

	private boolean lastActive = false;

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
		return this.energyStored.get();
	}

	@Override
	public void save(DataSet set, boolean forSync)
	{
		super.save(set, forSync);
		smeltTime.save(set);
		maxSmeltTime.save(set);
		energyStored.save(set);
		maxEnergyStored.save(set);
	}

	@Override
	public void load(DataSet set, boolean forSync)
	{
		super.load(set, forSync);
		smeltTime.load(set);
		maxSmeltTime.load(set);
		energyStored.load(set);
		maxEnergyStored.load(set);
	}

	public float getSmeltPercentage()
	{
		return this.maxSmeltTime.get() > 0 ? (float) this.smeltTime.get() / (float) this.maxSmeltTime.get() : 0.0F;
	}

	public float getEnergyFullness()
	{
		int capacity = getEnergyCapacity(world, null);
		return capacity > 0 ? (float) this.energyStored.get() / (float) capacity : 0.0F;
	}

	protected abstract void getRecipeAndStart();

	protected abstract void putOutputItems();

	public int getEnergyPerTick()
	{
		return Math.max(1,Math.round((1 + getSpeedModifier()) / getEfficiencyModifier()));
	}

	@Override
	public int getMaxTransfer()
	{
		return (int)(getEnergyPerTick() * 5 * getThroughputModifier());
	}

	@Override
	public void update(IGameInstance game)
	{
		super.update(game);

		if (!world.isClient())
		{
			if (this.maxSmeltTime.get() <= 0)
			{
				getRecipeAndStart();
			} else if (this.energyStored.get() <= 0 && this.smeltTime.get() > 0)
			{
				this.smeltTime.remove(1);
			} else if (this.energyStored.get() > getEnergyPerTick())
			{
				// because it's a bad furnace
				if (Util.RANDOM.nextFloat() >= 0.45F)
				{
					this.smeltTime.add(1);
				}
				if (this.smeltTime.get() >= this.maxSmeltTime.get())
				{
					putOutputItems();
					this.smeltTime.set(0);
					this.maxSmeltTime.set(0);
				}
			}

			if (this.energyStored.get() > 0 && this.smeltTime.get() > 0)
			{
				this.energyStored.remove(getEnergyPerTick());
			}


			boolean active = this.isActive();
			if (this.lastActive != active)
			{
				this.lastActive = active;

				this.onActiveChange(active);
			}
		}
	}

	@Override
	protected boolean needsSync()
	{
		return this.smeltTime.needsSync() || this.maxSmeltTime.needsSync() || this.energyStored.needsSync() || this.maxEnergyStored.needsSync() || super.needsSync();
	}

	@Override
	public void onSync()
	{
		super.onSync();
		smeltTime.onSync();
		maxSmeltTime.onSync();
		energyStored.onSync();
		maxEnergyStored.onSync();
	}

	public void onActiveChange(boolean active)
	{
		world.causeLightUpdate(this.x, this.y);
	}

	public boolean isActive()
	{
		return this.smeltTime.get() > 0;
	}

	@Override
	public boolean addEnergy(Pos2 pos, int joules, boolean simulate)
	{
		if (this.energyStored.get() + joules < getEnergyCapacity(world, pos))
		{
			if (!simulate && !world.isClient())
			{
				this.energyStored.add(joules);
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

	@Override
	public int getEnergyCapacity(IWorld world, Pos2 pos)
	{
		return (int)(maxEnergyStored.get() * this.getCapacityModifier());
	}
}
