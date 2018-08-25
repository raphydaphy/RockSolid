package com.raphydaphy.rocksolid.tile.machine;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.tileentity.base.TileEntityAssemblyConfigurable;
import com.raphydaphy.rocksolid.util.ModUtils;
import com.raphydaphy.rocksolid.util.ToolInfo;

import de.ellpeck.rockbottom.api.data.set.ModBasedDataSet;
import de.ellpeck.rockbottom.api.entity.Entity;
import de.ellpeck.rockbottom.api.entity.player.AbstractEntityPlayer;
import de.ellpeck.rockbottom.api.item.Item;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.MultiTile;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.BoundBox;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.ArrayList;
import java.util.List;

public abstract class TileMachineBase<T extends TileEntity> extends MultiTile
{
	private final Class<T> tileEntityClass;
	private final boolean hasAssemblyProperties;

	public TileMachineBase(String name, Class<T> tileEntityClass, float hardness, boolean hasAssemblyProperties, ToolInfo... infos)
	{
		this(name, tileEntityClass, hardness, hasAssemblyProperties, false, infos);
	}

	public TileMachineBase(String name, Class<T> tileEntityClass, float hardness, boolean hasAssemblyProperties, boolean forceDrop, ToolInfo... infos)
	{
		super(RockSolid.createRes(name));

		this.setHardness(hardness);
		this.tileEntityClass = tileEntityClass;
		this.hasAssemblyProperties = hasAssemblyProperties;

		if (forceDrop)
		{
			this.setForceDrop();
		}

		for (ToolInfo info : infos)
		{
			this.addEffectiveTool(info.getType(), info.getLevel());
		}

		this.register();
	}

	@Override
	public boolean canPlaceInLayer(TileLayer layer)
	{
		return layer == TileLayer.MAIN;
	}

	public boolean[][] autoStructure(int width, int height)
	{
		boolean[][] struct = new boolean[height][width];

		for (int x = 0; x < height; x++)
		{
			for (int y = 0; y < width; y++)
			{
				struct[x][y] = true;
			}
		}
		return struct;
	}

	public T getTE(IWorld world, TileState state, int x, int y)
	{
		Pos2 main = this.getMainPos(x, y, state);
		return world.getTileEntity(main.getX(), main.getY(), tileEntityClass);
	}

	@Override
	public boolean canProvideTileEntity()
	{
		return true;
	}

	@Override
	public TileEntity provideTileEntity(IWorld world, int x, int y, TileLayer layer)
	{
		TileState state = world.getState(x, y);
		return layer == TileLayer.MAIN && this.isMainPos(x, y, state) ? makeTE(world, x, y, layer) : null;
	}

	public abstract TileEntity makeTE(IWorld world, int x, int y, TileLayer layer);

	@Override
	public int getMainX()
	{
		return 0;
	}

	@Override
	public int getMainY()
	{
		return 0;
	}

	@Override
	public BoundBox getBoundBox(IWorld world, int x, int y, TileLayer layer)
	{
		return null;
	}

	@Override
	public boolean isFullTile()
	{
		return false;
	}


	@Override
	public void doPlace(IWorld world, int x, int y, TileLayer layer, ItemInstance instance, AbstractEntityPlayer placer)
	{
		super.doPlace(world, x, y, layer, instance, placer);
		T teUnchecked;
		if (!world.isClient() && hasAssemblyProperties && (teUnchecked = getTE(world, world.getState(x, y), x, y)) instanceof TileEntityAssemblyConfigurable)
		{
			TileEntityAssemblyConfigurable te = (TileEntityAssemblyConfigurable)teUnchecked;
			ModBasedDataSet data = instance.getAdditionalData();

			if (data != null)
			{
				te.setCapacityModifier(data.getFloat(ModUtils.ASSEMBLY_CAPACITY_KEY));
				te.setEfficiencyModifier(data.getFloat(ModUtils.ASSEMBLY_EFFICIENCY_KEY));
				te.setSpeedModifier(data.getFloat(ModUtils.ASSEMBLY_SPEED_KEY));
				te.setBonusYieldModifier(data.getFloat(ModUtils.ASSEMBLY_BONUS_KEY));
				te.setThroughputModifier(data.getFloat(ModUtils.ASSEMBLY_THROUGHPUT_KEY));
			}
		}
	}

	@Override
	public List<ItemInstance> getDrops(IWorld world, int x, int y, TileLayer layer, Entity destroyer)
	{
		if (!hasAssemblyProperties)
		{
			return super.getDrops(world, x, y, layer, destroyer);
		}
		List<ItemInstance> drops = new ArrayList<>();
		Item item = this.getItem();
		if (item != null)
		{
			TileEntityAssemblyConfigurable te = (TileEntityAssemblyConfigurable)getTE(world, world.getState(x, y), x, y);
			ItemInstance nbtOut = new ItemInstance(item);
			nbtOut.getOrCreateAdditionalData().addFloat(ModUtils.ASSEMBLY_CAPACITY_KEY, te.getCapacityModifier());
			nbtOut.getAdditionalData().addFloat(ModUtils.ASSEMBLY_EFFICIENCY_KEY, te.getEfficiencyModifier());
			nbtOut.getAdditionalData().addFloat(ModUtils.ASSEMBLY_SPEED_KEY, te.getSpeedModifier());
			nbtOut.getAdditionalData().addFloat(ModUtils.ASSEMBLY_BONUS_KEY, te.getBonusYieldModifier());
			nbtOut.getAdditionalData().addFloat(ModUtils.ASSEMBLY_THROUGHPUT_KEY, te.getThroughputModifier());
			drops.add(nbtOut);
		}

		return drops;
	}

}
