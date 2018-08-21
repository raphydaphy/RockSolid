package com.raphydaphy.rocksolid.tileentity;

import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.construction.smelting.FuelInput;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public abstract class TileEntityFueledBase extends TileEntity
{
	public static final String KEY_COAL_TIME = "coal_time";
	public static final String KEY_MAX_COAL_TIME = "max_coal_time";

	private boolean lastActive = false;

	public int coalTime;
	private int lastCoalTime;
	private int maxCoalTime;

	public TileEntityFueledBase(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
	}

	protected abstract boolean tryTickAction();

	protected abstract float getFuelModifier();

	protected abstract ItemInstance getFuel();

	protected abstract void removeFuel();

	protected abstract void onActiveChange(boolean active);

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
						int amount = (int) (getFuelValue(inst) * this.getFuelModifier());
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

	public static int getFuelValue(ItemInstance item)
	{
		return FuelInput.getFuelTime(item);
	}

	@Override
	protected boolean needsSync()
	{
		return this.lastCoalTime != this.coalTime;
	}

	@Override
	protected void onSync()
	{
		this.lastCoalTime = this.coalTime;
	}

	public boolean isActive()
	{
		return this.coalTime > 0;
	}

	public float getFuelPercentage()
	{
		return (float) this.coalTime / (float) this.maxCoalTime;
	}

	@Override
	public void save(DataSet set, boolean forSync)
	{
		super.save(set, forSync);
		set.addInt(KEY_COAL_TIME, this.coalTime);
		set.addInt(KEY_MAX_COAL_TIME, this.maxCoalTime);
	}

	@Override
	public void load(DataSet set, boolean forSync)
	{
		super.load(set, forSync);
		this.coalTime = set.getInt(KEY_COAL_TIME);
		this.maxCoalTime = set.getInt(KEY_MAX_COAL_TIME);
	}

	public boolean doesTick()
	{
		return true;
	}

}
