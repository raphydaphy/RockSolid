package com.raphydaphy.rocksolid.tile;

import java.util.Collections;
import java.util.List;

import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.tile.TileBasic;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class TileOre extends TileBasic
{
	private final ItemInstance itemDrop;

	public TileOre(IResourceName name, ItemInstance drop, int oreHardness, int toolLevel)
	{
		super(name);
		itemDrop = drop;
		this.setHardness((float) oreHardness);
		this.addEffectiveTool(ToolType.PICKAXE, toolLevel);

		this.register();
	}

	@Override
	public List<ItemInstance> getDrops(final IWorld world, final int x, final int y, final TileLayer layer,
			final Entity destroyer)
	{
		return Collections.singletonList(itemDrop);
	}

}
