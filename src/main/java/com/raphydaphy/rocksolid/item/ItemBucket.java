package com.raphydaphy.rocksolid.item;

import com.raphydaphy.rocksolid.init.ModTiles;

import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class ItemBucket extends ItemBase
{

	public ItemBucket()
	{
		super("bucket");
	}

	@Override
	public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY,
			AbstractEntityPlayer player, ItemInstance instance)
	{
		world.setState(TileLayer.LIQUIDS, x, y,
				ModTiles.WATER.getPlacementState(world, x, y, TileLayer.LIQUIDS, instance, player));
		return true;

	}

}
