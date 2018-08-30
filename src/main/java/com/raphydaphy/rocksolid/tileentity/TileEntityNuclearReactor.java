package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.energy.IEnergyTile;
import com.raphydaphy.rocksolid.init.ModItems;
import com.raphydaphy.rocksolid.tileentity.base.IActivatable;
import com.raphydaphy.rocksolid.tileentity.base.TileEntityAssemblyConfigurable;
import com.raphydaphy.rocksolid.util.ModUtils;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.IFilteredInventory;
import de.ellpeck.rockbottom.api.tile.entity.SyncedInt;
import de.ellpeck.rockbottom.api.tile.entity.TileInventory;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.Arrays;
import java.util.Collections;

public class TileEntityNuclearReactor extends TileEntityAssemblyConfigurable implements IEnergyTile, IActivatable
{
	private static final String NEW_HEAT_KEY = "new_heat";
	private static final String TEMPSHIFT_CAPACITY_MODIFIER_KEY = "tempshift_capacity_modifier";

	private final TileInventory inventory = new TileInventory(this, 4, (input) ->
	{
		if (input != null && input.getItem() == ModItems.URANIUM_ROD)
		{
			return Arrays.asList(0, 1, 2, 3);
		}
		return Collections.emptyList();
	}, Collections.emptyList());

	private int newHeat;
	private SyncedInt heat = new SyncedInt("heat");
	private SyncedInt energyStored = new SyncedInt("energy_stored");
	private SyncedInt tempshiftPlates = new SyncedInt("tempshift_plates");
	private float tempshiftCapacityModifier = 1;

	public TileEntityNuclearReactor(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
		if (heat.get() < getMinHeat())
		{
			heat.set(getMinHeat());
		}
	}

	private int getMinHeat()
	{
		return 60;
	}

	@Override
	public IFilteredInventory getTileInventory()
	{
		return inventory;
	}

	@Override
	public void save(DataSet set, boolean forSync)
	{
		super.save(set, forSync);
		heat.save(set);
		energyStored.save(set);
		tempshiftPlates.save(set);
		inventory.save(set);
		set.addFloat(TEMPSHIFT_CAPACITY_MODIFIER_KEY, tempshiftCapacityModifier);
		set.addInt(NEW_HEAT_KEY, newHeat);
	}

	@Override
	public void load(DataSet set, boolean forSync)
	{
		super.load(set, forSync);
		heat.load(set);
		energyStored.load(set);
		tempshiftPlates.load(set);
		inventory.load(set);
		tempshiftCapacityModifier = set.getFloat(TEMPSHIFT_CAPACITY_MODIFIER_KEY);
		newHeat = set.getInt(NEW_HEAT_KEY);
	}

	public void addTempshiftPlate(float modifier)
	{
		this.tempshiftPlates.add(1);
		this.tempshiftCapacityModifier *= modifier;
	}

	public void removeTempshiftPlate(float modifier)
	{
		this.tempshiftPlates.remove(1);
		this.tempshiftCapacityModifier /= modifier;
	}

	public int getTempshiftPlates()
	{
		return this.tempshiftPlates.get();
	}

	@Override
	public int getEnergyStored()
	{
		return this.energyStored.get();
	}

	private int getTickInterval()
	{
		return Math.round(40 / getSpeedModifier());
	}

