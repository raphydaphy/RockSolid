package com.raphydaphy.rocksolid.gui;

import com.google.common.base.Supplier;
import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.gas.Gas;
import com.raphydaphy.rocksolid.gui.component.ComponentCustomText;
import com.raphydaphy.rocksolid.gui.component.ComponentEnergyBar;
import com.raphydaphy.rocksolid.gui.component.ComponentGasBar;
import com.raphydaphy.rocksolid.gui.component.ComponentHeatBar;
import com.raphydaphy.rocksolid.network.PacketReactorDepth;
import com.raphydaphy.rocksolid.tileentity.TileEntityNuclearReactor;
import com.raphydaphy.rocksolid.tileentity.TileEntityTurbine;
import com.raphydaphy.rocksolid.util.ModUtils;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.GuiContainer;
import de.ellpeck.rockbottom.api.gui.component.ComponentButton;
import de.ellpeck.rockbottom.api.net.packet.IPacket;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.awt.*;
import java.util.List;

public class GuiNuclearReactor extends GuiContainer
{
	private final TileEntityNuclearReactor te;
	private int lastDepth;
	private ComponentCustomText depthText;
	private final ResourceName DEPTH = RockSolid.createRes("reactor_depth");

	public GuiNuclearReactor(AbstractEntityPlayer player, TileEntityNuclearReactor te)
	{
		super(player, 136, 135);
		this.te = te;
	}

	@Override
	public void init(IGameInstance game)
	{
		super.init(game);

		int depthTextX = 24;
		int depthTextY = 0;

		lastDepth = te.getDepth();
		depthText = new ComponentCustomText(this, depthTextX + 43, depthTextY + 5, 80, 1, 0.3f, ComponentCustomText.TextDirection.CENTER,
				lastDepth + " " + game.getAssetManager().localize(DEPTH));
		this.components.add(depthText);

		this.components.add(new ComponentButton(this, depthTextX + 77, depthTextY, 10, 10, (Supplier<Boolean>) () ->
		{
			if (te.getDepth() < 100)
			{
				IPacket updatePacket = new PacketReactorDepth(te.x, te.y, te.getDepth() + 1);
				if (game.getWorld().isClient())
				{
					RockBottomAPI.getNet().sendToServer(updatePacket);
				} else
				{
					updatePacket.handle(game, null);
				}
				return true;
			}
			return false;
		}, "+"));
		this.components.add(new ComponentButton(this, depthTextX, depthTextY, 10, 10,(Supplier<Boolean>) () ->
		{
			if (te.getDepth() > 0)
			{
				IPacket updatePacket = new PacketReactorDepth(te.x, te.y, te.getDepth() - 1);
				if (game.getWorld().isClient())
				{
					RockBottomAPI.getNet().sendToServer(updatePacket);
				} else
				{
					updatePacket.handle(game, null);
				}
				return true;
			}
			return false;
		}, "-"));

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
		if (te.getDepth() != lastDepth)
		{
			lastDepth = te.getDepth();
			depthText.setText(lastDepth + " " + game.getAssetManager().localize(DEPTH));
		}
	}

}
