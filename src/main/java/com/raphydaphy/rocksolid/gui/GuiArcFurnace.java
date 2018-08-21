package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tileentity.TileEntityArcFurnace;
import com.raphydaphy.rocksolid.util.ModUtils;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentProgressBar;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class GuiArcFurnace extends GuiContainer
{
	private final TileEntityArcFurnace te;

	public GuiArcFurnace(AbstractEntityPlayer player, TileEntityArcFurnace te)
	{
		super(player, 136, 111);
		this.te = te;

		int playerSlots = player.getInv().getSlotAmount();

		ShiftClickBehavior input = new ShiftClickBehavior(0, playerSlots - 1, playerSlots, playerSlots);
		shiftClickBehaviors.add(input);
		shiftClickBehaviors.add(input.reversed());

		ShiftClickBehavior output = new ShiftClickBehavior(0, playerSlots - 1, playerSlots + 1, playerSlots + 1);
		this.shiftClickBehaviors.add(output.reversed());
	}

	@Override
	public void init(IGameInstance game)
	{
		super.init(game);

		this.components.add(new ComponentProgressBar(this, 51, 5, 35, 8, ModUtils.PROGRESS, false, GuiArcFurnace.this.te::getBlastPercentage));
	}

	@Override
	public ResourceName getName()
	{
		return RockSolid.createRes("gui_arc_furnace");
	}

}
