package com.raphydaphy.rocksolid.container;

import com.raphydaphy.rocksolid.RockSolid;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ItemContainer;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class ContainerEmpty extends ItemContainer
{
	public ContainerEmpty(AbstractEntityPlayer player)
	{
		this(player, 0, 61);
	}

	public ContainerEmpty(AbstractEntityPlayer player, int invX, int invY)
	{
		super(player, player.getInv());
		this.addPlayerInventory(player, invX, invY);
	}

	@Override
	public IResourceName getName()
	{
		return RockSolid.createRes("container_base");
	}

}
