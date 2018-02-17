package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tileentity.TileEntitySmelter;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.awt.*;

public class GuiSmelter extends GuiContainer
{
	private final TileEntitySmelter te;

	public GuiSmelter(AbstractEntityPlayer player, TileEntitySmelter te)
	{
		super(player, 198, 140);
		this.te = te;
	}

	@Override
	public void init(IGameInstance game)
	{
		super.init(game);

		this.components.add(new ComponentProgressBar(this, 81, 24, 35, 8, Color.green.getRGB(), false, GuiSmelter.this.te::getSmeltPercent));

		this.components.add(new ComponentProgressBar(this, 80, 40, 8, 16, Color.ORANGE.getRGB(), true, GuiSmelter.this.te::getFuelPercentage));
	}

	@Override
	public IResourceName getName()
	{
		return RockSolid.createRes("gui_smelter");
	}

}
