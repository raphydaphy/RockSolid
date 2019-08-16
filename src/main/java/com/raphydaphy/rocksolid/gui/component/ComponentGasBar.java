package com.raphydaphy.rocksolid.gui.component;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.gas.Gas;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.gui.Gui;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.function.Supplier;

public class ComponentGasBar extends ComponentProgressBar {
    private static final ResourceName STORED = RockSolid.createRes("progress_overlay.gas_storage");
    private static final ResourceName EMPTY = RockSolid.createRes("progress_overlay.empty");
    private final Supplier<Integer> gas;
    private final Supplier<Gas> type;
    public ComponentGasBar(Gui gui, int x, int y, int sizeX, int sizeY, int progressColor, boolean isVertical, Supplier<Float> percent, Supplier<Integer> gas, Supplier<Gas> type) {
        super(gui, x, y, sizeX, sizeY, progressColor, isVertical, percent);
        this.gas = gas;
        this.type = type;
    }

    @Override
    public void renderOverlay(IGameInstance game, IAssetManager manager, IRenderer g, int x, int y) {
        boolean mouseOverGasBarX = (g.getMouseInGuiX() >= this.gui.getX() + this.x) && (g.getMouseInGuiX() <= (this.x + this.gui.getX() + this.width));
        boolean mouseOverGasBarY = (g.getMouseInGuiY() >= this.gui.getY() + this.y) && (g.getMouseInGuiY() <= (this.gui.getY() + this.y + this.height));

        if (mouseOverGasBarX && mouseOverGasBarY) {
            Gas curGas = type.get();
            g.drawHoverInfoAtMouse(game, manager, false, 500,
                    curGas == null ? manager.localize(EMPTY) : (manager.localize(STORED, gas.get()) + " " + manager.localize(curGas.name)));
        }
    }
}
