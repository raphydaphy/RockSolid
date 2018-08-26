package com.raphydaphy.rocksolid.gui.component;

import com.raphydaphy.rocksolid.RockSolid;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.function.Supplier;

public class ComponentHeatBar extends ComponentProgressBar
{
	private final Supplier<Integer> heat;

	public ComponentHeatBar(Gui gui, int x, int y, int sizeX, int sizeY, int progressColor, boolean isVertical, Supplier<Float> percent, Supplier<Integer> heat)
	{
		super(gui, x, y, sizeX, sizeY, progressColor, isVertical, percent);
		this.heat = heat;
	}

	private static final ResourceName STORED = RockSolid.createRes("progress_overlay.heat_storage");

	@Override
	public void renderOverlay(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y)
	{
		boolean mouseOverGasBarX = (g.getMouseInGuiX() >= this.gui.getX() + this.x) && (g.getMouseInGuiX() <= (this.x + this.gui.getX() + this.width));
		boolean mouseOverGasBarY = (g.getMouseInGuiY() >= this.gui.getY() + this.y) && (g.getMouseInGuiY() <= (this.gui.getY() + this.y + this.height));

		if (mouseOverGasBarX && mouseOverGasBarY)
		{
			g.drawHoverInfoAtMouse(game, manager, false, 500, manager.localize(STORED, heat.get()));
		}
	}
}
