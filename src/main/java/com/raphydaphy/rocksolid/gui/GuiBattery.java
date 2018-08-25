package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.gui.component.ComponentEnergyBar;
import com.raphydaphy.rocksolid.tileentity.TileEntityBattery;
import com.raphydaphy.rocksolid.util.ModUtils;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class GuiBattery extends GuiContainer
{
	private final TileEntityBattery te;

	public GuiBattery(AbstractEntityPlayer player, TileEntityBattery te)
	{
		super(player, 136, 101);
		this.te = te;
	}

	@Override
	public void init(IGameInstance game)
	{
		super.init(game);

		this.components.add(new ComponentEnergyBar(this, 27, 0, 81, 10, ModUtils.ENERGY, false, GuiBattery.this.te::getEnergyFullness, GuiBattery.this.te::getEnergyStored, GuiBattery.this.te::getMaxTransfer));
	}

	@Override
	public ResourceName getName()
	{
		return RockSolid.createRes("gui_battery");
	}

}
