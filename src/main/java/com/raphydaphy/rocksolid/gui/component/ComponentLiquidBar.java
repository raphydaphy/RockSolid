package com.raphydaphy.rocksolid.gui.component;

import com.raphydaphy.rocksolid.RockSolid;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.tile.TileLiquid;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.function.Supplier;

public class ComponentLiquidBar extends ComponentProgressBar
{
	private final Supplier<Integer> liquid;
	private final Supplier<TileLiquid> type;

	public ComponentLiquidBar(Gui gui, int x, int y, int sizeX, int sizeY, int progressColor, boolean isVertical, Supplier<Float> percent, Supplier<Integer> liquid, Supplier<TileLiquid> type)
	{
		super(gui, x, y, sizeX, sizeY, progressColor, isVertical, percent);
		this.liquid = liquid;
		this.type = type;
	}

	private static final ResourceName STORED = RockSolid.createRes("progress_overlay.liquid_storage");
	private static final ResourceName EMPTY = RockSolid.createRes("progress_overlay.empty");

	@Override
	public void renderOverlay(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y)
	{
		boolean mouseOverGasBarX = (g.getMouseInGuiX() >= this.gui.getX() + this.x) && (g.getMouseInGuiX() <= (this.x + this.gui.getX() + this.width));
		boolean mouseOverGasBarY = (g.getMouseInGuiY() >= this.gui.getY() + this.y) && (g.getMouseInGuiY() <= (this.gui.getY() + this.y + this.height));

		if (mouseOverGasBarX && mouseOverGasBarY)
		{
			TileLiquid curLiquid = type.get();
			g.drawHoverInfoAtMouse(game, manager, false, 500,
					curLiquid == null ? manager.localize(EMPTY) : (manager.localize(STORED, liquid.get()) + " " + manager.localize(curLiquid.getName())));
		}
	}
}
