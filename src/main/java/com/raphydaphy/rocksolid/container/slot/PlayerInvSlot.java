package com.raphydaphy.rocksolid.container.slot;

import com.raphydaphy.rocksolid.RockSolid;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.gui.container.ContainerSlot;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.function.Predicate;

public class PlayerInvSlot extends ContainerSlot
{
	public static final ResourceName JETPACK = RockSolid.createRes("jetpack_slot");

	private final AbstractEntityPlayer player;
	private final ResourceName name;
	private final Predicate<ItemInstance> allowed;

	public PlayerInvSlot(AbstractEntityPlayer player, int slot, ResourceName name, Predicate<ItemInstance> allowed, int x, int y)
	{
		super(player.getInv(), slot, x, y);
		this.player = player;
		this.name = name;
		this.allowed = allowed;
	}

	@Override
	public boolean canPlace(ItemInstance instance)
	{
		return this.allowed.test(instance);
	}

	@Override
	public void set(ItemInstance instance)
	{
		if (player.getAdditionalData() != null)
		{
			if (instance != null)
			{
				// adding new item
				DataSet instanceSet = new DataSet();
				instance.save(instanceSet);
				player.getAdditionalData().addDataSet(name, instanceSet);
			} else
			{
				// removing the item
				player.getAdditionalData().addDataSet(name, new DataSet());
			}
		}
	}

	@Override
	public ItemInstance get()
	{
		return getSlotItem(player, name);
	}

	public static ItemInstance getSlotItem(AbstractEntityPlayer player, ResourceName slot)
	{
		if (player.getAdditionalData() != null)
		{
			DataSet item = player.getAdditionalData().getDataSet(slot);
			if (item != null && !item.isEmpty() && item.hasKey("item_name") && !item.getString("item_name").equals(""))
			{
				return ItemInstance.load(item);
			}
		}
		return null;
	}
}
