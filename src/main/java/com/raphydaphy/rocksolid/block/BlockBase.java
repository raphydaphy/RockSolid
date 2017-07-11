package com.raphydaphy.rocksolid.block;

import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.tile.TileBasic;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class BlockBase extends TileBasic
{
	public BlockBase(IResourceName name, int oreHardness, int toolLevel) {
		super(name);
		this.setHardness((float)oreHardness);
        this.addEffectiveTool(ToolType.PICKAXE, toolLevel);
        
        this.register();
	}

}
