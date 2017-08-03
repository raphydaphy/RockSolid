package com.raphydaphy.rocksolid.init;

import com.raphydaphy.rocksolid.api.gas.Gas;
import com.raphydaphy.rocksolid.gas.GasHydrogen;
import com.raphydaphy.rocksolid.gas.GasOxygen;
import com.raphydaphy.rocksolid.gas.GasSteam;
import com.raphydaphy.rocksolid.gas.GasVacuum;

public class ModGasses
{
	public static Gas gasVacuum;
	public static Gas gasOxygen;
	public static Gas gasHydrogen;
	public static Gas gasSteam;

	public static void init()
	{
		gasVacuum = new GasVacuum();
		gasOxygen = new GasOxygen();
		gasHydrogen = new GasHydrogen();
		gasSteam = new GasSteam();
	}
}
