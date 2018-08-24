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
import de.ellpeck.rockbottom.api.tile.entity.TileEntity;
import de.ellpeck.rockbottom.api.tile.state.TileState;
import de.ellpeck.rockbottom.api.util.Pos2;
import de.ellpeck.rockbottom.api.util.reg.ResourceName;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

import java.util.Arrays;
import java.util.List;

public class TileEntityPump extends TileEntityAssemblyConfigurable implements IFluidTile<TileEntityPump>, IEnergyTile
{
	public static final String KEY_LIQUID_TYPE = "liquid_type";
	public static final String KEY_ENERGY_STORED = "energy_stored";
	private static final String KEY_LIQUID_VOLUME = "liquid_volume";
	private final ResourceName PUMP_SOUND = RockSolid.createRes("pump");
	private int liquidVolume = 0;
	private int lastLiquidVolume = 0;
	private int energyStored = 0;
	private int lastEnergyStored = 0;
	private TileLiquid liquidType;
	private int lastPlayed = -1;

	public TileEntityPump(IWorld world, int x, int y, TileLayer layer)
	{
		super(world, x, y, layer);
		liquidType = null;
	}

	@Override
	public void save(DataSet set, boolean forSync)
	{
		super.save(set, forSync);
		set.addInt(KEY_LIQUID_VOLUME, this.liquidVolume);
		set.addInt(KEY_ENERGY_STORED, this.energyStored);
		if (liquidType != null)
		{
			set.addString(KEY_LIQUID_TYPE, Registries.TILE_REGISTRY.getId(liquidType).toString());
		}
	}

	@Override
	public int getEnergyStored()
	{
		return this.energyStored;
	}

	@Override
	public void load(DataSet set, boolean forSync)
	{
		super.load(set, forSync);
		this.liquidVolume = set.getInt(KEY_LIQUID_VOLUME);
		this.energyStored = set.getInt(KEY_ENERGY_STORED);

		if (set.hasKey(KEY_LIQUID_TYPE))
		{
			liquidType = (TileLiquid) Registries.TILE_REGISTRY.get(ResourceName.intern(set.getString(KEY_LIQUID_TYPE)));
		}
	}

	@Override
	public int getMaxTransfer()
	{
		return 12;
	}

	@Override
	public void update(IGameInstance game)
	{
		super.update(game);
		if (this.energyStored > 8)
		{
			if (!this.world.isClient())
			{
				if (this.world.getState(TileLayer.LIQUIDS, x, y).getTile() instanceof TileLiquid && this.world.getState(TileLayer.LIQUIDS, x + 1, y).getTile() instanceof TileLiquid)
				{
					TileLiquid liquidIn = (TileLiquid) this.world.getState(TileLayer.LIQUIDS, x, y).getTile();
					if (this.liquidVolume + 25 <= 1000)
					{
						if (world.getTotalTime() % 8 == 0)
						{

							this.energyStored -= 1;
						}
						if (world.getTotalTime() % 80 == 0)
						{
							this.liquidType = liquidIn;
							this.liquidVolume += 25;
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
		return this.liquidVolume != this.lastLiquidVolume || this.energyStored != this.lastEnergyStored;
	}

	@Override
	public void onSync()
	{
		super.onSync();
		this.lastLiquidVolume = this.liquidVolume;
		this.lastEnergyStored = this.energyStored;
	}

	public int getLiquidVolume()
	{
		return this.liquidVolume;
	}

	public TileLiquid getLiquidType()
	{
		return this.liquidType;
	}

	@Override
	public boolean addFluid(Pos2 pos, TileLiquid liquid, int ml, boolean simulate)
	{
		if (liquid != null)
		{
			if (this.liquidType != null && !this.liquidType.equals(liquid))
			{
				return false;
			}

			if (this.liquidVolume + ml >= this.getFluidCapacity(world, pos, this.liquidType))
			{
				return false;
			}

			if (!simulate)
			{
				if (this.liquidType == null)
				{
					this.liquidType = liquid;
				}
				this.liquidVolume += ml;
			}

			return true;
		}
		return false;
	}

	public float getLiquidFullness()
	{
		return (float) this.liquidVolume / 1000f;
	}

	public float getEnergyFullness()
	{
		return (float) this.energyStored / 1000f;
	}

	@Override
	public boolean removeFluid(Pos2 pos, TileLiquid liquid, int ml, boolean simulate)
	{
		if (this.liquidType != null && liquid != null && liquid.equals(this.liquidType) && this.liquidVolume >= ml)
		{
			if (!simulate)
			{
				this.liquidVolume -= ml;

				if (this.liquidVolume == 0)
				{
					this.liquidType = null;
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
				return 1000;
			} else if (liquid != null && this.liquidType != null)
			{
				return liquid.equals(this.liquidType) ? 1000 : 0;
			}
		}
		return 0;
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
				return Arrays.asList(this.liquidType);
			}
		}
		return null;
	}

	@Override
	public boolean addEnergy(Pos2 pos, int joules, boolean simulate)
	{
		if (joules + energyStored <= getEnergyCapacity(world, pos))
		{
			if (!simulate)
			{
				this.energyStored += joules;
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
		return 1000;
	}

	@Override
	public boolean doesTick()
	{
		return true;
	}
}
