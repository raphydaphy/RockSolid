package com.raphydaphy.rocksolid.item;

import java.util.HashMap;
import java.util.Map;

import com.raphydaphy.rocksolid.render.ToolRenderer;

import de.ellpeck.rockbottom.api.item.ItemBasic;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ToolType;
import de.ellpeck.rockbottom.api.render.item.IItemRenderer;
import de.ellpeck.rockbottom.api.tile.Tile;
import de.ellpeck.rockbottom.api.util.reg.IResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.TileLayer;

public class ItemPickaxe extends ItemBasic
{
	private final float miningSpeed;
	private final Map<ToolType, Integer> toolTypes;

	public ItemPickaxe(final IResourceName name, final float miningSpeed, final int miningLevel)
	{
		super(name);
		this.toolTypes = new HashMap<ToolType, Integer>();
		this.miningSpeed = miningSpeed;
		this.maxAmount = 1;

		addToolType(ToolType.PICKAXE, miningLevel);
	}

	public ItemPickaxe addToolType(final ToolType type, final int level)
	{
		this.toolTypes.put(type, level);
		return this;
	}

	@Override
	protected IItemRenderer<ItemPickaxe> createRenderer(final IResourceName name)
	{
		return new ToolRenderer<ItemPickaxe>(name);
	}

	@Override
	public Map<ToolType, Integer> getToolTypes(final ItemInstance instance)
	{
		return this.toolTypes;
	}

	@Override
	public float getMiningSpeed(final IWorld world, final int x, final int y, final TileLayer layer, final Tile tile,
			final boolean isRightTool)
	{
		return isRightTool ? this.miningSpeed : super.getMiningSpeed(world, x, y, layer, tile, isRightTool);
	}
}
