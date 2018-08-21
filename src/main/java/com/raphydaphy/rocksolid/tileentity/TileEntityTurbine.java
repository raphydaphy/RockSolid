package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.energy.IEnergyTile;
import com.raphydaphy.rocksolid.gas.Gas;
import com.raphydaphy.rocksolid.gas.IGasTile;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class TileEntityTurbine extends TileEntity implements IEnergyTile, IGasTile<TileEntityTurbine>
{
	public static final String KEY_ENERGY_STORED = "energy_stored";
	private static final String KEY_STEAM_VOLUME = "steam_volume";
	private final ResourceName TURBINE_SOUND = RockSolid.createRes("turbine");
	private int steamVolume = 0;
	private int lastSteamVolume = 0;
	private int energyStored = 0;
	private int lastEnergyStored = 0;
	private int lastPlayed = -1;

	public TileEntityTurbine(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
	}

	@Override
	public void save(DataSet set, boolean forSync)
	{
		super.save(set, forSync);
		set.addInt(KEY_STEAM_VOLUME, this.steamVolume);
		set.addInt(KEY_ENERGY_STORED, this.energyStored);
	}

	@Override
	public void load(DataSet set, boolean forSync)
	{
		super.load(set, forSync);
		this.steamVolume = set.getInt(KEY_STEAM_VOLUME);
		this.energyStored = set.getInt(KEY_ENERGY_STORED);
	}

	@Override
	public int getEnergyStored()
	{
		return this.energyStored;
	}

	@Override
	public void update(IGameInstance game)
	{
		super.update(game);
		if (this.steamVolume > 0 && this.energyStored < 2500)
		{
			if (!world.isClient())
			{
				if (world.getTotalTime() % 12 == 0)
				{
					this.steamVolume--;
				}
				this.energyStored++;
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
		return this.steamVolume != this.lastSteamVolume || this.energyStored != this.lastEnergyStored;
	}

	@Override
	public void onSync()
	{
		super.onSync();
		this.lastSteamVolume = this.steamVolume;
		this.lastEnergyStored = this.energyStored;
	}

	public float getSteamFullness()
	{
		return (float) this.steamVolume / 1000;
	}

	public float getEnergyFullness()
	{
		return (float) this.energyStored / 2500f;
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
			if (this.energyStored - joules >= 0)
			{
				if (!simulate)
				{
					this.energyStored -= joules;
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public int getEnergyCapacity(IWorld world, Pos2 pos)
	{
		return 2500;
	}

	@Override
	public boolean addGas(Pos2 pos, Gas liquid, int cc, boolean simulate)
	{
		if (cc + this.steamVolume <= 1000)
		{
			if (!simulate)
			{
				this.steamVolume += cc;
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
		return 1000;
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
