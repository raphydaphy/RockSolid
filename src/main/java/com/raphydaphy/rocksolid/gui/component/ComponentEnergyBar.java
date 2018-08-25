package com.raphydaphy.rocksolid.gui.component;

import com.raphydaphy.rocksolid.RockSolid;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.function.Supplier;

public class ComponentEnergyBar extends ComponentProgressBar
{
	private final Supplier<Integer> energy;
	private final Supplier<Integer> throughput;
	public ComponentEnergyBar(Gui gui, int x, int y, int sizeX, int sizeY, int progressColor, boolean isVertical, Supplier<Float> percent, Supplier<Integer> energy, Supplier<Integer> throughput)
	{
		super(gui, x, y, sizeX, sizeY, progressColor, isVertical, percent);
		this.energy = energy;
		this.throughput = throughput;
	}

	private static final ResourceName STORED = RockSolid.createRes("progress_overlay.energy_storage");
	private static final ResourceName THROUGHPUT = RockSolid.createRes("progress_overlay.energy_throughput");

	@Override
	public void renderOverlay(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y)
	{
		boolean mouseOverGasBarX = (g.getMouseInGuiX() >= this.gui.getX() + this.x) && (g.getMouseInGuiX() <= (this.x + this.gui.getX() + this.width));
		boolean mouseOverGasBarY = (g.getMouseInGuiY() >= this.gui.getY() + this.y) && (g.getMouseInGuiY() <= (this.gui.getY() + this.y + this.height));

		if (mouseOverGasBarX && mouseOverGasBarY)
		{
			g.drawHoverInfoAtMouse(game, manager, false, 500,
					RockBottomAPI.getGame().getAssetManager().localize(STORED, energy.get()),
					RockBottomAPI.getGame().getAssetManager().localize(THROUGHPUT, throughput.get()));
		}
	}
}
