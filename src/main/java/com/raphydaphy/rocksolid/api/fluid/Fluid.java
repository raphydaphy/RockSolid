package com.raphydaphy.rocksolid.api.fluid;

import com.raphydaphy.rocksolid.api.RockSolidAPI;
import com.raphydaphy.rocksolid.api.content.RockSolidContent;

import de.ellpeck.rockbottom.api.tile.state.TileState;

public enum Fluid
{
	WATER("Water", 0.015f, 1, 0x2a88cc), LAVA("Lava", 0.012f, 2, 0x9e1c1c), OIL("Oil", 0.010f, 3,
			0x0f0f0f), FUEL("Refined Fuel", 0.008f, 4, 0xf2f207), EMPTY("Fluid", 0f, 0, 0x8c8c88);

	private String name;
	private float thickness;
	private int bucketMeta;
	private int color;

	public static final String KEY = "fluidStored";
	public static final String MAX_KEY = "maxFluid";
	public static final String TYPE_KEY = "fluidType";

	Fluid(String name, float thickness, int bucketMeta, int color)
	{
		this.name = name;
		this.thickness = thickness;
		this.bucketMeta = bucketMeta;
		this.color = color;
		RockSolidAPI.FLUID_REGISTRY.put(name, this);
	}

	public TileState getTile()
	{
		return RockSolidContent.FLUID.getDefState().prop(FluidTile.fluidType, this);
	}

	public int getColor()
	{
		return this.color;
	}

	public int getBucketMeta()
	{
		return this.bucketMeta;
	}

	public String getName()
	{
		return this.name;
	}

	public float getThickness()
	{
		return this.thickness;
	}

	public static Fluid getByName(String nameIn)
	{
		return RockSolidAPI.FLUID_REGISTRY.get(nameIn);
	}

}