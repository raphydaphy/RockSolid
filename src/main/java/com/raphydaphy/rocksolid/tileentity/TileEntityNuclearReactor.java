package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.energy.IEnergyTile;
import com.raphydaphy.rocksolid.init.ModItems;
import com.raphydaphy.rocksolid.item.ItemDurability;
import com.raphydaphy.rocksolid.tileentity.base.IActivatable;
import com.raphydaphy.rocksolid.tileentity.base.TileEntityAssemblyConfigurable;
import com.raphydaphy.rocksolid.util.ModUtils;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.IFilteredInventory;
import de.ellpeck.rockbottom.api.tile.entity.SyncedInt;
import de.ellpeck.rockbottom.api.tile.entity.TileInventory;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.Arrays;
import java.util.Collections;

public class TileEntityNuclearReactor extends TileEntityAssemblyConfigurable implements IEnergyTile, IActivatable
{
	private final TileInventory inventory = new TileInventory(this, 4, (input) ->
	{
		if (input != null && input.getItem() == ModItems.URANIUM_ROD)
		{
			return Arrays.asList(0, 1, 2, 3);
		}
		return Collections.emptyList();
	}, Collections.emptyList());

	private SyncedInt heat = new SyncedInt("heat");
	private SyncedInt energyStored = new SyncedInt("energy_stored");
	private SyncedInt depth = new SyncedInt("rod_depth");

	public TileEntityNuclearReactor(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
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
		depth.save(set);
		inventory.save(set);
	}

	@Override
	public void load(DataSet set, boolean forSync)
	{
		super.load(set, forSync);
		heat.load(set);
		energyStored.load(set);
		depth.load(set);
		inventory.load(set);
	}

	public void setDepth(int depth)
	{
		this.depth.set(depth);
		world.setDirty(x, y);
	}

	public int getDepth()
	{
		return this.depth.get();
	}

	@Override
	public int getEnergyStored()
	{
		return this.energyStored.get();
	}

	@Override
	public void update(IGameInstance game)
	{
		super.update(game);
		int production = Math.round(10 * getEfficiencyModifier() + getBonusYieldModifier());
		if (isActive() && this.energyStored.get() + production < getEnergyCapacity(world, null))
		{
			if (!world.isClient())
			{
				if (world.getTotalTime() % (12 / getSpeedModifier()) == 0)
				{
					if (this.heat.get() + 5 < getHeatCapacity())
					{
						this.heat.add(5);
					}
					else
					{
						damageRod(0, 5);
						damageRod(1, 5);
						damageRod(2, 5);
						damageRod(3, 5);
					}
					this.energyStored.add(production);
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
		return heat.needsSync() || energyStored.needsSync() || depth.needsSync();
	}

	@Override
	public void onSync()
	{
		super.onSync();
		heat.onSync();
		energyStored.onSync();
		depth.onSync();
	}

	public float getHeatFullness()
	{
		return (float) this.heat.get() / (float)getHeatCapacity();
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
