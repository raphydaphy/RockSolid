package com.raphydaphy.rocksolid.tile.machine;

import com.raphydaphy.rocksolid.init.ModItems;
import com.raphydaphy.rocksolid.init.ModTiles;
import com.raphydaphy.rocksolid.render.SpinningWheelRenderer;
import com.raphydaphy.rocksolid.tileentity.TileEntitySpinningWheel;
import com.raphydaphy.rocksolid.util.ToolInfo;
import de.ellpeck.rockbottom.api.entity.AbstractEntityItem;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ToolProperty;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.Util;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileSpinningWheel extends TileMachineBase<TileEntitySpinningWheel>
{
	public TileSpinningWheel()
	{
		super("spinning_wheel", TileEntitySpinningWheel.class, 7, false, new ToolInfo(ToolProperty.PICKAXE, 1));
	}

	@Override
	public TileEntity makeTE(IWorld world, int x, int y, TileLayer layer)
	{
		return new TileEntitySpinningWheel(world, x, y, layer);
	}

	@Override
	protected boolean[][] makeStructure()
	{
		return super.autoStructure(3, 3);
	}

	@Override
	public int getWidth()
	{
		return 3;
	}

	@Override
	public int getHeight()
	{
		return 3;
	}

	@Override
	public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY, AbstractEntityPlayer player)
	{
		TileEntitySpinningWheel te = getTE(world, world.getState(x, y), x, y);
		if (te != null)
		{
			int stage = te.getStage();

			if (stage == 0)
			{
				ItemInstance held = player.getInv().get(player.getSelectedSlot());
				if (held != null && held.getItem() == ModTiles.COTTON.getItem() && held.getAmount() >= 3)
				{
					if (!world.isClient())
					{
						player.getInv().set(player.getSelectedSlot(), held.copy().removeAmount(3));
						te.setStage(1);
					}
					return true;
				}
			} else
			{
				if (!world.isClient())
				{
					if (stage < 7)
					{
						te.setStage(stage + 1);
					} else
					{
						te.setStage(0);
						AbstractEntityItem.spawn(world, new ItemInstance(ModItems.LUNAR_COBALT_CLUSTER), x, y, Util.RANDOM.nextGaussian() * 0.1, Util.RANDOM.nextGaussian() * 0.1);
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	protected ITileRenderer<TileSpinningWheel> createRenderer(ResourceName name)
	{
		return new SpinningWheelRenderer(name, this);
	}

}
