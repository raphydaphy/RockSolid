package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tileentity.TileEntityTurbine;
import com.raphydaphy.rocksolid.util.ModUtils;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

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

		this.components.add(new ComponentProgressBar(this, 27, 16, 81, 10, ModUtils.ENERGY, false, () -> Math.min(GuiTurbine.this.te.getEnergyFullness(), 1)));

		this.components.add(new ComponentProgressBar(this, 27, 1, 81, 10, Color.gray.getRGB(), false, () -> Math.min(GuiTurbine.this.te.getSteamFullness(), 1)));
	}

	@Override
	public ResourceName getName()
	{
		return RockSolid.createRes("gui_turbine");
	}

}
