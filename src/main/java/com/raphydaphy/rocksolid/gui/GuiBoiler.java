package com.raphydaphy.rocksolid.gui;

import com.google.common.base.Supplier;
import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tileentity.TileEntityBoiler;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.awt.*;

public class GuiBoiler extends GuiContainer
{
	private final TileEntityBoiler te;

	public GuiBoiler(AbstractEntityPlayer player, TileEntityBoiler te)
	{
		super(player, 198, 140);
		this.te = te;
	}

	@Override
	public void init(IGameInstance game)
	{
		super.init(game);

		this.components.add(new ComponentProgressBar(this, 60, 45, 80, 10, Color.gray.getRGB(), false, (Supplier<Float>) () -> Math.min( GuiBoiler.this.te.getSteamFullness(), 1)));

		this.components.add(new ComponentProgressBar(this, 60, 10, 80, 10, Color.blue.getRGB(), false, () -> Math.min((float) GuiBoiler.this.te.getWaterFullness(), 1)));

		this.components.add(new ComponentProgressBar(this, 107, 25, 8, 17, Color.ORANGE.getRGB(), true, (Supplier<Float>) () -> GuiBoiler.this.te.getFuelPercentage()));
	}

	@Override
	public IResourceName getName()
	{
		return RockSolid.createRes("gui_boiler");
	}

}
