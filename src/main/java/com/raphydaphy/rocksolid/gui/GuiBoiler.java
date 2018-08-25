package com.raphydaphy.rocksolid.gui;

import com.google.common.base.Supplier;
import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.gas.Gas;
import com.raphydaphy.rocksolid.gui.component.ComponentGasBar;
import com.raphydaphy.rocksolid.gui.component.ComponentLiquidBar;
import com.raphydaphy.rocksolid.tileentity.TileEntityBoiler;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.awt.*;

public class GuiBoiler extends GuiContainer
{
	private final TileEntityBoiler te;

	public GuiBoiler(AbstractEntityPlayer player, TileEntityBoiler te)
	{
		super(player, 134, 132);
		this.te = te;

		int playerSlots = player.getInv().getSlotAmount();

		ShiftClickBehavior input = new ShiftClickBehavior(0, playerSlots - 1, playerSlots, playerSlots);
		shiftClickBehaviors.add(input);
		shiftClickBehaviors.add(input.reversed());
	}

	@Override
	public void init(IGameInstance game)
	{
		super.init(game);

		this.components.add(new ComponentGasBar(this, 27, 36, 81, 10, Color.gray.getRGB(), false, this.te::getSteamFullness, this.te::getSteamVolume, () -> Gas.STEAM));

		this.components.add(new ComponentLiquidBar(this, 27, 1, 81, 10, Color.blue.getRGB(), false, this.te::getWaterFullness, this.te::getWaterVolume, () -> GameContent.TILE_WATER));

		this.components.add(new ComponentProgressBar(this, 75, 16, 8, 16, Color.ORANGE.getRGB(), true, this.te::getFuelPercentage));
	}

	@Override
	public ResourceName getName()
	{
		return RockSolid.createRes("gui_boiler");
	}

}
