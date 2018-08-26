package com.raphydaphy.rocksolid.item;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.IRenderer;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.render.item.DefaultItemRenderer;
import de.ellpeck.rockbottom.api.render.item.IItemRenderer;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.List;

public class ItemDurability extends ItemBase
{
	private final int durability;

	public ItemDurability(String name, int durability)
	{
		super(name);
		this.durability = durability;
		this.maxAmount = 1;
	}

	@Override
	public void describeItem(IAssetManager manager, ItemInstance instance, List<String> desc, boolean isAdvanced)
	{
		super.describeItem(manager, instance, desc, isAdvanced);

		int highest = this.getHighestPossibleMeta() + 1;
		desc.add(manager.localize(ResourceName.intern("info.durability"), highest - instance.getMeta(), highest));
	}

	public void takeDamage(ItemInstance instance, AbstractEntityPlayer player, int amount)
	{
		IInventory inv = player.getInv();
		int selected = player.getSelectedSlot();

		int meta = instance.getMeta();
		if (meta + amount <= this.getHighestPossibleMeta())
		{
			instance.setMeta(meta + amount);
			inv.notifyChange(selected);
		} else
		{
			inv.set(selected, null);
			RockBottomAPI.getInternalHooks().onToolBroken(player.world, player, instance);
		}
	}

	@Override
	public boolean useMetaAsDurability()
	{
		return true;
	}

	@Override
	public int getHighestPossibleMeta()
	{
		return this.durability - 1;
	}

}
