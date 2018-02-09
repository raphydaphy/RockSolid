package com.raphydaphy.rocksolid.util;

import de.ellpeck.rockbottom.api.item.ToolType;

public class ToolInfo
{
	private final ToolType type;
	private final int level;

	public ToolInfo(ToolType type, int level)
	{
		this.type = type;
		this.level = level;
	}

	public ToolType getType()
	{
		return this.type;
	}

	public int getLevel()
	{
		return this.level;
	}
}
