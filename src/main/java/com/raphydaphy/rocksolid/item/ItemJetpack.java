package com.raphydaphy.rocksolid.item;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.fluid.IFluidTile;
import com.raphydaphy.rocksolid.init.ModTiles;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.TileLiquid;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.List;

public class ItemJetpack extends ItemDurability
{
	public ItemJetpack()
	{
		super("jetpack", 1001);
	}

	@Override
	public void describeItem(IAssetManager manager, ItemInstance instance, List<String> desc, boolean isAdvanced)
	{
		desc.add(instance.getDisplayName());
		int highest = this.getHighestPossibleMeta();
		int fuel = highest - instance.getMeta();
		desc.add(manager.localize(RockSolid.createRes("info.jetpack_fuel"), fuel, Math.round(((float)fuel / (float)highest) * 100)));
	}

	@Override
	public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY,
	                              AbstractEntityPlayer player, ItemInstance instance)
	{
		TileState liquidState = world.getState(TileLayer.LIQUIDS, x, y);
		TileState main = world.getState(TileLayer.MAIN, x, y);
		TileEntity te;
		if (main.getTile() instanceof MultiTile)
		{
			Pos2 mainPos = ((MultiTile) main.getTile()).getMainPos(x, y, main);
			te = world.getTileEntity(mainPos.getX(), mainPos.getY());
		} else
		{
			te = world.getTileEntity(x, y);
		}

		if (instance.getMeta() - 25 >= 0)
		{
			if (liquidState.getTile() instanceof TileLiquid)
			{
				TileLiquid liquid = (TileLiquid) liquidState.getTile();
				if (liquid == ModTiles.FUEL)
				{
					int curLevel = liquidState.get(liquid.level);
					if (curLevel > 0)
					{
						world.setState(TileLayer.LIQUIDS, x, y, liquidState.prop(liquid.level, curLevel - 1));
					} else
					{
						world.setState(TileLayer.LIQUIDS, x, y, GameContent.TILE_AIR.getDefState());
					}
					instance.setMeta(instance.getMeta() - 25);
				}
			} else if (te instanceof IFluidTile<?>)
			{
				IFluidTile<?> fluidTE = (IFluidTile<?>) te;
				if (fluidTE.removeFluid(new Pos2(x, y), ModTiles.FUEL, 25, false))
				{
					instance.setMeta(instance.getMeta() - 25);
					return true;
				}
				return false;
			} else
			{
				return false;
			}
		} else
		{
			return false;
		}
		return true;
	}
}
