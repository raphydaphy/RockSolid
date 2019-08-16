package com.raphydaphy.rocksolid.gas;

import com.raphydaphy.rocksolid.RockSolid;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;

import java.awt.*;

public enum Gas {
    STEAM("steam", 165, 165, 165), HYDROGEN("hydrogen", 200, 147, 216), OXYGEN("oxygen", 224, 255, 255);

    public final int color;
    public final ResourceName name;

    Gas(String name, int r, int g, int b) {
        this.name = RockSolid.createRes("gas." + name);
        this.color = new Color(r, g, b).getRGB();
    }
}
