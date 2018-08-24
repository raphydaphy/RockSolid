package com.raphydaphy.rocksolid.tileentity.base;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.construction.smelting.FuelInput;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.SyncedInt;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

// TODO: it's not ideal to have all fueled TE's extend configurable just so that boilers can be
public abstract class TileEntityFueledBase extends TileEntityAssemblyConfigurable implements IActivatable
{
	protected final SyncedInt smeltTime = new SyncedInt("smelt_time");
	protected final SyncedInt maxSmeltTime = new SyncedInt("max_smelt_time");
	protected final SyncedInt fuelTime = new SyncedInt("fuel_time");
	protected final SyncedInt maxFuelTime = new SyncedInt("max_fuel_time");

	private boolean lastActive = false;

	public TileEntityFueledBase(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
	}

	protected abstract void getRecipeAndStart();

	protected abstract void putOutputItems();

	protected abstract ItemInstance getFuel();

	protected abstract void removeFuel();

	protected abstract void onActiveChange(boolean active);

	@Override
	public void update(IGameInstance game)
	{
		super.update(game);

		if (!world.isClient())
		{
			if (this.maxSmeltTime.get() <= 0)
			{
				getRecipeAndStart();
			} else if (this.fuelTime.get() <= 0)
			{
				ItemInstance fuelSlot = this.getFuel();
				int fuelSlotTime;
				if (fuelSlot != null && (fuelSlotTime = FuelInput.getFuelTime(fuelSlot)) > 0)
				{
					this.fuelTime.set(fuelSlotTime);
					this.maxFuelTime.set(fuelSlotTime);
					this.removeFuel();
				}

				if (this.fuelTime.get() <= 0 && this.smeltTime.get() > 0)
				{
					this.smeltTime.remove(1);
				}
			} else
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

			if (this.fuelTime.get() > 0)
			{
				this.fuelTime.remove(1);
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
	public boolean isActive()
	{
		return this.fuelTime.get() > 0 || this.smeltTime.get() > 0;
	}

	public float getFuelPercentage()
	{
		return this.maxFuelTime.get() > 0 ? (float) this.fuelTime.get() / (float) this.maxFuelTime.get() : 0.0F;
	}

	public float getSmeltPercentage()
	{
		return this.maxSmeltTime.get() > 0 ? (float) this.smeltTime.get() / (float) this.maxSmeltTime.get() : 0.0F;
	}

	@Override
	protected boolean needsSync()
	{
		return smeltTime.needsSync() || maxSmeltTime.needsSync() || fuelTime.needsSync() || maxFuelTime.needsSync();
	}

	@Override
	protected void onSync()
	{
		smeltTime.onSync();
		maxSmeltTime.onSync();
		fuelTime.onSync();
		maxFuelTime.onSync();
	}

	@Override
	public void save(DataSet set, boolean forSync)
	{
		super.save(set, forSync);
		smeltTime.save(set);
		maxSmeltTime.save(set);
		fuelTime.save(set);
		maxFuelTime.save(set);
	}

	@Override
	public void load(DataSet set, boolean forSync)
	{
		super.load(set, forSync);
		smeltTime.load(set);
		maxSmeltTime.load(set);
		fuelTime.load(set);
		maxFuelTime.load(set);
	}

	public boolean doesTick()
	{
		return true;
	}

}
