package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tileentity.TileEntityTurbine;
import com.raphydaphy.rocksolid.util.GuiColors;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.awt.*;

public class GuiTurbine extends GuiContainer
{
	private final TileEntityTurbine te;

	public GuiTurbine(AbstractEntityPlayer player, TileEntityTurbine te)
	{
		super(player, 136, 112);
		this.te = te;
	}

	@Override
	public void init(IGameInstance game)
	{
		super.init(game);

		this.components.add(new ComponentProgressBar(this, 30, 16, 80, 10, GuiColors.ENERGY, false, () -> Math.min(GuiTurbine.this.te.getEnergyFullness(), 1)));

		this.components.add(new ComponentProgressBar(this, 30, 1, 80, 10, Color.gray.getRGB(), false, () -> Math.min(GuiTurbine.this.te.getSteamFullness(), 1)));
	}

	@Override
	public IResourceName getName()
	{
		return RockSolid.createRes("gui_turbine");
	}

}
