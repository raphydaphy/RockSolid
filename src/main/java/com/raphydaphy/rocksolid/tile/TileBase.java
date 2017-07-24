package com.raphydaphy.rocksolid.tile;

import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.tile.TileBasic;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;

public class TileBase extends TileBasic
{
	public TileBase(IResourceName name, int oreHardness, int toolLevel) {
		super(name);
		this.setHardness((float)oreHardness);
        this.addEffectiveTool(ToolType.PICKAXE, toolLevel);
        
        this.register();
	}
	
	public TileBase(IResourceName name, int oreHardness, int toolLevel, ToolType toolType) {
		super(name);
		this.setHardness((float)oreHardness);
        this.addEffectiveTool(toolType, toolLevel);
        
        this.register();
	}

}
