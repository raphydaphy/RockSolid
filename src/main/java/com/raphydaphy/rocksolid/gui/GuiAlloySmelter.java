package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.tileentity.TileEntityAlloySmelter;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class GuiAlloySmelter extends GuiContainer
{

	private final TileEntityAlloySmelter tile;

	public GuiAlloySmelter(final AbstractEntityPlayer player, final TileEntityAlloySmelter tile)
	{
		super(player, 198, 150);
		this.tile = tile;
	}

	@Override
	public void initGui(final IGameInstance game)
	{
		super.initGui(game);
		this.components.add(new ComponentProgressBar(this, this.guiLeft + 80, this.guiTop + 15, 40, 8,
				GuiAlloySmelter.PROGRESS_COLOR, false, this.tile::getSmeltPercentage));
		this.components.add(new ComponentProgressBar(this, this.guiLeft + 74, this.guiTop + 30, 8, 18,
				GuiAlloySmelter.FIRE_COLOR, true, this.tile::getFuelPercentage));
	}

	@Override
	public IResourceName getName()
	{
		return RockSolidAPILib.makeInternalRes("guiAlloySmelter");
	}

}
