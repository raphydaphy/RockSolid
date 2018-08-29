package com.raphydaphy.rocksolid.tile;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.init.ModTiles;
import com.raphydaphy.rocksolid.util.ToolInfo;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ToolProperty;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.Collections;
import java.util.List;

public class TileMoonSurface extends TileBase
{
	public TileMoonSurface()
	{
		super("moon.surface_rock", 6, new ToolInfo(ToolProperty.PICKAXE, 10));
	}

	public final List<ItemInstance> getDrops(IWorld var1, int var2, int var3, TileLayer var4, Entity var5)
	{
		return Collections.singletonList(new ItemInstance(ModTiles.MOON_TURF));
	}

	public final void onChangeAround(IWorld var1, int var2, int var3, TileLayer var4, int var5, int var6, TileLayer var7)
	{
		super.onChangeAround(var1, var2, var3, var4, var5, var6, var7);
		if (cantStay(var1, var2, var3, var4))
		{
			var1.setState(var4, var2, var3, ModTiles.MOON_TURF.getDefState());
		}

	}

	private static boolean cantStay(IWorld var0, int var1, int var2, TileLayer var3)
	{
		if (var0.isPosLoaded((double) var1, (double) (var2 + 1)))
		{
			return var0.getState(var3, var1, var2 + 1).getTile().hasSolidSurface(var0, var1, var2 + 1, var3);
		} else
		{
			return false;
		}
	}

	public final TileState getPlacementState(IWorld var1, int var2, int var3, TileLayer var4, ItemInstance var5, AbstractEntityPlayer var6)
	{
		return cantStay(var1, var2, var3, var4) ? ModTiles.MOON_TURF.getDefState() : this.getDefState();
	}
}
