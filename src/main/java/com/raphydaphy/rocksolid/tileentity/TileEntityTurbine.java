package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.energy.IEnergyTile;
import com.raphydaphy.rocksolid.gas.Gas;
import com.raphydaphy.rocksolid.gas.IGasTile;
import com.raphydaphy.rocksolid.tileentity.base.TileEntityAssemblyConfigurable;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.entity.SyncedInt;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class TileEntityTurbine extends TileEntityAssemblyConfigurable implements IEnergyTile, IGasTile<TileEntityTurbine>
{
	private final ResourceName TURBINE_SOUND = RockSolid.createRes("turbine");

	private SyncedInt steamVolume = new SyncedInt("steam_volume");
	private SyncedInt energyStored = new SyncedInt("energy_stored");

	private int lastPlayed = -1;

	public TileEntityTurbine(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
	}

	@Override
	public void save(DataSet set, boolean forSync)
	{
		super.save(set, forSync);
		steamVolume.save(set);
		energyStored.save(set);
	}

	@Override
	public void load(DataSet set, boolean forSync)
	{
		super.load(set, forSync);
		steamVolume.load(set);
		energyStored.load(set);
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
		if (this.steamVolume.get() > 0 && this.energyStored.get() < getEnergyCapacity(world, null))
		{
			if (!world.isClient())
			{
				if (world.getTotalTime() % Math.round(12 / getSpeedModifier()) == 0)
				{
					this.steamVolume.remove(1);
					this.energyStored.add(Math.round(10 * getEfficiencyModifier() + getBonusYieldModifier()));
				}
			}
			if (!(this.world.isDedicatedServer() && this.world.isServer()))
			{
				if (lastPlayed == -1 || world.getTotalTime() - lastPlayed >= 320)
				{
					world.playSound(TURBINE_SOUND, x + 0.5d, y + 0.5d, layer.index(), 1, 4);
					lastPlayed = world.getTotalTime();
				}
			}
		}
	}

	@Override
	protected boolean needsSync()
	{
		return steamVolume.needsSync() || energyStored.needsSync();
	}

	@Override
	public void onSync()
	{
		super.onSync();
		steamVolume.onSync();
		energyStored.onSync();
	}

	public float getSteamFullness()
	{
		return (float) this.steamVolume.get() / (float)getGasCapacity(null, null, Gas.STEAM);
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
		return Math.round(2500 * getCapacityModifier());
	}

	public int getSteamVolume()
	{
		return steamVolume.get();
	}

	@Override
	public boolean addGas(Pos2 pos, Gas gas, int cc, boolean simulate)
	{
		if (cc + this.steamVolume.get() <= getGasCapacity(world, pos, gas))
		{
			if (!simulate)
			{
				this.steamVolume.add(cc);
			}
			return true;
		}
		return false;
	}

	@Override
	public int getMaxTransfer()
	{
		return 3;
	}


	@Override
	public boolean removeGas(Pos2 pos, Gas liquid, int cc, boolean simulate)
	{
		return false;
	}

	@Override
	public int getGasCapacity(IWorld world, Pos2 pos, Gas gas)
	{
		return Math.round(1000 * getCapacityModifier());
	}

	@Nullable
	@Override
	public List<Gas> getGasAt(IWorld world, Pos2 pos)
	{
		return Collections.singletonList(Gas.STEAM);
	}

	@Override
	public boolean doesTick()
	{
		return true;
	}
}
