package com.raphydaphy.rocksolid.gui;

import com.google.common.base.Supplier;
import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tileentity.TileEntityArcFurnace;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

import java.awt.*;

public class GuiArcFurnace extends GuiContainer
{
	private final TileEntityArcFurnace te;

	public GuiArcFurnace(AbstractEntityPlayer player, TileEntityArcFurnace te)
	{
		super(player, 198, 140);
		this.te = te;
	}

	@Override
	public void init(IGameInstance game)
	{
		super.init(game);

		this.components.add(new ComponentProgressBar(this, 60, 28, 80, 10, Color.ORANGE.getRGB(), false, (Supplier<Float>) () -> GuiArcFurnace.this.te.getBlastPercentage()));
	}

	@Override
	public IResourceName getName()
	{
		return RockSolid.createRes("gui_arc_furnace");
	}

}
