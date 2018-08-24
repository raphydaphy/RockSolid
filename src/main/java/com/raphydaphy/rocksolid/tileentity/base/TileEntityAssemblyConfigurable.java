package com.raphydaphy.rocksolid.tileentity.base;

import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public abstract class TileEntityAssemblyConfigurable extends TileEntity
{
	private float capacityModifier = 1;
	private float efficiencyModifier = 1;
	private float speedModifier = 1;
	private float bonusYieldModifier = 1;
	private float throughputModifier = 1;

	public TileEntityAssemblyConfigurable(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
	}

	@Override
	public void save(DataSet set, boolean forSync)
	{
		super.save(set, forSync);
		set.addFloat("assembly_stat_capacity", capacityModifier);
		set.addFloat("assembly_stat_efficiency", efficiencyModifier);
		set.addFloat("assembly_stat_speed", speedModifier);
		set.addFloat("assembly_stat_bonud_yield", bonusYieldModifier);
		set.addFloat("assembly_stat_throughput", throughputModifier);
	}

	@Override
	public void load(DataSet set, boolean forSync)
	{
		super.load(set, forSync);
		capacityModifier = set.getFloat("assembly_stat_capacity");
		efficiencyModifier = set.getFloat("assembly_stat_efficiency");
		speedModifier = set.getFloat("assembly_stat_speed");
		bonusYieldModifier = set.getFloat("assembly_stat_bonud_yield");
		throughputModifier = set.getFloat("assembly_stat_throughput");
	}

	public float getCapacityModifier()
	{
		return capacityModifier;
	}

	public float getEfficiencyModifier()
	{
		return efficiencyModifier;
	}

	public float getSpeedModifier()
	{
		return speedModifier;
	}

	public float getBonusYieldModifier()
	{
		return bonusYieldModifier;
	}

	public float getThroughputModifier()
	{
		return throughputModifier;
	}

	public void setCapacityModifier(float capacityModifier)
	{
		this.capacityModifier = capacityModifier;
	}

	public void setEfficiencyModifier(float efficiencyModifier)
	{
		this.efficiencyModifier = efficiencyModifier;
	}

	public void setSpeedModifier(float speedModifier)
	{
		this.speedModifier = speedModifier;
	}

	public void setBonusYieldModifier(float bonusYieldModifier)
	{
		this.bonusYieldModifier = bonusYieldModifier;
	}

	public void setThroughputModifier(float throughputModifier)
	{
		this.throughputModifier = throughputModifier;
	}
}
