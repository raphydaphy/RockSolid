package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.api.energy.IEnergyProducer;
import com.raphydaphy.rocksolid.api.gas.IGasAcceptor;
import com.raphydaphy.rocksolid.init.ModGasses;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.world.IWorld;

public class TileEntityTurbine extends TileEntity implements IGasAcceptor, IEnergyProducer
{

	private boolean shouldSync = false;
	private boolean lastActive;

	public static final int productionPerTick = 30;
	public static final int gasConsumptionPerTick = 1;
	
	

	protected int gasStored;
	protected int maxGas = 5000;
	protected String gasType = ModGasses.gasVacuum.toString();

	protected int energyStored;
	protected int maxEnergy = 25000;

	public TileEntityTurbine(final IWorld world, final int x, final int y)
	{
		super(world, x, y);
	}

	@Override
	protected boolean needsSync()
	{
		return super.needsSync() || shouldSync;
	}

	@Override
	protected void onSync()
	{
		super.onSync();
		shouldSync = false;
	}

	@Override
	public void update(IGameInstance game)
	{
		super.update(game);
		
		if (this.gasStored >= gasConsumptionPerTick && this.energyStored + productionPerTick <= this.maxEnergy
				&& this.gasType.equals(ModGasses.gasSteam.toString()))
		{
			
			if (RockBottomAPI.getNet().isClient() == false)
			{
				this.gasStored -= gasConsumptionPerTick;
				this.energyStored += productionPerTick;

				if (this.gasStored == 0)
				{
					this.gasType = ModGasses.gasVacuum.toString();
				}
				this.shouldSync = true;
			}
		}

		boolean active = this.isActive();

		if (this.lastActive != active)
		{
			if (RockBottomAPI.getNet().isClient() == false)
			{
				this.lastActive = active;
				shouldSync = true;
			}
			this.onActiveChange(active);
		}
	}

	private void onActiveChange(boolean active)
	{
		world.causeLightUpdate(x, y);
	}

	public float getGeneratorFullness()
	{
		if (this.energyStored == 0)
		{
			return 0;
		}
		return (float) this.energyStored / (float) this.maxEnergy;
	}

	public boolean isActive()
	{
		return this.gasStored >= gasConsumptionPerTick && this.energyStored + productionPerTick <= this.maxEnergy
				&& this.gasType.equals(ModGasses.gasSteam.toString());
	}

	public float getGasTankFullness()
	{
		if (this.gasStored == 0)
		{
			return 0;
		}
		return (float) this.gasStored / (float) this.maxGas;
	}

	@Override
	public void save(final DataSet set, final boolean forSync)
	{
		super.save(set, forSync);
		set.addBoolean("shouldSync", this.shouldSync);
		set.addString("gasType", this.gasType);
		set.addInt("gasStored", this.gasStored);
		set.addInt("maxGas", this.maxGas);
		set.addInt("energyStored", this.energyStored);
		set.addInt("maxEnergy", this.maxEnergy);
	}

	@Override
	public void load(final DataSet set, final boolean forSync)
	{
		super.load(set, forSync);
		this.shouldSync = set.getBoolean("shouldSync");
		this.gasType = set.getString("gasType");
		this.gasStored = set.getInt("gasStored");
		this.maxGas = set.getInt("maxGas");
		this.energyStored = set.getInt("energyStored");
		this.maxEnergy = set.getInt("maxEnergy");
	}

	@Override
	public int getCurrentGas()
	{
		return this.gasStored;
	}

	@Override
	public int getMaxGas()
	{
		return this.maxGas;
	}

	@Override
	public String getGasType()
	{
		return this.gasType;
	}

	@Override
	public int getCurrentEnergy()
	{
		return this.energyStored;
	}

	@Override
	public int getMaxEnergy()
	{
		return this.maxEnergy;
	}

	@Override
	public boolean removeEnergy(int amount)
	{
		if (this.energyStored >= amount)
		{
			if (RockBottomAPI.getNet().isClient() == false)
			{
				this.energyStored -= amount;
				this.shouldSync = true;
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean addGas(int amount, String type)
	{
		if (type.equals(this.gasType) || this.gasType.equals(ModGasses.gasVacuum.toString()))
		{
			if (this.gasStored + amount <= this.maxGas)
			{
				if (RockBottomAPI.getNet().isClient() == false)
				{
					if (this.gasType.equals(ModGasses.gasVacuum.toString()))
					{
						this.gasType = type;
					}
					this.gasStored += amount;
					this.shouldSync = true;
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean setGasType(String type)
	{
		if (this.gasStored == 0 || this.gasType.equals(type))
		{
			if (RockBottomAPI.getNet().isClient() == false)
			{
				this.gasType = type;
				this.shouldSync = true;
			}
			return true;
		}
		return false;
	}

}
