package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.tileentity.TileEntityNuclearReactor;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IGraphics;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class GuiNuclearReactor extends GuiContainer
{
	public static final int ELECTRICITY_COLOR = 0x9400d3;
	public static final int PROGRESS_COLOR = 0x1a801a;
	public static final int FIRE_COLOR = 0x801a1a;
	private final TileEntityNuclearReactor tile;

	public GuiNuclearReactor(final AbstractEntityPlayer player, final TileEntityNuclearReactor tile)
	{
		super(player, 198, 150);
		this.tile = tile;
	}

	@Override
	public void init(final IGameInstance game)
	{
		super.init(game);
		this.components.add(new ComponentProgressBar(this, this.x + 60, this.y + 10, 80, 10, ELECTRICITY_COLOR, false,
				this.tile::getGeneratorFullness));
		this.components.add(new ComponentProgressBar(this, this.x + 74, this.y + 30, 8, 18,
				GuiNuclearReactor.FIRE_COLOR, true, this.tile::getFuelPercentage));
	}

	@Override
	public void renderOverlay(IGameInstance game, IAssetManager manager, IGraphics g)
	{
		super.renderOverlay(game, manager, g);
		boolean mouseOverPowerBarX = (g.getMouseInGuiX() >= this.x + 60) && (g.getMouseInGuiX() <= (this.x + 60 + 80));
		boolean mouseOverPowerBarY = (g.getMouseInGuiY() >= this.y + 10) && (g.getMouseInGuiY() <= (this.y + 10 + 10));

		if (mouseOverPowerBarX && mouseOverPowerBarY)
		{
			g.drawHoverInfoAtMouse(game, manager, false, 500, new String[] {
					"Storing " + this.tile.getCurrentEnergy() + "kWh of Energy", "Produces 150kWh per tick" });
		}
	}

	@Override
	public IResourceName getName()
	{
		return RockSolidAPILib.makeInternalRes("guiNuclearReactor");
	}

}
