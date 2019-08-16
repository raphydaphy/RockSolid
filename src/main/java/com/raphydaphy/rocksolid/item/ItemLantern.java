package com.raphydaphy.rocksolid.item;

import com.raphydaphy.rocksolid.RockSolid;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.item.ItemInstance;

import java.util.List;

public class ItemLantern extends ItemDurability {
    public ItemLantern() {
        super("lantern", 101);
    }

    @Override
    public void describeItem(IAssetManager manager, ItemInstance instance, List<String> desc, boolean isAdvanced, boolean isReal) {
        desc.add(instance.getDisplayName());
        if (isReal) {
            int highest = this.getHighestPossibleMeta();
            int fuel = highest - instance.getMeta();
            desc.add(manager.localize(RockSolid.createRes("info.lantern_fuel"), fuel));
        }
    }
}
