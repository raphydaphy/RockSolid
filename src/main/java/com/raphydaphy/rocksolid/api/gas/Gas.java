package com.raphydaphy.rocksolid.api.gas;

import org.newdawn.slick.Color;

import com.raphydaphy.rocksolid.api.RockSolidAPI;
import com.raphydaphy.rocksolid.api.content.RockSolidContent;

import de.ellpeck.rockbottom.api.tile.state.TileState;

public enum Gas
{
	HYDROGEN("Hydrogen", 0.01f, 1, new Color(200, 147, 216)), OXYGEN("Oxygen", 0.015f, 2,
			new Color(224, 255, 255)), STEAM("Steam", 0.03f, 3,
					new Color(165, 165, 165)), VACCUM("Gas", 0.0f, 0, new Color(199, 136, 53));

	private String name;
	private float weight;
	private int canisterMeta;
	private Color color;

	public static final String KEY = "gasStored";
	public static final String MAX_KEY = "maxGas";
	public static final String TYPE_KEY = "gasType";

	Gas(String name, float weight, int canisterMeta, Color color)
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

	public Color getColor()
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
		}
		else
		{
			System.out.println("Tried to access unknown Gas " + nameIn);
			return Gas.VACCUM;
		}
	}

}