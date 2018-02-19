package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.energy.IEnergyTile;
import com.raphydaphy.rocksolid.fluid.IFluidTile;
import com.raphydaphy.rocksolid.tile.multi.TilePump;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.TileLiquid;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.Arrays;
import java.util.List;

public class TileEntityBattery extends TileEntity implements IEnergyTile
{
	public static final String KEY_ENERGY_STORED = "energy_stored";

	private int energyStored = 0;
	private int lastEnergyStored = 0;

	public TileEntityBattery(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
	}

	@Override
	public void save(DataSet set, boolean forSync)
	{
		super.save(set, forSync);
		set.addInt(KEY_ENERGY_STORED, this.energyStored);
	}

	@Override
	public void load(DataSet set, boolean forSync)
	{
		super.load(set, forSync);
		this.energyStored = set.getInt(KEY_ENERGY_STORED);
	}

	@Override
	protected boolean needsSync()
	{
		return this.energyStored != this.lastEnergyStored;
	}

	@Override
	public void onSync()
	{
		super.onSync();
		this.lastEnergyStored = this.energyStored;
	}

	@Override
	public boolean addEnergy(Pos2 pos, int joules, boolean simulate)
	{
		if (joules + energyStored <= getEnergyCapacity(world, pos))
		{
			if (!simulate)
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
		if (energyStored - joules > 0)
		{
			if (!simulate)
			{
				this.energyStored -= joules;
			}
			return true;
		}
		return false;
	}

	@Override
	public int getEnergyCapacity(IWorld world, Pos2 pos)
	{
		return 25000;
	}

	public float getEnergyFullness()
	{
		return (float)energyStored / getEnergyCapacity(null, null);
	}

	@Override
	public boolean doesTick()
	{
		return false;
	}
}
