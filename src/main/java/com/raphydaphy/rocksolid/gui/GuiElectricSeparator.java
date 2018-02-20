package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tileentity.TileEntityElectric;
import com.raphydaphy.rocksolid.tileentity.TileEntityElectricSeparator;
import com.raphydaphy.rocksolid.tileentity.TileEntitySeparator;
import com.raphydaphy.rocksolid.util.ModUtils;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class GuiElectricSeparator extends GuiContainer
{
	private final TileEntityElectricSeparator te;

	public GuiElectricSeparator(AbstractEntityPlayer player, TileEntityElectricSeparator te)
	{
		super(player, 136, 120);
		this.te = te;
	}

	@Override
	public void init(IGameInstance game)
	{
		super.init(game);

		this.components.add(new ComponentProgressBar(this, 40, 4, 37, 8, ModUtils.PROGRESS, false, GuiElectricSeparator.this.te::getSmeltPercent));

		this.components.add(new ComponentProgressBar(this, 26, 20, 80, 10, ModUtils.ENERGY, false, () -> Math.min(GuiElectricSeparator.this.te.getEnergyFullness(), 1)));
	}

	@Override
	public IResourceName getName()
	{
		return RockSolid.createRes("gui_electric_-separator");
	}

}