package com.raphydaphy.rocksolid.gui;

import java.awt.Color;

import com.google.common.base.Supplier;
import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tileentity.TileEntityBoiler;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class GuiBoiler extends GuiContainer
{
	private final TileEntityBoiler te;

	public GuiBoiler(AbstractEntityPlayer player, TileEntityBoiler te)
	{
		super(player, 198, 150);
		this.te = te;
	}

	@Override
	public void init(IGameInstance game)
	{
		super.init(game);
		
		this.components.add(new ComponentProgressBar(this, this.x, this.y + 15, 80, 10, Color.gray.getRGB(), false, new Supplier<Float>() {

			@Override
			public Float get()
			{
				return Math.min((float)GuiBoiler.this.te.getSteam() / 7f, 1);
			}}));
	}

	@Override
	public IResourceName getName()
	{
		return RockSolid.createRes("gui_boiler");
	}

}
