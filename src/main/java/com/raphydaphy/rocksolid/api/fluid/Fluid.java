package com.raphydaphy.rocksolid.api.fluid;

import org.newdawn.slick.Color;

import com.raphydaphy.rocksolid.api.RockSolidAPI;
import com.raphydaphy.rocksolid.api.content.RockSolidContent;

import de.ellpeck.rockbottom.api.tile.state.TileState;

public enum Fluid {
	WATER("Water", 0.015f, 1, Color.blue),
	LAVA("Lava", 0.012f, 2, Color.orange),
	OIL("Oil", 0.010f, 3, Color.black),
	FUEL("Refined Fuel", 0.008f, 4, Color.yellow),
	EMPTY("Fluid", 0f, 0, Color.lightGray);
	
	private String name;
	private float thickness;
	private int bucketMeta;
	private Color color;
	
	public static final String KEY = "fluidStored";
	public static final String MAX_KEY = "maxFluid";
	public static final String TYPE_KEY = "fluidType";
	
	

	Fluid(String name, float thickness, int bucketMeta, Color color) {
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
	
	public Color getColor()
	{
		return this.color;
	}
	
	public int getBucketMeta()
	{
		return this.bucketMeta;
	}

	public String getName() {
		return this.name;
	}
	
	public float getThickness() {
		return this.thickness;
	}

	public static Fluid getByName(String nameIn) {
		return RockSolidAPI.FLUID_REGISTRY.get(nameIn);
	}

}