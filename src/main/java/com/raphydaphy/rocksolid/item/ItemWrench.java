package com.raphydaphy.rocksolid.item;

import org.lwjgl.glfw.GLFW;

import com.raphydaphy.rocksolid.init.ModMisc;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class ItemWrench extends ItemBase
{

	public ItemWrench()
	{
		super("wrench");
		this.maxAmount = 1;
	}

	@Override
	public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY,
			AbstractEntityPlayer player, ItemInstance instance)
	{
		if (layer == ModMisc.CONDUIT_LAYER)
		{
			if (RockBottomAPI.getGame().getInput().isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT))
			{
				world.destroyTile(x, y, layer, player, true);
				return true;
			}
		}
		return false;
	}

}
