package com.raphydaphy.rocksolid.util;

import de.ellpeck.rockbottom.api.item.ToolProperty;

public class ToolInfo
{
	private final ToolProperty type;
	private final int level;

	public ToolInfo(ToolProperty type, int level)
	{
		this.type = type;
		this.level = level;
	}

	public ToolProperty getType()
	{
		return this.type;
	}

	public int getLevel()
	{
		return this.level;
	}
}
