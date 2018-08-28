package com.raphydaphy.rocksolid.tileentity;

import com.raphydaphy.rocksolid.RockSolid;
import com.raphydaphy.rocksolid.energy.IEnergyTile;
import com.raphydaphy.rocksolid.fluid.IFluidTile;
import com.raphydaphy.rocksolid.tile.machine.TilePump;
import com.raphydaphy.rocksolid.tileentity.base.TileEntityAssemblyConfigurable;
import com.raphydaphy.rocksolid.util.ModUtils;
import de.ellpeck.rockbottom.api.GameContent;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.Registries;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.tile.TileLiquid;
import de.ellpeck.rockbottom.api.tile.entity.SyncedInt;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.Collections;
import java.util.List;

public class TileEntityPump extends TileEntityAssemblyConfigurable implements IFluidTile<TileEntityPump>, IEnergyTile
{
	public static final String KEY_LIQUID_TYPE = "liquid_type";

	private final ResourceName PUMP_SOUND = RockSolid.createRes("pump");

	private SyncedInt liquidVolume = new SyncedInt("liquid_volume");
	private SyncedInt energyStored = new SyncedInt("energy_stored");

	private TileLiquid liquidType;
	private int lastPlayed = -1;

	public TileEntityPump(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
	}

	@Override
	public void save(DataSet set, boolean forSync)
	{
		super.save(set, forSync);
		liquidVolume.save(set);
		energyStored.save(set);
		if (liquidType != null)
		{
			set.addString(KEY_LIQUID_TYPE, Registries.TILE_REGISTRY.getId(liquidType).toString());
		}
	}

	@Override
	public int getEnergyStored()
	{
		return this.energyStored.get();
	}

	public int getLiquidVolume()
	{
		return liquidVolume.get();
	}

	@Override
	public void load(DataSet set, boolean forSync)
	{
		super.load(set, forSync);
		liquidVolume.load(set);
		energyStored.load(set);

		if (set.hasKey(KEY_LIQUID_TYPE))
		{
			liquidType = (TileLiquid) Registries.TILE_REGISTRY.get(new ResourceName(set.getString(KEY_LIQUID_TYPE)));
		}
	}

	@Override
	public int getMaxTransfer()
	{
		return Math.round(12 * getThroughputModifier());
	}

	@Override
	public void update(IGameInstance game)
	{
		super.update(game);
		if (this.energyStored.get() > 8)
		{
			if (!this.world.isClient())
			{
				if (this.world.getState(TileLayer.LIQUIDS, x, y).getTile() instanceof TileLiquid && this.world.getState(TileLayer.LIQUIDS, x + 1, y).getTile() instanceof TileLiquid)
				{
					TileLiquid liquidIn = (TileLiquid) this.world.getState(TileLayer.LIQUIDS, x, y).getTile();
					if (this.liquidVolume.get() + 25 + Math.round(getBonusYieldModifier()) <= fluidCapacityNull())
					{
						if (world.getTotalTime() % Math.round(8 / getSpeedModifier()) == 0)
						{

							this.energyStored.remove(1);
							world.setDirty(x, y);
						}
						if (world.getTotalTime() % Math.round(80 / getSpeedModifier()) == 0)
						{
							this.liquidType = liquidIn;
							this.liquidVolume.add(25 + Math.round(getBonusYieldModifier()));
							world.setDirty(x, y);
							int topY = y;
							TileState top = null;
							for (int i = y; i < y + 30; i++)
							{
								TileState state = world.getState(TileLayer.LIQUIDS, x, i);
								if (state.getTile().equals(liquidIn))
								{
									topY = i;
									top = state;
								} else
								{
									break;
								}
							}
							if (top == null)
							{
								top = world.getState(TileLayer.LIQUIDS, x, topY);
							}
							int level = (top.get((liquidIn).level));
							if (level == 0)
							{
								world.setState(TileLayer.LIQUIDS, x, topY, GameContent.TILE_AIR.getDefState());
							} else
							{
								world.setState(TileLayer.LIQUIDS, x, topY, top.prop((liquidIn).level, level - 1));
							}
						}
					}
				}
			}
			if (!(this.world.isDedicatedServer() && this.world.isServer()))
			{
				if (lastPlayed == -1 || world.getTotalTime() - lastPlayed >= 320)
				{
					world.playSound(PUMP_SOUND, x + 0.5d, y + 0.5d, layer.index(), 1, 4);
					lastPlayed = world.getTotalTime();
				}
			}
		}
	}

	@Override
	protected boolean needsSync()
	{
		return this.liquidVolume.needsSync() || this.energyStored.needsSync();
	}

	@Override
	public void onSync()
	{
		super.onSync();
		this.liquidVolume.onSync();
		this.energyStored.onSync();
	}

	public TileLiquid getLiquidType()
	{
		return this.liquidType;
	}

	@Override
	public boolean addFluid(Pos2 pos, TileLiquid liquid, int ml, boolean simulate)
	{
		return false;
	}

	public float getLiquidFullness()
	{
		return (float) this.liquidVolume.get() / (float)fluidCapacityNull();
	}

	public float getEnergyFullness()
	{
		return (float) this.energyStored.get() / (float)getEnergyCapacity(null, null);
	}

	@Override
	public boolean removeFluid(Pos2 pos, TileLiquid liquid, int ml, boolean simulate)
	{
		if (liquid != null && liquid.equals(this.liquidType) && this.liquidVolume.get() >= ml)
		{
			if (!simulate)
			{
				this.liquidVolume.remove(ml);

				if (this.liquidVolume.get() == 0)
				{
					this.liquidType = null;
					world.setDirty(x, y);
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public int getFluidCapacity(IWorld world, Pos2 pos, TileLiquid liquid)
	{
		if (getLiquidsAt(world, pos) != null)
		{
			if (this.liquidType == null && liquid != null)
			{
				return fluidCapacityNull();
			} else if (liquid != null)
			{
				return liquid.equals(this.liquidType) ? fluidCapacityNull() : 0;
			}
		}
		return 0;
	}

	private int fluidCapacityNull()
	{
		return Math.round(1000 * getCapacityModifier());
	}

	@Override
	public List<TileLiquid> getLiquidsAt(IWorld world, Pos2 pos)
	{
		TileState state = world.getState(pos.getX(), pos.getY());

		if (state.getTile() instanceof TilePump)
		{
			Pos2 inner = ((TilePump) state.getTile()).getInnerCoord(state);

			if (inner.getY() != 0)
			{
				return Collections.singletonList(this.liquidType);
			}
		}
		return null;
	}

	@Override
	public boolean addEnergy(Pos2 pos, int joules, boolean simulate)
	{
		if (joules + energyStored.get() <= getEnergyCapacity(world, pos))
		{
			if (!simulate)
			{
				this.energyStored.add(joules);
				world.setDirty(x, y);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean removeEnergy(Pos2 pos, int joules, boolean simulate)
	{
		return false;
	}

	@Override
	public int getEnergyCapacity(IWorld world, Pos2 pos)
	{
		Pos2 innerCoord = ModUtils.innerCoord(world, pos);
		if (innerCoord != null && innerCoord.getY() == 0)
		{
			return 0;
		}
		return Math.round(1000 * getCapacityModifier());
	}

	@Override
	public boolean doesTick()
	{
		return true;
	}
}
