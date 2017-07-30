package com.raphydaphy.rocksolid.api.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.api.energy.TileEntityPowered;
import com.raphydaphy.rocksolid.util.RockSolidLib;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
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
	public void initGui(final IGameInstance game)
	{
		super.initGui(game);
		this.components.add(new ComponentProgressBar(this, this.guiLeft + this.progressBarOffset.getX(),
				this.guiTop + this.progressBarOffset.getY(), 40, 8, GuiBasicPowered.PROGRESS_COLOR, false,
				this.tile::getSmeltPercentage));
		this.components.add(new ComponentProgressBar(this, this.guiLeft + 60, this.guiTop + 38, 80, 10,
				new Color(148, 0, 211), false, this.tile::getEnergyFullness));
	}

	@Override
	public void renderOverlay(IGameInstance game, IAssetManager manager, Graphics g)
	{
		super.renderOverlay(game, manager, g);
		boolean mouseOverBarX = (game.getMouseInGuiX() >= this.guiLeft + 60)
				&& (game.getMouseInGuiX() <= (this.guiLeft + 60 + 80));
		boolean mouseOverBarY = (game.getMouseInGuiY() >= this.guiTop + 38)
				&& (game.getMouseInGuiY() <= (this.guiTop + 38 + 10));

		if (mouseOverBarX && mouseOverBarY)
		{
			RockBottomAPI.getApiHandler().drawHoverInfoAtMouse(game, manager, g, false, 500,
					new String[] { "Storing " + this.tile.getCurrentEnergy() + "kWh of Energy",
							"Uses " + tile.getPowerPerOperation() + "kWh per tick" });
		}
	}

	@Override
	public IResourceName getName()
	{
		return RockSolidLib.makeRes("guiBasicPowered");
	}

}
