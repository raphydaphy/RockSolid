package com.raphydaphy.rocksolid.api.gui;

import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.api.util.TileEntityProgressBar;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class GuiBasic extends GuiContainer
{
	private final TileEntityProgressBar tile;
	private final Pos2 progressBarOffset;

	public GuiBasic(final AbstractEntityPlayer player, final TileEntityProgressBar tile)
	{
		this(player, tile, new Pos2(80, 15));
	}

	public GuiBasic(final AbstractEntityPlayer player, final TileEntityProgressBar tile, final Pos2 progressBarOffset)
	{
		super(player, 198, 150);
		this.progressBarOffset = progressBarOffset;
		this.tile = tile;
	}

	@Override
	public void init(final IGameInstance game)
	{
		super.init(game);
		this.components.add(new ComponentProgressBar(this, this.x + this.progressBarOffset.getX(),
				this.y + this.progressBarOffset.getY(), 40, 8, Gui.GRADIENT_COLOR, false,
				this.tile::getSmeltPercentage));
	}

	@Override
	public IResourceName getName()
	{
		return RockSolidAPILib.makeInternalRes("guiBasic");
	}

}