	@Override
	public void update(IGameInstance game)
	{
		super.update(game);
		if (this.energyStored.get() < getEnergyCapacity(world, null))
		{
			if (!world.isClient())
			{
				int rods = 0;
				for (int slot = 0; slot < 4; slot++)
				{
					if (this.inventory.get(slot) != null)
					{
						float capacityMod = this.inventory.get(slot).getOrCreateAdditionalData().getFloat(ModUtils.ASSEMBLY_CAPACITY_KEY);
						if (capacityMod == 0)
						{
							this.inventory.get(slot).getAdditionalData().addFloat(ModUtils.ASSEMBLY_CAPACITY_KEY, 1);
							this.inventory.notifyChange(slot);
						}
						if (world.getTotalTime() % getTickInterval() == 0)
						{
							damageRod(slot, 1);
						}
						float heatFullness = 1 - getHeatFullness();
						if (heatFullness > 0.5f)
						{
							int bound = Math.round((50 * capacityMod) / (getHeatFullness() * 2f)) * 10;
							if (bound > 0 && Util.RANDOM.nextInt(bound) == 0)
							{
								damageRod(slot, 1);
							}
						}
						rods++;
					}
				}
				if (tempshiftPlates.get() > 0)
				{
					if (world.getTotalTime() % getTickInterval() == 0)
					{
						for (int plate = 0; plate < tempshiftPlates.get(); plate++)
						{
							int toRemove = Math.min(getHeat(), Math.round(6 * tempshiftCapacityModifier));
							newHeat -= toRemove;
						}
					}
				}
				if (rods > 0)
				{
					int rlNewHeat = (rods * (rods + 1) * 2) * rods;
					int production = Math.round(rods * 5 * rods * getEfficiencyModifier() + getBonusYieldModifier());

					if (world.getTotalTime() % getTickInterval() == 0)
					{
						if (this.heat.get() + newHeat <= getHeatCapacity())
						{
							if (this.heat.get() + newHeat < getMinHeat())
							{
								this.heat.set(getMinHeat());
							} else
							{
								this.heat.add(newHeat);
							}
						} else
						{
							this.heat.set(getHeatCapacity());
						}
					}

					if (world.getTotalTime() % getTickInterval() == 0)
					{
						if (this.heat.get() + rlNewHeat <= getHeatCapacity())
						{
							newHeat = rlNewHeat;
						} else
						{
							damageRod(0, rods);
							damageRod(1, rods);
							damageRod(2, rods);
							damageRod(3, rods);
							newHeat = getHeatCapacity() - this.heat.get();
						}
					}

					if (world.getTotalTime() % (Math.round(getTickInterval() / 3)) == 0)
					{
						if (this.energyStored.get() + production < this.getEnergyCapacity(world, null))
						{
							this.energyStored.add(production);
						} else
						{
							this.energyStored.set(getEnergyCapacity(world, null));
						}
					}
				}
			}
		}
	}

	private void damageRod(int slot, int damage)
	{
		ItemInstance item = this.inventory.get(slot);
		if (item != null && item.getItem() == ModItems.URANIUM_ROD)
		{
			int meta = item.getMeta();
			if (meta + damage <= item.getItem().getHighestPossibleMeta())
			{
				item.setMeta(meta + damage);
				this.inventory.notifyChange(slot);
			} else
			{
				this.inventory.set(slot, null);
			}
		}
	}

	@Override
	protected boolean needsSync()
	{
		return heat.needsSync() || energyStored.needsSync() || tempshiftPlates.needsSync();
	}

	@Override
	public void onSync()
	{
		super.onSync();
		heat.onSync();
		energyStored.onSync();
		tempshiftPlates.onSync();
	}

	public float getHeatFullness()
	{
		return ((float) this.heat.get() - 50) / ((float)getHeatCapacity() - 50);
	}

	public float getEnergyFullness()
	{
		return (float) this.energyStored.get() / (float)getEnergyCapacity(world, null);
	}

	@Override
	public boolean addEnergy(Pos2 pos, int joules, boolean simulate)
	{
		return false;
	}

	@Override
	public boolean removeEnergy(Pos2 pos, int joules, boolean simulate)
	{
		if (!world.isClient())
		{
			if (this.energyStored.get() - joules >= 0)
			{
				if (!simulate)
				{
					this.energyStored.remove(joules);
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public int getEnergyCapacity(IWorld world, Pos2 pos)
	{
		Pos2 innerCoord = ModUtils.innerCoord(world, pos);
		if (innerCoord != null && (innerCoord.getY() != 0 || innerCoord.getX() == 0 || innerCoord.getX() == 4))
		{
			return 0;
		}
		return Math.round(2500 * getCapacityModifier());
	}

	public int getHeatCapacity()
	{
		return Math.round(1000 * getCapacityModifier());
	}

	public int getHeat()
	{
		return heat.get();
	}

	public void removeHeat(int amount)
	{
		newHeat -= amount;
	}

	@Override
	public int getMaxTransfer()
	{
		return 50;
	}

	@Override
	public boolean doesTick()
	{
		return true;
	}

	@Override
	public boolean isActive()
	{
		return this.inventory.get(0) != null || this.inventory.get(1) != null || this.inventory.get(2) != null || this.inventory.get(3) != null;
	}
}
