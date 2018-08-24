package com.raphydaphy.rocksolid.tile;

import com.raphydaphy.rocksolid.RockSolid;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.tile.TileBasic;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.Collections;
import java.util.List;

public class TileOre extends TileBasic
{
	private final ItemInstance drop;

	public TileOre(String name, float hardness, int level, Item drop)
	{
		this(name, hardness, level, new ItemInstance(drop));
	}

	public TileOre(String name, float hardness, int level, ItemInstance drop)
	{
		super(RockSolid.createRes(name));

		this.drop = drop;

		this.setHardness(hardness);
		this.addEffectiveTool(ToolType.PICKAXE, level);

		this.register();
	}

	@Override
	public List<ItemInstance> getDrops(IWorld world, int x, int y, TileLayer layer, Entity destroyer)
	{
		return Collections.singletonList(drop);
	}

	@Override
	protected boolean hasItem()
	{
		return false;
	}
}
