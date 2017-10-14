package com.raphydaphy.rocksolid.tileentity;

import java.util.Arrays;
import java.util.List;

import com.raphydaphy.rocksolid.api.fluid.Fluid;
import com.raphydaphy.rocksolid.api.fluid.IFluidAcceptor;
import com.raphydaphy.rocksolid.api.gas.Gas;
import com.raphydaphy.rocksolid.api.gas.IGasProducer;
import com.raphydaphy.rocksolid.api.util.TileEntityFueled;
import com.raphydaphy.rocksolid.gui.inventory.ContainerInventory;

import de.ellpeck.rockbottom.api.RockBottomAPI;
import de.ellpeck.rockbottom.api.data.set.DataSet;
import de.ellpeck.rockbottom.api.inventory.Inventory;
import de.ellpeck.rockbottom.api.item.ItemInstance;
import de.ellpeck.rockbottom.api.tile.entity.IInventoryHolder;
import de.ellpeck.rockbottom.api.util.Direction;
import de.ellpeck.rockbottom.api.world.IWorld;
import de.ellpeck.rockbottom.api.world.layer.TileLayer;

public class TileEntityBoiler extends TileEntityFueled implements IInventoryHolder, IGasProducer, IFluidAcceptor
{

	public static final int COAL = 0;
	public final ContainerInventory inventory;
	private boolean shouldSync = false;

	public static final int productionPerTick = 1;
	public static final int fluidConsumptionPerTick = 1;

	protected int gasStored;
	protected int maxGas = 5000;
	protected String gasType = Gas.VACCUM.getName();

	protected int fluidStored = 0;
	protected int maxFluid = 5000;
	protected String fluidType = Fluid.EMPTY.getName();

	public TileEntityBoiler(final IWorld world, final int x, final int y, TileLayer layer)
	{
		super(world, x, y, layer);
		this.inventory = new ContainerInventory(this, 4);

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

	@Override
	protected boolean tryTickAction()
	{
		if (this.gasStored < (this.maxGas - productionPerTick - 1) && this.fluidStored >= fluidConsumptionPerTick
				&& this.fluidType.equals(Fluid.WATER.getName()))
		{
			if (this.coalTime > 0)
			{
				if (RockBottomAPI.getNet().isClient() == false)
				{

					if (this.gasType.equals(Gas.VACCUM.getName()))
					{
						this.gasType = Gas.STEAM.getName();
					}

					this.fluidStored -= fluidConsumptionPerTick;
					this.gasStored += productionPerTick;

					if (this.fluidStored == 0)
					{
						this.fluidType = Fluid.EMPTY.getName();
					}
					shouldSync = true;
				}

			}
			return true;
		}
		return false;
	}

	@Override
	protected float getFuelModifier()
	{
		return 0.25f;
	}

	@Override
	protected ItemInstance getFuel()
	{
		return this.inventory.get(0);
	}

	@Override
	protected void removeFuel()
	{
		this.inventory.remove(0, 1);
	}

	public float getFluidTankFullness()
	{
		if (fluidStored == 0)
		{
			return 0;
		}
		return (float) this.fluidStored / (float) this.maxFluid;
	}

	@Override
	protected void onActiveChange(final boolean active)
	{
		this.world.causeLightUpdate(this.x, this.y);
	}

	public float getGeneratorFullness()
	{
		if (this.gasStored == 0)
		{
			return 0;
		}
		return (float) this.gasStored / (float) this.maxGas;
	}

	@Override
	public void save(final DataSet set, final boolean forSync)
	{
		super.save(set, forSync);
		if (!forSync)
		{
			this.inventory.save(set);
		}
		set.addBoolean("shouldSync", this.shouldSync);
		set.addString("gasType", this.gasType);
		set.addInt("gasStored", this.gasStored);
		set.addInt("maxGas", this.maxGas);
		set.addString(Fluid.TYPE_KEY, this.fluidType);
		set.addInt(Fluid.KEY, this.fluidStored);
		set.addInt(Fluid.MAX_KEY, this.maxFluid);
	}

	@Override
	public void load(final DataSet set, final boolean forSync)
	{
		super.load(set, forSync);
		if (!forSync)
		{
			this.inventory.load(set);
		}
		this.shouldSync = set.getBoolean("shouldSync");
		this.gasType = set.getString("gasType");
		this.gasStored = set.getInt("gasStored");
		this.maxGas = set.getInt("maxGas");
		this.fluidType = set.getString(Fluid.TYPE_KEY);
		this.fluidStored = set.getInt(Fluid.KEY);
		this.maxFluid = set.getInt(Fluid.MAX_KEY);
	}

	@Override
	public Inventory getInventory()
	{
		return this.inventory;
	}

	@Override
	public List<Integer> getInputSlots(ItemInstance input, Direction dir)
	{
		return Arrays.asList(0);
	}

	@Override
	public List<Integer> getOutputSlots(Direction dir)
	{
		return null;
	}

	@Override
	public int getCurrentGas()
	{
		return this.gasStored;
	}

	@Override
	public int getMaxGas()
	{
		return this.maxGas;
	}

	@Override
	public String getGasType()
	{
		return this.gasType;
	}

	@Override
	public boolean removeGas(int amount)
	{
		if (this.gasStored >= amount)
		{
			if (RockBottomAPI.getNet().isClient() == false)
			{
				this.gasStored -= amount;

				if (this.gasStored == 0)
				{
					this.gasType = Gas.VACCUM.getName();
				}
				this.shouldSync = true;
			}
			return true;
		}
		return false;
	}

	@Override
	public int getCurrentFluid()
	{
		return this.fluidStored;
	}

	@Override
	public int getMaxFluid()
	{
		return this.maxFluid;
	}

	@Override
	public String getFluidType()
	{
		return this.fluidType;
	}

	@Override
	public boolean addFluid(int amount, String type)
	{
		if (this.fluidStored + amount <= this.maxFluid)
		{
			if (this.fluidType == null || type.equals(this.fluidType) || this.fluidType.equals(Fluid.EMPTY.getName()))
			{
				this.fluidType = type;
				this.fluidStored += amount;
				this.shouldSync = true;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean setFluidType(String type)
	{
		if (this.fluidType == null || this.fluidType.equals(Fluid.EMPTY.getName()) || this.fluidStored == 0)
		{
			this.fluidType = type;
			return true;
		}
		return false;
	}

}
