package com.raphydaphy.rocksolid.tile.machine;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.container.ContainerEmpty;
import com.raphydaphy.rocksolid.gui.GuiBattery;
import com.raphydaphy.rocksolid.render.BatteryRenderer;
import com.raphydaphy.rocksolid.tileentity.TileEntityBattery;
import com.raphydaphy.rocksolid.util.ModUtils;
import com.raphydaphy.rocksolid.util.ToolInfo;
import de.ellpeck.rockbottom.api.data.set.ModBasedDataSet;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.item.ToolProperty;
import de.ellpeck.rockbottom.api.render.tile.ITileRenderer;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.ArrayList;
import java.util.List;

public class TileBattery extends TileMachineBase<TileEntityBattery>
{
	public static final ResourceName ITEM_POWER = RockSolid.createRes("item_power");
	public TileBattery()
	{
		super("battery", TileEntityBattery.class,20,true, new ToolInfo(ToolProperty.PICKAXE, 6));
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
	protected ITileRenderer<TileBattery> createRenderer(ResourceName name)
	{
		return new BatteryRenderer(name, this);
	}

	@Override
	public TileEntity makeTE(IWorld world, int x, int y, TileLayer layer)
	{
		return new TileEntityBattery(world, x, y, layer);
	}

	@Override
	public boolean onInteractWith(IWorld world, int x, int y, TileLayer layer, double mouseX, double mouseY,
			AbstractEntityPlayer player)
	{
		TileEntityBattery te = getTE(world, world.getState(x, y), x, y);
		player.openGuiContainer(new GuiBattery(player, te), new ContainerEmpty(player, 0, 30));
		return true;
	}
	@Override
	public void doPlace(IWorld world, int x, int y, TileLayer layer, ItemInstance instance, AbstractEntityPlayer placer)
	{
		super.doPlace(world, x, y, layer, instance, placer);
		if (!world.isClient())
		{
			TileEntityBattery te = getTE(world, world.getState(x, y), x, y);
			ModBasedDataSet data = instance.getAdditionalData();

			if (data != null)
			{
				te.addEnergy(null, data.getInt(ITEM_POWER), false);
			}
		}
	}

	@Override
	public List<ItemInstance> getDrops(IWorld world, int x, int y, TileLayer layer, Entity destroyer)
	{
		List<ItemInstance> drops = super.getDrops(world, x, y, layer, destroyer);
		if (drops.size() >= 1)
		{
			for (ItemInstance item : drops)
			{
				if (item.getItem() == this.getItem())
				{
					item.getOrCreateAdditionalData().addInt(ITEM_POWER, getTE(world, world.getState(x, y), x, y).getEnergyStored());
					break;
				}
			}
		}

		return drops;
	}
}
