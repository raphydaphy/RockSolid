package com.raphydaphy.rocksolid.api.util;

import com.raphydaphy.rocksolid.api.fluid.Fluid;

import de.ellpeck.rockbottom.api.tile.state.TileProp;

public class FluidTypeProp extends TileProp<Fluid>
{

	private final Fluid def;

	public FluidTypeProp(String name, Fluid def)
	{
		super(name);
		this.def = def;
	}

	@Override
	public int getVariants()
	{
		return Fluid.values().length;
	}

	@Override
	public String toString()
	{
		return this.name;
	}

	@Override
	public Fluid getValue(int index)
	{
		return Fluid.values()[index];
	}

	@Override
	public int getIndex(Fluid value)
	{
		for (int fluid = 0; fluid < Fluid.values().length; fluid++)
		{
			Fluid curFluid = Fluid.values()[fluid];
			if (curFluid.equals(value))
			{
				return fluid;
			}
		}
		return 0;
	}

	@Override
	public Fluid getDefault()
	{
		return this.def;
	}
}
