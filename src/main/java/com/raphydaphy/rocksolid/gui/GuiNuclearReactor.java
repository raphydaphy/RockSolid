package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.gui.component.ComponentCustomText;
import com.raphydaphy.rocksolid.gui.component.ComponentEnergyBar;
import com.raphydaphy.rocksolid.gui.component.ComponentHeatBar;
import com.raphydaphy.rocksolid.tileentity.TileEntityNuclearReactor;
import com.raphydaphy.rocksolid.util.ModUtils;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.awt.*;

public class GuiNuclearReactor extends GuiContainer
{
	private final TileEntityNuclearReactor te;
	private int lastTempshiftPlates;
	private ComponentCustomText tempshiftPlateText;
	private final ResourceName TEMPSHIFT_PLATES = RockSolid.createRes("reactor_tempshift_plates");

	public GuiNuclearReactor(AbstractEntityPlayer player, TileEntityNuclearReactor te)
	{
		super(player, 136, 135);
		this.te = te;
	}

	@Override
	public void init(IGameInstance game)
	{
		super.init(game);

		lastTempshiftPlates = te.getTempshiftPlates();
		tempshiftPlateText = new ComponentCustomText(this, 24 + 43, 5, 80, 1, 0.3f, ComponentCustomText.TextDirection.CENTER,
				game.getAssetManager().localize(TEMPSHIFT_PLATES, lastTempshiftPlates));
		this.components.add(tempshiftPlateText);

		this.components.add(new ComponentEnergyBar(this, 27, 50, 81, 10, ModUtils.ENERGY, false, this.te::getEnergyFullness, this.te::getEnergyStored, this.te::getMaxTransfer));

		this.components.add(new ComponentHeatBar(this, 27, 35, 81, 10, Color.red.getRGB(), false, this.te::getHeatFullness, this.te::getHeat));
	}

	@Override
	public ResourceName getName()
	{
		return RockSolid.createRes("gui_nuclear_reactor");
	}

	@Override
	public void update(IGameInstance game)
	{
		if (te.getTempshiftPlates() != lastTempshiftPlates)
		{
			lastTempshiftPlates = te.getTempshiftPlates();
			tempshiftPlateText.setText(game.getAssetManager().localize(TEMPSHIFT_PLATES, lastTempshiftPlates));
		}
	}

}
