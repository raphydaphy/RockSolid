package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class GuiChest
  extends GuiContainer
{
  public GuiChest(AbstractEntityPlayer player)
  {
    super(player, 198, 150);
  }
  
  @Override
	public IResourceName getName() 
	{
		return RockSolidLib.makeRes("guiChest");
	}
}
