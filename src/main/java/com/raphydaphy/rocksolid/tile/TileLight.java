package com.raphydaphy.rocksolid.tile;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.container.slot.PlayerInvSlot;
import com.raphydaphy.rocksolid.init.ModMisc;
import com.raphydaphy.rocksolid.render.LightRenderer;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.TileBasic;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileLight extends TileBasic
{
	public TileLight()
	{
		super(RockSolid.createRes("light"));
		this.register();
	}

	@Override
	protected boolean hasItem()
	{
		return false;
	}

	@Override
	public int getLight(IWorld world, int x, int y, TileLayer layer)
	{
		return 75;
	}

	@Override
	public boolean canBreak(IWorld world, int x, int y, TileLayer layer, AbstractEntityPlayer player, boolean isRightTool)
	{
		return false;
	}

	@Override
	public boolean canPlaceInLayer(TileLayer layer)
	{
		return layer == ModMisc.LIGHTING_LAYER;
	}

	@Override
	protected LightRenderer createRenderer(ResourceName name)
	{
		return new LightRenderer(name);
	}

	@Override
	public void updateRandomly(IWorld world, int x, int y, TileLayer layer) {
		if (!world.isClient())
		{
			for (AbstractEntityPlayer player : world.getAllPlayers())
			{
				int playerX = (int)Math.floor(player.getX());
				int playerY = (int)Math.floor(player.getY());
				if (playerX == x && (playerY != y || playerY + 1 != y))
				{
					ItemInstance lantern = PlayerInvSlot.getSlotItem(player, PlayerInvSlot.LANTERN);
					if (lantern != null && lantern.getMeta() < 100)
					{
						return;
					}
				}
			}
			world.setState(layer, x, y, GameContent.TILE_AIR.getDefState());
		}
	}
}
