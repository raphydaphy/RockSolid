package com.raphydaphy.rocksolid.api.gui;

import com.raphydaphy.rocksolid.api.energy.TileEntityPowered;
import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class GuiBasicPowered extends GuiContainer
{
	private final TileEntityPowered tile;
	private final Pos2 progressBarOffset;

	public GuiBasicPowered(final AbstractEntityPlayer player, final TileEntityPowered tile)
	{
		this(player, tile, new Pos2(80, 15));
	}

	public GuiBasicPowered(final AbstractEntityPlayer player, final TileEntityPowered tile,
			final Pos2 progressBarOffset)
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
				this.y + this.progressBarOffset.getY(), 40, 8, GRADIENT_COLOR, false, this.tile::getSmeltPercentage));
		this.components.add(new ComponentProgressBar(this, this.x + 60, this.y + 38, 80, 10, 0x9400d3, false,
				this.tile::getEnergyFullness));
	}

	@Override
	public void renderOverlay(IGameInstance game, IAssetManager manager, IGraphics g)
	{
		super.renderOverlay(game, manager, g);
		boolean mouseOverBarX = (g.getMouseInGuiX() >= this.x + 60) && (g.getMouseInGuiY() <= (this.x + 60 + 80));
		boolean mouseOverBarY = (g.getMouseInGuiY() >= this.y + 38) && (g.getMouseInGuiY() <= (this.y + 38 + 10));

		if (mouseOverBarX && mouseOverBarY)
		{
			g.drawHoverInfoAtMouse(game, manager, false, 500,
					new String[] { "Storing " + this.tile.getCurrentEnergy() + "kWh of Energy",
							"Uses " + tile.getPowerPerOperation() + "kWh per tick" });
		}
	}

	@Override
	public IResourceName getName()
	{
		return RockSolidAPILib.makeInternalRes("guiBasicPowered");
	}

}
