package com.raphydaphy.rocksolid.gui;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.entity.EntityRocket;
import com.raphydaphy.rocksolid.gui.component.ComponentLiquidBar;
import com.raphydaphy.rocksolid.init.ModTiles;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentButton;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.awt.*;

public class GuiRocket extends GuiContainer
{
	private EntityRocket rocket;
	public GuiRocket(AbstractEntityPlayer player, EntityRocket rocket)
	{
		super(player, 136, 112);
		this.rocket = rocket;
	}

	@Override
	public void init(IGameInstance game)
	{
		super.init(game);

		this.components.add(new ComponentButton(this, 37, 0, 60, 18, () ->
		{
			System.out.println("go space");
			return this.rocket.getFuelFullness() > 0.5f;
		}, "Launch", "Good luck!"));
		this.components.add(new ComponentLiquidBar(this, 27, 24, 81, 10, Color.yellow.getRGB(), false, this.rocket::getFuelFullness, this.rocket::getFuelVolume, () -> ModTiles.FUEL));
	}

	@Override
	public ResourceName getName()
	{
		return RockSolid.createRes("gui_rocket");
	}
}
