package com.raphydaphy.rocksolid.item;

import com.raphydaphy.rocksolid.RockSolid;
import de.ellpeck.rockbottom.api.item.ItemBasic;

public class ItemBase extends ItemBasic {

    public ItemBase(String name) {
        super(RockSolid.createRes(name));
        this.register();
    }

}
