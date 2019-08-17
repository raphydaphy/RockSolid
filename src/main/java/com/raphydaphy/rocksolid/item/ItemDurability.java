package com.raphydaphy.rocksolid.item;

import com.raphydaphy.rocksolid.RockSolid;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.inventory.IInventory;
import de.ellpeck.rockbottom.api.item.ItemBasicTool;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.util.List;

public class ItemDurability extends ItemBasicTool {

    public ItemDurability(String name, int durability) {
        super(RockSolid.createRes(name), durability);
        register();
    }

    public void takeDamage(ItemInstance instance, AbstractEntityPlayer player, int amount) {
        IInventory inv = player.getInv();
        int selected = player.getSelectedSlot();

        int meta = instance.getMeta();
        if (meta + amount <= this.getHighestPossibleMeta()) {
            instance.setMeta(meta + amount);
            inv.notifyChange(selected);
        } else {
            inv.set(selected, null);
            RockBottomAPI.getInternalHooks().onToolBroken(player.world, player, instance);
        }
    }

}
