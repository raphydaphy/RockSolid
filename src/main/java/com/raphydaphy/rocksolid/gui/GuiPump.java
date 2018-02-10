package com.raphydaphy.rocksolid.gui;

import java.awt.Color;

import com.google.common.base.Supplier;
import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tileentity.TileEntityPump;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class GuiPump extends GuiContainer
{
	private final TileEntityPump te;

	public GuiPump(AbstractEntityPlayer player, TileEntityPump te)
	{
		super(player, 198, 120);
		this.te = te;
	}

	@Override
	public void init(IGameInstance game)
	{
		super.init(game);

		this.components.add(
				new ComponentProgressBar(this, 60, 25, 80, 10, Color.magenta.getRGB(), false, new Supplier<Float>()
				{

					@Override
					public Float get()
					{
						return Math.min((float) GuiPump.this.te.getLiquidVolume() / 1000f, 1);
					}
				}));

		this.components.add(
				new ComponentProgressBar(this, 60, 10, 80, 10, Color.blue.getRGB(), false, new Supplier<Float>()
				{

					@Override
					public Float get()
					{
						return Math.min((float) GuiPump.this.te.getLiquidFullness(), 1);
					}
				}));
	}

	@Override
	public IResourceName getName()
	{
		return RockSolid.createRes("gui_pump");
	}

}
