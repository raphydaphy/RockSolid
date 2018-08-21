package com.raphydaphy.rocksolid.container;

import com.raphydaphy.rocksolid.RockSolid;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

public class ContainerEmpty extends ItemContainer
{
	public ContainerEmpty(AbstractEntityPlayer player)
	{
		this(player, 0, 61);
	}

	public ContainerEmpty(AbstractEntityPlayer player, int invX, int invY)
	{
		super(player);
		this.addPlayerInventory(player, invX, invY);
	}

	@Override
	public ResourceName getName()
	{
		return RockSolid.createRes("container_base");
	}

}
