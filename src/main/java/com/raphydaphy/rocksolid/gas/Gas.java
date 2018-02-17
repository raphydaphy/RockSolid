package com.raphydaphy.rocksolid.gas;

import java.awt.*;

public enum Gas
{
	STEAM(165, 165, 165), HYDROGEN(200, 147, 216), OXYGEN(224, 255, 255);

	public final int color;

	Gas(int r, int g, int b)
	{
		this.color = new Color(r, g, b).getRGB();
	}
}
