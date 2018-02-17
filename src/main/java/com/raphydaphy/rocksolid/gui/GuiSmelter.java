package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tileentity.TileEntitySmelter;
import com.raphydaphy.rocksolid.util.GuiColors;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

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

		this.components.add(new ComponentProgressBar(this, 81, 14, 35, 8, GuiColors.PROGRESS, false, GuiSmelter.this.te::getSmeltPercent));

		this.components.add(new ComponentProgressBar(this, 80, 30, 8, 16, GuiColors.COAL, true, GuiSmelter.this.te::getFuelPercentage));
	}

	@Override
	public IResourceName getName()
	{
		return RockSolid.createRes("gui_smelter");
	}

}
