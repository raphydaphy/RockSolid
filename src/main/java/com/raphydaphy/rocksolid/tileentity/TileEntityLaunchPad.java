package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.energy.IEnergyTile;
import com.raphydaphy.rocksolid.entity.EntityRocket;
import com.raphydaphy.rocksolid.fluid.IFluidTile;
import com.raphydaphy.rocksolid.init.ModItems;
import com.raphydaphy.rocksolid.init.ModTiles;
import com.raphydaphy.rocksolid.tile.machine.TileLaunchPad;
import com.raphydaphy.rocksolid.tileentity.base.TileEntityAssemblyConfigurable;
import com.raphydaphy.rocksolid.util.ModUtils;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.TileLiquid;
import de.ellpeck.rockbottom.api.tile.entity.SyncedInt;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.Collections;
import java.util.List;

public class TileEntityLaunchPad extends TileEntityAssemblyConfigurable implements IFluidTile<TileEntityLaunchPad>, IEnergyTile
{
	private SyncedInt fuelVolume = new SyncedInt("fuel_volume");
	private SyncedInt energyStored = new SyncedInt("energy_stored");
	private EntityRocket rocket = null;

	public TileEntityLaunchPad(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
	}

	@Override
	public void save(DataSet set, boolean forSync)
	{
		super.save(set, forSync);
		fuelVolume.save(set);
		energyStored.save(set);
		if (rocket != null)
		{
			set.addUniqueId("rocket_uuid", rocket.getUniqueId());
		}
	}


	@Override
	protected boolean needsSync()
	{
		return fuelVolume.needsSync() || energyStored.needsSync();
	}

	@Override
	public void onSync()
	{
		super.onSync();
		fuelVolume.onSync();
		energyStored.onSync();
	}

	@Override
	public void load(DataSet set, boolean forSync)
	{
		super.load(set, forSync);
		fuelVolume.load(set);
		energyStored.load(set);
		if (set.hasKey("rocket_uuid"))
		{
			rocket = (EntityRocket)this.world.getEntity(set.getUniqueId("rocket_uuid"));
		}
	}

	@Override
	public int getEnergyStored()
	{
		return this.energyStored.get();
	}

	public int getFuelVolume()
	{
		return fuelVolume.get();
	}

	public boolean onRemoved()
	{
		if (rocket != null)
		{
			world.removeEntity(rocket, world.getChunk(x, y));
			return true;
		}
		return false;
	}

	public boolean setRocket(ItemInstance newRocket)
	{
		if (newRocket == null && this.rocket != null)
		{
			if (!world.isClient())
			{
				this.rocket = null;
				this.sendToClients();
				world.setDirty(x, y);
			}
			return true;
		}
		if (this.rocket == null && newRocket.getItem() == ModItems.ROCKET)
		{
			if (!world.isClient())
			{
				this.rocket = new EntityRocket(world);
				rocket.setPos(x + 2f, y + 2.25f);
				world.addEntity(rocket);
				this.sendToClients();
				world.setDirty(x, y);
				rocket.launchPad = new Pos2(this.x, this.y);
			}
			return true;
		}
		return false;
	}

	@Override
	public int getMaxTransfer()
	{
		return Math.round(12 * getThroughputModifier());
	}

	@Override
	public boolean addFluid(Pos2 pos, TileLiquid liquid, int ml, boolean simulate)
	{
		if (liquid.equals(ModTiles.FUEL) && ml + this.fuelVolume.get() <= fluidCapacityNull())
		{
			if (!simulate)
			{
				this.fuelVolume.add(ml);
				world.setDirty(x, y);
			}
			return true;
		}
		return false;
	}

	public float getLiquidFullness()
	{
		return (float) this.fuelVolume.get() / (float)fluidCapacityNull();
	}

	public float getEnergyFullness()
	{
		return (float) this.energyStored.get() / (float)getEnergyCapacity(null, null);
	}

	@Override
	public int getFluidCapacity(IWorld world, Pos2 pos, TileLiquid liquid)
	{
		if (getLiquidsAt(world, pos) != null)
		{
			return fluidCapacityNull();
		}
		return 0;
	}

	@Override
	public List<TileLiquid> getLiquidsAt(IWorld world, Pos2 pos)
	{
		TileState state = world.getState(pos.getX(), pos.getY());

		if (state.getTile() instanceof TileLaunchPad)
		{
			Pos2 inner = ((TileLaunchPad) state.getTile()).getInnerCoord(state);

			if (inner.getX() == 0)
			{
				return Collections.singletonList(ModTiles.FUEL);
			}
		}
		return null;
	}

	private int fluidCapacityNull()
	{
		return Math.round(250 * getCapacityModifier());
	}

	@Override
	public boolean removeFluid(Pos2 pos, TileLiquid liquid, int ml, boolean simulate)
	{
		return false;
	}

	@Override
	public boolean addEnergy(Pos2 pos, int joules, boolean simulate)
	{
		if (joules + energyStored.get() <= getEnergyCapacity(world, pos))
		{
			if (!simulate)
			{
				this.energyStored.add(joules);
				world.setDirty(x, y);
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
		Pos2 innerCoord = ModUtils.innerCoord(world, pos);
		if (innerCoord != null && innerCoord.getX() != 0)
		{
			return 0;
		}
		return Math.round(1000 * getCapacityModifier());
	}

	@Override
	public void update(IGameInstance game)
	{
		super.update(game);
		if (world.getTotalTime() % 5 == 0 && this.energyStored.get() >= Math.round(10 / getEfficiencyModifier()) && this.fuelVolume.get() >= 1 && rocket != null)
		{
			if (!this.world.isClient() && rocket.addFuel(1))
			{
				this.energyStored.remove(Math.round(10 / getEfficiencyModifier()));
				this.fuelVolume.remove(1);
				world.setDirty(x, y);
			}
		}
	}

	@Override
	public boolean doesTick()
	{
		return true;
	}
}
