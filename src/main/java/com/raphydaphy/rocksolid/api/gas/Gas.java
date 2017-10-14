package com.raphydaphy.rocksolid.api.gas;

import com.raphydaphy.rocksolid.api.RockSolidAPI;
import com.raphydaphy.rocksolid.api.content.RockSolidContent;

import de.ellpeck.rockbottom.api.tile.state.TileState;

public enum Gas
{
	HYDROGEN("Hydrogen", 0.01f, 1, 0xc893d8), OXYGEN("Oxygen", 0.015f, 2, 0xe0ffff), STEAM("Steam", 0.03f, 3,
			0xa5a5a5), VACCUM("Gas", 0.0f, 0, 0xc78835);

	private String name;
	private float weight;
	private int canisterMeta;
	private int color;

	public static final String KEY = "gasStored";
	public static final String MAX_KEY = "maxGas";
	public static final String TYPE_KEY = "gasType";

	Gas(String name, float weight, int canisterMeta, int color)
	{
		this.name = name;
		this.weight = weight;
		this.canisterMeta = canisterMeta;
		this.color = color;
		RockSolidAPI.GAS_REGISTRY.put(name, this);
	}

	public TileState getTile()
	{
		return RockSolidContent.GAS.getDefState().prop(GasTile.gasType, this);
	}

	public int getColor()
	{
		return this.color;
	}

	public int getCanisterMeta()
	{
		return this.canisterMeta;
	}

	public String getName()
	{
		return this.name;
	}

	public float getWeight()
	{
		return this.weight;
	}

	public static Gas getByName(String nameIn)
	{
		if (RockSolidAPI.GAS_REGISTRY.containsKey(nameIn))
		{
			return RockSolidAPI.GAS_REGISTRY.get(nameIn);
		} else
		{
			System.out.println("Tried to access unknown Gas " + nameIn);
			return Gas.VACCUM;
		}
	}

}