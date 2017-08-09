package com.raphydaphy.rocksolid.api.util;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.world.IWorld;

public abstract class TileEntityProgressBar extends TileEntity
{
	private boolean lastActive;
	private boolean shouldSync = false;

	public TileEntityProgressBar(final IWorld world, final int x, final int y)
	{
		super(world, x, y);
	}

	@Override
	protected boolean needsSync()
	{
		return super.needsSync() || shouldSync;
	}

	@Override
	protected void onSync()
	{
		super.onSync();
		shouldSync = false;
	}

	protected abstract boolean tryTickAction();

	protected abstract void onActiveChange(boolean active);

	public abstract boolean isActive();

	public abstract float getSmeltPercentage();

	@Override
	public void update(IGameInstance game)
	{
		super.update(game);

		if (!world.isClient())
		{
			this.tryTickAction();
		}

		boolean active = this.isActive();

		if (this.lastActive != active)
		{
			if (!world.isClient())
			{
				this.lastActive = active;
				shouldSync = true;
			}
			this.onActiveChange(active);
		}
	}

	@Override
	public void save(final DataSet set, final boolean forSync)
	{
		super.save(set, forSync);
		set.addBoolean("lastActive", this.lastActive);
		set.addBoolean("shouldsync", this.shouldSync);
	}

	@Override
	public void load(final DataSet set, final boolean forSync)
	{
		super.load(set, forSync);
		this.lastActive = set.getBoolean("lastActive");
		this.shouldSync = set.getBoolean("shouldSync");
	}

}
