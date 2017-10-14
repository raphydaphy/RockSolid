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
	public static final int ELECTRICITY_COLOR = 0x9400d3;
	public static final int PROGRESS_COLOR = 0x1a801a;
	public static final int FIRE_COLOR = 0x801a1a;
	private final TileEntityAlloySmelter tile;

	public GuiAlloySmelter(final AbstractEntityPlayer player, final TileEntityAlloySmelter tile)
	{
		super(player, 198, 150);
		this.tile = tile;
	}

	@Override
	public void init(final IGameInstance game)
	{
		super.init(game);
		this.components.add(new ComponentProgressBar(this, this.x + 80, this.y + 15, 40, 8,
				GuiAlloySmelter.PROGRESS_COLOR, false, this.tile::getSmeltPercentage));
		this.components.add(new ComponentProgressBar(this, this.x + 74, this.y + 30, 8, 18, GuiAlloySmelter.FIRE_COLOR,
				true, this.tile::getFuelPercentage));
	}

	@Override
	public IResourceName getName()
	{
		return RockSolidAPILib.makeInternalRes("guiAlloySmelter");
	}

}
