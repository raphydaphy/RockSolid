package com.raphydaphy.rocksolid.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.raphydaphy.rocksolid.api.util.RockSolidAPILib;
import com.raphydaphy.rocksolid.tileentity.TileEntityNuclearReactor;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class GuiNuclearReactor extends GuiContainer
{

	private final TileEntityNuclearReactor tile;

	public GuiNuclearReactor(final AbstractEntityPlayer player, final TileEntityNuclearReactor tile)
	{
		super(player, 198, 150);
		this.tile = tile;
	}

	@Override
	public void initGui(final IGameInstance game)
	{
		super.initGui(game);
		this.components.add(new ComponentProgressBar(this, this.guiLeft + 60, this.guiTop + 10, 80, 10,
				new Color(148, 0, 211), false, this.tile::getGeneratorFullness));
		this.components.add(new ComponentProgressBar(this, this.guiLeft + 74, this.guiTop + 30, 8, 18,
				GuiNuclearReactor.FIRE_COLOR, true, this.tile::getFuelPercentage));
	}

	@Override
	public void renderOverlay(IGameInstance game, IAssetManager manager, Graphics g)
	{
		super.renderOverlay(game, manager, g);
		boolean mouseOverPowerBarX = (game.getMouseInGuiX() >= this.guiLeft + 60)
				&& (game.getMouseInGuiX() <= (this.guiLeft + 60 + 80));
		boolean mouseOverPowerBarY = (game.getMouseInGuiY() >= this.guiTop + 10)
				&& (game.getMouseInGuiY() <= (this.guiTop + 10 + 10));

		if (mouseOverPowerBarX && mouseOverPowerBarY)
		{
			RockBottomAPI.getApiHandler().drawHoverInfoAtMouse(game, manager, g, false, 500, new String[] {
					"Storing " + this.tile.getCurrentEnergy() + "kWh of Energy", "Produces 150kWh per tick" });
		}
	}

	@Override
	public IResourceName getName()
	{
		return RockSolidAPILib.makeInternalRes("guiNuclearReactor");
	}

}
