package com.raphydaphy.rocksolid.api.util;

import com.raphydaphy.rocksolid.api.RockSolidAPI;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public abstract class TileEntityFueled extends TileEntity
{

	protected int coalTime;
	protected int maxCoalTime;

	private int lastCoal;
	private boolean lastActive;

	public TileEntityFueled(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
	}

	@Override
	public void update(IGameInstance game)
	{
		super.update(game);

		if (!RockBottomAPI.getNet().isClient())
		{
			boolean smelted = this.tryTickAction();

			if (this.coalTime > 0)
			{
				this.coalTime--;
			}

			if (smelted)
			{
				if (this.coalTime <= 0)
				{
					ItemInstance inst = this.getFuel();
					if (inst != null)
					{
						int amount = (int) (RockSolidAPI.getFuelValue(inst) * this.getFuelModifier());
						if (amount > 0)
						{
							this.maxCoalTime = amount;
							this.coalTime = amount;

							this.removeFuel();
						}
					}
				}
			}
		}

		boolean active = this.isActive();
		if (this.lastActive != active)
		{
			this.lastActive = active;

			this.onActiveChange(active);
		}
	}

	@Override
	protected boolean needsSync()
	{
		return this.lastCoal != this.coalTime;
	}

	@Override
	protected void onSync()
	{
		this.lastCoal = this.coalTime;
	}

	public boolean isActive()
	{
		return this.coalTime > 0;
	}

	public float getFuelPercentage()
	{
		return (float) this.coalTime / (float) this.maxCoalTime;
	}

	protected abstract boolean tryTickAction();

	protected abstract float getFuelModifier();

	protected abstract ItemInstance getFuel();

	protected abstract void removeFuel();

	protected abstract void onActiveChange(boolean active);

	@Override
	public void save(DataSet set, boolean forSync)
	{
		set.addInt("coal", this.coalTime);
		set.addInt("max_coal", this.maxCoalTime);
	}

	@Override
	public void load(DataSet set, boolean forSync)
	{
		this.coalTime = set.getInt("coal");
		this.maxCoalTime = set.getInt("max_coal");
	}
}