package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tileentity.TileEntitySeparator;
import com.raphydaphy.rocksolid.util.GuiColors;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class GuiSeparator extends GuiContainer
{
	private final TileEntitySeparator te;

	public GuiSeparator(AbstractEntityPlayer player, TileEntitySeparator te)
	{
		super(player, 136, 120);
		this.te = te;
	}

	@Override
	public void init(IGameInstance game)
	{
		super.init(game);

		this.components.add(new ComponentProgressBar(this, 40, 4, 37, 8, GuiColors.PROGRESS, false, GuiSeparator.this.te::getSmeltPercent));

		this.components.add(new ComponentProgressBar(this, 39, 20, 8, 16, GuiColors.COAL, true, GuiSeparator.this.te::getFuelPercentage));
	}

	@Override
	public IResourceName getName()
	{
		return RockSolid.createRes("gui_separator");
	}

}