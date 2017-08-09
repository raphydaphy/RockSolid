package com.raphydaphy.rocksolid.api.gui;

import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class GuiEmpty extends GuiContainer
{

	public GuiEmpty(final AbstractEntityPlayer player)
	{
		super(player, 198, 150);
	}

	@Override
	public IResourceName getName()
	{
		return RockSolidAPILib.makeInternalRes("guiEmpty");
	}

}
