package com.raphydaphy.rocksolid.tileentity;

import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileEntitySpinningWheel extends TileEntity
{
	// 0 = empty, 1 - 7 = spinning
	private int stage = 0;

	public TileEntitySpinningWheel(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
	}

	@Override
	public boolean doesTick()
	{
		return false;
	}

	public int getStage()
	{
		return stage;
	}

	public void setStage(int stage)
	{
		this.stage = stage;
		this.sendToClients();
		world.setDirty(x, y);
	}

	@Override
	public void save(DataSet set, boolean forSync)
	{
		super.save(set, forSync);
		set.addInt("stage", stage);
	}

	@Override
	public void load(DataSet set, boolean forSync)
	{
		super.load(set, forSync);
		stage = set.getInt("stage");
	}
}
