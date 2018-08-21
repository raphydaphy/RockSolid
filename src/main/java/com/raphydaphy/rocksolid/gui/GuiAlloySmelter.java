package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tileentity.TileEntityAlloySmelter;
import com.raphydaphy.rocksolid.util.ModUtils;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class GuiAlloySmelter extends GuiContainer
{
	private final TileEntityAlloySmelter te;

	public GuiAlloySmelter(AbstractEntityPlayer player, TileEntityAlloySmelter te)
	{
		super(player, 136, 120);
		this.te = te;
	}

	@Override
	public void init(IGameInstance game)
	{
		super.init(game);

		this.components.add(new ComponentProgressBar(this, 57, 4, 37, 8, ModUtils.PROGRESS, false, GuiAlloySmelter.this.te::getSmeltPercent));

		this.components.add(new ComponentProgressBar(this, 55, 20, 8, 16, ModUtils.COAL, true, GuiAlloySmelter.this.te::getFuelPercentage));
	}

	@Override
	public ResourceName getName()
	{
		return RockSolid.createRes("gui_alloy_smelter");
	}

}