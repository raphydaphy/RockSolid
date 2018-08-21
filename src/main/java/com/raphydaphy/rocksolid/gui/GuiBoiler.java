package com.raphydaphy.rocksolid.gui;

import com.google.common.base.Supplier;
import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tileentity.TileEntityBoiler;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.awt.*;

public class GuiBoiler extends GuiContainer
{
	private final TileEntityBoiler te;

	public GuiBoiler(AbstractEntityPlayer player, TileEntityBoiler te)
	{
		super(player, 134, 132);
		this.te = te;
	}

	@Override
	public void init(IGameInstance game)
	{
		super.init(game);

		this.components.add(new ComponentProgressBar(this, 28, 36, 80, 10, Color.gray.getRGB(), false, (Supplier<Float>) GuiBoiler.this.te::getSteamFullness));

		this.components.add(new ComponentProgressBar(this, 28, 1, 80, 10, Color.blue.getRGB(), false, GuiBoiler.this.te::getWaterFullness));

		this.components.add(new ComponentProgressBar(this, 75, 16, 8, 16, Color.ORANGE.getRGB(), true, (Supplier<Float>) GuiBoiler.this.te::getFuelPercentage));
	}

	@Override
	public ResourceName getName()
	{
		return RockSolid.createRes("gui_boiler");
	}

}
