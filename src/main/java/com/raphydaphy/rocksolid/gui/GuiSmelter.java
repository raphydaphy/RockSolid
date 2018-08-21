package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tileentity.TileEntityGranulator;
import com.raphydaphy.rocksolid.util.ModUtils;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class GuiSmelter extends GuiContainer
{
	private final TileEntityGranulator te;

	public GuiSmelter(AbstractEntityPlayer player, TileEntityGranulator te)
	{
		super(player, 136, 120);
		this.te = te;
	}

	@Override
	public void init(IGameInstance game)
	{
		super.init(game);

		this.components.add(new ComponentProgressBar(this, 49, 4, 35, 8, ModUtils.PROGRESS, false, GuiSmelter.this.te::getSmeltPercent));

		this.components.add(new ComponentProgressBar(this, 48, 20, 8, 16, ModUtils.COAL, true, GuiSmelter.this.te::getFuelPercentage));
	}

	@Override
	public ResourceName getName()
	{
		return RockSolid.createRes("gui_smelter");
	}

}